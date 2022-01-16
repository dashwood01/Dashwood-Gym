package com.dashwood.dashwoodgym.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.adapter.AdapterRecItemGymPlant;
import com.dashwood.dashwoodgym.database.SyncDatabase;
import com.dashwood.dashwoodgym.databinding.FragmentListGymPlantBinding;
import com.dashwood.dashwoodgym.inf.InformationPlant;
import com.dashwood.dashwoodgym.listener.OnAddPlant;
import com.dashwood.dashwoodgym.log.T;
import com.dashwood.dashwoodgym.viewmodel.PlantViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListGymPlantFragment extends Fragment {

    private FragmentListGymPlantBinding binding;
    private AdapterRecItemGymPlant adapterRecItemGymPlant;
    private PlantViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListGymPlantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNew();
        setView();
        setAction();
        startConnection();
    }

    private void setNew() {
        adapterRecItemGymPlant = new AdapterRecItemGymPlant(requireActivity());
    }

    private void setView() {
        viewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        binding.recItemGymPlant.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recItemGymPlant.setAdapter(adapterRecItemGymPlant);
    }

    private void setAction() {
        viewModel.getPlantList().observe(getViewLifecycleOwner(), list -> adapterRecItemGymPlant.sendItems(list));
        binding.btnAddPlant.setOnClickListener(v -> {
            DialogAddPlantFragment dialogAddPlantFragment = new DialogAddPlantFragment();
            dialogAddPlantFragment.setListener(isDone -> {
                if (!isDone) {
                    T.toast(requireContext(),getString(R.string.toast_error_not_save));
                    return;
                }
                startConnection();
            });
            dialogAddPlantFragment.show(requireActivity().getSupportFragmentManager(), "show_add_plant");
        });
    }

    private void startConnection() {
        viewModel.getMutablePlantList().setValue(SyncDatabase.getDatabase(requireContext()).getAllPlants());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}