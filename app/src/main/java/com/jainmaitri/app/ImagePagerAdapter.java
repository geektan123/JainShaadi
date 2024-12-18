package com.jainmaitri.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {
    private List<String> imageList; // Change the type to String for storing image URLs
    private LayoutInflater inflater;
    private Context context;

    public ImagePagerAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_slide_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (isActivityValid()) {
            // Load images from Firebase Storage using Glide
            Glide.with(holder.itemView.getContext())
                    .load(imageList.get(position))
                    .into(holder.imageView);
        }
    }
    private boolean isActivityValid() {
        return context instanceof Activity && !((Activity) context).isDestroyed() && !((Activity) context).isFinishing();
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slide_image_view);
        }
    }
}
