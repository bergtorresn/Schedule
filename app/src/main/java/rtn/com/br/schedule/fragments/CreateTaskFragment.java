package rtn.com.br.schedule.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.Date;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.activities.LoginActivity;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.models.UserTask;

public class CreateTaskFragment extends Fragment {

    private Button mButtonSend;
    private EditText mEditTextName;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        mEditTextName = view.findViewById(R.id.fragment_newtask_edittexttaskname);
        mButtonSend = view.findViewById(R.id.fragment_newtask_buttonsend);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                if (!name.isEmpty()){
                    UserTask userTask = new UserTask();
                    userTask.setName(name);
                    userTask.setCreated_at(new Date());
                    createNewUserTask(userTask);
                } else {
                    Alerts.genericAlert("Atenção", "Informe um nome para esta atividade", getActivity());
                }

            }
        });

        return view;
    }

    private void createNewUserTask(UserTask userTask) {
        FirebaseService.createUserTask(userTask, getActivity(), new CallbackDatabase() {
            @Override
            public void onCallback(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Alerts.genericAlert("Atenção", "Não foi possível criar esta atividade, tente novamente.", getActivity());
                }
            }
        });
    }

}
