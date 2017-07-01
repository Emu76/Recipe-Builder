package com.beachball.recipebuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BaseNavActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    Intent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        String[] navTitles = {"Search by ingredients","Search by name","Favourite searches","Saved recipes"};
        int[] drawableTitles = {R.drawable.ic_action_search, R.drawable.ic_action_search, R.drawable.ic_action_favorite, R.drawable.ic_action_save};

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_view_drawer);
        drawerList.setAdapter(new DrawerAdapter(navTitles, drawableTitles, LayoutInflater.from(getApplicationContext()), getApplicationContext()));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BaseNavActivity.this, "ok", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                if (BaseNavActivity.this.pendingIntent != null) {
                    BaseNavActivity.this.startActivity(BaseNavActivity.this.pendingIntent);
                }
            }

            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    protected void addContent(View layout) {
        ((FrameLayout) findViewById(R.id.content_layout)).addView(layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
