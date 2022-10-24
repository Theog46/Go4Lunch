package com.example.go4lunch.viewmodel_tests;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Message;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.Repository.FirestoreRepository;
import com.example.go4lunch.utils.FakeDataTestUtils;
import com.example.go4lunch.utils.LiveDataTestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FirestoreViewModelUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private final MutableLiveData<List<User>> workmatesMutableLiveDataTest = new MutableLiveData<>();
    private final MutableLiveData<List<Message>> messagesMutableLiveDataTest = new MutableLiveData<>();

    private final FirestoreRepository firestoreRepository = Mockito.mock(FirestoreRepository.class);

    @Before
    public void setUp() {
        workmatesMutableLiveDataTest.setValue(FakeDataTestUtils.createFakeUsersList());
        Mockito.when(firestoreRepository.getAllWorkmates()).thenReturn(workmatesMutableLiveDataTest);

        messagesMutableLiveDataTest.setValue(FakeDataTestUtils.createFakeMessages());
        Mockito.when(firestoreRepository.getAllMessages()).thenReturn(messagesMutableLiveDataTest);
    }

    @Test
    public void getAllWorkmates() throws InterruptedException {
        workmatesMutableLiveDataTest.setValue(FakeDataTestUtils.createFakeUsersList());

        Mockito.when(firestoreRepository.getAllWorkmates()).thenReturn(workmatesMutableLiveDataTest);

        List<User> userList = LiveDataTestUtils.getOrAwaitValue(firestoreRepository.getAllWorkmates());

        Assert.assertEquals("User 0 ID", userList.get(0).getUid());
        Assert.assertEquals("User 0 USERNAME", userList.get(0).getUsername());
        Assert.assertEquals("User 0 EMAIL", userList.get(0).getEmail());
        Assert.assertEquals("User 0 RESTAURANT_ID", userList.get(0).getRestaurantId());
        Assert.assertEquals("User 0 RESTAURANT_NAME", userList.get(0).getRestaurantName());
        Assert.assertEquals("User 0 URL_PICTURE", userList.get(0).getUrlPicture());
        Assert.assertEquals(false, userList.get(0).getLike());
        Assert.assertEquals(false, userList.get(0).getIsEating());

        Assert.assertEquals("User 1 ID", userList.get(1).getUid());
        Assert.assertEquals("User 1 USERNAME", userList.get(1).getUsername());
        Assert.assertEquals("User 1 EMAIL", userList.get(1).getEmail());
        Assert.assertEquals("User 1 RESTAURANT_ID", userList.get(1).getRestaurantId());
        Assert.assertEquals("User 1 RESTAURANT_NAME", userList.get(1).getRestaurantName());
        Assert.assertEquals("User 1 URL_PICTURE", userList.get(1).getUrlPicture());
        Assert.assertEquals(true, userList.get(1).getLike());
        Assert.assertEquals(true, userList.get(1).getIsEating());
    }

    @Test
    public void getAllMessages() throws InterruptedException {
        messagesMutableLiveDataTest.setValue(FakeDataTestUtils.createFakeMessages());

        Mockito.when(firestoreRepository.getAllMessages()).thenReturn(messagesMutableLiveDataTest);

        List<Message> messageList = LiveDataTestUtils.getOrAwaitValue(firestoreRepository.getAllMessages());

        Assert.assertEquals("0", messageList.get(0).getUid());
        Assert.assertEquals("Arthur", messageList.get(0).getUsername());
        Assert.assertEquals("URL", messageList.get(0).getUrlPicture());
        Assert.assertEquals("DATE", messageList.get(0).getDate());
        Assert.assertEquals("CONTENT_0", messageList.get(0).getMessage());

        Assert.assertEquals("1", messageList.get(1).getUid());
        Assert.assertEquals("Jacques", messageList.get(1).getUsername());
        Assert.assertEquals("URL", messageList.get(1).getUrlPicture());
        Assert.assertEquals("DATE", messageList.get(1).getDate());
        Assert.assertEquals("CONTENT_1", messageList.get(1).getMessage());

        Assert.assertEquals("2", messageList.get(2).getUid());
        Assert.assertEquals("Jeanne", messageList.get(2).getUsername());
        Assert.assertEquals("URL", messageList.get(2).getUrlPicture());
        Assert.assertEquals("DATE", messageList.get(2).getDate());
        Assert.assertEquals("CONTENT_2", messageList.get(2).getMessage());
    }
 }
