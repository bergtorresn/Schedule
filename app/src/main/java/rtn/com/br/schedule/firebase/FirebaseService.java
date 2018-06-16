package rtn.com.br.schedule.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bergtorres on 16/06/2018
 */
public class FirebaseService {

    public static void createUser(String email, String password, Activity activity) {

        GetFirebaseAuth.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("AUTH", "SUCESS CREATE USER");
                        } else {
                            Log.i("AUTH", "ERROR CREATE USER " + task.getException().getMessage());

                        }
                    }
                });
    }

    public static void singIn(String email, String password, Activity activity) {

        GetFirebaseAuth.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("AUTH", "SUCESS SINGIN");
                        } else {
                            Log.i("AUTH", "ERROR SINGIN " + task.getException().getMessage());

                        }
                    }
                });
    }

    public static void singOut() {

        try {
            GetFirebaseAuth.getFirebaseAuth().signOut();
            Log.i("AUTH", "SUCESS SINGOUT");
        } catch (Exception e) {
                Log.i("AUTH", "ERROR SINGOUT " + e.getMessage());
        }
    }

    public static boolean checkIfHaveUser() {

        FirebaseUser user = GetFirebaseAuth.getFirebaseAuth().getCurrentUser();

        if (user != null) {
            Log.i("AUTH", "HAVE USER");
            return true;
        }
        Log.i("AUTH", "DONT HAVE USER");
        return false;
    }


}
