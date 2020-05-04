package com.system.marques.softsoccer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NotificationsAdapter
    extends
        RecyclerView.Adapter<NotificationsAdapter.ViewHolder>
{
    private List<Notifications> notificationsList;

    private FirebaseFirestore firestore;
    private Context context;

    public NotificationsAdapter(Context context, List<Notifications> nlist)
    {
        this.notificationsList = nlist;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationsAdapter.ViewHolder holder, int position)
    {
        firestore = FirebaseFirestore.getInstance();

        String fromId = notificationsList.get(position).getFrom();

        holder.notifyMessage.setText(notificationsList.get(position).getMessage());

        firestore.collection("Users").document(fromId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                String name = documentSnapshot.getString("name");

                holder.notifyName.setText(name);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public TextView notifyName;
        public TextView notifyMessage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            view = itemView;

            notifyName = (TextView) view.findViewById(R.id.list_notify_name);
            notifyMessage = (TextView) view.findViewById(R.id.list_notify);
        }
    }
}
