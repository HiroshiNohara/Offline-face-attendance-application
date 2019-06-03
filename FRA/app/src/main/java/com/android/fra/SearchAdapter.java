package com.android.fra;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fra.db.Face;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.view.View.VISIBLE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Face> mFaceList;
    private int opened = -1;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View managementView;
        CheckBox checkBoxView;
        ImageView imageView;
        TextView uidTextView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView departmentTextView;
        TextView postTextView;
        TextView emailTextView;
        FrameLayout detailInfoView;
        LinearLayout root_view;
        ImageButton record_button;

        public ViewHolder(View view) {
            super(view);
            managementView = view;
            checkBoxView = (CheckBox) view.findViewById(R.id.worker_ifCheck);
            imageView = (ImageView) view.findViewById(R.id.worker_gender);
            uidTextView = (TextView) view.findViewById(R.id.worker_uid);
            nameTextView = (TextView) view.findViewById(R.id.worker_name);
            phoneTextView = (TextView) view.findViewById(R.id.detail_phone);
            departmentTextView = (TextView) view.findViewById(R.id.detail_department);
            postTextView = (TextView) view.findViewById(R.id.detail_post);
            emailTextView = (TextView) view.findViewById(R.id.detail_email);
            detailInfoView = (FrameLayout) view.findViewById(R.id.detail_info);
            root_view = (LinearLayout) view.findViewById(R.id.root_view);
            record_button = (ImageButton) view.findViewById(R.id.detail_button);
        }
    }

    public SearchAdapter(List<Face> faceList) {
        mFaceList = faceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.information_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.record_button.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_record));
        holder.record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Face face = mFaceList.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, CalendarViewActivity.class);
                intent.putExtra("uid", face.getUid());
                ((Activity) mContext).startActivityForResult(intent, 1);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.checkBoxView.setVisibility(View.GONE);
        final Face face = mFaceList.get(position);
        holder.uidTextView.setText(face.getUid());
        holder.nameTextView.setText(face.getName());
        holder.phoneTextView.setText(face.getPhone());
        holder.departmentTextView.setText(face.getDepartment());
        if (face.getPost() != null && !face.getPost().equals("") && !face.getPost().equals("null")) {
            holder.postTextView.setText(face.getPost());
        } else {
            holder.postTextView.setText(R.string.management_info_null);
        }
        if (face.getEmail() != null && !face.getEmail().equals("") && !face.getEmail().equals("null")) {
            holder.emailTextView.setText(face.getEmail());
        } else {
            holder.emailTextView.setText(R.string.management_info_null);
        }

        if (face.getGender().equals("male")) {
            Glide.with(mContext).load(R.drawable.ic_male).into(holder.imageView);
        } else if (face.getGender().equals("female")) {
            Glide.with(mContext).load(R.drawable.ic_female).into(holder.imageView);
        }
        holder.detailInfoView.measure(0, 0);
        final int height = holder.detailInfoView.getMeasuredHeight();
        if (position == opened) {
            show(holder.detailInfoView, height);
        } else {
            dismiss(holder.detailInfoView, height);
        }

        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.detailInfoView.measure(0, 0);
                final int height = holder.detailInfoView.getMeasuredHeight();
                if (holder.detailInfoView.getVisibility() == View.VISIBLE) {
                    opened = -1;
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
                    int openedTemp = opened;
                    opened = position;
                    notifyItemChanged(openedTemp);
                    notifyItemChanged(opened);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFaceList.size();
    }

    public void show(final View v, int height) {
        v.setVisibility(VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

    public void dismiss(final View v, int height) {
        ValueAnimator animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    v.setVisibility(View.GONE);
                }
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

}


