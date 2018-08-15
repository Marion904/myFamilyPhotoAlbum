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
            Glide.with(context).load(picture.getUrl())
                    .placeholder(R.drawable.view_holder)
                    .into(holder.imageView);
//<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
            
            holder.textView.setText(picture.getCaption());


        }else{
            text=(Text) content;
            holder.textView.setText((content.getCaption()));
            //Glide.with(context).load(picture.getUrl()).into(holder.imageView);
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

