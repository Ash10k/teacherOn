package com.ash.teacheron;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.adapter.R_Single_chat_adapter;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel1;
import com.ash.teacheron.retrofit.model.PostNewMessage_response;
import com.ash.teacheron.retrofit.model.SendPostMsg;
import com.ash.teacheron.retrofit.model.recommendedRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_chat_room extends AppCompatActivity {

    String id;
    AuthAPI apiInterface = RetrofitBuilder.build().create(AuthAPI.class);

    int chatLanugageID;
    String TAG = "Single_chat_room", Token ;

    EditText message_input;
    TextView send;
    String message_to_send;
    List<ChatResponseDataModel.ChatUser> list, MainList, Transalte_array;
    List<ChatResponseDataModel.ChatUser> list1;
    //single_chat_adapter adapter;
    R_Single_chat_adapter adapter;
    RecyclerView listView;

    View loader_view;
    AlertDialog loader_dialog;
    LinearLayoutManager layoutManager;
   // Dialog dialog;
    private boolean ischecked;
    int count = 1, last_page = 0, first_page = 1, usertype, isprivate;
    private NestedScrollView nsvMain;
    ImageView show_more;
    public static String Original_msg = "";
    int count_msg_toggle = 0, pos = -1;

    int receiver,sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_chat_room);
        MainList = new ArrayList<>();
        MainList.clear();

        //id= String.valueOf((getIntent().getIntExtra("contact_id",-1)));
        //Log.d("DEBUG_USER_ID", id);

        receiver=getIntent().getIntExtra("receiver",-1);
        sender=getIntent().getIntExtra("sender",-1);

      //  Toast.makeText(this, "Receiver: "+receiver+" Sender: "+sender, Toast.LENGTH_SHORT).show();




        Transalte_array = new ArrayList<>();
        Transalte_array.clear();

        //dialog = UiUtils.showProgress(Single_chat_room.this);

        //chatLanugageID = sharePreference.getLanguageID();

        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Single_chat_room.this);
        usertype=  (sharedPrefLocal.getUserId());
        Token=  sharedPrefLocal.getSessionId();



        //chat_id=getIntent().getStringExtra("chat_id");
        show_more = findViewById(R.id.show_more);
        if (usertype != 1) {
            show_more.setVisibility(View.GONE);
        } else {

            if (isprivate == 1)
                show_more.setVisibility(View.GONE);

            else {
                show_more.setVisibility(View.VISIBLE);
                show_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        Intent intent = new Intent(Single_chat_room.this, Participants.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        */
                    }
                });

            }

        }


        // Toast.makeText(Single_chat_room.this, String.valueOf( isprivate), Toast.LENGTH_SHORT).show();


        nsvMain = findViewById(R.id.form_m_list_fragment_nsvMain);
        //
        message_input = findViewById(R.id.message_input);
        send = findViewById(R.id.send);
        listView = findViewById(R.id.listview);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_to_send = message_input.getText().toString();
                if (message_to_send != null && !message_to_send.isEmpty()) {
                    post_chat(message_to_send);
                }
            }
        });


        if (!ischecked) {


            get_chat(count);

            ischecked = true;
        } else {

            get_chat(1);

            ischecked = false;
        }


        if (nsvMain != null) {
            nsvMain.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (count <= last_page) {

                        count++;
                        get_chat(count);
                        adapter.notifyDataSetChanged();
                    }


                }
                Log.d(TAG, "onCreate: triggered count: " + count);


            });
        }

//        final Handler ha=new Handler();
//        ha.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                get_chat();
//                ha.postDelayed(this, 5000);
//            }
//        }, 5000);

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                convert_language(Integer.parseInt(list.get(position).getId()),2,position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });*/


    }

    public void post_chat(String message_to_send) {

        Call<PostNewMessage_response> myCall = apiInterface.post_chat_message(  Token,  new SendPostMsg(receiver,sender,message_to_send));
        // Log.d(TAG, "claim_new: order:"+ord_ID+"type: "+type+"product"+prods);
        myCall.enqueue(new Callback<PostNewMessage_response>() {
            @Override
            public void onResponse(Call<PostNewMessage_response> call, Response<PostNewMessage_response> response) {

                Log.d(TAG, "response " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (response.code() == 201) {

                        Toast.makeText(Single_chat_room.this, " Message sent ", Toast.LENGTH_LONG).show();
                        message_input.setText("");
                        MainList.clear();
                        get_chat(1);
                    }

                } else {
                    Toast.makeText(Single_chat_room.this, " Data is empty ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Data is empty " + new Gson().toJson(response.body()));

                }
                //loader_dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PostNewMessage_response> call, Throwable t) {
                //Toast.makeText(Single_chat_room.this, "In failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }


    public void get_chat(int cnt) {

        Call<ChatResponseDataModel> myCall = apiInterface.get_chat_message(  Token,new recommendedRequest(String.valueOf(sender))  );

        myCall.enqueue(new Callback<ChatResponseDataModel>() {
            @Override
            public void onResponse(Call<ChatResponseDataModel> call, Response<ChatResponseDataModel> response) {

                Log.d(TAG, "response " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        list = response.body().getData();
                        // last_page = response.body().getChats();

                        for (int i = 0; i < list.size(); i++) {
                            MainList.add(list.get(i));
                        }


                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        adapter = new R_Single_chat_adapter(MainList, Single_chat_room.this, String.valueOf(usertype));

                        layoutManager = new LinearLayoutManager(Single_chat_room.this);
                        layoutManager.setReverseLayout(true);
                        layoutManager.setStackFromEnd(true);

                        listView.setLayoutManager(layoutManager);
                        listView.setAdapter(adapter);
                        listView.smoothScrollToPosition(0);

                        // listView.scrollToPosition(list.size());
                        count_msg_toggle = 1;

                        /* listView.setAdapter(adapter);
                         listView.smoothScrollToPosition(adapter.getItemCount()-1);*/

                    }

                } else {
                    Toast.makeText(Single_chat_room.this, " Data is empty ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Data is empty " + new Gson().toJson(response.body()));

                }
                //loader_dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ChatResponseDataModel> call, Throwable t) {
                // Toast.makeText(Single_chat_room.this, "In failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }



    private void scrollToBottom(final RecyclerView recyclerView) {
        // scroll to last item to get the view of last item
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        final int lastItemPosition = adapter.getItemCount() - 1;

        layoutManager.scrollToPositionWithOffset(lastItemPosition, 0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // then scroll to specific offset
                View target = layoutManager.findViewByPosition(lastItemPosition);
                if (target != null) {
                    int offset = recyclerView.getMeasuredHeight() - target.getMeasuredHeight();
                    layoutManager.scrollToPositionWithOffset(lastItemPosition, offset);
                }
            }
        });
    }
}