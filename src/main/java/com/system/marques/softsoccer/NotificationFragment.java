package com.system.marques.softsoccer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
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
public class NotificationFragment
    extends
        Fragment
{
    private RecyclerView listNotification;
    private NotificationsAdapter notificationsAdapter;

    private List<Notifications> notificationsList;
    private FirebaseFirestore firestore;

    public NotificationFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationsList = new ArrayList<>();

        listNotification = (RecyclerView) view.findViewById(R.id.id_notificationList);
        notificationsAdapter = new NotificationsAdapter(getContext(), notificationsList);

        listNotification.setHasFixedSize(true);
        listNotification.setLayoutManager(new LinearLayoutManager(container.getContext()));
        listNotification.setAdapter(notificationsAdapter);

        firestore = FirebaseFirestore.getInstance();

        String currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("Users").document(currentId).collection("Notifications").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    Notifications notifications = dc.getDocument().toObject(Notifications.class);
                    notificationsList.add(notifications);

                    notificationsAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}