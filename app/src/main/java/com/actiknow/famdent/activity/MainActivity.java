package com.actiknow.famdent.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.HomeServiceAdapter;
import com.actiknow.famdent.model.HomeService;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.actiknow.famdent.utils.qr_code.QRContents;
import com.actiknow.famdent.utils.qr_code.QREncoder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bugsnag.android.Bugsnag;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.actiknow.famdent.activity.LoginActivity.PERMISSION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {
    VisitorDetailsPref visitorDetailsPref;
    int version_code;
    CoordinatorLayout clMain;
    ImageView ivVisitorCard;
    ImageView ivIndiaSupplyLogo;

    ProgressDialog progressDialog;
    ProgressBar progressBar;

    RecyclerView rvHomeServiceList;
    List<HomeService> homeServices = new ArrayList<> ();
    HomeServiceAdapter homeServiceAdapter;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
//        checkPermissions ();
        initListener ();
        isLogin ();
//        initApplication ();

        if (! visitorDetailsPref.getBooleanPref (this, VisitorDetailsPref.LOGGED_IN_SESSION)) {
//            checkVersionUpdate ();
        }
    }

    private void initView () {
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        rvHomeServiceList = (RecyclerView) findViewById (R.id.rvHomeServiceList);
        ivVisitorCard = (ImageView) findViewById (R.id.ivVisitorCard);
        ivIndiaSupplyLogo = (ImageView) findViewById (R.id.ivIndiaSupplyLogo);

    }

    private void initData () {
        Bugsnag.init (this);
        visitorDetailsPref = VisitorDetailsPref.getInstance ();

        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_ID));
        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_NAME));
        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_EMAIL));
        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_MOBILE));
        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_FIREBASE_ID));
        Log.e ("karman", visitorDetailsPref.getStringPref (this, VisitorDetailsPref.VISITOR_LOGIN_KEY));


        progressDialog = new ProgressDialog (this);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager ().getPackageInfo (getPackageName (), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        version_code = pInfo.versionCode;


        homeServices.add (new HomeService (1, R.drawable.ic_list, "", "EXHIBITORS"));
        homeServices.add (new HomeService (2, R.drawable.ic_program, "", "PROGRAMMES"));
        homeServices.add (new HomeService (3, R.drawable.ic_hall_plan, "", "HALL PLAN"));
        homeServices.add (new HomeService (4, R.drawable.ic_favourite, "", "MY FAVOURITES"));
        homeServices.add (new HomeService (5, R.drawable.ic_card, "", "MY ENTRY PASS"));
        homeServices.add (new HomeService (6, R.drawable.ic_information, "", "INFORMATION"));


        homeServiceAdapter = new HomeServiceAdapter (this, homeServices);
        rvHomeServiceList.setAdapter (homeServiceAdapter);
        rvHomeServiceList.setHasFixedSize (true);
        rvHomeServiceList.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        rvHomeServiceList.addItemDecoration (new SimpleDividerItemDecoration (this));
        rvHomeServiceList.setItemAnimator (new DefaultItemAnimator ());


        Utils.setTypefaceToAllViews (this, clMain);
    }

    private void initListener () {
        ivIndiaSupplyLogo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Uri uri = Uri.parse ("http://indiasupply.com");
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity (intent);

            }
        });
        ivVisitorCard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                final Dialog dialog = new Dialog (MainActivity.this);
                dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
                dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                dialog.setContentView (R.layout.dialog_visitor_card);
                TextView tvID = (TextView) dialog.findViewById (R.id.tvVisitorID);
                TextView tvName = (TextView) dialog.findViewById (R.id.tvVisitorName);
                TextView tvEmail = (TextView) dialog.findViewById (R.id.tvVisitorEmail);
                ImageView ivQRCode = (ImageView) dialog.findViewById (R.id.ivQRCode);
                TextView tvMobile = (TextView) dialog.findViewById (R.id.tvVisitorNumber);

                tvID.setText (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_ID).toUpperCase ());
                tvName.setText (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_NAME).toUpperCase ());
                tvEmail.setText (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_EMAIL));
                tvMobile.setText (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_MOBILE));

                WindowManager manager = (WindowManager) getSystemService (WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay ();
                Point point = new Point ();
                display.getSize (point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;


                JSONObject jsonObject = new JSONObject ();

                try {
                    jsonObject.put (VisitorDetailsPref.VISITOR_ID, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_ID));
                    jsonObject.put (VisitorDetailsPref.VISITOR_NAME, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_NAME));
                    jsonObject.put (VisitorDetailsPref.VISITOR_MOBILE, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_MOBILE));
                    jsonObject.put (VisitorDetailsPref.VISITOR_EMAIL, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_EMAIL));
                } catch (JSONException e) {
                    e.printStackTrace ();
                }

                Log.e ("karman", jsonObject.toString ());

                QREncoder qrgEncoder = new QREncoder (jsonObject.toString (), null, QRContents.Type.TEXT, smallerDimension);
                try {
                    // Getting QR-Code as Bitmap
                    Bitmap bitmap = qrgEncoder.encodeAsBitmap ();
                    // Setting Bitmap to ImageView
                    ivQRCode.setImageBitmap (bitmap);
                } catch (WriterException e) {
                    Log.v ("karman", e.toString ());
                }

                Utils.setTypefaceToAllViews (MainActivity.this, tvName);
