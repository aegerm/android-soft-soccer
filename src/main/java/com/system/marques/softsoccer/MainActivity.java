package com.system.marques.softsoccer;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity
    extends
        AppCompatActivity
{
    private TextView lbProfile       = null;
    private TextView lbUsers         = null;
    private TextView lbNotifications = null;

    private ViewPager mainPager = null;

    private PagerViewAdapter adapter;

    private FirebaseAuth auth;

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if(firebaseUser == null)
        {
            sendToLogin();
        }
    }

    private void sendToLogin()
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        lbProfile       = (TextView) findViewById(R.id.lb_profile);
        lbUsers         = (TextView) findViewById(R.id.lb_users );
        lbNotifications = (TextView) findViewById(R.id.lb_notify);

        mainPager = (ViewPager) findViewById(R.id.mainpager);
        mainPager.setOffscreenPageLimit(2);

        adapter = new PagerViewAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);

        lbProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mainPager.setCurrentItem(0);
            }
        });

        lbUsers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mainPager.setCurrentItem(1);
            }
        });

        lbNotifications.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mainPager.setCurrentItem(2);
            }
        });

        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int i, float v, int i1)
            {
            }

            @Override
            public void onPageSelected(int i)
            {
                changeTabs(i);
            }

            @Override
            public void onPageScrollStateChanged(int i)
            {
            }
        });
    }

    private void changeTabs(int i)
    {
        if(i == 0)
        {
            lbProfile.setTextColor(getColor(R.color.textTabBright));
            lbProfile.setTextSize(22);

            lbUsers.setTextColor(getColor(R.color.textTabLight));
            lbUsers.setTextSize(16);

            lbNotifications.setTextColor(getColor(R.color.textTabLight));
            lbNotifications.setTextSize(16);
        }

        if(i == 1)
        {
            lbProfile.setTextColor(getColor(R.color.textTabLight));
            lbProfile.setTextSize(16);

            lbUsers.setTextColor(getColor(R.color.textTabBright));
            lbUsers.setTextSize(22);

            lbNotifications.setTextColor(getColor(R.color.textTabLight));
            lbNotifications.setTextSize(16);
        }

        if(i == 2)
        {
            lbProfile.setTextColor(getColor(R.color.textTabBright));
            lbProfile.setTextSize(16);

            lbUsers.setTextColor(getColor(R.color.textTabLight));
            lbUsers.setTextSize(16);

            lbNotifications.setTextColor(getColor(R.color.textTabBright));
            lbNotifications.setTextSize(22);
        }
    }
}
