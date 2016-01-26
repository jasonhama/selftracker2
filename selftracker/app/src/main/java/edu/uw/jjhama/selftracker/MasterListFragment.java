package edu.uw.jjhama.selftracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iguest on 1/25/16.
 */
public class MasterListFragment extends Fragment {

    private int count;

    //private Button button;
    private static final String TAG = "MasterListFragment";

    private ArrayAdapter<Caffine> adapter; //adapter for list view


    public MasterListFragment(){
        //required empty
    }

    private OnCaffineSelectionListener callback;

    public interface OnCaffineSelectionListener {
        public void onCaffineSelected(Caffine caffine);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.masterviewfragment, container, false);
        Log.v(TAG, "Loaded in");




        Button button = (Button) rootView.findViewById(R.id.recordNew);

        final ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList =(lv.getItemAtPosition(myItemInt).toString());
                Log.v(TAG, "selected something~" + selectedFromList);

                Bundle bundle = new Bundle();
                bundle.putString("title", selectedFromList);

                DetailFragment detail = new DetailFragment();
                detail.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, detail)
                        .addToBackStack(null)
                        .commit();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RecordFragment frag = new RecordFragment();
                frag.show(getActivity().getSupportFragmentManager(), "banana");


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container1, new MasterListFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        /** List View **/
        //model (starts out empty)
        ArrayList<Caffine> list = new ArrayList<Caffine>();

        //controller
        adapter = new ArrayAdapter<Caffine>(
                getActivity(), R.layout.listitem, R.id.txtItem, list);

        //support ListView or GridView
        AdapterView listView = (AdapterView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);


        //put everything from parse here in reverse order
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tracked");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    for(ParseObject item : objects){
                        String title = item.getString("title");
                        int amount =  item.getInt("amount");
                        count += amount;
                        Date date = item.getDate("createdAt");
                        adapter.insert((new Caffine(title,amount,date)),0);
                    }
                } else {
                    Log.v(TAG, "else is not an option");
                }
            }
        });

        //respond to ListItem clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Caffine caffine = (Caffine) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + caffine.toString());
                String s = caffine.toString();


                Bundle bun = new Bundle();
                bun.putString("title", s);

                DetailFragment detail = new DetailFragment();
                detail.setArguments(bun);


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, detail)
                        .addToBackStack(null)
                        .commit();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, new MasterListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return rootView;
    }


}
