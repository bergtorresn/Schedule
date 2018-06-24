package rtn.com.br.schedule.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by bergtorres on 16/06/2018
 */
public final class GetFirebase {

    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth != null) {
            return firebaseAuth;
        }
        return FirebaseAuth.getInstance();
    }


    public static DatabaseReference getFireDatabaseReferenceUsers(){
        if (databaseReference != null) {
            return databaseReference;
        }
        return FirebaseDatabase.getInstance().getReference().child("Users");
    }
}
