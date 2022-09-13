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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sgp.beats.R;
import com.sgp.beats.models.user;

import java.util.Objects;

import static android.content.ContentValues.TAG;


public class SignupFragment extends Fragment {


    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup,container,false);


        final EditText etName = view.findViewById(R.id.etName);
        final EditText etEmail = view.findViewById(R.id.etEmail);
        final EditText etPassword = view.findViewById(R.id.etPassword);
        final EditText etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        Button btnRegister = view.findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "button pressed", Toast.LENGTH_SHORT).show();

                if (etName.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty() &&
                        etPassword.getText().toString().isEmpty() && etConfirmPassword.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(), "ALL FIELDS ARE EMPTY", Toast.LENGTH_SHORT).show();
                }

                if (etName.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Name field is Empty", Toast.LENGTH_SHORT).show();
                }
                if (etEmail.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Email field is Empty", Toast.LENGTH_SHORT).show();
                }
                if (etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Password field is Empty", Toast.LENGTH_SHORT).show();
                }
                if (etConfirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Confirm Password", Toast.LENGTH_SHORT).show();
                }
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                    final FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef = database.getReference("users");

                                        FirebaseUser userAuthenticate = mAuth.getCurrentUser();

                                        String email= etEmail.getText().toString();
                                        String name = etName.getText().toString();
                                        String password = etPassword.getText().toString();
                                        myRef.child((userAuthenticate.getUid())).setValue(new user(name,email,password));
                                        Bundle bundle = new Bundle();
                                        Fragment fragment = new LogoutFragment();
                                        bundle.putString("name", name);
                                        fragment.setArguments(bundle);

                                        Toast.makeText(getContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onComplete:sign in done ");
                                        setFragment(new LogoutFragment());
                                    }else{
                                        Log.d(TAG, "onComplete:sign in not done ");
                                    }

                                }
                            });

                }
                else {
                    Toast.makeText(getContext(), "Password Do Not Match!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

    private void setFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.signUpLayout,fragment);
        fragmentTransaction.commit();
    }
}