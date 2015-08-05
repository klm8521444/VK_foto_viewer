package ru.bk.klim9.vkfotoviewer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.bk.klim9.vkfotoviewer.activities.PhotoListActivity;
import ru.bk.klim9.vkfotoviewer.adapters.AlbumsAdapter;
import ru.bk.klim9.vkfotoviewer.model.Album;

public class AlbumsListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView list;
    private ArrayList<Album> albums;

    public static AlbumsListFragment newInstance() {
        return new AlbumsListFragment();
    }

    public AlbumsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        list = (RecyclerView) rootView.findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        loadAlbums(progressBar);

        return rootView;
    }

    private void loadAlbums(final ProgressBar progressBar) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("need_covers", 1);
        VKRequest getAlbumsRequest = new VKRequest("photos.getAlbums",
                new VKParameters(requestParams));
        getAlbumsRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray albumsArray = response.json.getJSONObject("response").getJSONArray("items");

                    albums = new ArrayList<>();
                    for (int i = 0; i < albumsArray.length(); i++) {
                        JSONObject album = albumsArray.getJSONObject(i);
                        albums.add(new Album(album.getLong("id"),
                                album.getString("title"),
                                album.getString("thumb_src")));
                    }

                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(new AlbumsAdapter(getActivity(), albums, AlbumsListFragment.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getActivity(), R.string.error_try_later, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_album:
                final int position = list.getChildLayoutPosition(view);
                Intent intent = new Intent(getActivity(), PhotoListActivity.class);
                intent.putExtra(PhotoListActivity.EXTRA_ALBUM_ID, albums.get(position).id);
                startActivity(intent);
                break;
        }
    }
}
