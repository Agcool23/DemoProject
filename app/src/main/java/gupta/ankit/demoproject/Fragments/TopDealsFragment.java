package gupta.ankit.demoproject.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gupta.ankit.demoproject.Adapters.DealsAdapter;
import gupta.ankit.demoproject.Controller.DealsFetchListener;
import gupta.ankit.demoproject.Controller.DealsService;
import gupta.ankit.demoproject.Controller.RestManager;
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
public class TopDealsFragment extends Fragment implements DealsAdapter.DealClickListener,DealsFetchListener{


    private static final String TAG = TopDealsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DealsAdapter mDealsAdapter;
    private DealsDatabase mDatabase;
    private ProgressDialog mDialog;

    View view;
    Map<String, String> header;
    public static final String Fragment_TAG = TopDealsFragment.class.getSimpleName();

    public static TopDealsFragment newInstance(){
        TopDealsFragment topDealsFragment = new TopDealsFragment();
        return topDealsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.top_deals_fragment, container, false);
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_top_deals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mDealsAdapter = new DealsAdapter(this);
        mRecyclerView.setAdapter(mDealsAdapter);
    }

    @Override
    public void onDeliverAllDeals(List<DealsData> dealsDataList) {

    }

    @Override
    public void onDeliverDeals(DealsData dealsData) {
        mDealsAdapter.addDeals(dealsData);
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
        DealsService dealsService = RestManager.getRetrofit().create(DealsService.class);
        Call<DealsData.Response> call = dealsService.getDeals("top","true",10,1,header);
        call.enqueue(new Callback<DealsData.Response>() {
            @Override
            public void onResponse(Call<DealsData.Response> call, Response<DealsData.Response> response) {
                if (response.isSuccessful()){
                    List<DealsData> dealsDataList = response.body().getDeals().getData();

                    for (int i = 0;i< dealsDataList.size();i++){
                        DealsData dealsData = dealsDataList.get(i);
                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(dealsData);
                        mDealsAdapter.addDeals(dealsData);
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
                mDialog.dismiss();
                Snackbar.make(view,t.getLocalizedMessage(),Snackbar.LENGTH_LONG);
            }
        });
    }
    public class SaveIntoDatabase extends AsyncTask<DealsData, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

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
