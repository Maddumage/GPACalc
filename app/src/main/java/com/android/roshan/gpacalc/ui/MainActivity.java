package com.android.roshan.gpacalc.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.fragment.GraphFragment;
import com.android.roshan.gpacalc.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction fragmentTransaction;
    HomeFragment homeFragment;
    GraphFragment graphFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.content_main, homeFragment).commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showNoticeDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                fab.setEnabled(false);
                fab.setVisibility(View.GONE);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                graphFragment = new GraphFragment();
                fragmentTransaction.replace(R.id.content_main, graphFragment).commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
//            SettingsFragment settingsFragment = new SettingsFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_main,settingsFragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showNoticeDialog() {


        // Create an instance of the dialog fragment and show it
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_result, null);
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.MySwitch);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_sem_no = (EditText) promptsView.findViewById(R.id.semester_no_picker);
        final EditText input_sub_code = (EditText) promptsView.findViewById(R.id.input_subject_code);
        final EditText input_sub_name = (EditText) promptsView.findViewById(R.id.input_subject_name);
        final EditText input_sub_credit = (EditText) promptsView.findViewById(R.id.input_subject_credit);
        final Spinner spinner = (Spinner) promptsView.findViewById(R.id.result_spinner);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String sem_no,sub_code, sub_name, sub_credit, sub_result;
                                sem_no = input_sem_no.getText().toString();
                                sub_code = input_sub_code.getText().toString();
                                sub_name = input_sub_name.getText().toString();
                                sub_credit = input_sub_credit.getText().toString();
                                sub_result = spinner.getSelectedItem().toString();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
