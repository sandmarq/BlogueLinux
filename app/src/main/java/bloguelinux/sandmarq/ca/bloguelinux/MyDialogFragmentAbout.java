package bloguelinux.sandmarq.ca.bloguelinux;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by sandrine on 2014-12-24.
 */
public class MyDialogFragmentAbout extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialogAbout = new AlertDialog.Builder(getActivity());
        theDialogAbout.setTitle("About");
        theDialogAbout.setMessage("message");
        theDialogAbout.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "sure", Toast.LENGTH_SHORT).show();
            }
        });
        theDialogAbout.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "NOPE!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        return theDialogAbout.create();
    }
}
