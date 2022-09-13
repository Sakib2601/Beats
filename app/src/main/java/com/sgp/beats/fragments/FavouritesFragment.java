package com.sgp.beats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.sgp.beats.PlayMusic;
import com.sgp.beats.R;
import com.sgp.beats.adapters.SongListAdapter;
import com.sgp.beats.models.Songs;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {

    public FavouritesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites,container,false);

        final RecyclerView favSongsRv = view.findViewById(R.id.favSongsRV);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final List<Songs> favSongList = new ArrayList<>();

        if (user != null){

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot songsData: snapshot.child("favourite").child(user.getUid()).getChildren()){
                        Songs favSongs = songsData.getValue(Songs.class);
                        favSongList.add(favSongs);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    favSongsRv.setLayoutManager(linearLayoutManager);

                    favSongsRv.setAdapter(new SongListAdapter(favSongList));





                };
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }else{

            Toast.makeText(getContext(), "Login to access favourites", Toast.LENGTH_SHORT).show();
        }




        return view;
    }
}