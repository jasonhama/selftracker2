package edu.uw.jjhama.selftracker;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class SummaryFragment extends android.support.v4.app.Fragment {

    int count;

    private static final String TAG = "SummaryFragment";

    private ArrayAdapter<Caffine> adapter; //adapter for list view

    public SummaryFragment(){
        //required empty
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.summaryfragment, container, false);

        //check for same title
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tracked");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {

                        //count it all up
                        count += item.getInt("amount");

                    }
                } else {
                    Log.v(TAG, "else is not an option");
                }
                String ret = count + "";
                TextView bacon = (TextView) rootView.findViewById(R.id.amountVal);
                bacon.setText(ret);
            }
        });

        return rootView;
    }

}
