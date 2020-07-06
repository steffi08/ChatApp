package com.example.mychatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.core.view.LayoutInflaterFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapplication.MessageActivity;
import com.example.mychatapplication.Model.User;
import com.example.mychatapplication.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    public UserAdapter(Context mContext,List<User> mUser){
        this.mContext=mContext;
        this.mUser=mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          final User user= mUser.get(position);
          holder.username.setText(user.getUsername());
          holder.imageURL.setImageResource(R.drawable.ic_person);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(mContext, MessageActivity.class);
                  intent.putExtra("userid",user.getId());
                  mContext.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         public TextView username;
         public ImageView imageURL;

         public ViewHolder(View itemView){
             super(itemView);
             username=itemView.findViewById(R.id.usernames);
             imageURL=itemView.findViewById(R.id.profile_images);

         }

    }

}
