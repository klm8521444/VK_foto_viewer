package ru.bk.klim9.vkfotoviewer.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.api.VKError;
import ru.bk.klim9.vkfotoviewer.R;
import ru.bk.klim9.vkfotoviewer.fragments.AlbumsListFragment;
import ru.bk.klim9.vkfotoviewer.fragments.LoginFragment;


public class MainActivity extends BaseActivity implements LoginFragment.OnLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getTitleToolBar());

        final String vkAppId = "4935736";
        VKSdk.initialize(vkSdkListener, vkAppId);

        if (VKSdk.wakeUpSession() || VKSdk.isLoggedIn()) {
            showAlbumsListFragment();
        } else {
            showLoginFragment();
        }
    }

    @Override
    public void onVKLoginPressed() {
        VKSdk.authorize(vkPermissions, true, false);
    }

    private void showLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                LoginFragment.newInstance()).commit();
    }

    private void showAlbumsListFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                AlbumsListFragment.newInstance()).commit();
    }

    private static final String[] vkPermissions = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS
    };

    private final VKSdkListener vkSdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError vkError) {
            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken vkAccessToken) {
            VKSdk.authorize(vkPermissions, true, false);
        }

        @Override
        public void onAccessDenied(VKError vkError) {
            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            showAlbumsListFragment();
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            showAlbumsListFragment();
        }
    };

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.albums);
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
