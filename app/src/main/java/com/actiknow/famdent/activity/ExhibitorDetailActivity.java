package com.actiknow.famdent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.model.ExhibitorDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.Utils;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;


public class ExhibitorDetailActivity extends AppCompatActivity {
    int exhibitor_id;
    TextView tvExhibitorName;
    TextView tvFullAddress;
    TextView tvContactNumber1;
    TextView tvEmail;
    TextView tvWebsite;
    FloatingActionButton fabAddNote;
    ExhibitorDetail exhibitorDetail;
    ImageView ivFavourite;
    ImageView ivBack;

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
        tvContactNumber1 = (TextView) findViewById (R.id.tvContactNumber1);
        tvEmail = (TextView) findViewById (R.id.tvEmail);
        tvWebsite = (TextView) findViewById (R.id.tvWebsite);
        fabAddNote = (FloatingActionButton) findViewById (R.id.fabAddNote);

        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
    }

    private void initData () {
        exhibitorDetail = new ExhibitorDetail (1, true, "", "3M INDIA LIMITED", "Hall 1", "Stall 32", "3M Space Division, Concord Block, UB City 24, Vittal Mallya Road, New Delhi 110075, India", "+919873684678\n+919879879879\n18004253030", "karman.singh@actiknowbi.com", "www.indiasupply.com", "");
        tvExhibitorName.setText (exhibitorDetail.getExhibitor_name ());
        tvFullAddress.setText (exhibitorDetail.getAddress ());
        tvContactNumber1.setText (exhibitorDetail.getNumber ());
        tvEmail.setText (exhibitorDetail.getEmail ());
        tvWebsite.setText (exhibitorDetail.getWebsite ());

        if (exhibitorDetail.isFavourite ()) {
            ivFavourite.setImageResource (R.drawable.ic_star);
        } else {
            ivFavourite.setImageResource (R.drawable.ic_star_border);
        }


        Utils.setTypefaceToAllViews (this, tvContactNumber1);
    }

    private void initListener () {
        tvContactNumber1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent sIntent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + "+919873684678"));
                sIntent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity (sIntent);
            }
        });

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
                            .positiveColor (getResources ().getColor (R.color.app_text_color))
                            .contentColor (getResources ().getColor (R.color.app_text_color))
                            .negativeColor (getResources ().getColor (R.color.app_text_color))
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
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite)
                            .positiveColor (getResources ().getColor (R.color.app_text_color))
                            .contentColor (getResources ().getColor (R.color.app_text_color))
                            .negativeColor (getResources ().getColor (R.color.app_text_color))
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
                        .contentColor (getResources ().getColor (R.color.app_text_color))
                        .positiveColor (getResources ().getColor (R.color.app_text_color))
                        .negativeColor (getResources ().getColor (R.color.app_text_color))
                        .typeface (SetTypeFace.getTypeface (ExhibitorDetailActivity.this), SetTypeFace.getTypeface (ExhibitorDetailActivity.this))
                        .inputType (InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE|InputType.TYPE_TEXT_FLAG_MULTI_LINE)
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



