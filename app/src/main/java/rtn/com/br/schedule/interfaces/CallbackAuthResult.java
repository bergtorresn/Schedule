package rtn.com.br.schedule.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by bergtorres on 24/06/2018
 */
public interface CallbackAuthResult {

    void onCallback(Task<AuthResult> resultTask);
}
