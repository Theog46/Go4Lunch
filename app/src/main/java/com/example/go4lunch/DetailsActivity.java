package com.example.go4lunch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.LikedRestaurants;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.Utils.SharedPreferencesUtils;
import com.example.go4lunch.Utils.StarsRating;
import com.example.go4lunch.ViewModel.DetailsRestaurantsViewModel;
import com.example.go4lunch.ViewModel.FirestoreViewModel;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private String placeId;
    private DetailsRestaurantsViewModel viewModel;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantPicture;
    private ImageView firstStar;
    private ImageView secondStar;
    private ImageView thirdStar;
    private ImageView restaurantPhone;
    private ImageView restaurantLike;
    private ImageView restaurantWebsite;
    private FloatingActionButton floatingActionButton;
    private FirebaseAuth mAuth;
    private FirestoreViewModel firestoreViewModel;
    private LikedRestaurants likedRestaurants;
    private RecyclerView rv;
    private DetailsAdapter adapter;
    List<User> data = new ArrayList<>();
    RestaurantsListViewModel restaurantsListViewModel;
    FirebaseFirestore db;
    Boolean likeState;
    String likeRestaurantName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listview_item_details);
        placeId = getIntent().getExtras().getString("key");
        mAuth = FirebaseAuth.getInstance();

        rv = findViewById(R.id.rv_details);
        rv.setLayoutManager(new LinearLayoutManager(this));




        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailsRestaurantsViewModel.class);
        firestoreViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(FirestoreViewModel.class);





        restaurantName = findViewById(R.id.detail_restaurant_name);
        restaurantAddress = findViewById(R.id.detail_restaurant_address);
        restaurantPicture = findViewById(R.id.restaurant_image);
        firstStar = findViewById(R.id.star_1);
        secondStar = findViewById(R.id.star_2);
        thirdStar = findViewById(R.id.star_3);
        restaurantPhone = findViewById(R.id.restaurant_phone_icon);
        restaurantWebsite = findViewById(R.id.restaurant_website_icon);
        restaurantLike = findViewById(R.id.restaurant_star_icon);
        adapter = new DetailsAdapter(this, data);
        rv.setAdapter(adapter);
        getRestaurantDetails(placeId);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





    private void getRestaurantDetails(String placeId) {
        viewModel.initDetailsMutableLiveData(placeId);

        viewModel.getDetailRestaurantLiveData().observe(this, detailRestaurantItem -> {


            db = FirebaseFirestore.getInstance();



            db.collection("users").whereEqualTo("restaurantId", placeId)
                    .get()
                    .addOnCompleteListener(task -> {
                        data.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()) {
                            User workmate = documentSnapshot.toObject(User.class);
                            Log.d("SUH", workmate.getRestaurantName() + " YESSSS " + detailRestaurantItem.getName());

                                    data.add(workmate);
                                    adapter.notifyDataSetChanged();
                        }



                    });

            restaurantName.setText(detailRestaurantItem.getName());
            restaurantAddress.setText(detailRestaurantItem.getAddress());
            Glide.with(this)
                    .load(detailRestaurantItem.getPhotos().get(0).getUrl() + BuildConfig.MAPS_API_KEY)
                    .apply(new RequestOptions().fitCenter())
                    .into(restaurantPicture);
            StarsRating.calculateRating(detailRestaurantItem.getStars(), firstStar, secondStar, thirdStar);



            String phone = detailRestaurantItem.getPhone();
            restaurantPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:" + phone));
                    startActivity(i);
                }
            });

            String url = detailRestaurantItem.getWebsite();
            restaurantWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            String uid = mAuth.getUid();
            firestoreViewModel.getYourLunch(uid).observe(this, value -> {
                likeState = value.getLike();

                if (likeState.equals(true)) {
                    restaurantLike.setImageResource(R.drawable.ic_baseline_star_24);
                } else {
                    restaurantLike.setImageResource(R.drawable.ic_baseline_star_border_24);
                }
                Log.d("likeState", String.valueOf(likeState));
            });

            restaurantLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (likeState.equals(true)) {
                            firestoreViewModel.removeLikeRestaurant(uid);
                            restaurantLike.setImageResource(R.drawable.ic_baseline_star_border_24);
                    } else {
                        firestoreViewModel.likeRestaurant(uid);
                        restaurantLike.setImageResource(R.drawable.ic_baseline_star_24);
                    }
                }
            });

            floatingActionButton = findViewById(R.id.detail_fab);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    firestoreViewModel.chooseRestaurantToEat(user.getUid(), detailRestaurantItem.getId(), detailRestaurantItem.getName(), true);
                    floatingActionButton.setImageResource(R.drawable.fui_ic_check_circle_black_128dp);

                    StringBuilder sb = new StringBuilder();
                    CharSequence hi = getString(R.string.notif_hi);
                    CharSequence place = getString(R.string.notif_place);
                    CharSequence place2 = getString(R.string.notif_place2);
                    CharSequence wk = getString(R.string.notif_workmates);

                    db.collection("users").whereEqualTo("restaurantId", placeId)
                            .get()
                            .addOnCompleteListener(task -> {
                                data.clear();
                                for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                    User workmate = documentSnapshot.toObject(User.class);

                                    Log.d("SUH", workmate.getUsername() + " YESSSS " + detailRestaurantItem.getName());


                                    sb.append(workmate.getUsername() + ", ");

                                    data.add(workmate);
                                    adapter.notifyDataSetChanged();


                                }
                                sb.setLength(sb.length() -2);
                                sb.append(".");
                                SharedPreferences.Editor editor = new SharedPreferencesUtils(getBaseContext()).getEditor();
                                editor.putString("Lunch", hi + " " + user.getDisplayName() + " " + place + " " + detailRestaurantItem.getName() + " " + place2 + System.lineSeparator() + detailRestaurantItem.getAddress() + System.lineSeparator() + wk + System.lineSeparator() + sb.toString());
                                editor.commit();
                                Log.d("WK", sb.toString());

                            });







                }
            });


        });
    }


}
