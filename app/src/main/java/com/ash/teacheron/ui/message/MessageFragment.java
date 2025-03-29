package com.ash.teacheron.ui.message;

import android.content.Intent;
import android.os.Bundle;
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
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.recommendedRequest;
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
    chat_adapter adapter;
    ListView listView;
    List<ChatResponseDataModel.ChatUser> list;
    ImageView icon_group;
    ImageView open_nav;
    int usertype;
    String option = "",userId,token;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        open_nav=fragmentView.findViewById(R.id.open_nav);
        swipeRefreshLayout=fragmentView.findViewById(R.id.swipeRefreshLayout);
        show=fragmentView.findViewById(R.id.findnot);
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


        return fragmentView;
    }

    public void load_chat() {

        swipeRefreshLayout.setRefreshing(true);

        Call<ChatResponseDataModel> myCall = apiInterface.get_chat_room(token,new recommendedRequest(userId));
        // Log.d(TAG, "claim_new: order:"+ord_ID+"type: "+type+"product"+prods);
        myCall.enqueue(new Callback<ChatResponseDataModel>() {
            @Override
            public void onResponse(Call<ChatResponseDataModel> call, Response<ChatResponseDataModel> response) {

                Log.d(TAG, "res body chat: " + new Gson().toJson(response.body()));
                if (response.isSuccessful())
                {
                    if (response.body().status.equals("success"))
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        list = response.body().getData();
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
                            adapter = new chat_adapter(getContext(), getRecentChat(response.body().getData()));
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
            public void onFailure(Call<ChatResponseDataModel> call, Throwable t)
            {
                Toast.makeText(getContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                //Log.d(TAG,Log.getStackTraceString(new Exception()));
                // loader_dialog.dismiss();
            }
        });

    }

    private List<ChatResponseDataModel.ChatUser> getRecentChat(List<ChatResponseDataModel.ChatUser> mList)
    {
        List<ChatResponseDataModel.ChatUser> chatList=new ArrayList<>();
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