package gupta.ankit.demoproject.Controller;


import java.util.List;

import gupta.ankit.demoproject.Model.DealsData;

public interface DealsFetchListener {
    void onDeliverAllDeals(List<DealsData> dealsDataList);

    void onDeliverDeals(DealsData dealsData);

    void onHideDialog();
}
