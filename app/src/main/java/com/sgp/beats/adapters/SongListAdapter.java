package com.sgp.beats.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sgp.beats.PlayMusic;
import com.sgp.beats.R;
import com.sgp.beats.fragments.AccountFragment;
import com.sgp.beats.fragments.FavouritesFragment;
import com.sgp.beats.models.Songs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SongListAdapter extends Adapter<SongListAdapter.SongsViewHolder> {

    private static final String TAG = SongListAdapter.class.getSimpleName();
    private List<Songs> songsArrayList;
    private List<Songs> songsListFull;
    private Context context;

    public SongListAdapter(List<Songs> songsArrayList) {
        this.songsArrayList = songsArrayList;
        //Log.d(TAG, "SongListAdapter: size "+songsArrayList.size());
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SongsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SongsViewHolder holder, final int position) {


        


        holder.nameTv.setText(songsArrayList.get(position).getName());
        holder.artistTv.setText(songsArrayList.get(position).getArtist());
        Glide.with(context)
                .asBitmap()
                .load(songsArrayList.get(position).getAlbum_pic())
                .into(holder.imgCover);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent toMusicPlayer = new Intent(context, PlayMusic.class);
                toMusicPlayer.putExtra("url", songsArrayList.get(position).getUrl());
                toMusicPlayer.putExtra("SongName",songsArrayList.get(position).getName());
                toMusicPlayer.putExtra("ArtistName",songsArrayList.get(position).getArtist());
                toMusicPlayer.putExtra("albumPic",songsArrayList.get(position).getAlbum_pic());

                context.startActivity(toMusicPlayer);

            }
        });

        final DatabaseReference fvtRef;


        final Songs songs = new Songs();
        fvtRef = FirebaseDatabase.getInstance().getReference("favourite");

//                        holder.likeBtn.setBackgroundResource(R.drawable.ic_fav_button_24);
//
//                        fvtRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("album_pic").setValue(songsArrayList.get(position).getAlbum_pic());
//                            fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("artist").setValue(songsArrayList.get(position).getArtist());
//                            fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("name").setValue(songsArrayList.get(position).getName());
//                            fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("url").setValue(songsArrayList.get(position).getUrl());
//
//                            Log.d(TAG, "onDataChange: data added to favourites");
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });


        //final boolean[] btnLiked = {false};


        if (FirebaseAuth.getInstance().getCurrentUser() != null ){
            like(songsArrayList.get(position).getName(),holder.likeBtn);
        }else{
            Toast.makeText(context, "Login to add songs in favourites", Toast.LENGTH_SHORT).show();
        }

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FirebaseAuth.getInstance().getCurrentUser()!= null){

                    like(songsArrayList.get(position).getName(),holder.likeBtn);
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    fvtRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.child(user.getUid()).child(songsArrayList.get(position).getName()).hasChild("liked")){
                                //holder.likeBtn.setBackgroundResource(R.drawable.ic_fav_button_24);
                                Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show();
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("album_pic").setValue(songsArrayList.get(position).getAlbum_pic());
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("artist").setValue(songsArrayList.get(position).getArtist());
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("name").setValue(songsArrayList.get(position).getName());
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("url").setValue(songsArrayList.get(position).getUrl());
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("liked").setValue(true);

                            }else{
                                Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show();
                                fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).removeValue();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


//                    if (!fvtRef.child(user.getUid()).child(songsArrayList.get(position).getName()).child("liked").equals(true)){
//                        holder.likeBtn.setBackgroundResource(R.drawable.ic_fav_button_24);
//                        Toast.makeText(context, "add to favourites", Toast.LENGTH_SHORT).show();
//                        btnLiked[0] = true;
//
//
//                    }else{
//                        holder.likeBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
//                        Toast.makeText(context, "remove from favourites", Toast.LENGTH_SHORT).show();
//                        btnLiked[0] = false;
//                    }
                }
                else{
                   Toast.makeText(context, "Login to add songs in favourites ", Toast.LENGTH_SHORT).show();
               }


               }



        });






    }


    public void setSongsArrayList(List<Songs> songsArrayList) {
        this.songsArrayList = songsArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();

    }


    class SongsViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv,artistTv;
        ImageView imgCover;
        ImageView likeBtn;
        CardView parent;

        public SongsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.songNameTV);
            artistTv = itemView.findViewById(R.id.txtArtist);
            imgCover = itemView.findViewById(R.id.imgCover);
            parent = itemView.findViewById(R.id.parent);
            likeBtn = itemView.findViewById(R.id.btnAddToFav);
        }





    }



    private void like(final String id, final ImageView likeBtn) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("favourite").child(firebaseUser.getUid());


        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()){
                    likeBtn.setImageResource(R.drawable.ic_fav_button_24);
                    likeBtn.setTag("liked");

                }else{
                    likeBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    likeBtn.setTag("like");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
