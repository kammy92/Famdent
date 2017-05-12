package com.actiknow.famdent.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.ExhibitorDetail;
import com.actiknow.famdent.model.StallDetail;
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
import java.util.Map;


public class ExhibitorDetailActivity extends AppCompatActivity {
    int exhibitor_id;
    TextView tvExhibitorName;
    TextView tvFullAddress;
    TextView tvContactPerson;
    TextView tvEmail;
    TextView tvWebsite;
    FloatingActionButton fabAddNote;
    ExhibitorDetail exhibitorDetail;
    ImageView ivFavourite;
    ImageView ivBack;
    CoordinatorLayout clMain;
    RelativeLayout rlNotes;
    TextView tvNotes;
    LinearLayout llPhone;
    TextView tvAddFavourite;
    TextView tvAddNotes;

    RelativeLayout rlMain;
    LinearLayout llButtons;

    ProgressDialog progressDialog;

    DatabaseHandler db;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_exhibitor_detail);
        getExtras ();
        initView ();
        initData ();
        initListener ();
        getOfflineExhibitorDetails (exhibitor_id);
//        getExhibitorDetails (exhibitor_id);
    }

    private void initView () {
        tvExhibitorName = (TextView) findViewById (R.id.tvExhibitorName);
        tvFullAddress = (TextView) findViewById (R.id.tvFullAddress);
        tvContactPerson = (TextView) findViewById (R.id.tvContactPerson);
        tvEmail = (TextView) findViewById (R.id.tvEmail);
        tvWebsite = (TextView) findViewById (R.id.tvWebsite);
        tvAddFavourite = (TextView) findViewById (R.id.tvAddFavourite);
        tvAddNotes = (TextView) findViewById (R.id.tvAddNotes);
        fabAddNote = (FloatingActionButton) findViewById (R.id.fabAddNote);

        rlMain = (RelativeLayout) findViewById (R.id.rlMain);
        llButtons = (LinearLayout) findViewById (R.id.llButtons);

        llPhone = (LinearLayout) findViewById (R.id.llPhone);
        rlNotes = (RelativeLayout) findViewById (R.id.rlNotes);
        tvNotes = (TextView) findViewById (R.id.tvNotes);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());

        progressDialog = new ProgressDialog (this);


//        ArrayList<String> contactList = new ArrayList<> ();
//        contactList.add ("+91 9873684678");
//        contactList.add ("+91 9879879879");
//        contactList.add ("1800 425 3030");
//        exhibitorDetail = new ExhibitorDetail (1, false, contactList, "", "3M INDIA LIMITED", "Hall 1", "Stall 32", "3M Space Division, Concord Block, UB City 24, Vittal Mallya Road, New Delhi 110075, India", "karman.singh@actiknowbi.com", "www.indiasupply.com", "");
//        tvExhibitorName.setText (exhibitorDetail.getExhibitor_name ());
//        tvFullAddress.setText (exhibitorDetail.getAddress ());


//        for (int i = 0; i < exhibitorDetail.getContactList ().size (); i++) {
//            final ArrayList<String> contactList2 = exhibitorDetail.getContactList ();
//            TextView tv = new TextView (this);
//            tv.setText (Html.fromHtml ("<u><font color='blue'>" + contactList2.get (i) + "</font></u>"), TextView.BufferType.SPANNABLE);
//            tv.setTextSize (14);
//            tv.setPadding (0, 5, 0, 5);
//            tv.setTypeface (SetTypeFace.getTypeface (this, Constants.font_name));
//            tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
//            final int finalI = i;
//            tv.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick (View v) {
//                    Intent sIntent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + contactList2.get (finalI)));
//                    sIntent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity (sIntent);
//                }
//            });
//            llPhone.addView (tv);
//        }


//        tvEmail.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getEmail () + "</font></u>"), TextView.BufferType.SPANNABLE);
//        tvWebsite.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getWebsite () + "</font></u>"), TextView.BufferType.SPANNABLE);
//        tvNotes.setText (exhibitorDetail.getNotes ());


//        if (exhibitorDetail.isFavourite ()) {
//            ivFavourite.setImageResource (R.drawable.ic_star);
//            tvAddFavourite.setVisibility (View.GONE);
//            llButtons.setWeightSum (1);
//        } else {
//            ivFavourite.setImageResource (R.drawable.ic_star_border);
//            tvAddFavourite.setVisibility (View.VISIBLE);
//            llButtons.setWeightSum (2);
//        }

