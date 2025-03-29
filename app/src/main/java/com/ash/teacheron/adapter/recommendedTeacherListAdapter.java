package com.ash.teacheron.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.R;
import com.ash.teacheron.ViewListUp;
import com.ash.teacheron.retrofit.model. recommendedTeacherResponse;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class recommendedTeacherListAdapter extends RecyclerView.Adapter<recommendedTeacherListAdapter.MyViewHolder>  {

    private List< recommendedTeacherResponse.TutorRequest> arrayList, filteredList;
    String activityId = "", activityName = "";
    Context ctx;
   // BeneficiaryList.CallBackData callBackData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private OnItemClickListener listener;
    public recommendedTeacherListAdapter(Context ctx, List< recommendedTeacherResponse.TutorRequest> arrayList ) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        filteredList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requiment_main_recycler2, parent, false);
        final MyViewHolder recyclerViewHolder = new MyViewHolder(view);



        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try{
            holder.tv1.setText(filteredList.get(position).name );
            holder.tv2.setText( filteredList.get(position).location);
            Glide.with(holder.itemView.getContext())
                    .load(filteredList.get(position).profileImageUrl)
                    .placeholder(R.drawable.baseline_account_circle_24) // Show default image while loading
                    .error(R.drawable.baseline_account_circle_24) // Show default image if loading fails
                    .into(holder.profile_image);

            //        holder.tv3.setText( filteredList.get(position).teacherDetail.feeAmount+" "+filteredList.get(position).teacherDetail.feeSchedule);
            if (filteredList.get(position).teacherSubject!=null)
            {
                holder.tv4.setText(filteredList.get(position).teacherSubject.get(0).optionSubject.title);
                holder.tv_subject.setText(filteredList.get(position).teacherSubject.get(0).optionSubject.title);

            }
            if (filteredList.get(position).teacherMeta!=null)
            {
                holder.tv_description.setText(filteredList.get(position).teacherMeta.speciality);
            }



            holder.openView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(filteredList.get(position), 1);
                }
            });

            holder.openEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(filteredList.get(position), 2);
                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2,tv3,tv4,tv_subject,tv_description;
        CircleImageView profile_image;
         CardView openView,openEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.location);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv_subject=itemView.findViewById(R.id.tv_subject);
            profile_image=itemView.findViewById(R.id.profile_image);
            openView=itemView.findViewById(R.id.openView);
            openEdit=itemView.findViewById(R.id.openEdit);

            tv_description=itemView.findViewById(R.id.tv_description);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(recommendedTeacherResponse.TutorRequest item, int instruction);
    }
    public void onclickList(OnItemClickListener listener) {
        this.listener = listener;
    }
}
