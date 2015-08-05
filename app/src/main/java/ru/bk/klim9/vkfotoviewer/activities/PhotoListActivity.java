package ru.bk.klim9.vkfotoviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ru.bk.klim9.vkfotoviewer.R;
import ru.bk.klim9.vkfotoviewer.adapters.PhotosAdapter;
import ru.bk.klim9.vkfotoviewer.model.Photo;

public class PhotoListActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_ALBUM_ID = "album_id";

    private RecyclerView list;
    private long albumId;
    private ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        albumId = intent.getLongExtra(EXTRA_ALBUM_ID, -1);
        if (albumId == -1) {
            Toast.makeText(this, R.string.error_try_later, Toast.LENGTH_LONG).show();
        } else {
            list = (RecyclerView) findViewById(R.id.list);
            list.setHasFixedSize(true);
            list.setItemAnimator(new DefaultItemAnimator());
            final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            list.setLayoutManager(layoutManager);

            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

            loadPhotos(progressBar);
        }
    }

    private void loadPhotos(final ProgressBar progressBar) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("album_id", albumId);
        VKRequest photosRequest = new VKRequest("photos.get", new VKParameters(requestParams));
        photosRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray photosArray = response.json.getJSONObject("response").getJSONArray("items");

                    photos = new ArrayList<>();
                    for (int i = 0; i < photosArray.length(); i++) {
                        JSONObject photo = photosArray.getJSONObject(i);
                        photos.add(new Photo(photo.getLong("id"),
                                photo.getString("photo_130"),
                                photo.getString("photo_604")));
                    }

                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(new PhotosAdapter(PhotoListActivity.this, photos, PhotoListActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PhotoListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(PhotoListActivity.this, R.string.error_try_later, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photoLayout:
                final int position = list.getChildLayoutPosition(view);
                Intent intent = new Intent(this, PhotoActivity.class);
                Photo photo = photos.get(position);
                final String photoSrc = photo.photoSrc_604.isEmpty() ? photo.photoSrc_130 : photo.photoSrc_604;
                intent.putExtra(PhotoActivity.EXTRA_PHOTO_SRC, photoSrc);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_list;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.photos);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
