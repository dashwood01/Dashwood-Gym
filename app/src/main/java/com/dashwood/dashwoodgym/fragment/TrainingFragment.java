package com.dashwood.dashwoodgym.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.databinding.FragmentTrainingBinding;


public class TrainingFragment extends Fragment {

    private FragmentTrainingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNew();
        setView();
        setAction();
    }

    private void setNew() {

    }

    private void setView() {

    }

    private void setAction() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}