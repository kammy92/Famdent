package com.actiknow.famdent.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.activity.ProgrammeDetailActivity;
import com.actiknow.famdent.model.Programme;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.SetTypeFace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul jain l on 27/04/2017.
 */


public class ProgrammeAdapter extends RecyclerView.Adapter<ProgrammeAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    private List<Programme> programmeList = new ArrayList<Programme>();

    public ProgrammeAdapter(Activity activity, List<Programme> programmeList) {
        this.activity = activity;
        this.programmeList = programmeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_programme, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final Programme programme = programmeList.get(position);

        holder.tvProgrammeName.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvDoctorName.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvDate.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvTime.setTypeface (SetTypeFace.getTypeface (activity));

        holder.tvProgrammeName.setText(programme.getProgram_name());
        holder.tvDoctorName.setText(programme.getDoctor_name());
        holder.tvDate.setText(programme.getDate());
        holder.tvTime.setText(programme.getTime());
        //Glide.with(activity).load("").placeholder(homeService.getIcon()).into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return programmeList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProgrammeName;
        TextView tvDoctorName;
        TextView tvDate;
        TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            tvProgrammeName = (TextView) view.findViewById(R.id.tvProgrammeName);
            tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Programme programme = programmeList.get(getLayoutPosition());
            Intent intent = new Intent (activity, ProgrammeDetailActivity.class);
            intent.putExtra (AppConfigTags.EVENT_ID, programme.getId ());
            activity.startActivity (intent);
            activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
