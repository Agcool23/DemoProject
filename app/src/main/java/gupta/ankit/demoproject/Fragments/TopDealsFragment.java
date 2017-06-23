package gupta.ankit.demoproject.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import gupta.ankit.demoproject.Controller.DealsService;
import gupta.ankit.demoproject.Controller.RestManager;
import gupta.ankit.demoproject.Model.DealsData;
import gupta.ankit.demoproject.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopDealsFragment extends Fragment {


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
        header = new HashMap<>();
        header.put("accept","application/json");
        header.put("accept","text/javascript");
        header.put("X-Desidime-Client","0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DealsService dealsService = RestManager.getRetrofit().create(DealsService.class);
        Call<DealsData.Response> call = dealsService.getDeals("top","true",10,1,header);
        call.enqueue(new Callback<DealsData.Response>() {
            @Override
            public void onResponse(Call<DealsData.Response> call, Response<DealsData.Response> response) {
                if (response.isSuccessful()){
                    Log.e("Response",response.body().getDeals().getData()+"");
                }
            }

            @Override
            public void onFailure(Call<DealsData.Response> call, Throwable t) {

                Log.e("error",t.toString());
            }
        });
    }
}
