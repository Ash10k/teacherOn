package com.ash.teacheron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ash.teacheron.R;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.ChatResponseDataModelother;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class chat_adapterFragment extends ArrayAdapter<ChatResponseDataModelother.ChatUser> implements Filterable {

    Context c;
    List<ChatResponseDataModelother.ChatUser> list;
    List<ChatResponseDataModelother.ChatUser> filteredList;
    LayoutInflater inflater;

    public chat_adapterFragment(Context context, List<ChatResponseDataModelother.ChatUser> objects) {
        super(context, 0, objects);
        this.c = context;
        this.list = new ArrayList<>(objects);
        this.filteredList = new ArrayList<>(objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public ChatResponseDataModelother.ChatUser getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_chat_list, parent, false);
        }

        TextView date_joined = convertView.findViewById(R.id.date_joined);
        TextView name = convertView.findViewById(R.id.description);
        ImageView profile_img = convertView.findViewById(R.id.profile_img);
        TextView sender = convertView.findViewById(R.id.sender);

        try {
            ChatResponseDataModelother.ChatUser currentUser = getItem(position);

            date_joined.setText(currentUser.getName());
            name.setText(currentUser.recentMessage);
            sender.setText(currentUser.recentMessageHumanTime);

            Glide.with(convertView.getContext())
                    .load(currentUser.userDetails.profileImageUrl)

                    .into(profile_img);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ChatResponseDataModelother.ChatUser> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.addAll(list);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ChatResponseDataModelother.ChatUser user : list) {
                        if (user.getName().toLowerCase().contains(filterPattern) ||
                                (user.recentMessage != null && user.recentMessage.toLowerCase().contains(filterPattern))) {
                            filteredResults.add(user);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                results.count = filteredResults.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<ChatResponseDataModelother.ChatUser>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}

