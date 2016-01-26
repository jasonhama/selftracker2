package edu.uw.jjhama.selftracker;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

/**
 * Created by iguest on 1/25/16.
 */
public class DetailFragment extends Fragment {
    private static final String TAG = "DetailFragment";
    Caffine c;
    public DetailFragment(){
        //required empty
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.detailfragment, container, false);

        //get the bundle from the MasterListFragment
        Bundle bundle = getArguments();
        final String title = bundle.getString("title");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tracked");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {

                        //find where titles are the same and set data on fragment
                        if(item.getString("title").equals(title)){
                            Log.v(TAG, item.getInt("amount") +"");
                            Log.v(TAG, item.getDate("createdAt") + "");
                            TextView bacon = (TextView) rootView.findViewById(R.id.textView3);
                            bacon.setText(title);
                            TextView ham = (TextView) rootView.findViewById(R.id.textView4);
                            int amount = item.getInt("amount");
                            ham.setText(amount + "");
                            TextView chedder = (TextView) rootView.findViewById(R.id.textView5);
                            String date = item.getCreatedAt().toString();
                            chedder.setText(date);
                        }
                    }
                } else {
                    Log.v(TAG, "else is not an option");
                }
            }


        });

        return rootView;
    }
}
