package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mychatapplication.Fragment.ChatFragment;
import com.example.mychatapplication.Fragment.ProfileFragment;
import com.example.mychatapplication.Fragment.UsersFragment;
import com.example.mychatapplication.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ImageView profie_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profie_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("");

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              User user=dataSnapshot.getValue(User.class);
              username.setText(user.getUsername());
              profie_image.setImageResource(R.drawable.ic_person_black_24dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatFragment(),"CHATS");
        viewPagerAdapter.addFragment(new UsersFragment(),"USERS");

        viewPagerAdapter.addFragment(new ProfileFragment(),"PROFILE");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater=getMenuInflater();
        Inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean logout(MenuItem item){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();

        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment ,String title){
            fragments.add(fragment);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
