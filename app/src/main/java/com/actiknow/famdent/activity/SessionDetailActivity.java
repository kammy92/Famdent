package com.actiknow.famdent.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.SessionDetail;
import com.actiknow.famdent.model.SessionSpeaker;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by actiknow on 4/28/17.
 */

public class SessionDetailActivity extends AppCompatActivity {
    TextView tvDate;
    TextView tvTime;
    TextView tvLocation;
    TextView tvCategory;
    LinearLayout llTopics;
    SessionDetail sessionDetail;
    ImageView ivFavourite;
    ImageView ivBack;
    CoordinatorLayout clMain;

    RelativeLayout rlMain;
    ImageView ivSpeakerImage;
    ProgressBar progressBar;
    TextView tvSpeakerName;
    TextView tvSessionTitle;
    //    ExpandableTextView tvSpeakerQualification;


    int session_id;


    //    ArrayList<EventSpeaker> programmeSpeakerList = new ArrayList<> ();
    TextView tvAddFavourite;
    ProgressDialog progressDialog;

    DatabaseHandler db;

    //    private WrappingViewPager viewPager;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_session_detail);
        getExtras ();
        initView ();
        initData ();
        initListener ();

        getOfflineSessionDetails (session_id);
//        getSessionDetailFromServer (session_id);
    }

    private void initListener () {
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        ivFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (sessionDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (SessionDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_session)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (SessionDetailActivity.this), SetTypeFace.getTypeface (SessionDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateOfflineFavouriteStatus (false, session_id);
//                                    updateFavouriteStatus (false, session_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (SessionDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_session)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (SessionDetailActivity.this), SetTypeFace.getTypeface (SessionDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateOfflineFavouriteStatus (true, session_id);
//                                    updateFavouriteStatus (true, session_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

        tvAddFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (sessionDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (SessionDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_session)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (SessionDetailActivity.this), SetTypeFace.getTypeface (SessionDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateOfflineFavouriteStatus (false, session_id);
//                                    updateFavouriteStatus (false, session_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (SessionDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_session)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (SessionDetailActivity.this), SetTypeFace.getTypeface (SessionDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateOfflineFavouriteStatus (true, session_id);
//                                    updateFavouriteStatus (true, session_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });
    }

    private void initView () {
        rlMain = (RelativeLayout) findViewById (R.id.rlMain);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        llTopics = (LinearLayout) findViewById (R.id.llTopics);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvTime = (TextView) findViewById (R.id.tvTime);
        tvLocation = (TextView) findViewById (R.id.tvLocation);
        tvCategory = (TextView) findViewById (R.id.tvCategory);
        tvAddFavourite = (TextView) findViewById (R.id.tvAddFavourite);
        ivSpeakerImage = (ImageView) findViewById (R.id.ivSpeakerImage);
        progressBar = (ProgressBar) findViewById (R.id.progressBar);
        tvSpeakerName = (TextView) findViewById (R.id.tvSpeakerName);
        tvSessionTitle = (TextView) findViewById (R.id.tvSessionTitle);
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());

        progressDialog = new ProgressDialog (this);
        Utils.setTypefaceToAllViews (this, tvSpeakerName);
    }

    private void getExtras () {
        Intent intent = getIntent ();
        session_id = intent.getIntExtra (AppConfigTags.SESSION_ID, 0);
    }

    private void getOfflineSessionDetails (int session_id) {
        sessionDetail = db.getSessionDetail (session_id);

        for (int i = 0; i < sessionDetail.getTopicList ().size (); i++) {
            ArrayList<String> topicListTemp = sessionDetail.getTopicList ();
            TextView tv = new TextView (SessionDetailActivity.this);
            tv.setText ("\u25B8 " + topicListTemp.get (i));
            tv.setTextSize (16);
            tv.setTypeface (SetTypeFace.getTypeface (SessionDetailActivity.this, Constants.font_name));
            tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
            llTopics.addView (tv);
        }

        for (int i = 0; i < sessionDetail.getSessionSpeakerList ().size (); i++) {
            ArrayList<SessionSpeaker> sessionSpeakerList = sessionDetail.getSessionSpeakerList ();
            SessionSpeaker sessionSpeaker = sessionSpeakerList.get (i);
            tvSpeakerName.setText ("by " + sessionSpeaker.getName ());
            Glide.with (SessionDetailActivity.this)
                    .load (sessionSpeaker.getImage ())
                    .listener (new RequestListener<String, GlideDrawable> () {
                        @Override
                        public boolean onException (Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility (View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady (GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility (View.GONE);
                            return false;
                        }
                    })
                    .into (ivSpeakerImage);
        }


        tvSessionTitle.setText (sessionDetail.getTitle ());
        tvDate.setText ("Date: " + Utils.convertTimeFormat (sessionDetail.getDate (), "yyyy-MM-dd", "dd/MM/yyyy"));
        tvTime.setText ("Time: " + Utils.convertTimeFormat (sessionDetail.getTime (), "HH:mm", "hh:mm a"));
        tvLocation.setText ("Location: " + sessionDetail.getLocation ());
        tvCategory.setText ("Category: " + sessionDetail.getCategory ());

        if (sessionDetail.isFavourite ()) {
            ivFavourite.setImageResource (R.drawable.ic_star);
            tvAddFavourite.setVisibility (View.GONE);
        } else {
            ivFavourite.setImageResource (R.drawable.ic_star_border);
            tvAddFavourite.setVisibility (View.VISIBLE);
        }


        rlMain.setVisibility (View.VISIBLE);
    }

    private void getSessionDetailFromServer (int session_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_SESSION_LIST + "/" + session_id, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_SESSION_LIST + "/" + session_id,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        sessionDetail = new SessionDetail (
                                                jsonObj.getInt (AppConfigTags.SESSION_DETAILS_ID),
                                                jsonObj.getBoolean (AppConfigTags.SESSION_DETAILS_FAVOURITE),
                                                jsonObj.getString (AppConfigTags.SESSION_DETAILS_TITLE),
                                                jsonObj.getString (AppConfigTags.SESSION_DETAILS_DATE),
                                                jsonObj.getString (AppConfigTags.SESSION_DETAILS_TIME),
                                                jsonObj.getString (AppConfigTags.SESSION_DETAILS_LOCATION),
                                                jsonObj.getString (AppConfigTags.SESSION_DETAILS_CATEGORY)
                                        );

                                        JSONArray jsonArraySpeakers = jsonObj.getJSONArray (AppConfigTags.SESSION_DETAILS_SPEAKERS);
                                        ArrayList<SessionSpeaker> sessionSpeakerList = new ArrayList<> ();
                                        for (int i = 0; i < jsonArraySpeakers.length (); i++) {
                                            JSONObject jsonObjectSpeakers = jsonArraySpeakers.getJSONObject (i);
                                            SessionSpeaker sessionSpeaker = new SessionSpeaker (
                                                    jsonObjectSpeakers.getInt (AppConfigTags.EVENT_DETAIL_SPEAKER_ID),
                                                    jsonObjectSpeakers.getString (AppConfigTags.SESSION_DETAILS_SPEAKER_IMAGE),
                                                    jsonObjectSpeakers.getString (AppConfigTags.SESSION_DETAILS_SPEAKER_NAME)
                                            );
                                            sessionSpeakerList.add (sessionSpeaker);
                                        }
                                        sessionDetail.setSessionSpeakerList (sessionSpeakerList);

                                        ArrayList<String> topicList = new ArrayList<> ();
                                        JSONArray jsonArrayTopic = jsonObj.getJSONArray (AppConfigTags.SESSION_DETAILS_TOPICS);
                                        for (int j = 0; j < jsonArrayTopic.length (); j++) {
                                            JSONObject jsonObjectTopic = jsonArrayTopic.getJSONObject (j);
                                            topicList.add (jsonObjectTopic.getString (AppConfigTags.SESSION_DETAILS_TOPIC_TEXT));
                                        }
                                        sessionDetail.setTopicList (topicList);
                                    }


                                    for (int i = 0; i < sessionDetail.getTopicList ().size (); i++) {
                                        ArrayList<String> topicListTemp = sessionDetail.getTopicList ();
                                        TextView tv = new TextView (SessionDetailActivity.this);
                                        tv.setText ("\u25B8 " + topicListTemp.get (i));
                                        tv.setTextSize (16);
                                        tv.setTypeface (SetTypeFace.getTypeface (SessionDetailActivity.this, Constants.font_name));
                                        tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
                                        llTopics.addView (tv);
                                    }

                                    for (int i = 0; i < sessionDetail.getSessionSpeakerList ().size (); i++) {
                                        ArrayList<SessionSpeaker> sessionSpeakerList = sessionDetail.getSessionSpeakerList ();
                                        SessionSpeaker sessionSpeaker = sessionSpeakerList.get (i);
                                        tvSpeakerName.setText ("by " + sessionSpeaker.getName ());
                                        Glide.with (SessionDetailActivity.this)
                                                .load (sessionSpeaker.getImage ())
                                                .listener (new RequestListener<String, GlideDrawable> () {
                                                    @Override
                                                    public boolean onException (Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                        progressBar.setVisibility (View.GONE);
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onResourceReady (GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                        progressBar.setVisibility (View.GONE);
                                                        return false;
                                                    }
                                                })
                                                .into (ivSpeakerImage);
                                    }


                                    tvSessionTitle.setText (sessionDetail.getTitle ());
                                    tvDate.setText ("Date: " + Utils.convertTimeFormat (sessionDetail.getDate (), "yyyy-MM-dd", "dd/MM/yyyy"));
                                    tvTime.setText ("Time: " + Utils.convertTimeFormat (sessionDetail.getTime (), "HH:mm", "hh:mm a"));
                                    tvLocation.setText ("Location: " + sessionDetail.getLocation ());
                                    tvCategory.setText ("Category: " + sessionDetail.getCategory ());

                                    if (sessionDetail.isFavourite ()) {
                                        ivFavourite.setImageResource (R.drawable.ic_star);
                                        tvAddFavourite.setVisibility (View.GONE);
                                    } else {
                                        ivFavourite.setImageResource (R.drawable.ic_star_border);
                                        tvAddFavourite.setVisibility (View.VISIBLE);
                                    }

                                    rlMain.setVisibility (View.VISIBLE);
//                                    setUpViewPager (viewPager2);

                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    e.printStackTrace ();
                                }
                            } else {
                                progressDialog.dismiss ();
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (SessionDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
            progressDialog.dismiss ();
        }
    }

    private void updateOfflineFavouriteStatus (final boolean add_favourite, final int session_id) {
        if (add_favourite) {
            db.addSessionToFavourite (session_id);
            sessionDetail.setFavourite (true);
            ivFavourite.setImageResource (R.drawable.ic_star);
            tvAddFavourite.setVisibility (View.GONE);
            Utils.showSnackBar (SessionDetailActivity.this, clMain, "Session added to favourites", Snackbar.LENGTH_LONG, null, null);
        } else {
            db.removeSessionFromFavourite (session_id);
            sessionDetail.setFavourite (false);
            ivFavourite.setImageResource (R.drawable.ic_star_border);
            tvAddFavourite.setVisibility (View.VISIBLE);
            Utils.showSnackBar (SessionDetailActivity.this, clMain, "Session removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    updateOfflineFavouriteStatus (true, session_id);
                }
            });
        }
    }

    private void updateFavouriteStatus (final boolean add_favourite, final int session_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            String URL;
            if (add_favourite) {
                URL = AppConfigURL.URL_SESSION_FAVOURITE + "/add";
            } else {
                URL = AppConfigURL.URL_SESSION_FAVOURITE + "/remove";
            }
            Utils.showLog (Log.INFO, AppConfigTags.URL, URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        if (add_favourite) {
                                            sessionDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            Utils.showSnackBar (SessionDetailActivity.this, clMain, "Session added to favourites", Snackbar.LENGTH_LONG, null, null);
                                        } else {
                                            sessionDetail.setFavourite (false);
                                            ivFavourite.setImageResource (R.drawable.ic_star_border);
                                            tvAddFavourite.setVisibility (View.VISIBLE);
                                            Utils.showSnackBar (SessionDetailActivity.this, clMain, "Session removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                                @Override
                                                public void onClick (View v) {
                                                    updateFavouriteStatus (true, session_id);
                                                }
                                            });
                                        }
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    e.printStackTrace ();
                                }
                            } else {
                                progressDialog.dismiss ();
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {

                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.SESSION_ID, String.valueOf (session_id));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (SessionDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
            progressDialog.dismiss ();
        }
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
