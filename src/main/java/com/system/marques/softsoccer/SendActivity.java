package com.system.marques.softsoccer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity
    extends
        AppCompatActivity
{
    private TextView userIdView;

    private String userId;
    private String userName;

    private String currentId;

    private EditText messageText;
    private Button btnSend;

    private FirebaseFirestore firestore;

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        firestore = FirebaseFirestore.getInstance();

        currentId = FirebaseAuth.getInstance().getUid();

        userIdView = (TextView) findViewById(R.id.user_name_id);
        messageText = (EditText) findViewById(R.id.id_tf_name);
        btnSend = (Button) findViewById(R.id.send_id);
        bar = (ProgressBar) findViewById(R.id.bar_prog);

        userId = getIntent().getStringExtra("user_id");
        userName = getIntent().getStringExtra("user_name");

        userIdView.setText("Enviar para " + userName);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String message = messageText.getText().toString();

                if(!TextUtils.isEmpty(message))
                {
                    bar.setVisibility(View.VISIBLE);

                    Map<String, Object> notificationMessage = new HashMap<>();

                    notificationMessage.put("message", message);
                    notificationMessage.put("from", currentId);

                    firestore.collection("Users/" + userId + "/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                    {
                        @Override
                        public void onSuccess(DocumentReference documentReference)
                        {
                            Toast.makeText(SendActivity.this, "Enviando Notificação...", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.INVISIBLE);
                            messageText.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(SendActivity.this, "Erro ao enviar notificação", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }
}