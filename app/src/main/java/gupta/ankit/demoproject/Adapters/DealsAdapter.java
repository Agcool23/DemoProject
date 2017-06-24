package gupta.ankit.demoproject.Adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gupta.ankit.demoproject.Model.DealsData;
import gupta.ankit.demoproject.R;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.Holder>{
    private static final String TAG = DealsAdapter.class.getSimpleName();
    private final DealClickListener mListener;
    private List<DealsData> dealsDataList;

    public DealsAdapter(DealClickListener listener) {
        dealsDataList = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_card_layout, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        DealsData dealsData = dealsDataList.get(position);

        holder.mTitle.setText(dealsData.getTitle());
        if (dealsData.getDescription() == null) {
            holder.mDescription.setText("No Description Found");
        }else{
            if (Build.VERSION.SDK_INT < 24) {
                holder.mDescription.setText(Html.fromHtml(dealsData.getDescription()));
            } else {
                holder.mDescription.setText(Html.fromHtml(dealsData.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            }
        }

        if (dealsData.isFromDatabase()) {
        holder.mPhoto.setImageBitmap(dealsData.getPicture());
    } else {
            if (dealsData.getImage().isEmpty() && dealsData.getImage().length() < 1) {
                Picasso.with(holder.itemView.getContext()).load(R.mipmap.ic_launcher).into(holder.mPhoto);
            } else {
                Picasso.with(holder.itemView.getContext()).load(dealsData.getImage()).into(holder.mPhoto);
            }
        }
}

    @Override
    public int getItemCount() {
        return dealsDataList.size();
    }

    public void addDeals(DealsData dealsData) {
        dealsDataList.add(dealsData);
        notifyDataSetChanged();
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
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto;
        private TextView mTitle, mDescription;

        public Holder(View itemView) {
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
    public interface DealClickListener {

        void onClick(int position);
    }
}
