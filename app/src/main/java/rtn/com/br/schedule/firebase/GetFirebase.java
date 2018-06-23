package rtn.com.br.schedule.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by bergtorres on 16/06/2018
 */
public class GetFirebase {

    private static FirebaseAuth mFirebaseAuth;
    private static DatabaseReference mDatabaseReferencUsers;

    private GetFirebase(){

    }

    public static FirebaseAuth getFirebaseAuth(){
        if (mFirebaseAuth != null) {
            return mFirebaseAuth;
        }
        return FirebaseAuth.getInstance();
    }


    public static DatabaseReference getFireDatabaseReferenceUsers(){
        if (mDatabaseReferencUsers != null) {
            return mDatabaseReferencUsers;
        }
        return FirebaseDatabase.getInstance().getReference().child("Users");
    }
}
