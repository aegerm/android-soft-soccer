package com.system.marques.softsoccer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity
    extends
        AppCompatActivity
{
    private TextView notifyData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String dataMessage = getIntent().getStringExtra("dataMessage");
        String dataFrom = getIntent().getStringExtra("dataFrom");

        notifyData = (TextView) findViewById(R.id.id_notification_name);

        notifyData.setText("Mensagem : " + dataMessage + " De :" + dataFrom);
    }
}