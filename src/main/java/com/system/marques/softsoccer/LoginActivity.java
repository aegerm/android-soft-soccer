package com.system.marques.softsoccer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity
    extends
        AppCompatActivity
{
    private EditText etdEmail    = null;
    private EditText etdPassword = null;

    private Button btnLogin  = null;
    private Button btnCreate = null;

    private FirebaseAuth auth = null;
    private FirebaseFirestore firestore;

    private ProgressBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        etdEmail    = (EditText) findViewById(R.id.edt_name);
        etdPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin  = (Button) findViewById(R.id.btn_login);
        btnCreate = (Button) findViewById(R.id.btn_account);

        bar = (ProgressBar) findViewById(R.id.bar);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bar.setVisibility(View.VISIBLE);

                String email = etdEmail.getText().toString();
                String password = etdPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                String tokenId = FirebaseInstanceId.getInstance().getToken();
                                String currentId = auth.getCurrentUser().getUid();

                                Map<String, Object> tokenMap = new HashMap<>();

                                tokenMap.put("token_id", tokenId);

                                firestore.collection("Users").document(currentId).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        sendMain();
                                        bar.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }

                            else
                            {
                                bar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(LoginActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private void sendMain()
    {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}