package com.example.go4lunch.viewmodel_tests;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Maps.Location;
import com.example.go4lunch.Model.Maps.Result;
import com.example.go4lunch.Repository.DetailRepository;
import com.example.go4lunch.Repository.FirestoreRepository;
import com.example.go4lunch.Repository.NearbyRepository;
import com.example.go4lunch.Repository.UserLocationRepository;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.example.go4lunch.utils.FakeDataTestUtils;
import com.example.go4lunch.utils.LiveDataTestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantsViewModelUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();
    private final Application application = Mockito.mock(Application.class);

    private final NearbyRepository nearbyRepository = Mockito.mock(NearbyRepository.class);
    private final UserLocationRepository userLocationRepository = Mockito.mock(UserLocationRepository.class);
    private final FirestoreRepository firestoreRepository = Mockito.mock(FirestoreRepository.class);
    private final DetailRepository detailRepository = Mockito.mock(DetailRepository.class);


    private final MutableLiveData<Location> userLocationLiveDataTest = new MutableLiveData<>();
    private RestaurantsListViewModel restaurantsListViewModel;
    private final MutableLiveData<List<Result>> restaurantItemMutableLiveData = new MutableLiveData<>();


    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testLocation() throws InterruptedException {
        userLocationLiveDataTest.setValue(FakeDataTestUtils.createFakeLocation());
        Mockito.when(userLocationRepository.locationLiveData()).thenReturn(userLocationLiveDataTest);
        Location location = LiveDataTestUtils.getOrAwaitValue(userLocationRepository.locationLiveData());

        Assert.assertEquals(48.9242751, location.getLat(), 0);
        Assert.assertEquals(2.6842851, location.getLng(), 0);
    }

    @Test
    public void testNearbySearch() throws InterruptedException {
        restaurantItemMutableLiveData.setValue(FakeDataTestUtils.createApiResults());

        Mockito.when(nearbyRepository.getNearbySearch("48.9242751,26842851")).thenReturn(restaurantItemMutableLiveData);

        List<Result> result = LiveDataTestUtils.getOrAwaitValue(nearbyRepository.getNearbySearch("48.9242751,26842851"));

        Assert.assertEquals("ID_1", result.get(0).getPlaceId());

        Assert.assertEquals("name", result.get(0).getName());

        Assert.assertEquals("icon", result.get(0).getIcon());

        Assert.assertEquals("ID_2", result.get(1).getPlaceId());

        Assert.assertEquals("name_2", result.get(1).getName());

        Assert.assertEquals("icon2", result.get(1).getIcon());

        Assert.assertEquals("ID_3", result.get(2).getPlaceId());

        Assert.assertEquals("name3", result.get(2).getName());

        Assert.assertEquals("icon3", result.get(2).getIcon());

    }
}
