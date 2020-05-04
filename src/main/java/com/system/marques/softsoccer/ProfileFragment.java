package com.system.marques.softsoccer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment
    extends
        Fragment
{
    private Button logout;
    private TextView textView;

    private FirebaseAuth auth;

    private FirebaseFirestore firestore;

    private String userId;

    public ProfileFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        userId = auth.getCurrentUser().getUid();

        logout = (Button) view.findViewById(R.id.btn_logout);
        textView = (TextView) view.findViewById(R.id.lb_name);

        firestore.collection("Users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                String userName = documentSnapshot.getString("name");

                textView.setText(userName);
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Map<String, Object> tokenRemove = new HashMap<>();
                tokenRemove.put("token_id", FieldValue.delete());

                firestore.collection("Users").document(userId).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        auth.signOut();

                        Intent login = new Intent(container.getContext(), LoginActivity.class);
                        startActivity(login);
                    }
                });
            }
        });

        return view;

    }
}
