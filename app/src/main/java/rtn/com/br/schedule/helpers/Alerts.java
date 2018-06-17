package rtn.com.br.schedule.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by bergtorres on 17/06/2018
 */
public class Alerts {

    private static AlertDialog.Builder alert;

    public static void genericAlert(String title, String message, Activity activity){

        alert = new AlertDialog.Builder(activity);

        alert.setTitle(title);

        alert.setMessage(message);

        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "OK");
            }
        });

        alert.create();
        alert.show();

    }
}
