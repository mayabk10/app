package com.example.myapp;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class SomeClass {
    public static void navigationSolution(DrawerLayout drawerLayout,
                                              MaterialToolbar materialToolbar,
                                              NavigationView navigationView,
                                              Activity activity) {
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        View headerView = navigationView.getHeaderView(0);
        ImageView closeImage = headerView.findViewById(R.id.closeDrawer);

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawer(navigationView);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                // Handle item selection.
                Intent intent1 = activity.getIntent();
                Bundle data = intent1.getExtras();
                String user = intent1.getStringExtra("userName");
                if(item.getItemId()==R.id.addW){
                    intent = new Intent(activity,AddWorkOut.class);
                    intent.putExtra("userName" ,user);
                    activity.startActivity(intent);
                    return true;
                }
                else if(item.getItemId()==R.id.editW){
                    intent = new Intent(activity,EditWorkOutActivity.class);
                    intent.putExtra("userName" ,user);
                    activity.startActivity(intent);
                    return true;
                }
                else if (item.getItemId()==R.id.userPage){
                    intent = new Intent(activity,PictureActivity.class);
                    intent.putExtra("userName" ,user);
                    activity.startActivity(intent);
                    return true;
                } else if (item.getItemId()==R.id.splash) {
                    intent = new Intent(activity,Splash.class);
                    intent.putExtra("userName" ,user);
                    activity.startActivity(intent);
                    return true;
                }  else if(item.getItemId()==R.id.statisticsPage){
                    intent = new Intent(activity,StatisticsPage.class);
                    intent.putExtra("userName" ,user);
                    activity.startActivity(intent);
                    return true;
                }


                return true;


            }
        });


    }
}
