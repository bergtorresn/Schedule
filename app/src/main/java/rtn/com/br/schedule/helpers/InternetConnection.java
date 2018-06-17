package rtn.com.br.schedule.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by bergtorres on 17/06/2018
 */
public class InternetConnection {

    public static final boolean CheckInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;

        } else {
            return false;
        }
    }
}
