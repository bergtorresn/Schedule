package rtn.com.br.schedule.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rtn.com.br.schedule.R;

public class TaskItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;

    public TaskItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_items, container, false);

        mRecyclerView = view.findViewById(R.id.fragment_taskitems_recyclerview);
        mButton = view.findViewById(R.id.fragment_taskitems_buttonnewtask);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
