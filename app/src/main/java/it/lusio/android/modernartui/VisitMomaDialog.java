package it.lusio.android.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


/**
 * Class to implement a new Dialog fore 'More Information' task
 *
 * @author Luciano Quercia
 * @see android.app.DialogFragment
 */
public class VisitMomaDialog extends DialogFragment {

    public static final String URL = "http://www.moma.org/collection/object.php?object_id=94278";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.more_info);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URL));
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VisitMomaDialog.this.dismiss();
            }
        });
        return builder.create();
    }
}
