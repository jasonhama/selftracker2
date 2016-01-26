package edu.uw.jjhama.selftracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iguest on 1/25/16.
 */
public class RecordFragment extends DialogFragment {
    private static final String TAG = "RecordFragment";
    Button button;

    public RecordFragment(){
        //required empty
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(TAG,"Dialog called");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Dialog f = (Dialog) dialog;
                        EditText t = (EditText) f.findViewById(R.id.title2);
                        EditText amt = (EditText) f.findViewById(R.id.amount2);
                        String title = (String) t.getText().toString();
                        String amount = (String) amt.getText().toString();

                        //Log.v(TAG, bacon);
                        Log.v(TAG, title);
                        Log.v(TAG, amount);

                        ParseObject gameScore = new ParseObject("Tracked");
                        gameScore.put("title", title);
                        gameScore.put("amount", Integer.parseInt(amount));
                        gameScore.saveInBackground();
                        Context context = getActivity();
                        CharSequence text = "Entry Added";
                        int duration = Toast.LENGTH_SHORT;





                        Bundle bun = new Bundle();
                        bun.putString("title", title);

                        DetailFragment detail = new DetailFragment();
                        detail.setArguments(bun);



                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container1, new MasterListFragment())
                                .addToBackStack(null)
                                .commit();

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container2, detail)
                                .addToBackStack(null)
                                .commit();

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RecordFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


}
