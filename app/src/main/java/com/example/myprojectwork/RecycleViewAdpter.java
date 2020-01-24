package com.example.myprojectwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import static com.example.myprojectwork.MainActivity.myContext;


public class RecycleViewAdpter extends RecyclerView.Adapter<RecycleViewAdpter.ViewHolder> {
    private Context mContext;
    private ArrayList<User> mUsers;

    public RecycleViewAdpter(Context mContext,ArrayList<User> mUsers){
        this.mUsers=mUsers;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.users_single_layout,parent,false);
        return new RecycleViewAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user=mUsers.get(position);
        holder.username.setText(user.getUsername());
        //if(user.getImageURL().equals("default")){
            //holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        //}else{
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        //}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Clicking",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(myContext, MessageActivity.class);
                intent.putExtra("userid",user.getUid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;

        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.username1);
            profile_image=itemView.findViewById(R.id.profile_img);

        }
    }
}