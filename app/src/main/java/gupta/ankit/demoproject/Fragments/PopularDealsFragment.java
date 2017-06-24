package gupta.ankit.demoproject.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import gupta.ankit.demoproject.Adapters.DealsAdapter;
import gupta.ankit.demoproject.Controller.DealsFetchListener;
import gupta.ankit.demoproject.Controller.DealsService;
import gupta.ankit.demoproject.Controller.RestManager;
import gupta.ankit.demoproject.Helper.PaginationAdapterCallback;
import gupta.ankit.demoproject.Helper.PaginationScrollListener;
import gupta.ankit.demoproject.Helper.Utils;
import gupta.ankit.demoproject.Model.DealsData;
import gupta.ankit.demoproject.R;
import gupta.ankit.demoproject.database.DealsDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularDealsFragment extends Fragment implements DealsAdapter.DealClickListener,DealsFetchListener,PaginationAdapterCallback {

    private RecyclerView mRecyclerView;
    private DealsAdapter mDealsAdapter;
    private DealsDatabase mDatabase;
    private ProgressDialog mDialog;
    LinearLayoutManager linearLayoutManager;
    DealsService dealsService;

    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    Map<String, String> header;
    View view;
    public static final String Fragment_TAG = PopularDealsFragment.class.getSimpleName();

    public static PopularDealsFragment newInstance(){
        PopularDealsFragment popularDealsFragment = new PopularDealsFragment();
        return popularDealsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.popular_deals_fragment, container, false);
        initViews();
        mDatabase = new DealsDatabase(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadDealsFeed();
    }
    private void loadDealsFeed(){
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading Deals...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        mDealsAdapter.reset();
        mDialog.show();

        if (getNetworkAvailability()){
            getFeed();
        }else{
            getFeedFromDatabase();
        }
    }
    private void getFeedFromDatabase() {
        mDatabase.fetchDeals(this);
    }
    private void initViews(){
        header = new HashMap<>();
        header.put("accept","application/json");
        header.put("accept","text/javascript");
        header.put("X-Desidime-Client","0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0");
        errorLayout = (LinearLayout)view.findViewById(R.id.error_layout);
        btnRetry = (Button)view.findViewById(R.id.error_btn_retry);
        txtError = (TextView)view.findViewById(R.id.error_txt_cause);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_popular_deals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mDealsAdapter);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        dealsService = RestManager.getRetrofit().create(DealsService.class);
    }

    @Override
    public void onDeliverAllDeals(List<DealsData> dealsDataList) {

    }

    @Override
    public void onDeliverDeals(DealsData dealsData) {
        mDealsAdapter.addDeal(dealsData);
    }

    @Override
    public void onHideDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onClick(int position) {
        DealsData selectedDeal = mDealsAdapter.getSelectedDeal(position);
        Snackbar.make(view,"Selected Deal Id :  "+String.valueOf(selectedDeal.getId()),Snackbar.LENGTH_LONG);
    }
    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getActivity());
    }
    public void getFeed(){
        hideErrorView();
        callTopRatedMoviesApi().enqueue(new Callback<DealsData.Response>() {
            @Override
            public void onResponse(Call<DealsData.Response> call, Response<DealsData.Response> response) {
                if (response.isSuccessful() && response.body().getDeals().getData().size() > 0){
                    TextView noDataTV = (TextView)view.findViewById(R.id.noDataTVPopular);
                    noDataTV.setVisibility(View.GONE);
                    hideErrorView();
                    List<DealsData> dealsDataList = response.body().getDeals().getData();

                    TOTAL_PAGES = (response.body().getDeals().getTotalCount()/10);
                    Log.e(Fragment_TAG,response.body().getDeals().getTotalCount()+"");
                    for (int i = 0;i< dealsDataList.size();i++){
                        DealsData dealsData = dealsDataList.get(i);
                        PopularDealsFragment.SaveIntoDatabase task = new PopularDealsFragment.SaveIntoDatabase();
                        task.execute(dealsData);
                        mDealsAdapter.addDeal(dealsData);
                    }
                }else if (response.isSuccessful() && response.body().getDeals().getData().size() == 0){
                    TextView noDataTV = (TextView)view.findViewById(R.id.noDataTVPopular);
                    noDataTV.setVisibility(View.VISIBLE);
                }else{
                    int sc = response.code();
                    switch (sc){
                        case 400:
                            Snackbar.make(view,"Error 400 : Bad Request",Snackbar.LENGTH_LONG);
                            break;
                        case 404:
                            Snackbar.make(view,"Error 404 : Not Found",Snackbar.LENGTH_LONG);
                            break;
                        default:
                            Snackbar.make(view,"Error : Generic Error",Snackbar.LENGTH_LONG);
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DealsData.Response> call, Throwable t) {
                mDialog.dismiss();
                showErrorView(t);
            }
        });
    }
    public void loadNextPage(){
        callTopRatedMoviesApi().enqueue(new Callback<DealsData.Response>() {
            @Override
            public void onResponse(Call<DealsData.Response> call, Response<DealsData.Response> response) {
                if (response.isSuccessful()){
                    mDealsAdapter.removeLoadingFooter();
                    isLoading = false;
                    List<DealsData> dealsDataList = response.body().getDeals().getData();

                    for (int i = 0;i< dealsDataList.size();i++){
                        DealsData dealsData = dealsDataList.get(i);
                        PopularDealsFragment.SaveIntoDatabase task = new PopularDealsFragment.SaveIntoDatabase();
                        task.execute(dealsData);
                        mDealsAdapter.addDeal(dealsData);
                    }
                    if (currentPage != TOTAL_PAGES){
                        mDealsAdapter.addLoadingFooter();
                    } else{
                        isLastPage = true;
                    }
                }else{
                    int sc = response.code();
                    switch (sc){
                        case 400:
                            Snackbar.make(view,"Error 400 : Bad Request",Snackbar.LENGTH_LONG);
                            break;
                        case 404:
                            Snackbar.make(view,"Error 404 : Not Found",Snackbar.LENGTH_LONG);
                            break;
                        default:
                            Snackbar.make(view,"Error : Generic Error",Snackbar.LENGTH_LONG);
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DealsData.Response> call, Throwable t) {
                if (mDialog != null){mDialog.dismiss();}
                mDealsAdapter.showRetry(true, fetchErrorMessage(t));
            }
        });
    }

    private Call<DealsData.Response> callTopRatedMoviesApi() {
        return RestManager.getRetrofit().create(DealsService.class).getDeals("popular",
                "true",
                10,
                currentPage,
                header
        );
    }
    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            mDialog.dismiss();
        }
    }
    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            mDialog.dismiss();

            txtError.setText(fetchErrorMessage(throwable));
        }
    }
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!Utils.isNetworkAvailable(getActivity())) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mDealsAdapter = new DealsAdapter(this,PopularDealsFragment.this);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public class SaveIntoDatabase extends AsyncTask<DealsData, Void, Void> {


        private final String TAG = TopDealsFragment.SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(DealsData... params) {

            DealsData dealsData = params[0];

            try {
                InputStream stream = new URL(dealsData.getImage()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                dealsData.setPicture(bitmap);
                mDatabase.addDeal(dealsData);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }
}
