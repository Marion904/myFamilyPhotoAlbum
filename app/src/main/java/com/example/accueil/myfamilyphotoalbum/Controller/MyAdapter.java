package com.example.accueil.myfamilyphotoalbum.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accueil.myfamilyphotoalbum.R;
import com.example.accueil.myfamilyphotoalbum.model.Content;
import com.example.accueil.myfamilyphotoalbum.model.Picture;
import com.example.accueil.myfamilyphotoalbum.model.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {



    private List<Content> album;
    Picture picture;
    Text text;
    Context context;

    public MyAdapter(List<Content> album) {
            this.album = album;
            }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_content_card, null);
            ViewHolder rcv = new ViewHolder(layoutView);
            return rcv;
            }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Content content = album.get(position);
        if(content.toString().equals("Picture")){
            picture = (Picture) content;
            //holder.imageView.setImageBitmap(load(picture.getUrl()));
        }else{
            text=(Text) content;
            holder.textView.setText((content.getCaption()));
        }




//            Glide.with(context).load(content.getmImgChaussette()).into(holder.imageView);
            }

    @Override
    public int getItemCount() {
            return album.size();
            }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contentImage);
            textView = itemView.findViewById(R.id.contentText);
        }
    }

}

