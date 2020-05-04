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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;
import com.system.marques.softsoccer.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity
    extends
        AppCompatActivity
{
    private EditText edtName     = null;
    private EditText edtLogin    = null;
    private EditText edtPassword = null;

    private Button btnCreate = null;
    private Button btnBack   = null;

    private FirebaseFirestore firestore = null;
    private FirebaseAuth auth = null;

    private ProgressBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        edtName     = (EditText) findViewById(R.id.edt_alias);
        edtLogin    = (EditText) findViewById(R.id.edt_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnCreate = (Button) findViewById(R.id.btn_create);
        btnBack   = (Button) findViewById(R.id.btn_back);

        bar = (ProgressBar) findViewById(R.id.progressbar);

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bar.setVisibility(View.VISIBLE);

                final String name = edtName.getText().toString();
                String login = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(login) && !TextUtils.isEmpty(password))
                {
                    auth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                String userId = auth.getCurrentUser().getUid();

                                String tokeId = FirebaseInstanceId.getInstance().getToken();

                                Map<String, Object> map = new HashMap<>();
                                map.put("name", name);
                                map.put("token_id", tokeId);

                                firestore.collection("Users").document(userId).set(map).addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        bar.setVisibility(View.INVISIBLE);

                                        sendLogin();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        bar.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }

                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Não foi possível registrar!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void sendLogin()
    {
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}