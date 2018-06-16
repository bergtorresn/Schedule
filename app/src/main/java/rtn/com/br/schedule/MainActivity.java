package rtn.com.br.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rtn.com.br.schedule.firebase.FirebaseService;


public class MainActivity extends AppCompatActivity {

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseService.createUser("bergjbe@gmail.com", "berg123456", MainActivity.this);


    }
}