//                tvName.setTypeface (SetTypeFace.getTypeface (activity, "OCRA.otf"));
//                tvEmail.setTypeface (SetTypeFace.getTypeface (activity, "OCRA.otf"));
//                tvMobile.setTypeface (SetTypeFace.getTypeface (activity, "OCRA.otf"));
                dialog.show ();
            }
        });
    }

    private void checkVersionUpdate () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_CHECK_VERSION, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.GET, AppConfigURL.URL_CHECK_VERSION,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, "" + AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);

                                    if (! error) {
                                        int db_version_code = jsonObj.getInt (AppConfigTags.VERSION_CODE);
                                        String db_version_name = jsonObj.getString (AppConfigTags.VERSION_NAME);
                                        String version_updated_on = jsonObj.getString (AppConfigTags.VERSION_UPDATED_ON);
                                        int version_update_critical = jsonObj.getInt (AppConfigTags.VERSION_UPDATE_CRITICAL);

                                        if (db_version_code > version_code) {
                                            switch (version_update_critical) {
                                                case 0:
                                                    visitorDetailsPref.putBooleanPref (MainActivity.this, visitorDetailsPref.LOGGED_IN_SESSION, true);
                                                    new MaterialDialog.Builder (MainActivity.this)
                                                            .content (R.string.dialog_text_new_version_available)
                                                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .typeface (SetTypeFace.getTypeface (MainActivity.this), SetTypeFace.getTypeface (MainActivity.this))
                                                            .canceledOnTouchOutside (false)
                                                            .cancelable (false)
                                                            .positiveText (R.string.dialog_action_update)
                                                            .negativeText (R.string.dialog_action_ignore)
                                                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                                                @Override
                                                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                    final String appPackageName = getPackageName (); // getPackageName() from Context or Activity object
                                                                    try {
                                                                        startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse ("market://details?id=" + appPackageName)));
                                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                                        startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse ("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                                    }
                                                                }
                                                            })
                                                            .onNegative (new MaterialDialog.SingleButtonCallback () {
                                                                @Override
                                                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                    dialog.dismiss ();
                                                                }
                                                            }).show ();
                                                    break;
                                                case 1:
                                                    new MaterialDialog.Builder (MainActivity.this)
                                                            .content (R.string.dialog_text_new_version_available)
                                                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                                                            .typeface (SetTypeFace.getTypeface (MainActivity.this), SetTypeFace.getTypeface (MainActivity.this))
                                                            .canceledOnTouchOutside (false)
                                                            .cancelable (false)

                                                            .cancelListener (new DialogInterface.OnCancelListener () {
                                                                @Override
                                                                public void onCancel (DialogInterface dialog) {

                                                                }
                                                            })
                                                            .positiveText (R.string.dialog_action_update)
//                                                            .negativeText (R.string.dialog_action_close)
                                                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                                                @Override
                                                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                    final String appPackageName = getPackageName (); // getPackageName() from Context or Activity object
                                                                    try {
                                                                        startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse ("market://details?id=" + appPackageName)));
                                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                                        startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse ("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                                    }
                                                                }
                                                            })
