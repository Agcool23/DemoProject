package gupta.ankit.demoproject.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gupta.ankit.demoproject.Model.DealsData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface DealsService {

    @GET("deals.json")
    Call<DealsData.Response> getDeals(@Query("type") String type,
                                      @Query("deal_view") String dealView,
                                      @Query("per_page") int noOfPages,
                                      @Query("page") int pageNo,
                                      @HeaderMap Map<String,String> headers);
}
