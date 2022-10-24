package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.go4lunch.Model.AutoComplete.ResultsAutoComplete;
import com.example.go4lunch.Model.Maps.Location;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.ViewModel.AutoCompleteViewModel;
import com.example.go4lunch.ViewModel.FirestoreViewModel;
import com.example.go4lunch.ViewModel.RestaurantItem;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    private TextView username_nav;
    private TextView email_nav;
    private ImageView image_nav;
    private GoogleSignInClient mGoogleSignInClient;
    private FirestoreViewModel firestoreViewModel;
    private User mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AutoCompleteViewModel autoCompleteViewModel;
    private AutoCompleteTextView autoCompleteTextView;
    private RestaurantsListViewModel restaurantsListViewModel;
    private ImageView searchIcon;
    List<RestaurantItem> data = new ArrayList<>();
    String userLocation;
    private Fragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        this.configureViewPager();


        firestoreViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(FirestoreViewModel.class);
        autoCompleteViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AutoCompleteViewModel.class);
        restaurantsListViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(RestaurantsListViewModel.class);
        this.searchAutoComplete();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firestoreViewModel.getYourLunch(user.getUid()).observe(this, value -> mUser = value);

        View headerV = navigationView.getHeaderView(0);
        username_nav = headerV.findViewById(R.id.nav_username);
        email_nav = headerV.findViewById(R.id.nav_email);
        image_nav = headerV.findViewById(R.id.nav_image);

        username_nav.setText(user.getDisplayName());
        email_nav.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl())
                .transform(new CircleCrop())
                .into(image_nav);

        searchIcon = findViewById(R.id.dropdown_search);
        this.openAutoComplete();


    }


    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_restaurant:
                yourLunch();
                return true;
            case R.id.activity_main_drawer_settings:
                openSettings();
                return true;
            case R.id.activity_main_drawer_power:
                logout();
                return true;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        autoCompleteTextView = findViewById(R.id.autoComplete);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureViewPager() {
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);

        // 3 - Add icons to each tabs
        tabs.getTabAt(0).setIcon(R.drawable.ic_baseline_map_24);
        tabs.getTabAt(1).setIcon(R.drawable.ic_baseline_list_24);
        tabs.getTabAt(2).setIcon(R.drawable.ic_baseline_people_24);
        tabs.getTabAt(3).setIcon(R.drawable.ic_baseline_chat_24);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void yourLunch() {
        FirebaseUser user = mAuth.getCurrentUser();

        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("key", mUser.getRestaurantId());
        navigationView.getContext().startActivity(i);
    }

    private void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("key", mUser.getRestaurantId());
        navigationView.getContext().startActivity(i);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void openAutoComplete() {
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchAutoComplete() {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2) {
                    autoCompleteViewModel.getAutoCompleteResults(charSequence.toString());
                } if (charSequence.length() < 10) {
                    reinit();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        autoCompleteViewModel.getListAutoComplete().observe(this, this::filterResults);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            MutableLiveData<List<ResultsAutoComplete>> predictions = autoCompleteViewModel.getListAutoComplete();
            if (parent.getItemAtPosition(position) == getResources().getString(R.string.places_autocomplete_no_results_for_query)) {
                autoCompleteTextView.setText("");
            } else {
                for (ResultsAutoComplete prediction : Objects.requireNonNull(predictions.getValue())) {
                    if (parent.getItemAtPosition(position) == prediction.getDescription()) {
                        Log.d("AUTO", prediction.getDescription());
                        String placeId = prediction.getPlace_id();

                        update(placeId);


                    }
                }
            }
        });
    }

    private void update(String placeId) {
        restaurantsListViewModel.callPlacesForAutoComplete(placeId);
        restaurantsListViewModel.getDetailRestaurant().observe(this, changedDetailRestaurant -> {

        });
    }

    private void reinit() {
        Location userLoc = (restaurantsListViewModel.getLocationReinit());
        String userLocStr = userLoc.getLat() + "," + userLoc.getLng();
        restaurantsListViewModel.initList(userLocStr);
    }

    private void filterResults(List<ResultsAutoComplete> resultsAutoCompletes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_dropdown_item_1line);
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setAdapter(adapter);

        for (ResultsAutoComplete prediction : resultsAutoCompletes) {
            if (prediction.getTypes().contains("restaurant")) {
                adapter.add(prediction.getDescription());
                adapter.notifyDataSetChanged();
            }
        }
        if (adapter.getCount() == 0) {
            adapter.add(getResources().getString(R.string.adapter_empty));
            adapter.notifyDataSetChanged();
        }
    }


}
