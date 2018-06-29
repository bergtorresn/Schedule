package rtn.com.br.schedule.fragments;


import android.app.AlertDialog;
import android.content.Context;
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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import rtn.com.br.schedule.activities.HomeActivity;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
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
    // Properties
    private String mArrayStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private String mArrayPriority[] = {"Alta", "Média", "Baixa"};
    private Integer mPrioritySelected;
    private Integer mStatusSelected;

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
        FirebaseService.getTasks(getActivity(), new CallbackDataSnapshot() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTask userTask = snapshot.getValue(UserTask.class);
                    userTask.setKey(snapshot.getKey());
                    if (!mListUserTasks.contains(userTask)) {
                        mListUserTasks.add(userTask);
                    }
                }
                Collections.sort(mListUserTasks, new Comparator<UserTask>() {
                    public int compare(UserTask one, UserTask other) {
                        return one.getPriority().compareTo(other.getPriority());
                    }
                });
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
        inflater.inflate(R.menu.menu_tasklist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasklist_buttonMenu_singout:
                alertSigOut();
            case R.id.tasklist_buttonMenu_filter:
                alertFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertSigOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Deseja sair da sua conta?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.singOut();
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
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

    /**
     * Método responsável por aplicar um filtro na lista de tarefas,
     * o 1º loop verifica se a tarefa tem prioridade e status igual ao informandos nos spinners,caso tenha, a tarefa é adicionada em uma lista
     * o 2º loop adiciona as outras tarefas na lista.
     *
     * @param priority número da prioridade: 0 - Alta, 1 - Média, 2 - Baixa
     * @param status   número do status: 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída
     */
    private void sorteByPriorityAndStatus(Integer priority, Integer status) {
        List<UserTask> arrayAux = new ArrayList<>();
        arrayAux.addAll(mListUserTasks);
        mListUserTasks.clear();
        for (UserTask userTask : arrayAux) {
            if (userTask.getPriority().equals(priority) && userTask.getStatus().equals(status)) {
                mListUserTasks.add(userTask);
            }
        }
        for (UserTask userTask : arrayAux) {
            if (!mListUserTasks.contains(userTask)) {
                mListUserTasks.add(userTask);
            }
        }
        mUserTaskAdapter.notifyDataSetChanged();
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

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener) {
            mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(child, rv.getChildAdapterPosition(child));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
