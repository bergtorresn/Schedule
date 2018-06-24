package rtn.com.br.schedule.helpers;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by bergtorres on 23/06/2018
 *
 * https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
 *
 */
public interface MyCallback {

    void onCallback(DataSnapshot dataSnapshot);
}
