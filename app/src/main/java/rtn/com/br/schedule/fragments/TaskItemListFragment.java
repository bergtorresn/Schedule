package rtn.com.br.schedule.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackDataSnapshot;
import rtn.com.br.schedule.models.TaskItem;
import rtn.com.br.schedule.models.User;
import rtn.com.br.schedule.models.UserTask;

public class TaskItemListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;
    private List<TaskItem> mTaskItemList;
    private UserTask mUserTask;

    public TaskItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_item_list, container, false);

        Bundle bundle = getArguments();
        mUserTask = (UserTask) bundle.getSerializable("userTask");
        mTaskItemList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.fragment_taskitems_recyclerview);
        mButton = view.findViewById(R.id.fragment_taskitems_buttonnewtask);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentCreateTaskItem(mUserTask);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // fetchUserTasks(mUserTask);
    }

    private void fetchUserTasks(UserTask userTask) {
        FirebaseService.getTaskItems(userTask, getActivity(), new CallbackDataSnapshot() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TaskItem taskItem = snapshot.getValue(TaskItem.class);
                    Log.i("TESTE", taskItem.getName());
                }

            }

            @Override
            public void onCallbackDatabaseError(DatabaseError databaseError) {
                Alerts.genericAlert("Atenção", "Não foi possível se comunicar como servidor, tente novamente.", getActivity());
            }
        });
    }

    private void showFragmentCreateTaskItem(UserTask userTask) {
        CreateTaskItemFragment createTaskItemFragment = new CreateTaskItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userTask", userTask);
        createTaskItemFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tasklist2_framecontent, createTaskItemFragment);
        fragmentTransaction.addToBackStack("backToTaskItemList");
        fragmentTransaction.commit();
    }
}
