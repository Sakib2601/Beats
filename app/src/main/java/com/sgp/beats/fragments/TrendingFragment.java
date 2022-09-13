package com.sgp.beats.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sgp.beats.R;
import com.sgp.beats.adapters.SongListAdapter;
import com.sgp.beats.models.Songs;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class TrendingFragment extends Fragment {

    public TrendingFragment() {
        // Required empty public constructor
    }

    private RecyclerView trendingRV;
    private List<Songs> trendingSongs;
   // private SearchView searchView;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trending,container,false);

        trendingRV  = view.findViewById(R.id.trendingRV);

//        searchView = view.findViewById(R.id.trendingSearch);

        trendingSongs  = new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("trending");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot songsData: snapshot.getChildren()){
                    Songs trendSongs = songsData.getValue(Songs.class);
                    trendingSongs.add(trendSongs);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                trendingRV.setLayoutManager(linearLayoutManager);

                trendingRV.setAdapter(new SongListAdapter(trendingSongs));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//
//        if (searchView != null){
//
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    search(newText);
//                    Toast.makeText(getContext(), "search box clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
//        }



        return view;
    }

//    private void search(String newText) {
//        ArrayList<Songs> mySongs = new ArrayList<>();
//        for (Songs songs: trendingSongs){
//            if (songs.getName().toLowerCase().startsWith(newText.toLowerCase())){
//                Log.e(TAG, "search: value "+songs.getName() );
//                mySongs.add(songs);
//            }
//        }
//        trendingRV.setAdapter(new SongListAdapter(mySongs));
//    }


}