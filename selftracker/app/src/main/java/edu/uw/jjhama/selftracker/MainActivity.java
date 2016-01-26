package edu.uw.jjhama.selftracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.parse.Parse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        showSummaryFragment();
        showFavoritesFragment();
    }

    private void showSummaryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container1, new SummaryFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showFavoritesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container2, new MasterListFragment())
                .addToBackStack(null)
                .commit();
    }

    //back button stuff
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
               super.onBackPressed();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