//                                                            .onNegative (new MaterialDialog.SingleButtonCallback () {
//                                                                @Override
//                                                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                                                    finish ();
//                                                                    overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
//                                                                }
//                                                            })
                                                            .show ();
                                                    break;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    }

                    ,
                    new Response.ErrorListener ()

                    {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }

            )

            {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
            checkVersionUpdate ();
        }
    }

    private void logOutFromDevice (final int device_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_logging_out), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_LOGOUT, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_LOGOUT,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, "" + AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_ID, "");
                                    visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_NAME, "");
                                    visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_EMAIL, "");
                                    visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_MOBILE, "");
                                    visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY, "");

                                    Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                                    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity (intent);
                                    overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                    progressDialog.dismiss ();
                                    Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                                Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                            }
                            progressDialog.dismiss ();
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            progressDialog.dismiss ();
                            Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
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
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
            Utils.showSnackBar (this, clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (android.provider.Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }

    private void isLogin () {
        if (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY) == "") {
            Intent myIntent = new Intent (this, LoginActivity.class);
            startActivity (myIntent);
        }
        if (visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY) == "")// || userDetailPref.getStringPref (MainActivity.this, VisitorDetailsPref.HOSPITAL_DEFAULT_PATIENT_ID) == "")
            finish ();
    }

    private void initApplication () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_initializing), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_INIT, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_INIT,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
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
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, visitorDetailsPref.getStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
            progressDialog.dismiss ();
//            initApplication ();
        }
    }

    @Override
    public void onBackPressed () {
        MaterialDialog dialog = new MaterialDialog.Builder (this)
                .content (R.string.dialog_text_quit_application)
                .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                .neutralColor (getResources ().getColor (R.color.app_text_color_dark))
                .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                .typeface (SetTypeFace.getTypeface (this), SetTypeFace.getTypeface (this))
                .canceledOnTouchOutside (false)
                .cancelable (false)
                .positiveText (R.string.dialog_action_yes)
                .negativeText (R.string.dialog_action_no)
                .neutralText (R.string.dialog_action_logout)
                .onNeutral (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_ID, "");
                        visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_NAME, "");
                        visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_EMAIL, "");
                        visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_MOBILE, "");
                        visitorDetailsPref.putStringPref (MainActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY, "");

                        Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity (intent);
                        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                })
                .onPositive (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        visitorDetailsPref.putBooleanPref (MainActivity.this, VisitorDetailsPref.LOGGED_IN_SESSION, false);


                        finish ();
                        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                }).build ();
        dialog.show ();
    }

    public void checkPermissions () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission (Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission (Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions (new String[] {Manifest.permission.RECEIVE_SMS, Manifest.permission.VIBRATE,
                                Manifest.permission.READ_SMS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS,
                                Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE);
            }
/*
            if (checkSelfPermission (Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.PERMISSION_REQUEST_CODE);
            }
            if (checkSelfPermission (Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[] {Manifest.permission.INTERNET}, MainActivity.PERMISSION_REQUEST_CODE);
            }
            if (checkSelfPermission (Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[] {Manifest.permission.RECEIVE_BOOT_COMPLETED,}, MainActivity.PERMISSION_REQUEST_CODE);
            }
            if (checkSelfPermission (Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainActivity.PERMISSION_REQUEST_CODE);
            }
*/
        }
    }

    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale (permission);
                    if (! showRationale) {
                        AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);
                        builder.setMessage ("Permission are required please enable them on the App Setting page")
                                .setCancelable (false)
                                .setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                                    public void onClick (DialogInterface dialog, int id) {
                                        dialog.dismiss ();
                                        Intent intent = new Intent (Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts ("package", getPackageName (), null));
                                        startActivity (intent);
                                    }
                                });
                        AlertDialog alert = builder.create ();
                        alert.show ();
                        // user denied flagging NEVER ASK AGAIN
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                    } else if (Manifest.permission.RECEIVE_SMS.equals (permission)) {
//                        Utils.showToast (this, "Camera Permission is required");
//                        showRationale (permission, R.string.permission_denied_contacts);
                        // user denied WITHOUT never ask again
                        // this is a good place to explain the user
                        // why you need the permission and ask if he want
                        // to accept it (the rationale)
                    } else if (Manifest.permission.READ_SMS.equals (permission)) {
                    } else if (Manifest.permission.VIBRATE.equals (permission)) {
                    } else if (Manifest.permission.GET_ACCOUNTS.equals (permission)) {
                    } else if (Manifest.permission.READ_CONTACTS.equals (permission)) {
                    } else if (Manifest.permission.CALL_PHONE.equals (permission)) {
                    } else if (Manifest.permission.READ_PHONE_STATE.equals (permission)) {
                    }
                }
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

}
