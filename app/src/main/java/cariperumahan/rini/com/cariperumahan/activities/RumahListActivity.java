package cariperumahan.rini.com.cariperumahan.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import cariperumahan.rini.com.cariperumahan.R;
import cariperumahan.rini.com.cariperumahan.adapter.RumahAdapter;
import cariperumahan.rini.com.cariperumahan.interfaces.PaginationAdapterCallback;
import cariperumahan.rini.com.cariperumahan.interfaces.PaginationScrollListenerLinear;
import cariperumahan.rini.com.cariperumahan.interfaces.RecyClerviewClick;
import cariperumahan.rini.com.cariperumahan.model.Record;
import cariperumahan.rini.com.cariperumahan.model.Rumah;
import cariperumahan.rini.com.cariperumahan.utils.Globalhelper;

import static cariperumahan.rini.com.cariperumahan.utils.GlobalVars.BASE_IP;

public class RumahListActivity extends AppCompatActivity implements PaginationAdapterCallback {

    private RumahAdapter adapter;

    private Gson gson;

    LinearLayoutManager linearLayoutManager;

    private RecyclerView rv;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    private LinearLayout parentLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoData;
    private LinearLayout btnAdd;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    private static final String TAG =RumahListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah_list);

        gson = new Gson();

        rv = findViewById(R.id.rv);
        progressBar = findViewById(R.id.progress_utama);
        errorLayout = findViewById(R.id.error_layout);
        btnRetry = findViewById(R.id.error_btn_retry);
        txtError = findViewById(R.id.error_txt_cause);
        parentLayout = findViewById(R.id.parentLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tvNoData = findViewById(R.id.tv_No_Data);
        btnAdd = findViewById(R.id.btnAdd);

//        if (Globalhelper.getRole().equals("sopir")) {
//            btnAdd.setVisibility(View.GONE);
//        } else {
//            btnAdd.setVisibility(View.VISIBLE);
//        }

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        adapter = new RumahAdapter(this, new RecyClerviewClick() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstPage();

            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadFirstPage();
            }
        });

        rv.addOnScrollListener(new PaginationScrollListenerLinear
                (linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage = currentPage + 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount(){
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage(){
                return isLastPage;
            }

            @Override
            public boolean isLoading(){
                return isLoading;
            }

        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFirstPage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToRegister();
            }
        });


    }

    public void intentToRumahData(Rumah rumah){
        //Intent intent = new Intent(getApplicationContext(), InsertRumahActivity.class);
        //intent.putExtra( "data_rumah",rumah);
       // startActivity(intent);
        //
        // finish();
    }

    private void intentToRegister() {
    /*    Intent intent = new Intent(getApplicationContext(), InsertRumahActivity.class);
        startActivity(intent);
        finish();*/

    }

    private void loadNextPage() {

        hideErrorView();

        String url = BASE_IP+"rumah/readAll.php?page=";

        AndroidNetworking.get(url+currentPage)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //melakukan respon apapun
                        hideErrorView();

                        List<Rumah> results = new ArrayList<>();
                        try {
                            String message = response.getString("message");
                            String records = response.getString("records");
                            String paging = response.getString("paging");
                            JSONObject pageObj = new JSONObject("paging");

                            TOTAL_PAGES = pageObj.getInt("total pages");
                            JSONArray dataArr = new JSONArray(records);

                            if (dataArr.length()>0){
                                for (int i = 0; i < dataArr.length(); i++) {
                                    Rumah fromJson = gson.fromJson(dataArr
                                            .getJSONObject(i).toString(), Rumah.class);
                                    results.add(fromJson);
                                }
                            }

                            if (results.isEmpty()){
                                tvNoData.setVisibility(View.VISIBLE);
                                rv.setVisibility(View.GONE);
                            }else{
                                tvNoData.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.removeLoadingFooter();
                        isLoading = false;

                        adapter.addAll(results);

                        if (currentPage < TOTAL_PAGES) {
                            adapter.addLoadingFooter();
                        }
                        else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        //handle error
                        showErrorView(error);
                    }
                });

    }

    private void loadFirstPage() {

        if (progressBar!=null && progressBar.getVisibility()==View.GONE)
            progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        hideErrorView();

        if (adapter!=null && !adapter.isEmpty())
            adapter.clearAll();

        currentPage=1;
        isLastPage=false;
        String url = BASE_IP+"rumah/readAll.php?page=";

        Log.e(TAG, "url "+ url.toString());

        AndroidNetworking.get(url+PAGE_START)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideErrorView();



                        List<Rumah> results =new ArrayList<>();
                        try {

                            Log.e(TAG, "respon "+response.toString(1));

                            if (response.getString("message").equals("success")){
                                String records = response.getString("records");
                                String paging = response.getString("paging");
                                JSONObject pageObj = new JSONObject(paging);

                                TOTAL_PAGES = pageObj.getInt("total_pages");

                                JSONArray dataArr = new JSONArray(records);

                                Log.e("dataArr", dataArr.toString(1));

                                if (dataArr.length()>0){
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Rumah fromJson = gson.fromJson(dataArr.getJSONObject(i).toString(), Rumah.class);
                                        results.add(fromJson);
                                    }

                                }

                                if (results.isEmpty()){
                                    tvNoData.setVisibility(View.VISIBLE);
                                    rv.setVisibility(View.GONE);
                                }else{
                                    tvNoData.setVisibility(View.GONE);
                                    rv.setVisibility(View.VISIBLE);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }

                        //List<Po> results = fetchResults(response);
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.addAll(results);


                        if (currentPage <= TOTAL_PAGES) {
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        //handle error
                        showErrorView(error);
                    }
                });



    }

    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }

    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void retryPageLoad() {
        loadNextPage();

    }
    private void intentToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        intentToMain();

    }
}
