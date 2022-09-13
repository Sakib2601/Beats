package com.sgp.beats.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sgp.beats.R;

import static android.content.ContentValues.TAG;


public class LoginFragment extends Fragment {



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_login,container,false);

        final EditText edtEmail,edtPassword;

        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        Button btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty() && edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Both fields are empty", Toast.LENGTH_SHORT).show();
                }
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String email = edtEmail.getText().toString();
                String pwd = edtPassword.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: entered in database");
                                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    setFragment(new LogoutFragment());

                                }else
                                {
                                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });



        return view;
    }

    private void setFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginFrame,fragment);
        fragmentTransaction.commit();
    }
}