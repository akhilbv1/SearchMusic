package android.searchmusic.helpers;

import android.content.Context;

public class ProgressDialogBar {

    private final Context context;
    private android.app.ProgressDialog progress;

    // Construct progress dialog
    public ProgressDialogBar(Context context) {

        this.context = context;
    }

    //Show Progress dialog bar
    public void showProgressDialog(String message) {

        progress = new android.app.ProgressDialog(context);
        progress.setInverseBackgroundForced(true);
        progress.setMessage(message);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    // Dismiss progress dialog bar
    public void dismissProgressDialog() {

        if (progress.isShowing()) {
            progress.dismiss();
        }

    }
}
