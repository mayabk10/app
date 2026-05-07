package com.example.myapp;

import static java.security.AccessController.getContext;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity   {
private TextView name;
private RecyclerView recyclerView;
private WorkOutAdaptor workOutAdaptor;
private Button dateFrom;
private Button dateTo;
long dateFromAddDatePicker;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar materialToolbar;
long dateToAddDatePicker;
private Button showWorkOuts;
private String dateAddTo;
private String dateAdd;
private Button editWorkOut;
private TextView textView;
    private String pick1;
    private String pick2;
    private  long date1;
    private  long date2;
private long big= 999999999L *1000000000;
private Button addWorkOut;
private LiveData<List<WorkOuts>> currentLiveData;
private MediatorLiveData<List<WorkOuts>> workOutMediatorLiveData = new MediatorLiveData<>();
private String[] choices1={"חתירה ללא רגליים","חתירה ללא ידיים","פרפר"};
private String[] choices2={"ידיים","רגליים","חזה"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        materialToolbar = findViewById(R.id.materialToolbar);
        SomeClass.navigationSolution(drawerLayout,materialToolbar,navigationView,this);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        editWorkOut = findViewById(R.id.buttonEdit);
        recyclerView = findViewById(R.id.recyclerView);
        dateFrom = findViewById(R.id.buttonFrom);
        textView = findViewById(R.id.textView7);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,16);
        calendar.set(Calendar.HOUR,20);
        calendar.set(Calendar.MINUTE,57);
        calendar.add(Calendar.SECOND,5);
        Intent intent1 = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        dateTo = findViewById(R.id.buttonTo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workOutAdaptor = new WorkOutAdaptor(new ArrayList<>(), new WorkOutAdaptor.OnWorkOutClickListener() {
            @Override
            public void onWorkOutClick(WorkOuts workOuts) {
                int id = workOuts.getId();
                String userName = workOuts.getUser();
                long date = workOuts.getDate();
                String kind = workOuts.getKind();
                String mainExercise=workOuts.getMainExercise();
            }
        });
        recyclerView.setAdapter(workOutAdaptor);

        showWorkOuts = findViewById(R.id.button4);
        addWorkOut = findViewById(R.id.buttonAdd);
        DB db = DB.getDatabase(this);
        WorkOutDao workOutDao = db.workOutDao();
        assert data != null;
        String user = data.getString("userName");
        textView.setText("שלום " + user + " איזה אימון תרצה לחפש ? ");
        LiveData<List<WorkOuts>> workOuts = workOutDao.getAll(user);
        workOuts.observe(this, new Observer<List<WorkOuts>>() {
            @Override
            public void onChanged(List<WorkOuts> workOuts) {
                workOutAdaptor.setWorkOuts(workOuts);
            }
        });

        currentLiveData = workOutDao.getAll(user);
        workOutMediatorLiveData.observe(this, new Observer<List<WorkOuts>>() {
            @Override
            public void onChanged(List<WorkOuts> workOuts ) {
                workOutAdaptor.setWorkOuts(workOuts);
            }
        });
        workOutMediatorLiveData.addSource(currentLiveData, new Observer<List<WorkOuts>>() {
            @Override
            public void onChanged(List<WorkOuts> workOuts) {
                workOutMediatorLiveData.setValue(workOuts);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                db.dataBaseWriteExecutor.execute(new Runnable(){
                    @Override
                    public void run() {
                        List<WorkOuts> workOuts1 = workOutAdaptor.getWorkOuts();
                        WorkOuts workOuts2 = workOuts1.get(viewHolder.getAbsoluteAdapterPosition());
                        workOutDao.delete(workOuts2);
                    }
                });
            }

        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
            dateFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year=2026 ;
                    int month=0;
                    int day =0;
                    DatePickerDialog datePickerDialog = new
                            DatePickerDialog(Splash .this, new DatePickerDialog.OnDateSetListener() {
                        @Override

                        public void onDateSet(DatePicker view, int selectedYear,
                                              int selectedMonth, int selectedDay) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(selectedYear, selectedMonth,
                                    selectedDay);
                            dateFromAddDatePicker = calendar.getTimeInMillis();
                            String date = String.valueOf(selectedDay) +"." +
                                    String.valueOf(selectedMonth+1) +
                                    "." + String.valueOf(selectedYear);
                            dateFrom.setText(date);
                            dateAdd = date;
                        }
                    },year, month, day);
                    datePickerDialog.show();
                }
            });

            dateTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year=2026 ;
                    int month=0;
                    int day =0;
                    DatePickerDialog datePickerDialog = new
                            DatePickerDialog(Splash.this, new DatePickerDialog.OnDateSetListener() {
                        @Override

                        public void onDateSet(DatePicker view, int selectedYear,
                                              int selectedMonth, int selectedDay) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(selectedYear, selectedMonth,
                                    selectedDay);
                            dateToAddDatePicker = calendar.getTimeInMillis();
                            String date = String.valueOf(selectedDay) +"." +
                                    String.valueOf(selectedMonth+1) +
                                    "." + String.valueOf(selectedYear);
                            dateTo.setText(date);
                            dateAddTo = date;
                        }
                    },year, month, day);
                    datePickerDialog.show();
                }
            });
            addWorkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(user);
                    Intent intent = new Intent(view.getContext(),AddWorkOut.class);
                    intent.putExtra("userName",user);

                    startActivity(intent);

                }
            });
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pick1=choices1[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pick2=choices2[i];

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editWorkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),EditWorkOutActivity.class);
                intent1.putExtra("userName",user);
                startActivity(intent1);
            }
        });
        showWorkOuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(pick2);
                if (pick1 != null && pick2 != null) {
                    System.out.println("1");
                    workOutMediatorLiveData.removeSource(currentLiveData);
                    if (dateAdd != null) {
                        if (dateAddTo != null) {
                            date1=dateFromAddDatePicker;
                            System.out.println(dateAddTo);
                            date2=dateToAddDatePicker;
                        } else {
                            System.out.println("3");
                            date2=big;
                        }
                    } else {
                        System.out.println("4");
                        date1=0;
                        if (dateAddTo != null) {
                            System.out.println("5");
                            date2=dateToAddDatePicker;
                        } else {
                            System.out.println("6");
                           date2=big;
                        }
                    }
                    System.out.println(currentLiveData.getValue());
                    db.dataBaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            currentLiveData = workOutDao.getSpecifics(user, pick2, pick1, -1, big);
                            System.out.println(currentLiveData.getValue());
                        }
                    });



                    workOutMediatorLiveData.addSource(currentLiveData, new Observer<List<WorkOuts>>() {
                        @Override
                        public void onChanged(List<WorkOuts> workOuts) {
                            System.out.println(workOuts.toString());
                            workOutMediatorLiveData.setValue(workOuts);
                        }
                    });
                }
            }
        });
        assert data !=null;

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices1);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTo.setAdapter(ad);
        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices2);
        ad1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(ad1);

    }




}
