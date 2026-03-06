package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogInFragment extends Fragment {
public interface LogInListener{
    void changeFragmentToSignUp();
}
LogInListener listener;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // if (getActivity() instanceof LogInListener) {
        //  listener = (LogInListener) getActivity();
       // } else {
           // throw new RuntimeException(getActivity().toString()
              //     + " must implement LogInListener");
       // }
            return inflater.inflate(R.layout.log_in_layout,container,false);

    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView moveTosignUp = view.findViewById(R.id.moveToSignUp);
        EditText name = view.findViewById(R.id.editTextText2);
        EditText password = view.findViewById(R.id.editTextTextPassword2);
        DB db =DB.getDatabase(this.getContext());
        UserDao userDao = db.userDao();
        moveTosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                welcomeActivity.changeFragmentToSignUp();
            }
        });
        Button logIn = view.findViewById(R.id.button3);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2 = name.getText().toString().toLowerCase();
                User user= userDao.getFromName(name2);
                if(user==null){
                    Toast.makeText(getContext(),"שם משתמש זה אינו קיים במערכת",Toast.LENGTH_SHORT).show();
                } else if (!user.getPassword().equals(password.getText().toString()) ) {
                    Toast.makeText(getContext(),"הסיסמא לא נכונה",Toast.LENGTH_SHORT).show();
                    System.out.println(user.getPassword());
                }
                else {
                    Intent intent = new Intent(getContext(),Splash.class);
                    intent.putExtra("userName" , name.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
