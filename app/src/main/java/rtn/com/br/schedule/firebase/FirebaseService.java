package rtn.com.br.schedule.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.InternetConnection;
import rtn.com.br.schedule.models.User;
import rtn.com.br.schedule.models.UserTask;

/**
 * Created by bergtorres on 16/06/2018
 */
public class FirebaseService {

    /**
     * FirebaseAuth
     */

    /**
     * Método responsável por criar um perfil de acesso com email e senha para o usuário,
     * após criar o perfil, é chamado o método 'updateAuthUser' passando o parâmetro name para este método,
     *
     * @param name
     * @param email
     * @param password
     * @param activity
     */
    public static void createAuthUser(final String name, String email, String password, final Activity activity) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");
            GetFirebase.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("AUTH", "SUCESS CREATE USER");
                                updateAuthUser(name);
                            } else {
                                Log.i("AUTH", "ERROR CREATE USER " + task.getException().getMessage());
                                Alerts.genericAlert("ERROR", task.getException().getMessage(), activity);
                            }
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por atualizar o perfil do usuário com o Nome,
     * após atualizar o perfil do usuário, é chamado o método 'createUserInBD'
     *
     * @param name
     */
    private static void updateAuthUser(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        getUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("AUTH", "SUCCESS USER PROFILE UPDATE");
                            createUserInDB();
                        } else {
                            Log.d("AUTH", "ERROR USER PROFILE UPDATE " + task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * Método responsável por criar no Database um nó para um novo usuário,
     * o nó terá como râmo principal o Uid e como râmo filho o nome do usuário
     */
    private static void createUserInDB() {
        User user = new User();
        user.setName(getUser().getDisplayName());
        GetFirebase.getFireDatabaseReferenceUsers().child(getUser().getUid()).setValue(user);
        Log.d("AUTH", "SUCCESS CREATE USER IN DATABASE");
    }


    /**
     * Método responsável por efetuar o login no aplicativo
     *
     * @param email
     * @param password
     * @param activity
     */
    public static void singIn(String email, String password, Activity activity) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");

            GetFirebase.getFirebaseAuth().signInWithEmailAndPassword(email, password)
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
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }


    /**
     * Método responsável por efetuar o Logout do aplicativo
     */
    public static void singOut() {

        try {
            GetFirebase.getFirebaseAuth().signOut();
            Log.i("AUTH", "SUCESS SINGOUT");
        } catch (Exception e) {
            Log.i("AUTH", "ERROR SINGOUT " + e.getMessage());
        }
    }


    /**
     * Método responsável por verificar se existe um usuáiro logado no aplicativo,
     *
     * @return boolean
     */
    public static boolean checkIfHaveUser() {

        FirebaseUser user = GetFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) {
            Log.i("AUTH", "HAVE USER");
            return true;
        }
        Log.i("AUTH", "DONT HAVE USER");
        return false;
    }

    /**
     * Método responsável por retornar o usuário logado
     *
     * @return FirebaseUser
     */
    private static FirebaseUser getUser() {
        FirebaseUser user = GetFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) {
            return user;
        }

        return null;
    }


    /**
     * FirebaseDatabase
     */

    public static void createUserTask(UserTask userTask, Activity activity) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");
            GetFirebase.getFireDatabaseReferenceUsers().child(getUser().getUid()).child("tasks").push().setValue(userTask);
            Log.i("TASK", "SUCCESS CREATE TASK");

        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    public static void editTask() {

    }

    public static void deleteTask() {

    }

    public static void listTasks(Activity activity) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            GetFirebase.getFireDatabaseReferenceUsers().child(GetFirebase.getFirebaseAuth().getCurrentUser().getUid()).child("tasks").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }


}
