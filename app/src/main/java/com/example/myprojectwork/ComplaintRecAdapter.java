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

public class ComplaintRecAdapter  extends RecyclerView.Adapter<ComplaintRecAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Complaint> mUsers;

    private int count=1;

    public ComplaintRecAdapter(Context mContext, ArrayList<Complaint> mUsers){
        this.mUsers=mUsers;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ComplaintRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.complaint_single_layout,parent,false);
        return new ComplaintRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Complaint cmp = mUsers.get(position);
        holder.username.setText("Complaint no. "+count++);
        //if(user.getImageURL().equals("default")){
        //holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        //}else{

        //String uri = "@drawable/complaintimg11";
        //Glide.with(mContext).load(uri).into(holder.profile_image);
        //holder.profile_image.setImageResource(R.drawable.complaintimg11);
        //}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"Clicking",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(myContext, ComplainLoadActivity.class);
                intent.putExtra("key",cmp.getKey());
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

        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.username1);
            profile_image=itemView.findViewById(R.id.profile_img);

        }
    }
}
