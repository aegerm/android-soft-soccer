package com.system.marques.softsoccer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment
    extends
        Fragment
{
    private RecyclerView userView;

    private List<Users> usersList;

    private UsersRecyclerAdapter recyclerAdapter;

    private FirebaseFirestore firestore;

    public UsersFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        firestore = FirebaseFirestore.getInstance();

        userView = (RecyclerView) view.findViewById(R.id.id_userslist);

        usersList = new ArrayList<>();

        recyclerAdapter = new UsersRecyclerAdapter(usersList);

        userView.setHasFixedSize(true);
        userView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        userView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        usersList.clear();

        firestore.collection("Users").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    if(dc.getType() == DocumentChange.Type.ADDED)
                    {
                        String id = dc.getDocument().getId();

                        Users users = dc.getDocument().toObject(Users.class).withId(id);

                        usersList.add(users);

                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}