package com.example.mychatapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mychatapplication.Adapter.UserAdapter;
import com.example.mychatapplication.Model.Chat;
import com.example.mychatapplication.Model.User;
import com.example.mychatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<String> userList ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(firebaseUser.getUid())){
                       userList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())){
                        userList.add(chat.getSender());
                    }
                }
                readChat();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
   private void readChat(){

        mUsers=new CopyOnWriteArrayList<>(new ArrayList<User>());

        reference=FirebaseDatabase.getInstance().getReference().child("User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              mUsers.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    User user=snapshot.getValue(User.class);
                    for (String id : userList){
                        if(user.getId().equals(id)){
                            if(mUsers.size() !=0){
                                    for (User user1 : mUsers) {

                                        if (!user.getId().equals(user1.getId())) {
                                            if(!mUsers.contains(user)) {
                                                mUsers.add(user);
                                            }
                                        }
                                    }


                            }
                            else {
                                if(!mUsers.contains(user)) {
                                    mUsers.add(user);
                                }
                            }

                        }
                    }
                }

                userAdapter=new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   }


}







































