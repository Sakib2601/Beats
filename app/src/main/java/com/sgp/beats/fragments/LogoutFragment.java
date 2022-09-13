package com.sgp.beats.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sgp.beats.R;


public class LogoutFragment extends Fragment {

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_logout, container, false);

        //view(R.layout.fragment_logout);


        final TextView txtUserName = view.findViewById(R.id.txtUserName);

        Button btnLogOut = view.findViewById(R.id.btnLogout);
        CardView policyCardView = view.findViewById(R.id.policyCardView);
        CardView termsCardView = view.findViewById(R.id.termsCardView);
        CardView feedbackCardView = view.findViewById(R.id.feedbackCardView);

        //String name = getArguments().getString("name");

        final ChipNavigationBar navigationBar = view.findViewById(R.id.bottom_menu);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUserName.setText("Hello "+(String) snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        policyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //privacy policy link
                String url="https://beatsprivacypolicy.blogspot.com/2020/10/privacy-policy.html";
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        termsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //t&c link
                String url="https://beatsprivacypolicy.blogspot.com/2020/10/tearms-conditions.html";
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        feedbackCardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "beatssgp@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Theme of mail");
                startActivity(Intent.createChooser(intent, "Select post client"));

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                getFragmentManager().beginTransaction().replace(R.id.logOutFrame,new AccountFragment()).commit();

            }
        });

        return view;
    }
}