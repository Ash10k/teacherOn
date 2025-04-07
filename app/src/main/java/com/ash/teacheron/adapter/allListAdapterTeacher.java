package com.ash.teacheron.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.R;
import com.ash.teacheron.Single_chat_room;
import com.ash.teacheron.ViewListUp;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.retrofit.model.requirementResponse;

import java.util.List;

public class allListAdapterTeacher extends RecyclerView.Adapter<allListAdapterTeacher.MyViewHolder>  {

    private List<requirementResponse.TutorRequest> arrayList, filteredList;
    String activityId = "", activityName = "";
    Context ctx;
   // BeneficiaryList.CallBackData callBackData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private  OnItemClickListener listener;

    public allListAdapterTeacher(Context ctx, List<requirementResponse.TutorRequest> arrayList) {

        this.arrayList = arrayList;

        this.ctx = ctx;
        //   this.callBackData = callBackData;
        filteredList = arrayList;
        //sharedPreferences = ctx.getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requiment_main_recyclerteacher, parent, false);
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

        holder.tv1.setText(filteredList.get(position).tutorOption+" | "+filteredList.get(position).tutorType);
        holder.tv2.setText( filteredList.get(position).requirements);
        holder.tv3.setText( filteredList.get(position).budget+" "+filteredList.get(position).budgetType);
        holder.tv4.setText(filteredList.get(position).location+" | "+filteredList.get(position).requirements);


        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent;
                Context context = holder.itemView.getContext();
                intent = new Intent(context,  ViewListUp.class);
                intent.putExtra("reqID",filteredList.get(position).id);
                intent.putExtra("subjectId",filteredList.get(position).subjectId);
                context.startActivity(intent);*/
              //  Toast.makeText(context, "putting ini:"+filteredList.get(position).id, Toast.LENGTH_SHORT).show();
                listener.onItemClick(filteredList.get(position), 1);
            }
        });

        holder.openEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Context context = holder.itemView.getContext();
                intent = new Intent(context,  Single_chat_room.class);
                intent.putExtra("receiver",filteredList.get(position).id);
                intent.putExtra("sender",filteredList.get(position).userId);
                context.startActivity(intent);
                //  Toast.makeText(context, "putting ini:"+filteredList.get(position).id, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2,tv3,tv4;

         CardView openView,openEdit,openClose;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            openView=itemView.findViewById(R.id.openView);
            openEdit=itemView.findViewById(R.id.openEdit);


        }
    }
    public void updateData(List<requirementResponse.TutorRequest> newData) {
        this.filteredList.clear();
        this.filteredList.addAll(newData);
        notifyDataSetChanged();
    }

    public void onclickList(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(requirementResponse.TutorRequest item, int instruction);
    }

}
