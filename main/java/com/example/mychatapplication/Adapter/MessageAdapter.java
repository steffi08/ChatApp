package com.example.mychatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapplication.MessageActivity;
import com.example.mychatapplication.Model.Chat;
import com.example.mychatapplication.Model.User;
import com.example.mychatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mchat;
    private String imageurl;

    FirebaseUser firebaseUser;


    public MessageAdapter(Context mContext,List<Chat> mchat,String imageurl) {
        this.mContext = mContext;
        this.mchat = mchat;
        this.imageurl=imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup  parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.right_chat_item,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.left_chat_item,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());
       // holder.imageURL.setImageResource(R.drawable.ic_person);



    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView imageURL;

        public ViewHolder(View itemView){
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message );
            imageURL=itemView.findViewById(R.id.profile_images);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mchat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;

        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}
