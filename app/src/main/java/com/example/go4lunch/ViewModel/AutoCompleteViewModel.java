package com.example.go4lunch.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.AutoComplete.ResultsAutoComplete;
import com.example.go4lunch.Repository.AutoCompleteRepository;

import java.util.List;

public class AutoCompleteViewModel extends ViewModel {
    AutoCompleteRepository autoCompleteRepository;

    public AutoCompleteViewModel(
            AutoCompleteRepository autoCompleteRepository
    ) {
        this.autoCompleteRepository = autoCompleteRepository;
    }

    public void getAutoCompleteResults(String placeId) {
        autoCompleteRepository.getAutoCompleteResults(placeId);
    }

    public MutableLiveData<List<ResultsAutoComplete>> getListAutoComplete() {
        return autoCompleteRepository.getAutoCompleteMutableLiveData();
    }
}
