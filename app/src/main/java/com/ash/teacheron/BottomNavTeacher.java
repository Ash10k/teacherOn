package com.ash.teacheron;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.teacherui.TeacherAllMessage;
import com.ash.teacheron.teacherui.TeacherHomeFrag;
import com.ash.teacheron.teacherui.TeacherProfile;
import com.ash.teacheron.teacherui.TutorJobs;
import com.ash.teacheron.ui.dashboard.DashboardFragment;
import com.ash.teacheron.ui.home.HomeFragment;
import com.ash.teacheron.ui.message.MessageFragment;
import com.ash.teacheron.ui.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BottomNavTeacher extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private Toolbar toolbar;
    TextView nav_header_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_teacher);

        // Initialize Views
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        View headerView = navigationView.getHeaderView(0);
        nav_header_text = headerView.findViewById(R.id.nav_header_text);


        // Set up Toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load Default Fragment
        if (savedInstanceState == null) {
            loadFragment(new TeacherHomeFrag());
        }

        // Bottom Navigation Click Listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new TeacherHomeFrag();
            }
            else if (item.getItemId() == R.id.navigation_dashboard) {
                selectedFragment = new TutorJobs();
            }
            else if (item.getItemId() == R.id.navigation_profile) {
                //selectedFragment = new Profile();
                selectedFragment = new TeacherProfile();

            } else if (item.getItemId() == R.id.navigation_notifications) {
                selectedFragment = new MessageFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // Side Navigation Click Listener
        navigationView.setNavigationItemSelectedListener(item ->
        {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_about) {
                //selectedFragment = new AboutFragment();

                Intent intent=new Intent(BottomNavTeacher.this,DemoActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);

            } else if (item.getItemId() == R.id.nav_logout) {
                new AlertDialog.Builder(BottomNavTeacher.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(BottomNavTeacher.this);
                                sharedPrefLocal.clear();
                                Intent intent = new Intent(BottomNavTeacher.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null) // Dismiss dialog on "No"
                        .show();
                return true;
            }
            else if (item.getItemId() == R.id.terms) {
                Intent intent=new Intent(BottomNavTeacher.this,DemoActivity.class);
                intent.putExtra("type",3);
                startActivity(intent);
                return true;
            }
            else if (item.getItemId() == R.id.privacypol) {
                Intent intent=new Intent(BottomNavTeacher.this,DemoActivity.class);
                intent.putExtra("type",4);
                startActivity(intent);
                return true;
            }
            else if (item.getItemId() == R.id.contactus) {
                Intent intent=new Intent(BottomNavTeacher.this,DemoActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                return true;
            }


            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                drawerLayout.closeDrawers();
            }
            return true;
        });
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(BottomNavTeacher.this);
        nav_header_text.setText(sharedPrefLocal.getUserName());
    }

    // Load Fragment Function
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

}