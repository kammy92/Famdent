package com.actiknow.famdent.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.activity.EventListActivity;
import com.actiknow.famdent.activity.ExhibitorListActivity;
import com.actiknow.famdent.activity.HallPlanActivity;
import com.actiknow.famdent.activity.InformationActivity;
import com.actiknow.famdent.activity.MyFavouriteActivity;
import com.actiknow.famdent.activity.SessionListActivity;
import com.actiknow.famdent.model.HomeService;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.actiknow.famdent.utils.qr_code.QRContents;
import com.actiknow.famdent.utils.qr_code.QREncoder;
import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WINDOW_SERVICE;


public class HomeServiceAdapter extends RecyclerView.Adapter<HomeServiceAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    private List<HomeService> homeServiceList = new ArrayList<HomeService> ();

    public HomeServiceAdapter(Activity activity, List<HomeService> homeServiceList) {
        this.activity = activity;
        this.homeServiceList = homeServiceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_home, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final HomeService homeService = homeServiceList.get(position);
        holder.tvServiceName.setTypeface(SetTypeFace.getTypeface(activity));
        holder.tvServiceName.setText(homeService.getService_name ());
        Glide.with(activity).load("").placeholder(homeService.getIcon()).into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return homeServiceList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvServiceName;
        ImageView ivIcon;

        public ViewHolder(View view) {
            super(view);
            tvServiceName = (TextView) view.findViewById(R.id.tvServiceName);
            ivIcon = (ImageView) view.findViewById(R.id.ivServiceIcon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeService homeService = homeServiceList.get(getLayoutPosition());
            switch (homeService.getId()) {
                case 1:
                    Intent intent = new Intent (activity, ExhibitorListActivity.class);
                    activity.startActivity (intent);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 2:
                    Intent intent2 = new Intent (activity, EventListActivity.class);
                    activity.startActivity (intent2);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 3:
                    Intent intent7 = new Intent (activity, SessionListActivity.class);
                    activity.startActivity (intent7);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 4:
                    Intent intent3 = new Intent (activity, HallPlanActivity.class);
                    activity.startActivity (intent3);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 5:
                    Intent intent4 = new Intent (activity, MyFavouriteActivity.class);
                    activity.startActivity (intent4);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 6:
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    final Dialog dialog = new Dialog (activity);
                    dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
                    dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                    dialog.setContentView (R.layout.dialog_visitor_card);
                    TextView tvID = (TextView) dialog.findViewById (R.id.tvVisitorID);
                    TextView tvName = (TextView) dialog.findViewById (R.id.tvVisitorName);
                    TextView tvEmail = (TextView) dialog.findViewById (R.id.tvVisitorEmail);
                    ImageView ivQRCode = (ImageView) dialog.findViewById (R.id.ivQRCode);
                    TextView tvMobile = (TextView) dialog.findViewById (R.id.tvVisitorNumber);

                    tvID.setText (visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_ID).toUpperCase ());
                    tvName.setText (visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_NAME).toUpperCase ());
                    tvEmail.setText (visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_EMAIL));
                    tvMobile.setText (visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_MOBILE));


                    WindowManager manager = (WindowManager) activity.getSystemService (WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay ();
                    Point point = new Point ();
                    display.getSize (point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    JSONObject jsonObject = new JSONObject ();
                    try {
                        jsonObject.put (VisitorDetailsPref.VISITOR_ID, visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_ID));
                        jsonObject.put (VisitorDetailsPref.VISITOR_NAME, visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_NAME));
                        jsonObject.put (VisitorDetailsPref.VISITOR_MOBILE, visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_MOBILE));
                        jsonObject.put (VisitorDetailsPref.VISITOR_EMAIL, visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_EMAIL));
                        jsonObject.put (VisitorDetailsPref.VISITOR_TYPE, visitorDetailsPref.getStringPref (activity, VisitorDetailsPref.VISITOR_TYPE));
                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }

                    QREncoder qrgEncoder = new QREncoder (jsonObject.toString (), null, QRContents.Type.TEXT, smallerDimension);
                    try {
                        // Getting QR-Code as Bitmap
                        Bitmap bitmap = qrgEncoder.encodeAsBitmap ();
                        // Setting Bitmap to ImageView
                        ivQRCode.setImageBitmap (bitmap);
                    } catch (WriterException e) {
                        Log.v ("karman", e.toString ());
                    }

                    Utils.setTypefaceToAllViews (activity, tvName);
                    dialog.show ();
                    break;
                case 7:
                    Intent intent6 = new Intent (activity, InformationActivity.class);
                    activity.startActivity (intent6);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }
        }
    }
}
