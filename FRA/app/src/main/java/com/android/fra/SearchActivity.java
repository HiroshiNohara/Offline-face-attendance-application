package com.android.fra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fra.db.Date;
import com.android.fra.db.Face;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends BaseActivity {

    private SharedPreferences pref;
    private List<Face> faceList = new ArrayList<>();
    private List<Face> searchFaceList = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private SearchAdapter adapter;
    private SearchView searchView;
    private TextView noFindHint;
    private SwipeRefreshLayout swipeRefresh;
    private static boolean fingerprintReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean("is_set_fingerprint", false) && pref.getBoolean("is_set_search_fingerprint", false) && !fingerprintReturn) {
            Intent intent = new Intent(SearchActivity.this, FingerprintActivity.class);
            startActivityForResult(intent, 0);
        }
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_activity_toolBar);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.function_search));
        NavigationView navView = (NavigationView) findViewById(R.id.search_activity_nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.search_activity_drawer_layout);
        View headerLayout = navView.inflateHeaderView(R.layout.nav_header);
        ImageView drawerImageView = (ImageView) headerLayout.findViewById(R.id.nav_header_image);
        TextView navTextView = (TextView) headerLayout.findViewById(R.id.nav_account);
        navView.setCheckedItem(R.id.nav_search);
        navTextView.setText(this.getString(R.string.app_name));
        Glide.with(this)
                .load(R.drawable.nav_icon)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 5)))
                .into(drawerImageView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        MenuItem menuItem = navView.getMenu().findItem(R.id.nav_management);
        boolean isEggOn = pref.getBoolean("is_egg_on", false);
        if (isEggOn) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }
                switch (menuItem.getItemId()) {
                    case R.id.nav_capture:
                        Intent cameraIntent = new Intent(SearchActivity.this, CameraActivity.class);
                        cameraIntent.putExtra("capture_mode", 0);
                        startActivity(cameraIntent);
                        break;
                    case R.id.nav_register:
                        Intent registerIntent = new Intent(SearchActivity.this, RegisterActivity.class);
                        startActivity(registerIntent);
                        break;
                    case R.id.nav_search:
                        break;
                    case R.id.nav_management:
                        Intent managementIntent = new Intent(SearchActivity.this, ManagementActivity.class);
                        startActivity(managementIntent);
                        break;
                    case R.id.nav_settings:
                        Intent settingsIntent = new Intent(SearchActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                    default:
                }
                return true;
            }
        });
        initFace();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecyclerView();
            }
        });
    }

    public void searchItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        adapter = new SearchAdapter(searchFaceList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(this.getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Pattern pattern = Pattern.compile("[0-9]*");
                searchFaceList.clear();
                for (Face face : faceList) {
                    if (pattern.matcher(query).matches()) {
                        if (face.getUid().contains(query)) {
                            searchFaceList.add(face);
                        }
                    } else {
                        if (face.getName().contains(query)) {
                            searchFaceList.add(face);
                        }
                    }
                }
                searchItems();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Pattern pattern = Pattern.compile("[0-9]*");
                noFindHint = (TextView) findViewById(R.id.noFindHint);
                if (newText == null || newText.length() == 0) {
                    initFace();
                    noFindHint.setVisibility(View.GONE);
                } else {
                    noFindHint.setVisibility(View.GONE);
                    searchFaceList.clear();
                    for (Face face : faceList) {
                        if (pattern.matcher(newText).matches()) {
                            if (face.getUid().contains(newText)) {
                                searchFaceList.add(face);
                            }
                        } else {
                            if (face.getName().contains(newText)) {
                                searchFaceList.add(face);
                            }
                        }
                    }
                    searchItems();
                    if (searchFaceList.isEmpty()) {
                        noFindHint.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
        return true;
    }

    private void initFace() {
        faceList = LitePal.findAll(Face.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(faceList);
        recyclerView.setAdapter(adapter);
    }

    private void refreshRecyclerView() {
        initFace();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(faceList);
        recyclerView.setAdapter(adapter);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    fingerprintReturn = data.getBooleanExtra("fingerprint_return", false);
                } else {
                    finish();
                }
                break;
            default:
        }
    }

}