//        if (exhibitorDetail.getNotes ().length () > 0) {
//            rlNotes.setVisibility (View.VISIBLE);
//        } else {
//            rlNotes.setVisibility (View.GONE);
//        }
        Utils.setTypefaceToAllViews (this, tvEmail);
    }

    private void initListener () {
        tvEmail.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent email = new Intent (Intent.ACTION_SEND);
                email.putExtra (Intent.EXTRA_EMAIL, new String[] {exhibitorDetail.getEmail ()});
                email.putExtra (Intent.EXTRA_SUBJECT, "Enquiry");
                email.putExtra (Intent.EXTRA_TEXT, "");
                email.setType ("message/rfc822");
                startActivity (Intent.createChooser (email, "Choose an Email client :"));
            }
        });

        tvWebsite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Uri uri = Uri.parse ("http://" + exhibitorDetail.getWebsite ());
                Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity (intent);
            }
        });


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
                if (exhibitorDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_exhibitor)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (false, exhibitor_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_exhibitor)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (true, exhibitor_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

        tvAddFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (exhibitorDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite_exhibitor)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (false, exhibitor_id);
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite_exhibitor)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateFavouriteStatus (true, exhibitor_id);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

        fabAddNote.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                final MaterialDialog.Builder mBuilder = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                        .content (R.string.dialog_text_add_notes)
                        .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                        .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                        .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                        .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                        .inputType (InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE | InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                        .alwaysCallInputCallback ()
                        .canceledOnTouchOutside (true)
                        .cancelable (true)
                        .positiveText (R.string.dialog_action_save)
                        .negativeText (R.string.dialog_action_cancel);

                mBuilder.input (null, null, new MaterialDialog.InputCallback () {
                    @Override
                    public void onInput (MaterialDialog dialog, CharSequence input) {
                    }
                });

                mBuilder.onPositive (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        updateNoteStatus (exhibitor_id, dialog.getInputEditText ().getText ().toString ());
//                        exhibitorDetail.setNotes (dialog.getInputEditText ().getText ().toString ());
//                        tvNotes.setText (exhibitorDetail.getNotes ());
//                        if (exhibitorDetail.getNotes ().length () > 0) {
//                            rlNotes.setVisibility (View.VISIBLE);
//                        } else {
//                            rlNotes.setVisibility (View.GONE);
//                        }
                    }
                });

                MaterialDialog dialog = mBuilder.build ();
                dialog.getInputEditText ().setText (exhibitorDetail.getNotes ());
                dialog.getInputEditText ().setSingleLine (false);
                dialog.show ();
            }
        });

        tvAddNotes.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                final MaterialDialog.Builder mBuilder = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                        .content (R.string.dialog_text_add_notes)
                        .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                        .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                        .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                        .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                        .inputType (InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE | InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                        .alwaysCallInputCallback ()
                        .canceledOnTouchOutside (true)
                        .cancelable (true)
                        .positiveText (R.string.dialog_action_save)
                        .negativeText (R.string.dialog_action_cancel);

                mBuilder.input (null, null, new MaterialDialog.InputCallback () {
                    @Override
                    public void onInput (MaterialDialog dialog, CharSequence input) {
                    }
                });

                mBuilder.onPositive (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        updateNoteStatus (exhibitor_id, dialog.getInputEditText ().getText ().toString ());
//                        exhibitorDetail.setNotes (dialog.getInputEditText ().getText ().toString ());
//                        tvNotes.setText (exhibitorDetail.getNotes ());
//                        if (exhibitorDetail.getNotes ().length () > 0) {
//                            rlNotes.setVisibility (View.VISIBLE);
//                        } else {
//                            rlNotes.setVisibility (View.GONE);
//                        }
                    }
                });

                MaterialDialog dialog = mBuilder.build ();
                dialog.getInputEditText ().setText (exhibitorDetail.getNotes ());
                dialog.getInputEditText ().setSingleLine (false);
                dialog.show ();
            }
        });
    }

    private void getExtras () {
        Intent intent = getIntent ();
        exhibitor_id = intent.getIntExtra (AppConfigTags.EXHIBITOR_ID, 0);
    }

    private void getOfflineExhibitorDetails (int exhibitor_id) {

        exhibitorDetail = db.getExhibitorDetail (exhibitor_id);

        tvExhibitorName.setText (exhibitorDetail.getExhibitor_name ());


        tvFullAddress.setText ("Stall :" + exhibitorDetail.getAddress ());
        tvContactPerson.setText (exhibitorDetail.getContact_person ());
        tvEmail.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getEmail () + "</font></u>"), TextView.BufferType.SPANNABLE);
        tvWebsite.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getWebsite () + "</font></u>"), TextView.BufferType.SPANNABLE);
        tvNotes.setText (exhibitorDetail.getNotes ());

        for (int i = 0; i < exhibitorDetail.getContactList ().size (); i++) {
            final ArrayList<String> contactList2 = exhibitorDetail.getContactList ();
            TextView tv = new TextView (ExhibitorDetailActivity.this);
            tv.setText (Html.fromHtml ("<u><font color='blue'>" + contactList2.get (i) + "</font></u>"), TextView.BufferType.SPANNABLE);
            tv.setTextSize (14);
            tv.setPadding (0, 5, 0, 5);
            tv.setTypeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this, Constants.font_name));
            tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
            final int finalI = i;
            tv.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent sIntent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + contactList2.get (finalI)));
                    sIntent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (sIntent);
                }
            });
            llPhone.addView (tv);
        }


        if (exhibitorDetail.isFavourite ()) {
            ivFavourite.setImageResource (R.drawable.ic_star);
            tvAddFavourite.setVisibility (View.GONE);
            llButtons.setWeightSum (1);
        } else {
            ivFavourite.setImageResource (R.drawable.ic_star_border);
            tvAddFavourite.setVisibility (View.VISIBLE);
            llButtons.setWeightSum (2);
        }

        if (exhibitorDetail.getNotes ().length () > 0) {
            tvNotes.setText (exhibitorDetail.getNotes ());
            rlNotes.setVisibility (View.VISIBLE);
            tvAddNotes.setText ("EDIT NOTES");
        } else {
            rlNotes.setVisibility (View.GONE);
            tvAddNotes.setText ("ADD NOTES");
        }

        rlMain.setVisibility (View.VISIBLE);
    }

    private void getExhibitorDetails (final int exhibitor_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EXHIBITOR_DETAIL + "/" + exhibitor_id, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EXHIBITOR_DETAIL + "/" + exhibitor_id,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean is_error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! is_error) {
                                        exhibitorDetail = new ExhibitorDetail (
                                                jsonObj.getInt (AppConfigTags.EXHIBITOR_ID),
                                                jsonObj.getBoolean (AppConfigTags.EXHIBITOR_FAVOURITE),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_LOGO),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_NAME),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_CONTACT_PERSON),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_ADDRESS),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_EMAIL),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_WEBSITE),
                                                jsonObj.getString (AppConfigTags.EXHIBITOR_NOTES));

                                        JSONArray jsonArrayStallDetails = jsonObj.getJSONArray (AppConfigTags.STALL_DETAILS);
                                        exhibitorDetail.clearStallDetailList ();
                                        for (int j = 0; j < jsonArrayStallDetails.length (); j++) {
                                            JSONObject jsonObjectStallDetail = jsonArrayStallDetails.getJSONObject (j);
                                            StallDetail stallDetail = new StallDetail (
                                                    jsonObjectStallDetail.getString (AppConfigTags.STALL_NAME),
                                                    jsonObjectStallDetail.getString (AppConfigTags.STALL_NUMBER),
                                                    jsonObjectStallDetail.getString (AppConfigTags.HALL_NUMBER)
                                            );
                                            exhibitorDetail.setStallDetailInList (stallDetail);
                                        }

                                        ArrayList<String> contactList = new ArrayList<> ();
                                        JSONArray jsonArrayContacts = jsonObj.getJSONArray (AppConfigTags.EXHIBITOR_CONTACTS);
                                        for (int j = 0; j < jsonArrayContacts.length (); j++) {
                                            JSONObject jsonObjectContactDetail = jsonArrayContacts.getJSONObject (j);
                                            contactList.add (jsonObjectContactDetail.getString (AppConfigTags.CONTACT));
                                        }
                                        exhibitorDetail.setContactList (contactList);


                                        tvExhibitorName.setText (exhibitorDetail.getExhibitor_name ());


                                        tvFullAddress.setText ("Stall Number :" + exhibitorDetail.getAddress ());
                                        tvContactPerson.setText (exhibitorDetail.getContact_person ());
                                        tvEmail.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getEmail () + "</font></u>"), TextView.BufferType.SPANNABLE);
                                        tvWebsite.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getWebsite () + "</font></u>"), TextView.BufferType.SPANNABLE);
                                        tvNotes.setText (exhibitorDetail.getNotes ());

                                        for (int i = 0; i < exhibitorDetail.getContactList ().size (); i++) {
                                            final ArrayList<String> contactList2 = exhibitorDetail.getContactList ();
                                            TextView tv = new TextView (ExhibitorDetailActivity.this);
                                            tv.setText (Html.fromHtml ("<u><font color='blue'>" + contactList2.get (i) + "</font></u>"), TextView.BufferType.SPANNABLE);
                                            tv.setTextSize (14);
                                            tv.setPadding (0, 5, 0, 5);
                                            tv.setTypeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this, Constants.font_name));
                                            tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
                                            final int finalI = i;
                                            tv.setOnClickListener (new View.OnClickListener () {
                                                @Override
                                                public void onClick (View v) {
                                                    Intent sIntent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + contactList2.get (finalI)));
                                                    sIntent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity (sIntent);
                                                }
                                            });
                                            llPhone.addView (tv);
                                        }




                                        if (exhibitorDetail.isFavourite ()) {
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            llButtons.setWeightSum (1);
                                        } else {
                                            ivFavourite.setImageResource (R.drawable.ic_star_border);
                                            tvAddFavourite.setVisibility (View.VISIBLE);
                                            llButtons.setWeightSum (2);
                                        }

                                        if (exhibitorDetail.getNotes ().length () > 0) {
                                            tvNotes.setText (exhibitorDetail.getNotes ());
                                            rlNotes.setVisibility (View.VISIBLE);
                                            tvAddNotes.setText ("EDIT NOTES");
                                        } else {
                                            rlNotes.setVisibility (View.GONE);
                                            tvAddNotes.setText ("ADD NOTES");
                                        }


                                        rlMain.setVisibility (View.VISIBLE);
                                    }
                                    progressDialog.dismiss ();
                                } catch (JSONException e) {
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
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Utils.showLog (Log.ERROR, AppConfigTags.ERROR, new String (response.data), true);

                            }
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
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ExhibitorDetailActivity.this, visitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
            };
            Utils.sendRequest (strRequest, 5);
        } else {
            progressDialog.dismiss ();
        }
    }

    private void updateFavouriteStatus (final boolean add_favourite, final int exhibitors_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            String URL;
            if (add_favourite) {
                URL = AppConfigURL.URL_EXHIBITOR_FAVOURITE + "/add";
            } else {
                URL = AppConfigURL.URL_EXHIBITOR_FAVOURITE + "/remove";
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
                                            exhibitorDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            llButtons.setWeightSum (1);
                                            Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor added to favourites", Snackbar.LENGTH_LONG, null, null);
                                        } else {
                                            exhibitorDetail.setFavourite (false);
                                            ivFavourite.setImageResource (R.drawable.ic_star_border);
                                            tvAddFavourite.setVisibility (View.VISIBLE);
                                            llButtons.setWeightSum (2);
                                            Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                                @Override
                                                public void onClick (View v) {
                                                    updateFavouriteStatus (true, exhibitors_id);
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
                    params.put (AppConfigTags.EXHIBITOR_ID, String.valueOf (exhibitors_id));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ExhibitorDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
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

    private void updateNoteStatus (final int exhibitors_id, final String notes) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EXHIBITOR_NOTE, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.URL_EXHIBITOR_NOTE,
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
                                        exhibitorDetail.setNotes (notes);
                                        tvNotes.setText (exhibitorDetail.getNotes ());
                                        if (exhibitorDetail.getNotes ().length () > 0) {
                                            rlNotes.setVisibility (View.VISIBLE);
                                            tvAddNotes.setText ("EDIT NOTES");
                                        } else {
                                            rlNotes.setVisibility (View.GONE);
                                            tvAddNotes.setText ("ADD NOTES");
                                        }
                                        Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
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
                    params.put (AppConfigTags.EXHIBITOR_ID, String.valueOf (exhibitors_id));
                    params.put (AppConfigTags.EXHIBITOR_NOTES, notes);
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ExhibitorDetailActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
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



