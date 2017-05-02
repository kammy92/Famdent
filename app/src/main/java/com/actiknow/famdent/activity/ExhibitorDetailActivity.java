package com.actiknow.famdent.activity;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.model.ExhibitorDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.Utils;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;


public class ExhibitorDetailActivity extends AppCompatActivity {
    int exhibitor_id;
    TextView tvExhibitorName;
    TextView tvFullAddress;
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

    LinearLayout llButtons;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_exhibitor_detail);
        getExtras ();
        initView ();
        initData ();
        initListener ();

    }

    private void initView () {
        tvExhibitorName = (TextView) findViewById (R.id.tvExhibitorName);
        tvFullAddress = (TextView) findViewById (R.id.tvFullAddress);
        tvEmail = (TextView) findViewById (R.id.tvEmail);
        tvWebsite = (TextView) findViewById (R.id.tvWebsite);
        tvAddFavourite = (TextView) findViewById (R.id.tvAddFavourite);
        tvAddNotes = (TextView) findViewById (R.id.tvAddNotes);
        fabAddNote = (FloatingActionButton) findViewById (R.id.fabAddNote);

        llButtons = (LinearLayout) findViewById (R.id.llButtons);

        llPhone = (LinearLayout) findViewById (R.id.llPhone);
        rlNotes = (RelativeLayout) findViewById (R.id.rlNotes);
        tvNotes = (TextView) findViewById (R.id.tvNotes);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
    }

    private void initData () {

        ArrayList<String> contactList = new ArrayList<> ();
        contactList.add ("+91 9873684678");
        contactList.add ("+91 9879879879");
        contactList.add ("18004253030");
        exhibitorDetail = new ExhibitorDetail (1, false, contactList, "", "3M INDIA LIMITED", "Hall 1", "Stall 32", "3M Space Division, Concord Block, UB City 24, Vittal Mallya Road, New Delhi 110075, India", "karman.singh@actiknowbi.com", "www.indiasupply.com", "");
        tvExhibitorName.setText (exhibitorDetail.getExhibitor_name ());
        tvFullAddress.setText (exhibitorDetail.getAddress ());


        for (int i = 0; i < exhibitorDetail.getContactList ().size (); i++) {
            final ArrayList<String> contactList2 = exhibitorDetail.getContactList ();
            TextView tv = new TextView (this);
            tv.setText (Html.fromHtml ("<u><font color='blue'>" + contactList2.get (i) + "</font></u>"), TextView.BufferType.SPANNABLE);
            tv.setTextSize (15);
            tv.setPadding (0, 5, 0, 5);
            tv.setTypeface (SetTypeFace.getTypeface (this, Constants.font_name));
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


        tvEmail.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getEmail () + "</font></u>"), TextView.BufferType.SPANNABLE);
        tvWebsite.setText (Html.fromHtml ("<u><font color='blue'>" + exhibitorDetail.getWebsite () + "</font></u>"), TextView.BufferType.SPANNABLE);
        tvNotes.setText (exhibitorDetail.getNotes ());


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
            rlNotes.setVisibility (View.VISIBLE);
        } else {
            rlNotes.setVisibility (View.GONE);
        }

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
                            .content (R.string.dialog_text_remove_favourite)
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
                                    exhibitorDetail.setFavourite (false);
                                    ivFavourite.setImageResource (R.drawable.ic_star_border);
                                    tvAddFavourite.setVisibility (View.VISIBLE);
                                    llButtons.setWeightSum (2);
                                    Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                        @Override
                                        public void onClick (View v) {
                                            exhibitorDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            llButtons.setWeightSum (1);
                                        }
                                    });
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite)
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
                                    exhibitorDetail.setFavourite (true);
                                    ivFavourite.setImageResource (R.drawable.ic_star);
                                    Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor added to favourites", Snackbar.LENGTH_LONG, null, null);
                                    tvAddFavourite.setVisibility (View.GONE);
                                    llButtons.setWeightSum (1);
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
                            .content (R.string.dialog_text_remove_favourite)
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
                                    exhibitorDetail.setFavourite (false);
                                    ivFavourite.setImageResource (R.drawable.ic_star_border);
                                    tvAddFavourite.setVisibility (View.VISIBLE);
                                    llButtons.setWeightSum (2);
                                    Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                        @Override
                                        public void onClick (View v) {
                                            exhibitorDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                            tvAddFavourite.setVisibility (View.GONE);
                                            llButtons.setWeightSum (1);
                                        }
                                    });
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite)
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
                                    exhibitorDetail.setFavourite (true);
                                    ivFavourite.setImageResource (R.drawable.ic_star);
                                    Utils.showSnackBar (ExhibitorDetailActivity.this, clMain, "Exhibitor added to favourites", Snackbar.LENGTH_LONG, null, null);
                                    tvAddFavourite.setVisibility (View.GONE);
                                    llButtons.setWeightSum (1);
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
                        exhibitorDetail.setNotes (dialog.getInputEditText ().getText ().toString ());
                        tvNotes.setText (exhibitorDetail.getNotes ());
                        if (exhibitorDetail.getNotes ().length () > 0) {
                            rlNotes.setVisibility (View.VISIBLE);
                        } else {
                            rlNotes.setVisibility (View.GONE);
                        }
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
                        exhibitorDetail.setNotes (dialog.getInputEditText ().getText ().toString ());
                        tvNotes.setText (exhibitorDetail.getNotes ());
                        if (exhibitorDetail.getNotes ().length () > 0) {
                            rlNotes.setVisibility (View.VISIBLE);
                        } else {
                            rlNotes.setVisibility (View.GONE);
                        }
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

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }


}



