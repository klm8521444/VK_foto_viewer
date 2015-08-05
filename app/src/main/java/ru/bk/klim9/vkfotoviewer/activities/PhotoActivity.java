package ru.bk.klim9.vkfotoviewer.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import ru.bk.klim9.vkfotoviewer.R;

public class PhotoActivity extends BaseActivity {

    public static final String EXTRA_PHOTO_SRC = "photo_id";

    private String photoSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Прячем statusBar (строчка со временем, батарейкой и пр.)
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        photoSrc = getIntent().getStringExtra(EXTRA_PHOTO_SRC);
        loadPhoto();
    }

    private void loadPhoto() {
        final ImageView photoView = (ImageView) findViewById(R.id.photo);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Glide.with(this)
                .load(photoSrc)
                .into(new GlideDrawableImageViewTarget(photoView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_photo;
    }

    @Override
    protected String getTitleToolBar() {
        return "";
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }
}
