package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsPage extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar materialToolbar;
    private TextView textViewCount;
    private TextView textViewMainExCount;
    private WorkOutAdaptor workOutAdaptor;
    private int ex1,ex2,ex3;
    private LiveData<List<WorkOuts>> currentLiveData;
    private MediatorLiveData<List<WorkOuts>> workOutMediatorLiveData = new MediatorLiveData<>();
    private String pick;
    private long DatePast;
    private String [] dates = {"שבוע","חודש","שנה"};
    private String Text1,Text2;

private   int WONumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_page);
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        materialToolbar = findViewById(R.id.materialToolbar);
        textViewCount = findViewById(R.id.textView12);
        textViewMainExCount = findViewById(R.id.textView9);
        long currentDate = System.currentTimeMillis();
        DB db = DB.getDatabase(this);
        WorkOutDao workOutDao = db.workOutDao();
        assert data != null;
        String user = data.getString("userName");

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               pick = dates[i];
               if(i==0){
                   DatePast=currentDate-604800000L;
                   Text1=" בשבוע האחרון";}
               else if(i==1){
                   DatePast=currentDate-2629746000L;
                   Text1=" בחודש האחרון";
               }
               else if(i==2) {
                   DatePast = currentDate - 31556926000L;
                   Text1=" בשנה האחרונה";
               }
                db.dataBaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        WONumber = db.workOutDao().getNumber(user, DatePast, currentDate);
                        textViewCount.setText("התאמנתי " + WONumber+" פעמים "+Text1 );
                        ex1 = db.workOutDao().getMainExCount(user,DatePast,currentDate,"חתירה ללא רגליים");
                        ex2 = db.workOutDao().getMainExCount(user,DatePast,currentDate,"חתירה ללא ידיים");
                        ex3 = db.workOutDao().getMainExCount(user,DatePast,currentDate,"פרפר");
                        if(ex1>ex2 && ex1>ex3){
                            Text2="חתירה ללא רגליים";
                        }
                        else if(ex2>ex1 && ex2>ex3){
                            Text2 = "חתירה ללא ידיים";
                        }
                        else if(ex3>ex1 && ex3>ex2) {
                            Text2 = "פרפר";
                        }
                        System.out.println(ex1);
                        System.out.println(ex2);
                        System.out.println(ex3);
                        textViewMainExCount.setText("התרגיל האהוב עלייך " + Text1 + " היה " + Text2 );
                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });







        SomeClass.navigationSolution(drawerLayout,materialToolbar,navigationView,this);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,dates);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
    }


}