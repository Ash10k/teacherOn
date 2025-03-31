package com.ash.teacheron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ash.teacheron.R;
import com.ash.teacheron.retrofit.model.recommendedProfile;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;

import java.util.List;


public class proExperience_adapter2 extends ArrayAdapter {
    Context c;
    private List<recommendedProfile.TutorRequest.TeacherExperience> list;
    RelativeLayout color_value_for_customer;
    String TAG = "claimed gvn grn products adapter";

    TextView price, product_name, amt, items, items_ordered, penalty, penalty_reason;
    ImageView imageView;

    public proExperience_adapter2(Context context, List objects) {
        super(context, 0, objects);
        this.c = context;
        list = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.custom_single_item_adjustment, parent, false);
        }

        product_name = convertView.findViewById(R.id.name);


        items = convertView.findViewById(R.id.items);

        items_ordered = convertView.findViewById(R.id.items_ordered);


        if (list != null) {
            product_name.setText( (list.get(position).institutionName)+"\n"+list.get(position).jobDescription );
            items.setText(String.valueOf(""+list.get(position).designation));
            items_ordered.setText(String.valueOf( (""+list.get(position).association)));
        }
        return convertView;
    }
}

