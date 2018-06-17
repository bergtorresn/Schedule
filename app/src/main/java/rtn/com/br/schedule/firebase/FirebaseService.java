package rtn.com.br.schedule.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.Connection;

/**
 * Created by bergtorres on 16/06/2018
 */
public class FirebaseService {

    /**
     * Método responsável por criar um novo usuário
     *
     * @param email
     * @param password
     * @param activity
     */
    public static void createUser(String email, String password, final Activity activity) {
        if (Connection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");
            GetFirebaseAuth.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("AUTH", "SUCESS CREATE USER");
                                Alerts.genericAlert("SUCESS", "NEW USER", activity);
                            } else {
                                Log.i("AUTH", "ERROR CREATE USER " + task.getException().getMessage());
                                Alerts.genericAlert("ERROR", task.getException().getMessage(), activity);
                            }
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");

        }
    }


    /**
     * Método responsável por efetuar o login no aplicativo
     *
     * @param email
     * @param password
     * @param activity
     */
    public static void singIn(String email, String password, Activity activity) {
        if (Connection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");

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
        } else {

        }
    }


    /**
     * Método responsável por efetuar o Logout do aplicativo
     */
    public static void singOut() {

        try {
            GetFirebaseAuth.getFirebaseAuth().signOut();
            Log.i("AUTH", "SUCESS SINGOUT");
        } catch (Exception e) {
            Log.i("AUTH", "ERROR SINGOUT " + e.getMessage());
        }
    }


    /**
     * Método responsável por verificar se existe um usuáiro ativo no aplicativo,
     *
     * @return boolean, caso seja true o usuário será direcionado p/ tela de tarefas,
     * caso seja false, o usuário será direcionado para tela inicial
     */
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
