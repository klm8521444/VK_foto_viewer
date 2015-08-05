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
import ru.bk.klim9.vkfotoviewer.adapters.view_holders.AlbumViewHolder;
import ru.bk.klim9.vkfotoviewer.model.Album;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

    private final Context context;
    private final ArrayList<Album> albums;
    private final View.OnClickListener listener;

    public AlbumsAdapter(Context context, ArrayList<Album> albums, View.OnClickListener listener) {
        this.context = context;
        this.albums = albums;
        this.listener = listener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(context)
                .inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(album.thumbSrc)
                .into(new GlideDrawableImageViewTarget(holder.albumThumb) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        holder.albumTitle.setText(album.title);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
