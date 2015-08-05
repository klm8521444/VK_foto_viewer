package ru.bk.klim9.vkfotoviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;

import ru.bk.klim9.vkfotoviewer.R;
import ru.bk.klim9.vkfotoviewer.adapters.view_holders.PhotoViewHolder;
import ru.bk.klim9.vkfotoviewer.model.Photo;

public class PhotosAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private final Context context;
    private final ArrayList<Photo> photos;
    private final View.OnClickListener listener;

    public PhotosAdapter(Context context, ArrayList<Photo> photos, View.OnClickListener listener) {
        this.context = context;
        this.photos = photos;
        this.listener = listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
        final Photo photo = photos.get(position);
        final String photoSrc = photo.photoSrc_604.isEmpty() ? photo.photoSrc_130 : photo.photoSrc_604;
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(photoSrc)
                .into(new GlideDrawableImageViewTarget(holder.photo) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
