package com.dashwood.dashwoodgym.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dashwood.dashwoodgym.inf.InformationPlant;

import java.util.List;

public class PlantViewModel extends ViewModel {
    private MutableLiveData<List<InformationPlant>> mutablePlantList;

    public PlantViewModel() {
        this.mutablePlantList = new MutableLiveData<>();
    }

    public MutableLiveData<List<InformationPlant>> getMutablePlantList() {
        return mutablePlantList;
    }

    public LiveData<List<InformationPlant>> getPlantList() {
        return mutablePlantList;
    }
}
