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

import java.util.List;

public class recommendedTeacherListAdapter extends RecyclerView.Adapter<recommendedTeacherListAdapter.MyViewHolder>  {

    private List< recommendedTeacherResponse.TutorRequest> arrayList, filteredList;
    String activityId = "", activityName = "";
    Context ctx;
   // BeneficiaryList.CallBackData callBackData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public recommendedTeacherListAdapter(Context ctx, List< recommendedTeacherResponse.TutorRequest> arrayList) {

        this.arrayList = arrayList;

        this.ctx = ctx;
        //   this.callBackData = callBackData;
        filteredList = arrayList;
        //sharedPreferences = ctx.getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requiment_main_recycler2, parent, false);
        final MyViewHolder recyclerViewHolder = new MyViewHolder(view);

       /* recyclerViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityId.equals("6") || activityId.equals("9") || activityId.equals("12")) {

                    Intent intent = new Intent(ctx, ImageCapture.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra(Constants.ACTIVITY_ID, activityId);
                    intent.putExtra(Constants.ACTIVITY_NAME, activityName);
                    ctx.startActivity(intent);
                }
            }
        });*/

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv1.setText(filteredList.get(position).name );
        holder.tv2.setText( filteredList.get(position).location);
        holder.tv3.setText( filteredList.get(position).teacherDetail.feeAmount+" "+filteredList.get(position).teacherDetail.feeSchedule);
        holder.tv4.setText(filteredList.get(position).teacherSubject.get(0).optionSubject.title);
        holder.tv_subject.setText(filteredList.get(position).teacherSubject.get(0).optionSubject.title);
        holder.tv_description.setText(filteredList.get(position).teacherMeta.speciality);
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Context context = holder.itemView.getContext();
                intent = new Intent(context,  ViewListUp.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2,tv3,tv4,tv_subject,tv_description;

         CardView openView,openEdit,openClose;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.location);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv_subject=itemView.findViewById(R.id.tv_subject);
            openView=itemView.findViewById(R.id.openView);
            openEdit=itemView.findViewById(R.id.openEdit);
            openClose=itemView.findViewById(R.id.openClose);
            tv_description=itemView.findViewById(R.id.tv_description);
        }
    }
}
