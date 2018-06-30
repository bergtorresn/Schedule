package rtn.com.br.schedule.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.adapters.TaskItemAdapter;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.RecyclerTouchListener;
import rtn.com.br.schedule.interfaces.CallbackDataSnapshot;
import rtn.com.br.schedule.interfaces.ClickListener;
import rtn.com.br.schedule.models.TaskItem;
import rtn.com.br.schedule.models.User;
import rtn.com.br.schedule.models.UserTask;

public class TaskItemListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskItemAdapter mTaskItemAdapter;
    private FloatingActionButton mButton;
    private List<TaskItem> mTaskItemList;
    private UserTask mUserTask;

    // Properties
    private String mArrayStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private String mArrayPriority[] = {"Alta", "Média", "Baixa"};
    private Integer mPrioritySelected;
    private Integer mStatusSelected;

    public TaskItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_item_list, container, false);

        Bundle bundle = getArguments();
        mUserTask = (UserTask) bundle.getSerializable("userTask");
        mTaskItemList = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.fragment_taskitems_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showFragmentTaskDetail(mTaskItemList.get(position), mUserTask.getUid());
            }
        }));

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
        fetchTaskItems(mUserTask);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasklist_buttonMenu_filter:
                alertFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTaskItems(UserTask userTask) {
        FirebaseService.getTaskItems(userTask.getUid(), getActivity(), new CallbackDataSnapshot() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TaskItem taskItem = snapshot.getValue(TaskItem.class);
                    taskItem.setUid(snapshot.getKey());
                    if (!mTaskItemList.contains(taskItem)){
                        mTaskItemList.add(taskItem);
                    }
                }
                Collections.sort(mTaskItemList, new Comparator<TaskItem>() {
                    public int compare(TaskItem one, TaskItem other) {
                        return one.getPriority().compareTo(other.getPriority());
                    }
                });
                mTaskItemAdapter = new TaskItemAdapter(mTaskItemList);
                mTaskItemAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mTaskItemAdapter);
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

    private void showFragmentTaskDetail(TaskItem taskItem, String userTaskUid) {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("taskItem", taskItem);
        bundle.putString("uid", userTaskUid);
        taskDetailFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tasklist2_framecontent, taskDetailFragment);
        fragmentTransaction.addToBackStack("backToTaskItemList");
        fragmentTransaction.commit();
    }

    private void sorteByPriorityAndStatus(Integer priority, Integer status) {
        ArrayList<TaskItem> arrayAux = new ArrayList<>();
        arrayAux.addAll(mTaskItemList);
        mTaskItemList.clear();
        for (TaskItem taskItem : arrayAux) {
            if (taskItem.getPriority().equals(priority) && taskItem.getStatus().equals(status)){
                mTaskItemList.add(taskItem);
            }
        }
        for (TaskItem taskItem: arrayAux) {
            if (!mTaskItemList.contains(taskItem)){
                mTaskItemList.add(taskItem);
            }
        }
        mTaskItemAdapter.notifyDataSetChanged();
    }

    private void alertFilter() {

        // 1 - Pega o layout do Spinner
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_spinner, null);
        // 2 - Pega a instacia do Spinner
        Spinner spinnerStatus = view.findViewById(R.id.tasklist_spinner_dialog_status);
        Spinner spinnerProprity = view.findViewById(R.id.tasklist_spinner_dialog_priority);
        // 3 - Adapter para ler a lista
        ArrayAdapter<String> arrayAdapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mArrayStatus);
        ArrayAdapter<String> arrayAdapterPriority = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mArrayPriority);
        // 4 - Configura o adapter para exibir em DropDown
        arrayAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 5 - Adiciona o adapter ao Spinner
        spinnerStatus.setAdapter(arrayAdapterStatus);
        spinnerProprity.setAdapter(arrayAdapterPriority);

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProprity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrioritySelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Escolha um filtro");
        builder.setNeutralButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sorteByPriorityAndStatus(mPrioritySelected, mStatusSelected);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        builder.setView(view); // Add Spinner no Alerta
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
}
