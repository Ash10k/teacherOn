package com.ash.teacheron.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ash.teacheron.R;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class R_Single_chat_adapter extends RecyclerView.Adapter<R_Single_chat_adapter.ViewHolder>   {

    //private   List<menu_products> items;
    private List<ChatResponseDataModel.ChatUser> list;
    private static final int MESSEGE_HOVER_CLICK=100;
    private final static int SENDER_ROW=111;
    private final static int RECEIVER_ROW=222;
    private OnItemClickListener listener;
    Context c;
    String TAG = "Content adapter";
    String currentUserID;

    public R_Single_chat_adapter(List<ChatResponseDataModel.ChatUser> items, Context context, String currentUserID) {
        //this.items = items;
        c = context;
        //list = items;


        list = items;
       // Collection<ChatResponseDataModel1.Datum> collection = list;
        //Collections.reverse(Collections.singletonList(collection));
        //list=Collections.reverse(Collections.singletonList(collection));
        this.currentUserID=currentUserID;

    }

    @Override
    public int getItemViewType(int position) {
   //     Log.d("HAMLET_DEBUG",list.get(position).getMessage()+" -> SENDER ID : "+list.get(position).getUserId()+"\t CURRENT USER ID : "+currentUserID);
//        int vType=list.get(position).getChatId()==Integer.parseInt(currentUserID)?RECEIVER_ROW:SENDER_ROW;
//        return vType;
        if (list.get(position).getId()==Integer.parseInt(currentUserID))
            return RECEIVER_ROW;
        return SENDER_ROW;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        if (viewType==SENDER_ROW)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_row, parent, false);
        }
        else {
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_row, parent, false);
        }
        ViewHolder vh= new ViewHolder(v);
        vh.setIsRecyclable(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        //RelativeLayout view_details ;


        TextView text_label,timestamp,user_name;
        CardView crd2;
        ImageView translate,delete;
       // RelativeLayout more;

        public ViewHolder(View itemView)
        {
            super(itemView);

            text_label=itemView.findViewById(R.id.text_label);
            crd2=itemView.findViewById(R.id.crd2);
          //  translate=itemView.findViewById(R.id.transal);
            //  delete=itemView.findViewById(R.id.del);
           // more=itemView.findViewById(R.id.more);
            timestamp=itemView.findViewById(R.id.timestamp);
            user_name=itemView.findViewById(R.id.user_name);
        }

        public void bind(final ChatResponseDataModel.ChatUser item, final OnItemClickListener listener)
        {

            try
            {

                text_label.setText(String.valueOf(item.recentMessage));

                /*if (item.getConverted_state()==1)
                {
                    text_label.setTextColor(itemView.getContext().getResources().getColor(R.color.purple_200));
                }
                else
                {
                    text_label.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                }*/
              //  more.setVisibility(View.GONE);
                timestamp.setText(String.valueOf( (item.recentMessageHumanTime)));
                user_name.setText(String.valueOf(item.name));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, 1, getAdapterPosition());

                    // listener.onItemClick(item, 3);
                   /* if (more.getVisibility() == View.VISIBLE)
                    {
                        more.setVisibility(View.GONE);

                    }
                    else
                    {
                        more.setVisibility(View.VISIBLE);
                    }*/

                }
            });

           /* translate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, 1, getAdapterPosition());

                }
            });*/


//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(item, 2, getAdapterPosition());
//                }
//            });

           /* text_label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, MESSEGE_HOVER_CLICK, getAdapterPosition());

                }
            });*/

        }

        private String getFormatedDate(String created_at)
        {
            String formatedDate="";
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(created_at);
                formatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("ERRORHamlet",e.getMessage());
            }
            return formatedDate;
        }


    }

    public interface OnItemClickListener {
        void onItemClick(ChatResponseDataModel.ChatUser item, int instruction, int adapterPosition);

        void onLongClicked(ChatResponseDataModel.ChatUser item, int instruction);
    }


    public void onclickList(OnItemClickListener listener) {

        this.listener = listener;
    }



}
