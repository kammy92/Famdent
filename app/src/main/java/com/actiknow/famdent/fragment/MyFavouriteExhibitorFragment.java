package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.ExhibitorAdapter;
import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.model.StallDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MyFavouriteExhibitorFragment extends Fragment {
    RecyclerView rvExhibitor;

    SwipeRefreshLayout swipeRefreshLayout;
    List<Exhibitor> exhibitorList = new ArrayList<> ();
    ExhibitorAdapter exhibitorAdapter;
    TextView tvNoResult;
    List<StallDetail> stallDetailList = new ArrayList<> ();



    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_my_favourite_exhibitor, container, false);
        initView (rootView);
        initData ();
        initListener ();
        getExhibitorList ();
        return rootView;
    }


    private void initView(View rootView) {
        rvExhibitor = (RecyclerView) rootView.findViewById (R.id.rvExhibitorList);
        tvNoResult = (TextView) rootView.findViewById (R.id.tvNoResult);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById (R.id.swipeRefreshLayout);
        Utils.setTypefaceToAllViews (getActivity (), rvExhibitor);
    }

    private void initData() {
        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (R.color.colorPrimaryDark));
        swipeRefreshLayout.setRefreshing (true);
        exhibitorAdapter = new ExhibitorAdapter (getActivity (), exhibitorList);
        rvExhibitor.setAdapter (exhibitorAdapter);
        rvExhibitor.setHasFixedSize (true);
        rvExhibitor.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false));
        rvExhibitor.addItemDecoration (new SimpleDividerItemDecoration (getActivity ()));
        rvExhibitor.setItemAnimator (new DefaultItemAnimator ());
        Utils.setTypefaceToAllViews (getActivity (), tvNoResult);
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing (false);
            }
        });

        exhibitorAdapter = new ExhibitorAdapter (getActivity (), exhibitorList);
        rvExhibitor.setAdapter (exhibitorAdapter);
        rvExhibitor.setHasFixedSize (true);
        rvExhibitor.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false));
        rvExhibitor.addItemDecoration (new SimpleDividerItemDecoration (getActivity ()));
        rvExhibitor.setItemAnimator (new DefaultItemAnimator ());
    }

    private void getExhibitorList () {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            tvNoResult.setVisibility (View.GONE);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EXHIBITOR_FAVOURITE_LIST, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EXHIBITOR_FAVOURITE_LIST,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            exhibitorList.clear ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean is_error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! is_error) {
                                        JSONArray jsonArrayExhibitor = jsonObj.getJSONArray (AppConfigTags.EXHIBITOR);
                                        for (int i = 0; i < jsonArrayExhibitor.length (); i++) {
                                            JSONObject jsonObjectExhibitor = jsonArrayExhibitor.getJSONObject (i);
                                            Exhibitor exhibitor = new Exhibitor (
                                                    jsonObjectExhibitor.getInt (AppConfigTags.EXHIBITOR_ID),
                                                    jsonObjectExhibitor.getString (AppConfigTags.EXHIBITOR_LOGO),
                                                    jsonObjectExhibitor.getString (AppConfigTags.EXHIBITOR_NAME));

                                            JSONArray jsonArrayStallDetails = jsonObjectExhibitor.getJSONArray (AppConfigTags.STALL_DETAILS);
                                            exhibitor.clearStallDetailList ();
                                            for (int j = 0; j < jsonArrayStallDetails.length (); j++) {
                                                JSONObject jsonObjectStallDetail = jsonArrayStallDetails.getJSONObject (j);
                                                StallDetail stallDetail = new StallDetail (
                                                        jsonObjectStallDetail.getString (AppConfigTags.STALL_NAME),
                                                        jsonObjectStallDetail.getString (AppConfigTags.HALL_NUMBER),
                                                        jsonObjectStallDetail.getString (AppConfigTags.STALL_NUMBER)
                                                );
                                                exhibitor.setStallDetailInList (stallDetail);
                                            }
                                            exhibitorList.add (i, exhibitor);
                                            exhibitorAdapter.notifyDataSetChanged ();
                                        }
                                        if (jsonArrayExhibitor.length () > 0) {
                                            swipeRefreshLayout.setRefreshing (false);
                                        } else {
                                            swipeRefreshLayout.setRefreshing (false);
                                            tvNoResult.setVisibility (View.VISIBLE);
                                        }
                                    } else {
                                        swipeRefreshLayout.setRefreshing (false);
                                        tvNoResult.setVisibility (View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                    swipeRefreshLayout.setRefreshing (false);
                                    tvNoResult.setVisibility (View.VISIBLE);
                                }
                            } else {
                                swipeRefreshLayout.setRefreshing (false);
                                tvNoResult.setVisibility (View.VISIBLE);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Utils.showLog (Log.ERROR, AppConfigTags.ERROR, new String (response.data), true);

                            }
                            swipeRefreshLayout.setRefreshing (false);
                            tvNoResult.setVisibility (View.VISIBLE);
                        }
                    }) {

                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (getActivity (), visitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest, 5);
        } else {
            swipeRefreshLayout.setRefreshing (false);
            tvNoResult.setVisibility (View.VISIBLE);
        }
    }
}
