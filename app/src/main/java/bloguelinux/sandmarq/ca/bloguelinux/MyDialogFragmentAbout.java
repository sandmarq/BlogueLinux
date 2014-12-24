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

        final String textAboutTitle = getString(R.string.text_about_title);
        final String textAboutMsg = getString(R.string.text_about_msg);
        final String textAboutOK = getString(R.string.text_about_ok);
        final String textAboutCANCEL = getString(R.string.text_about_cancel);

        AlertDialog.Builder theDialogAbout = new AlertDialog.Builder(getActivity());
        theDialogAbout.setTitle(textAboutTitle);
        theDialogAbout.setMessage(textAboutMsg);
        theDialogAbout.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), textAboutOK, Toast.LENGTH_SHORT).show();
            }
        });
        theDialogAbout.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), textAboutCANCEL, Toast.LENGTH_SHORT).show();
            }
        });

        return theDialogAbout.create();
    }
}
