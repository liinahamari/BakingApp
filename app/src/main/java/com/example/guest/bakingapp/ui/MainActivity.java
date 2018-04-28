package com.example.guest.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.squareup.haha.perflib.Main;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

public class MainActivity extends BaseActivity implements MainFragment.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected Fragment getFragment() {
        return new MainFragment();
    }

    @Override
    public void onItemClicked(Reciep reciep, int position) {
            Log.d(TAG, reciep.getName() + " chosen.");
            if (isNetAvailable(this)) {
//                if (findViewById(R.id.twopane_detail_container) == null) {
                    startActivity(DetailActivity.newIntent(this, reciep));
//                } else {
//                    Fragment detailFragment = DetailFragment.newInstance(movie);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.twopane_detail_container, detailFragment)
//                            .commit();
//                }
//            } else {
//                Toast.makeText(this, "Lack of connection, try again later...", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
