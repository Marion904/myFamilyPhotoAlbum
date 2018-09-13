package com.example.accueil.myfamilyphotoalbum.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.accueil.myfamilyphotoalbum.R;
import com.example.accueil.myfamilyphotoalbum.model.Content;
import com.example.accueil.myfamilyphotoalbum.model.Picture;
import com.example.accueil.myfamilyphotoalbum.model.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {



    private List<Picture> album;
    Picture picture;
    Context context;

    public MyAdapter(Context context,List<Picture> album) {
            this.album = album;
            this.context = context;
            }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_content_card, null);
            ViewHolder rcv = new ViewHolder(layoutView);
            return rcv;
            }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picture pic = album.get(position);

        if(context != null){
            //holder.imageView.setImageBitmap(load(picture.getUrl()));
            Glide.with(context).load(pic.getUrl())
                    .placeholder(R.drawable.view_holder)
                    .into(holder.imageView);
//<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

                //holder.textView.setText(picture.getCaption());


        }

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

