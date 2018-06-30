package rtn.com.br.schedule.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.activities.HomeActivity;
import rtn.com.br.schedule.activities.MainActivity;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.firebase.GetFirebase;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.RecyclerTouchListener;
import rtn.com.br.schedule.interfaces.CallbackDataSnapshot;
import rtn.com.br.schedule.interfaces.ClickListener;
import rtn.com.br.schedule.models.UserTask;

//https://medium.com/@harivigneshjayapalan/android-implementing-custom-recycler-view-part-i-9ce5e9af7fea
//https://medium.com/@harivigneshjayapalan/android-recyclerview-implementing-single-item-click-and-long-press-part-ii-b43ef8cb6ad8
public class UserTaskListFragment extends Fragment {

    // UI Elements
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;
    private List<UserTask> mListUserTasks;
    private UserTaskAdapter mUserTaskAdapter;

    public UserTaskListFragment() {
    } // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_task_list, container, false);
        mListUserTasks = new ArrayList<>();
        mButton = view.findViewById(R.id.fragment_tasklist_buttonnewtask);
        mRecyclerView = view.findViewById(R.id.fragment_tasklist_recyclerview);

        // CLICK ON ITEM RECYLEVIRE, AND CALL showFragmentTaskItems, PASS ITEM PRESS FOR NEW FRAGMENT

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showFragmentTaskItemList(mListUserTasks.get(position));
            }
        }));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentCreateUserTask();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchUserTasks();
    }

    private void fetchUserTasks() {
        FirebaseService.getUserTasks(getActivity(), new CallbackDataSnapshot() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTask userTask = snapshot.getValue(UserTask.class);
                    userTask.setUid(snapshot.getKey());
                    if (!mListUserTasks.contains(userTask)) {
                        mListUserTasks.add(userTask);
                    }
                }
                mUserTaskAdapter = new UserTaskAdapter(mListUserTasks);
                mUserTaskAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mUserTaskAdapter);
            }

            @Override
            public void onCallbackDatabaseError(DatabaseError databaseError) {
                Alerts.genericAlert("Atenção", "Não foi possível se comunicar como servidor, tente novamente.", getActivity());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasklist_buttonMenu_logout:
                alertSigOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertSigOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Deseja sair da sua conta?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    GetFirebase.getFirebaseAuth().signOut();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
                    Log.i("AUTH", "SUCCESS SINGOUT");
                } catch (Exception e) {
                    Log.i("AUTH", "ERROR SINGOUT " + e.getMessage());
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    private void showFragmentTaskItemList(UserTask userTask) {
        TaskItemListFragment itemsFragment = new TaskItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userTask", userTask);
        itemsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tasklist2_framecontent, itemsFragment);
        fragmentTransaction.addToBackStack("backToUserTaskList");
        fragmentTransaction.commit();
    }

    private void showFragmentCreateUserTask() {
        CreateTaskFragment createTaskFragment = new CreateTaskFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tasklist2_framecontent, createTaskFragment);
        fragmentTransaction.addToBackStack("backToUserTaskList");
        fragmentTransaction.commit();
    }
}
