package com.example.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddWorkOut extends AppCompatActivity {
    private String[] choices1={"hwg","agsgsg","shshs"};
    private String[] choices2={"ajhaj","sksks","jhjhjh"};
    long datePicker;
    private Button goBack;
    private Button addWO;
    private String dateAdd;
    private Button date1;
    private String pick1;
    private String pick2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_out);
        date1 = findViewById(R.id.button);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String user = data.getString("name");
        DB db = DB.getDatabase(this);
        WorkOutDao workOutDao = db.workOutDao();
        addWO = findViewById(R.id.buttonAW);
        goBack = findViewById(R.id.buttonBack);
        Spinner spinnerKind = findViewById(R.id.spinnerKind);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year=2026 ;
                int month=0;
                int day =0;
                DatePickerDialog datePickerDialog = new
                        DatePickerDialog(AddWorkOut .this, new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int selectedDay) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(selectedYear, selectedMonth,
                                selectedDay);
                        datePicker = calendar.getTimeInMillis();
                        String date = String.valueOf(selectedDay) +"." +
                                String.valueOf(selectedMonth+1) +
                                "." + String.valueOf(selectedYear);
                        date1.setText(date);
                        dateAdd = date;
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        spinnerKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pick1=choices1[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spinnerMain = findViewById(R.id.spinnerMain);
        spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pick2 = choices2[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addWO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.dataBaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        WorkOuts workOuts = new WorkOuts(user,datePicker,pick1,pick2);
                        db.workOutDao().Insert(workOuts);

                    }
                });
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Splash.class);
                intent.putExtra("userName",user);
                startActivity(intent);
            }
        });
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices1);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerKind.setAdapter(ad);
        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,choices2);
        ad1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerMain.setAdapter(ad1);
    }
}