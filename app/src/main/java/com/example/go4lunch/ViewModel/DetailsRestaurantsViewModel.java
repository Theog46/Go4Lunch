package com.example.go4lunch.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Repository.DetailRepository;

public class DetailsRestaurantsViewModel extends ViewModel {

    private DetailRepository detailRepository;


    public DetailsRestaurantsViewModel(
            DetailRepository detailRepository
    ) {
        this.detailRepository = detailRepository;



    }

    public LiveData<DetailRestaurantItem> getDetailRestaurantLiveData() {
        return Transformations.map(detailRepository.getPlaceDetailResults(), restaurant -> {
            return new DetailRestaurantItem(
                    restaurant.getPlaceId(),
                    restaurant.getName(),
                    restaurant.getFormattedAddress(),
                    restaurant.getPhotos(),
                    restaurant.getFormattedPhoneNumber(),
                    restaurant.getWebsite(),
                    restaurant.getRating(),
                    false
            );

        });
    }

    public void initDetailsMutableLiveData(String placeId) {
        detailRepository.getPlaceDetailResults(placeId);
    }


}
