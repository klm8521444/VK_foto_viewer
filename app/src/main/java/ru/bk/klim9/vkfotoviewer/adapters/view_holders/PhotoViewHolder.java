package ru.bk.klim9.vkfotoviewer.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ru.bk.klim9.vkfotoviewer.R;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public ImageView photo;
    public ProgressBar progressBar;

    public PhotoViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);
        photo = (ImageView) itemView.findViewById(R.id.photo);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        itemView.setOnClickListener(listener);
    }
}
