package rtn.com.br.schedule.firebase;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by bergtorres on 16/06/2018
 */
public class GetFirebaseAuth {

    private static FirebaseAuth mFirebaseAuth;

    private GetFirebaseAuth(){

    }

    public static FirebaseAuth getFirebaseAuth(){
        if (mFirebaseAuth != null) {
            return mFirebaseAuth;
        }
        return FirebaseAuth.getInstance();
    }
}
