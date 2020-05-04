package com.system.marques.softsoccer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UsersRecyclerAdapter
    extends
        RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>
{
    private List<Users> usersList;

    public UsersRecyclerAdapter(List<Users> usersList)
    {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_item, parent, false);

        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final String name = usersList.get(position).getName();

        holder.textView.setText(name);

        final String userId = usersList.get(position).userId;

        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent sendIntent = new Intent(view.getContext(), SendActivity.class);
                sendIntent.putExtra("user_id", userId);
                sendIntent.putExtra("user_name", name);
                view.getContext().startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private View view;
        private TextView textView;

        public ViewHolder(View itemView)
        {
            super(itemView);

            view = itemView;

            textView = (TextView) view.findViewById(R.id.list_name_users);
        }
    }
}