package com.actiknow.famdent.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.activity.ExhibitorListActivity;
import com.actiknow.famdent.activity.HallPlanActivity;
import com.actiknow.famdent.activity.InformationActivity;
import com.actiknow.famdent.activity.MyFavouriteActivity;
import com.actiknow.famdent.activity.ProgrammeListActivity;
import com.actiknow.famdent.model.HomeService;
import com.actiknow.famdent.utils.SetTypeFace;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


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
                    Intent intent2 = new Intent (activity, ProgrammeListActivity.class);
                    activity.startActivity (intent2);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 3:
                    Intent intent3 = new Intent (activity, HallPlanActivity.class);
                    activity.startActivity (intent3);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 4:
                    Intent intent4 = new Intent (activity, MyFavouriteActivity.class);
                    activity.startActivity (intent4);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 5:
                    break;
                case 6:
                    Intent intent6 = new Intent (activity, InformationActivity.class);
                    activity.startActivity (intent6);
                    activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }
        }
    }
}
