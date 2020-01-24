package com.example.myprojectwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public  static final int MSG_TYPE_LEFT=0;
    public  static final int MSG_TYPE_RIGT=1;

    private Context mContext;
    private ArrayList<Chats> mChats;
    private String imageUrl;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext,ArrayList<Chats> mChats,String imageUrl){
        this.mChats=mChats;
        this.mContext=mContext;
        this.imageUrl=imageUrl;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chats Chats=mChats.get(position);
        holder.show_message.setText(Chats.getMessage());
        //if(true)){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
       // }
        //else{
         //   Glide.with(mContext).load(imageUrl).into(holder.profile_image);
       // }

    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            show_message=itemView.findViewById(R.id.show_msg);
            profile_image=itemView.findViewById(R.id.profile_img_box);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChats.get(position).getSender().equals(fuser.getUid())){
            return  MSG_TYPE_RIGT;
        }else
        {return  MSG_TYPE_LEFT;}
    }
}