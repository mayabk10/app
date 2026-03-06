package com.example.myapp;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity   {
private TextView name;
private RecyclerView recyclerView;
private WorkOutAdaptor workOutAdaptor;
private Button dateFrom;
private Button dateTo;
long dateFromAddDatePicker;
long dateToAddDatePicker;
private Button showWorkOuts;
private String dateAddTo;
private String dateAdd;
    private String pick1;
    private String pick2;
private long big= 999999999L *1000000000;
private Button addWorkOut;
private LiveData<List<WorkOuts>> currentLiveData;
private MediatorLiveData<List<WorkOuts>> workOutMediatorLiveData = new MediatorLiveData<>();
private String[] choices1={"hwg","agsgsg","shshs"};
private String[] choices2={"ajhaj","sksks","jhjhjh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        recyclerView = findViewById(R.id.recyclerView);
        dateFrom = findViewById(R.id.buttonFrom);
        dateTo = findViewById(R.id.buttonTo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workOutAdaptor = new WorkOutAdaptor(new ArrayList<>());
        recyclerView.setAdapter(workOutAdaptor);
        showWorkOuts = findViewById(R.id.button4);
        addWorkOut = findViewById(R.id.buttonAdd);
        DB db = DB.getDatabase(this);
        WorkOutDao workOutDao = db.workOutDao();
        String user = intent.getStringExtra("userName");
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
                    Intent intent = new Intent(view.getContext(),AddWorkOut.class);
                    intent.putExtra("name" ,user);
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

        showWorkOuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(pick2);
                if(pick1 != null && pick2 != null){
                    System.out.println("1");
                    workOutMediatorLiveData.removeSource(currentLiveData);
                    if(dateFrom!=null){
                        if(dateTo!=null){
                            System.out.println("2");
                            currentLiveData=workOutDao.getSpecifics(user,pick1,pick2,dateFromAddDatePicker,dateToAddDatePicker);
                        }else {
                            System.out.println("3");
                            currentLiveData=workOutDao.getSpecifics(user,pick1,pick2,dateFromAddDatePicker,big);
                        }
                    }else{
                        System.out.println("4");
                        if(dateTo!=null){
                            System.out.println("5");
                            currentLiveData=workOutDao.getSpecifics(user,pick1,pick2,-1,dateToAddDatePicker);
                        }else{
                            System.out.println("6");
                            currentLiveData=workOutDao.getSpecifics(user,pick1,pick2,-1,big);
                        }
                    }
                    workOutMediatorLiveData.addSource(currentLiveData, new Observer<List<WorkOuts>>() {
                        @Override
                        public void onChanged(List<WorkOuts> workOuts) {
                            workOutMediatorLiveData.setValue(workOuts);
                        }
                    });
                }
            }
        });
        assert data !=null;

       name = findViewById(R.id.textView);
       name.setText("ברוכים הבאים  " + user );
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices1);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTo.setAdapter(ad);
        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices2);
        ad1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(ad1);

    }




}