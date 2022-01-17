package com.dashwood.dashwoodgym.arg;

import android.os.Parcel;
import android.os.Parcelable;

import com.dashwood.dashwoodgym.inf.InformationPlant;

public class PlantParcelable implements Parcelable {

    private final InformationPlant informationPlant;

    public PlantParcelable(InformationPlant informationPlant) {
        this.informationPlant = informationPlant;
    }

    public PlantParcelable(Parcel source) {
        informationPlant = (InformationPlant) source.readValue(InformationPlant.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public InformationPlant getInformationPlant() {
        return informationPlant;
    }


    public static final Creator<PlantParcelable> CREATOR = new Creator<PlantParcelable>() {
        @Override
        public PlantParcelable[] newArray(int size) {
            return new PlantParcelable[size];
        }

        @Override
        public PlantParcelable createFromParcel(Parcel source) {
            return new PlantParcelable(source);
        }
    };
}