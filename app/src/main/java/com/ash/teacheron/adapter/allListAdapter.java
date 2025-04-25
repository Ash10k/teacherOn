package com.ash.teacheron.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.AddNewRequirement;
import com.ash.teacheron.BothBottomAndSideNavigation;
import com.ash.teacheron.EditPost;
import com.ash.teacheron.MainActivity;
import com.ash.teacheron.R;
import com.ash.teacheron.RecommndedAct;
import com.ash.teacheron.ViewListUp;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.requirementResponse;

import java.util.ArrayList;
import java.util.List;

public class allListAdapter extends RecyclerView.Adapter<allListAdapter.MyViewHolder>  {

    private List<requirementResponse.TutorRequest> arrayList, filteredList;
    String activityId = "", activityName = "";
    Context ctx;
   // BeneficiaryList.CallBackData callBackData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public allListAdapter(Context ctx, List<requirementResponse.TutorRequest> arrayList) {

        this.arrayList = arrayList;

        this.ctx = ctx;
        //   this.callBackData = callBackData;
        filteredList = arrayList;
        //sharedPreferences = ctx.getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requiment_main_recycler, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_new_requirement, parent, false);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.tv1.setText(filteredList.get(position).tutorOption+" "+filteredList.get(position).subject.title+" "+filteredList.get(position).tutorType);
        holder.tv2.setText( filteredList.get(position).requirements);
        holder.tv3.setText( filteredList.get(position).budget+" "+filteredList.get(position).budgetType);
        holder.tv4.setText(filteredList.get(position).location+" | "+filteredList.get(position).requirements);
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent;
                Context context = holder.itemView.getContext();
                intent = new Intent(context,  ViewListUp.class);
                intent.putExtra("reqID",filteredList.get(position).id);
                intent.putExtra("subjectId",filteredList.get(position).subjectId);
                context.startActivity(intent);
                //Toast.makeText(context, "putting ini:"+filteredList.get(position).id, Toast.LENGTH_SHORT).show();
            }
        });

        holder.openEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Context context = holder.itemView.getContext();
                intent = new Intent(context,  EditPost.class);
                intent.putExtra("reqID",filteredList.get(position).id);
                intent.putExtra("subjectId",filteredList.get(position).subjectId);

                intent.putExtra("budget",filteredList.get(position).budget);
                intent.putExtra("budget_currency_id",filteredList.get(position).budgetCurrencyId);
                intent.putExtra("budget_type",filteredList.get(position).budgetType);
                intent.putExtra("communicate_language_id",filteredList.get(position).communicateLanguageId);
                intent.putExtra("gender_preference",filteredList.get(position).genderPreference);
                intent.putExtra("level_id",filteredList.get(position).levelId);
                intent.putExtra("location",filteredList.get(position).location);
                intent.putExtra("requirement_type",filteredList.get(position).requirementType);
                intent.putExtra("requirements",filteredList.get(position).requirements);
                intent.putExtra("travel_limit",filteredList.get(position).travelLimit);
                intent.putExtra("tutor_from_country_id",filteredList.get(position).tutorFromCountryId);
                intent.putExtra("tutor_option",filteredList.get(position).tutorOption);
                intent.putExtra("tutor_type",filteredList.get(position).tutorType);
                intent.putExtra("user_id",filteredList.get(position).userId);
                intent.putExtra("id",filteredList.get(position).id);
                context.startActivity(intent);
                //Toast.makeText(context, "putting ini:"+filteredList.get(position).budget, Toast.LENGTH_SHORT).show();

            }
        });


        holder.openClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete post requirement")
                        .setMessage("Do you want to delete this post?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    filteredList.remove(position);
                                    notifyDataSetChanged();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2,tv3,tv4;

        LinearLayout openView,openEdit,openClose;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            openView=itemView.findViewById(R.id.openView);
            openEdit=itemView.findViewById(R.id.openEdit);
            openClose=itemView.findViewById(R.id.openClose);

        }
    }
}
