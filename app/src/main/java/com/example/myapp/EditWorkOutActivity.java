package com.example.myapp;

import static java.lang.Integer.parseInt;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class EditWorkOutActivity extends AppCompatActivity {
    private String[] choices1={"ידיים","רגליים","חזה"};
    private String[] choices2={"חתירה ללא רגליים","חתירה ללא ידיים","פרפר"};
    long datePicker;
    private WorkOutAdaptor workOutAdaptor;
    private Button goBack;
    private Button addWO;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar materialToolbar;
    private LiveData<List<WorkOuts>> currentLiveData;
    private MediatorLiveData<List<WorkOuts>> workOutMediatorLiveData = new MediatorLiveData<>();
    private String dateAdd;
    private Button date1;
    private String pick1;
    private String pick2;
    private int id;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work_out);
        date1 = findViewById(R.id.button);
        Intent intent = getIntent();
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        materialToolbar = findViewById(R.id.materialToolbar);
        SomeClass.navigationSolution(drawerLayout,materialToolbar,navigationView,this);
        Bundle data = intent.getExtras();
        assert data !=null;
        String user = data.getString("userName");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workOutAdaptor = new WorkOutAdaptor(new ArrayList<>(), new WorkOutAdaptor.OnWorkOutClickListener() {
            @Override
            public void onWorkOutClick(WorkOuts workOuts) {
                 int id1 = workOuts.getId();
                 id=id1;
                String userName = workOuts.getUser();
                long date = workOuts.getDate();
                String kind = workOuts.getKind();
                String mainExercise=workOuts.getMainExercise();
                System.out.println(id);
            }
        });
        recyclerView.setAdapter(workOutAdaptor);
        DB db = DB.getDatabase(this);
        WorkOutDao workOutDao = db.workOutDao();
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
                        DatePickerDialog(EditWorkOutActivity .this, new DatePickerDialog.OnDateSetListener() {
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
                System.out.println(user);
                db.dataBaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                    System.out.println(id);

                        WorkOuts workOuts = new WorkOuts(id,user,datePicker,pick1,pick2,1);
                        System.out.println(workOuts);
                        db.workOutDao().update(workOuts);

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