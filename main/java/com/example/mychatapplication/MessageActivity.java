package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mychatapplication.Adapter.MessageAdapter;
import com.example.mychatapplication.Model.Chat;
import com.example.mychatapplication.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageView profie_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profie_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        btn_send=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        intent=getIntent();
         final String userid =intent.getStringExtra("userid");

         btn_send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String msg=text_send.getText().toString();
                 if(!msg.equals("")){
                     sendmessage(firebaseUser.getUid(),userid,msg);
                 }
                 else{
                     Toast.makeText(MessageActivity.this,"You cant send a empty message",Toast.LENGTH_SHORT);
                 }
                 text_send.setText("");
             }
         });


        reference= FirebaseDatabase.getInstance().getReference().child("User").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                profie_image.setImageResource(R.drawable.ic_person_black_24dp);


                readmessage(firebaseUser.getUid(),userid,user.getImageURL());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmessage(String sender,String receiver,String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        reference.child("Chats").push().setValue(hashMap);

    }
    private void readmessage(final String myid,final String userid,final  String imageURL){
        mChat=new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference().child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);

                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid)  ||
                        chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }
                }
                messageAdapter =new MessageAdapter(MessageActivity.this,mChat,imageURL);
                recyclerView.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
















