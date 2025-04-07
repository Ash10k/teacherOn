package com.ash.teacheron.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ash.teacheron.R;
import com.ash.teacheron.Single_chat_room;
import com.ash.teacheron.adapter.chat_adapter;
import com.ash.teacheron.adapter.chat_adapterFragment;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.ChatResponseDataModelother;
import com.ash.teacheron.retrofit.model.recommendedRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MessageFragment extends Fragment {

    View fragmentView;

    AuthAPI apiInterface = RetrofitBuilder.build().create(AuthAPI.class);
    String TAG = "Chat_friend_request_list" ;
    TextView show;

    LinearLayout l1;
    SwipeRefreshLayout swipeRefreshLayout;
    chat_adapterFragment adapter;
    ListView listView;
    List<ChatResponseDataModelother.ChatUser> list;
    ImageView icon_group;
    ImageView open_nav;
    int usertype;
    String option = "",userId,token;
    TextInputEditText searchBox;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        //Toast.makeText(getContext(), "inside msg", Toast.LENGTH_SHORT).show();
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        open_nav=fragmentView.findViewById(R.id.open_nav);
        swipeRefreshLayout=fragmentView.findViewById(R.id.swipeRefreshLayout);
        show=fragmentView.findViewById(R.id.findnot);
        searchBox=fragmentView.findViewById(R.id.searchBox);
        show.setVisibility(View.INVISIBLE);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                load_chat();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load_chat();

            }
        });

        listView = fragmentView.findViewById(R.id.listview);
       // icon_group=fragmentView.findViewById(R.id.icon_group);
        load_chat();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Single_chat_room.class);
                intent.putExtra("sender", Integer.parseInt(list.get(position).studentMetaId));
                intent.putExtra("receiver", list.get(position).getId());
                startActivity(intent);
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return fragmentView;
    }

    public void load_chat() {

        swipeRefreshLayout.setRefreshing(true);

        Call<ChatResponseDataModelother> myCall = apiInterface.get_chat_room(token,new recommendedRequest(userId));
        // Log.d(TAG, "claim_new: order:"+ord_ID+"type: "+type+"product"+prods);
        myCall.enqueue(new Callback<ChatResponseDataModelother>() {
            @Override
            public void onResponse(Call<ChatResponseDataModelother> call, Response<ChatResponseDataModelother> response) {

                Log.d(TAG, "res body chat: " + new Gson().toJson(response.body()));
                if (response.isSuccessful())
                {
                    if (response.body().status.equals("success"))
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        list = response.body().data;
                        //Collections.reverse(list);

                        if (list.size()==0)
                        {
                            show.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            show.setVisibility(View.INVISIBLE);
                        }




                        try{
                            adapter = new chat_adapterFragment(getContext(), getRecentChat(response.body().data));
                            listView.setAdapter(adapter);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }
                else {
                    try{
                        Toast.makeText(getContext(), " Data is empty ", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Data is empty " + new Gson().toJson(response.body()));

                    }
                    catch( Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
                //loader_dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ChatResponseDataModelother> call, Throwable t)
            {
                Toast.makeText(getContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                //Log.d(TAG,Log.getStackTraceString(new Exception()));
                // loader_dialog.dismiss();
            }
        });

    }

    private List<ChatResponseDataModelother.ChatUser> getRecentChat(List<ChatResponseDataModelother.ChatUser> mList)
    {
        List<ChatResponseDataModelother.ChatUser> chatList=new ArrayList<>();
        HashMap<String,String> chatTimeHistory=new HashMap<>();
        for(int i=0;i<mList.size();i++)
        {
            chatTimeHistory.put(String.valueOf(mList.get(i).getId()),"");
        }
        return mList;
    }

    @Override
    public void onResume() {
        super.onResume();
        load_chat();
    }


}