package com.actiknow.famdent.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.fragment.ProgrammeSpeakerFragment;
import com.actiknow.famdent.model.ProgrammeDetail;
import com.actiknow.famdent.model.ProgrammeSpeaker;
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
import java.util.List;
import java.util.Map;

/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeDetailActivity extends AppCompatActivity {
    TextView tvDate;
    TextView tvTime;
    TextView tvDuration;
    TextView tvCost;
    LinearLayout llTopics;
    ProgrammeDetail programmeDetail;
    ViewPagerAdapter adapter;
    ImageView ivFavourite;
    ImageView ivBack;
    CoordinatorLayout clMain;
    RelativeLayout rlMain;

    ImageView ivSpeakerImage;
    ProgressBar progressBar;
    TextView tvSpeakerName;
    //    ExpandableTextView tvSpeakerQualification;
    TextView tvSpeakerQualification;

    int event_id;


    //    ArrayList<ProgrammeSpeaker> programmeSpeakerList = new ArrayList<> ();
    TextView tvAddFavourite;
    ProgressDialog progressDialog;

    //    private WrappingViewPager viewPager;

    private ViewPager viewPager2;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageSelected (int position) {
            addBottomDots (position);
        }

        @Override
        public void onPageScrolled (int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged (int arg0) {
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_programme_detail);
        getExtras ();
        initView ();
        initData ();
        initListener ();

        getEventDetailFromServer (event_id);
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
                if (programmeDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_programme)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (false, event_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_programme)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (true, event_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

        tvAddFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (programmeDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_programme)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (false, event_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_programme)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (true, event_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

        tvSpeakerQualification.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();
                ProgrammeSpeaker programmeSpeaker = programmeSpeakerList.get (0);
                MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                        .title (programmeSpeaker.getName ())
                        .content (programmeSpeaker.getQualification ())
                        .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                        .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                        .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                        .canceledOnTouchOutside (false)
                        .cancelable (false)
                        .positiveText (R.string.dialog_action_ok)
                        .build ();
                dialog.show ();
            }
        });
    }

    private void initView () {
        rlMain = (RelativeLayout) findViewById (R.id.rlMain);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        dotsLayout = (LinearLayout) findViewById (R.id.layoutDots);
//        viewPager = (WrappingViewPager) findViewById (R.id.viewpager);
        viewPager2 = (ViewPager) findViewById (R.id.viewpager);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        llTopics = (LinearLayout) findViewById (R.id.llTopics);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvTime = (TextView) findViewById (R.id.tvTime);
        tvDuration = (TextView) findViewById (R.id.tvDuration);
        tvCost = (TextView) findViewById (R.id.tvCost);
        tvAddFavourite = (TextView) findViewById (R.id.tvAddFavourite);
        ivSpeakerImage = (ImageView) findViewById (R.id.ivSpeakerImage);
        progressBar = (ProgressBar) findViewById (R.id.progressBar);
        tvSpeakerName = (TextView) findViewById (R.id.tvSpeakerName);
        //tvSpeakerQualification = (ExpandableTextView) findViewById (R.id.tvSpeakerQualification);
        tvSpeakerQualification = (TextView) findViewById (R.id.tvSpeakerQualification);
    }

    private void initData () {
        progressDialog = new ProgressDialog (this);
        Utils.setTypefaceToAllViews (this, tvCost);
    }

    private void getExtras () {
        Intent intent = getIntent ();
        event_id = intent.getIntExtra (AppConfigTags.EVENT_ID, 0);
    }

    private void getEventDetailFromServer (int event_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EVENT_DETAIL + "/" + event_id, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EVENT_DETAIL + "/" + event_id,
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
                                        programmeDetail = new ProgrammeDetail (
                                                jsonObj.getInt (AppConfigTags.EVENT_DETAIL_ID),
                                                jsonObj.getBoolean (AppConfigTags.EVENT_DETAIL_FAVOURITE),
                                                jsonObj.getString (AppConfigTags.EVENT_DETAIL_NAME),
                                                jsonObj.getString (AppConfigTags.EVENT_DETAIL_DATE),
                                                jsonObj.getString (AppConfigTags.EVENT_DETAIL_TIME),
                                                jsonObj.getString (AppConfigTags.EVENT_DETAIL_DURATION),
                                                jsonObj.getString (AppConfigTags.EVENT_DETAIL_FEES));

                                        JSONArray jsonArraySpeakers = jsonObj.getJSONArray (AppConfigTags.EVENT_DETAIL_SPEAKERS);
                                        ArrayList<ProgrammeSpeaker> programmeSpeakerList = new ArrayList<> ();
                                        for (int i = 0; i < jsonArraySpeakers.length (); i++) {
                                            JSONObject jsonObjectSpeakers = jsonArraySpeakers.getJSONObject (i);
                                            ProgrammeSpeaker programmeSpeaker = new ProgrammeSpeaker (
                                                    jsonObjectSpeakers.getInt (AppConfigTags.EVENT_DETAIL_SPEAKER_ID),
                                                    jsonObjectSpeakers.getString (AppConfigTags.EVENT_DETAIL_SPEAKER_IMAGE),
                                                    jsonObjectSpeakers.getString (AppConfigTags.EVENT_DETAIL_SPEAKER_NAME),
                                                    jsonObjectSpeakers.getString (AppConfigTags.EVENT_DETAIL_SPEAKER_QUALIFICATION),
                                                    jsonObjectSpeakers.getString (AppConfigTags.EVENT_DETAIL_SPEAKER_EXPERIENCE)
                                            );
                                            programmeSpeakerList.add (programmeSpeaker);
                                        }
                                        programmeDetail.setProgrammeSpeakerList (programmeSpeakerList);

                                        ArrayList<String> topicList = new ArrayList<> ();
                                        JSONArray jsonArrayTopic = jsonObj.getJSONArray (AppConfigTags.EVENT_DETAIL_TOPICS);
                                        for (int j = 0; j < jsonArrayTopic.length (); j++) {
                                            JSONObject jsonObjectTopic = jsonArrayTopic.getJSONObject (j);
                                            topicList.add (jsonObjectTopic.getString (AppConfigTags.EVENT_DETAIL_TOPIC_TEXT));
                                        }
                                        programmeDetail.setTopicList (topicList);
                                    }


                                    for (int i = 0; i < programmeDetail.getTopicList ().size (); i++) {
                                        ArrayList<String> topicListTemp = programmeDetail.getTopicList ();
                                        TextView tv = new TextView (ProgrammeDetailActivity.this);
                                        tv.setText ("\u25B8 " + topicListTemp.get (i));
                                        tv.setTextSize (16);
                                        tv.setTypeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this, Constants.font_name));
                                        tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
                                        llTopics.addView (tv);
                                    }

                                    for (int i = 0; i < programmeDetail.getProgrammeSpeakerList ().size (); i++) {
                                        ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();
                                        ProgrammeSpeaker programmeSpeaker = programmeSpeakerList.get (i);
                                        tvSpeakerName.setText (programmeSpeaker.getName ());
                                        tvSpeakerQualification.setText (programmeSpeaker.getQualification ());


                                        Glide.with (ProgrammeDetailActivity.this)
                                                .load (programmeSpeaker.getImage ())
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

                                    tvDate.setText ("Date: " + Utils.convertTimeFormat (programmeDetail.getDate (), "yyyy-MM-dd", "dd/MM/yyyy"));
                                    tvTime.setText ("Time: " + Utils.convertTimeFormat (programmeDetail.getTime (), "HH:mm", "hh:mm a"));
                                    tvDuration.setText ("Duration: " + programmeDetail.getDuration ());
                                    tvCost.setText (programmeDetail.getFees ());

                                    if (programmeDetail.isFavourite ()) {
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
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ProgrammeDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
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

    private void updateFavouriteStatus (final boolean add_favourite, final int event_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            String URL;
            if (add_favourite) {
                URL = AppConfigURL.URL_EVENT_FAVOURITE + "/add";
            } else {
                URL = AppConfigURL.URL_EVENT_FAVOURITE + "/remove";
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
                                            programmeDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            Utils.showSnackBar (ProgrammeDetailActivity.this, clMain, "Exhibitor added to favourites", Snackbar.LENGTH_LONG, null, null);
                                        } else {
                                            programmeDetail.setFavourite (false);
                                            ivFavourite.setImageResource (R.drawable.ic_star_border);
                                            tvAddFavourite.setVisibility (View.VISIBLE);
                                            Utils.showSnackBar (ProgrammeDetailActivity.this, clMain, "Exhibitor removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                                @Override
                                                public void onClick (View v) {
                                                    updateFavouriteStatus (true, event_id);
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
                    params.put (AppConfigTags.EVENT_ID, String.valueOf (event_id));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ProgrammeDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
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

    private void setUpViewPager (ViewPager viewPager) {
        ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();
        adapter = new ViewPagerAdapter (getSupportFragmentManager ());
        for (int i = 0; i < programmeSpeakerList.size (); i++) {
            ProgrammeSpeaker programmeSpeaker = programmeSpeakerList.get (i);
            Log.e ("kammy", " " + programmeSpeaker.getId ());
            Log.e ("kammy", " " + programmeSpeaker.getName ());
            adapter.addFragment (new ProgrammeSpeakerFragment ());
        }

        addBottomDots (0);
        viewPager.addOnPageChangeListener (viewPagerPageChangeListener);
        viewPager.setAdapter (adapter);
    }

    private void addBottomDots (int currentPage) {
        ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();

        dots = new TextView[programmeSpeakerList.size ()];

        dotsLayout.removeAllViews ();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView (this);
            dots[i].setText (Html.fromHtml ("&#8226;"));
            dots[i].setTextSize (35);
            dots[i].setTextColor (getResources ().getColor (R.color.text_color_grey_dark));
            dotsLayout.addView (dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor (getResources ().getColor (R.color.colorPrimary));
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<> ();

        public ViewPagerAdapter (FragmentManager manager) {
            super (manager);
        }

        @Override
        public Fragment getItem (int position) {
            return ProgrammeSpeakerFragment.newInstance (position, programmeDetail.getProgrammeSpeakerList ());
        }

        @Override
        public int getCount () {
            return mFragmentList.size ();
        }

        public void addFragment (Fragment fragment) {
            mFragmentList.add (fragment);
        }
    }

}
