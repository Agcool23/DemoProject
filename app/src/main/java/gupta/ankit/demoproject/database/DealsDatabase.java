package gupta.ankit.demoproject.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gupta.ankit.demoproject.Controller.DealsFetchListener;
import gupta.ankit.demoproject.Helper.Constants;
import gupta.ankit.demoproject.Helper.Utils;
import gupta.ankit.demoproject.Model.DealsData;
import gupta.ankit.demoproject.R;

public class DealsDatabase extends SQLiteOpenHelper{

    Context context;
    private static final String TAG = DealsData.class.getSimpleName();

    public DealsDatabase(Context context){
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        }catch (SQLException ex){
            Log.e(TAG,ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
    }
    public void addDeal(DealsData dealsData){
        Log.e(TAG, "Values Got " + dealsData.getTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (dealsData.getId()==0 && dealsData.getId().toString()==null)
        {
            values.put(Constants.DATABASE.PRODUCT_ID, 0);
        }else{
            values.put(Constants.DATABASE.PRODUCT_ID, dealsData.getId());
        }
        if (dealsData.getTitle()== null)
        {
            values.put(Constants.DATABASE.TITLE,"No Title Found");
        }else {
            values.put(Constants.DATABASE.TITLE,dealsData.getTitle());
        }
        if (dealsData.getDescription()==null)
        {
            values.put(Constants.DATABASE.DESCRIPTION,"No Description Found");
        }else {
            if (Build.VERSION.SDK_INT < 24) {
                values.put(Constants.DATABASE.DESCRIPTION,Html.fromHtml(dealsData.getDescription()).toString());
            } else {
                values.put(Constants.DATABASE.DESCRIPTION,Html.fromHtml(dealsData.getDescription(), Html.FROM_HTML_MODE_COMPACT).toString());
            }
            values.put(Constants.DATABASE.DESCRIPTION,dealsData.getDescription());
        }

        if (dealsData.getImage()==null)
        {
            values.put(Constants.DATABASE.IMAGE_URL,"N");
        }else {
            values.put(Constants.DATABASE.IMAGE_URL,dealsData.getImage());
        }
        if (dealsData.getPicture()==null && dealsData.getPicture().toString() ==null)
        {
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher);
            values.put(Constants.DATABASE.PHOTO, Utils.getPictureByteOfArray(icon));
        }else {
            values.put(Constants.DATABASE.PHOTO, Utils.getPictureByteOfArray(dealsData.getPicture()));
        }
        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
        db.close();
    }
    public void fetchDeals(DealsFetchListener listener) {
        DealsFetcher fetcher = new DealsFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }
    public class DealsFetcher extends Thread {

        private final DealsFetchListener mListener;
        private final SQLiteDatabase mDb;

        public DealsFetcher(DealsFetchListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_DEALS_QUERY, null);

            final List<DealsData> dealsDataList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        DealsData dealsData = new DealsData();
                        dealsData.setFromDatabase(true);
                        dealsData.setTitle(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.TITLE)));
                        dealsData.setDescription(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.DESCRIPTION)));
                        dealsData.setPicture(Utils.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Constants.DATABASE.PHOTO))));
                        dealsData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRODUCT_ID))));
                        dealsData.setImage(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.IMAGE_URL)));

                        dealsDataList.add(dealsData);
                        publishDeals(dealsData);

                    } while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllDeals(dealsDataList);
                    mListener.onHideDialog();
                }
            });
        }

        public void publishDeals(final DealsData dealsData) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverDeals(dealsData);
                }
            });
        }
    }
}
