package ru.bk.klim9.vkfotoviewer.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.bk.klim9.vkfotoviewer.R;

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    public ImageView albumThumb;
    public TextView albumTitle;
    public ProgressBar progressBar;

    public AlbumViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);
        albumThumb = (ImageView) itemView.findViewById(R.id.albumThumb);
        albumTitle = (TextView) itemView.findViewById(R.id.albumTitle);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        itemView.setOnClickListener(listener);
    }
}
