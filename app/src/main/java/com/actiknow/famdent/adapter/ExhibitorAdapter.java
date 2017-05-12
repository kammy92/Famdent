package com.actiknow.famdent.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.activity.ExhibitorDetailActivity;
import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.model.StallDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.SetTypeFace;

import java.util.ArrayList;
import java.util.List;


public class ExhibitorAdapter extends RecyclerView.Adapter<ExhibitorAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    private List<Exhibitor> exhibitorList = new ArrayList<Exhibitor>();

    public ExhibitorAdapter(Activity activity, List<Exhibitor> exhibitorList) {
        this.activity = activity;
        this.exhibitorList = exhibitorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_exhibitor, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final Exhibitor exhibitor = exhibitorList.get (position);
        List<StallDetail> stallDetails = exhibitor.getStallDetailList ();

        holder.llStallDetails.removeAllViews ();
        for (int i = 0; i < stallDetails.size (); i++) {
            LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams (
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView (activity);
            tv.setTypeface (SetTypeFace.getTypeface (activity));
            tv.setLayoutParams (lparams);
            tv.setText ("Stall : " + stallDetails.get (i).getStall_number ());
            holder.llStallDetails.addView (tv);
        }

        holder.tvExhibitorName.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvExhibitorName.setText (exhibitor.getExhibitor_name ());
    }

    @Override
    public int getItemCount() {
        return exhibitorList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvExhibitorName;
        ImageView ivExhibitorLogo;
        LinearLayout llStallDetails;

        public ViewHolder(View view) {
            super(view);
            tvExhibitorName = (TextView) view.findViewById(R.id.tvExhibitorName);
            ivExhibitorLogo = (ImageView) view.findViewById (R.id.ivExhibitorLogo);
            llStallDetails = (LinearLayout) view.findViewById (R.id.llStallDetails);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Exhibitor exhibitor = exhibitorList.get(getLayoutPosition());

            Intent intent = new Intent (activity, ExhibitorDetailActivity.class);
            intent.putExtra (AppConfigTags.EXHIBITOR_ID, exhibitor.getId ());
            activity.startActivity (intent);
            activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);


        }
    }
}
