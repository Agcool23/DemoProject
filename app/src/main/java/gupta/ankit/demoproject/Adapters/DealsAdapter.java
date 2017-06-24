package gupta.ankit.demoproject.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gupta.ankit.demoproject.Helper.PaginationAdapterCallback;
import gupta.ankit.demoproject.Model.DealsData;
import gupta.ankit.demoproject.R;

public class DealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = DealsAdapter.class.getSimpleName();
    private final DealClickListener mListener;
    private List<DealsData> dealsDataList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private Context context;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public DealsAdapter(DealClickListener listener,Fragment fragment) {
        this.mCallback = (PaginationAdapterCallback) fragment;
        dealsDataList = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.deal_card_layout, parent, false);
        viewHolder = new DealsVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        DealsData dealsData = dealsDataList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final DealsVH dealsVH = (DealsVH) holder;

                dealsVH.mTitle.setText(dealsData.getTitle());
                if (dealsData.getDescription() == null) {
                    dealsVH.mDescription.setText(R.string.no_description);
                }else{
                    if (Build.VERSION.SDK_INT < 24) {
                        dealsVH.mDescription.setText(Html.fromHtml(dealsData.getDescription()));
                    } else {
                        dealsVH.mDescription.setText(Html.fromHtml(dealsData.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                    }
                }

                if (dealsData.isFromDatabase()) {
                    dealsVH.mPhoto.setImageBitmap(dealsData.getPicture());
                } else {
                    if (dealsData.getImage().isEmpty() && dealsData.getImage().length() < 1) {
                        Picasso.with(dealsVH.itemView.getContext()).load(R.mipmap.ic_launcher).into(dealsVH.mPhoto);
                    } else {
                        Picasso.with(dealsVH.itemView.getContext()).load(dealsData.getImage()).into(dealsVH.mPhoto);
                    }
                }
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }

                break;
        }
}

    @Override
    public int getItemCount() {
        return dealsDataList == null ? 0 : dealsDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dealsDataList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addDeal(DealsData dealsData) {
        dealsDataList.add(dealsData);
        notifyDataSetChanged();
    }
    public void addAll(List<DealsData> dealsDataList) {
        for (DealsData dealsData : dealsDataList) {
            addDeal(dealsData);
        }
    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
        addDeal(new DealsData());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dealsDataList.size() - 1;
        DealsData dealsData = getItem(position);

        if (dealsData != null) {
            dealsDataList.remove(position);
            notifyItemRemoved(position);
        }
    }
    private DealsData getItem(int position) {
        return dealsDataList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(dealsDataList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }
    /**
     * @param position
     * @return
     */

    public DealsData getSelectedDeal(int position) {
        return dealsDataList.get(position);
    }

    public void reset() {
        dealsDataList.clear();
        notifyDataSetChanged();
    }
    private class DealsVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto;
        private TextView mTitle, mDescription;

        DealsVH(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.deal_photo);
            mTitle = (TextView) itemView.findViewById(R.id.deal_title);
            mDescription = (TextView) itemView.findViewById(R.id.deal_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }
    private class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }
    public interface DealClickListener {

        void onClick(int position);
    }
}
