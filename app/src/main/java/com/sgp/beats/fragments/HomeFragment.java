package com.sgp.beats.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sgp.beats.MainActivity;
import com.sgp.beats.R;
import com.sgp.beats.adapters.SongListAdapter;
import com.sgp.beats.models.Songs;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference databaseReference;
    private List<Songs> songsList;
    private RecyclerView songsRV;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        songsRV = view.findViewById(R.id.songsRV);
        searchView = view.findViewById(R.id.searchView);





        songsList = new ArrayList<>();



        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot songsData: snapshot.child("songs").getChildren()){
                    Songs songs = songsData.getValue(Songs.class);
                    songsList.add(songs);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                songsRV.setLayoutManager(linearLayoutManager);

                songsRV.setAdapter(new SongListAdapter(songsList));


            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        if (searchView != null){

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }


        return view;
    }

    private void search(String string) {

        ArrayList<Songs> newList = new ArrayList<>();
        for (Songs object : songsList){
            if (object.getName().toLowerCase().startsWith(string.toLowerCase()) || object.getArtist().toLowerCase().startsWith(string.toLowerCase()) ){

                newList.add(object);

            }
        }

        songsRV.setAdapter(new SongListAdapter(newList));
    }

}