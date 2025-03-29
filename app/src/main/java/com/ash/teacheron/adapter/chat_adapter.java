package com.ash.teacheron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ash.teacheron.R;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.bumptech.glide.Glide;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class chat_adapter extends ArrayAdapter {

        Context c;

        List<ChatResponseDataModel.ChatUser> list;
        String TAG="products_adapter";

        TextView date_joined,name,time_received,sender;
        ImageView profile_img;

        public chat_adapter(Context context, List objects)
        {
            super(context, 0, objects);
            this.c=context;
            list=objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            if (convertView==null)
            {
                convertView= LayoutInflater.from(c).inflate(R.layout.custom_chat_list,parent,false);

            }

            date_joined=convertView.findViewById(R.id.date_joined);
            name=convertView.findViewById(R.id.description);
            profile_img=convertView.findViewById(R.id.profile_img);
           // time_received=convertView.findViewById(R.id.time_received);
          sender=convertView.findViewById(R.id.sender);

            if (list!=null)
            {
                try
                {
                    date_joined.setText(String.valueOf(list.get(position).getName()));
                    Glide.with(convertView.getContext()).load(list.get(position).userDetails.profileImageUrl).into(profile_img);
                    name.setText(list.get(position).recentMessage);
                    sender.setText(list.get(position).recentMessageHumanTime);

                   // time_received.setText(String.valueOf( list.get(position).getId()));


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            return convertView;
        }



     String converter_time(String timestamp)
    {
        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = Instant.parse(timestamp);
        }

        // Convert Instant to LocalDateTime in local timezone
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

        // Define the desired date-time format
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }

        // Format the LocalDateTime
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDateTime = localDateTime.format(formatter);
        }

        System.out.println("Local Time: " + formattedDateTime);
        return formattedDateTime;
    }

}


