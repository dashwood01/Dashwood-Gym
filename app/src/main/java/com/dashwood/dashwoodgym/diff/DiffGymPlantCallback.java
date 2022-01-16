package com.dashwood.dashwoodgym.diff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.dashwood.dashwoodgym.inf.InformationPlant;

public class DiffGymPlantCallback extends DiffUtil.ItemCallback<InformationPlant> {
    @Override
    public boolean areItemsTheSame(@NonNull InformationPlant oldItem, @NonNull InformationPlant newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull InformationPlant oldItem, @NonNull InformationPlant newItem) {
        return oldItem.getId() == newItem.getId();
    }
}
