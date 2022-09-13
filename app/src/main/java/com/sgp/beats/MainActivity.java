package com.sgp.beats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sgp.beats.fragments.AccountFragment;
import com.sgp.beats.fragments.FavouritesFragment;
import com.sgp.beats.fragments.HomeFragment;
import com.sgp.beats.fragments.TrendingFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private static final String TAG = MainActivity.class.getSimpleName();

    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        chipNavigationBar = findViewById(R.id.bottom_menu);


        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new HomeFragment()).commit();

        chipNavigationBar.setItemSelected(R.id.navHome,true);


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id){
                    case R.id.navHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new HomeFragment()).commit();
                        break;
                    case R.id.navTrending:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new TrendingFragment()).commit();
                        break;
                    case R.id.navFavourite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new FavouritesFragment()).commit();
                        break;
                    case R.id.navAcc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new AccountFragment()).commit();
                        break;


                }

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            //user already logged in
        }
        else{

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new AccountFragment()).commit();
            chipNavigationBar.setItemSelected(R.id.navAcc,true);


        }
    }

//
//    public void toggleClick(View view) {
//
//        Toast.makeText(this, "btn pressed", Toast.LENGTH_SHORT).show();

//       Boolean btnLiked = false;

//
//        Button btnLike = findViewById(R.id.btnAddToFav);
//        if (!btnLiked){
//            btnLike.setBackgroundResource(R.drawable.ic_fav_button_24);
//            btnLiked = true;
//        }else {
//            btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
//            btnLiked = false;
//        //}
}