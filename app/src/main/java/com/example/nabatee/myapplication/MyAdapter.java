package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Ads[] AdsItem;
   // private List<Ads> mUserLsit=new ArrayList<>();
    private List<Ads> adsList=new ArrayList<>();
   // public Ads asd;
    private Context mContext;
    public MyAdapter(Ads[] adsitem){this.AdsItem=adsitem;}

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_ads,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    public MyAdapter(Context mContext, List<Ads> mUserLsit) {
        this.mContext=mContext;
        this.adsList = mUserLsit;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
     Ads asd=adsList.get(i);
     final String keys;
      //  Picasso.get().load(asd.ADimage).into(viewHolder.ADImage);
        Picasso.get().load(asd.ADimage).resize(430,350).into(viewHolder.ADImage);
        viewHolder.ADName.setText(asd.Name);
        keys=asd.keyID;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"this is key "+keys,Toast.LENGTH_SHORT).show();
                Intent ADintent=new Intent(mContext,AdsContent.class);
                ADintent.putExtra("Key",keys);
                ADintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(ADintent);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ADName;
        public ImageView ADImage;
        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            ADName=itemLayoutView.findViewById(R.id.CardName);
            ADImage=itemLayoutView.findViewById(R.id.ADSImage);
        }
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }
}
