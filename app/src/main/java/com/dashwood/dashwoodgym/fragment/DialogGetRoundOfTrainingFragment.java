package com.dashwood.dashwoodgym.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.arg.PlantParcelable;
import com.dashwood.dashwoodgym.databinding.FragmentDialogGetRountOfTrainingBinding;
import com.dashwood.dashwoodgym.handler.HandlerEditText;
import com.dashwood.dashwoodgym.inf.InformationPlant;

public class DialogGetRoundOfTrainingFragment extends DialogFragment {

    private FragmentDialogGetRountOfTrainingBinding binding;
    private InformationPlant informationPlant;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogGetRountOfTrainingBinding.inflate(inflater, container, false);
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
        setCancelable(false);
    }

    private void setAction() {
        binding.btnCancel.setOnClickListener(v -> dismiss());
        binding.btnAccept.setOnClickListener(v -> {
            if (HandlerEditText.isEmpty(binding.layoutRound, binding.edtTraining, getString(R.string.toast_error_empty)))
                return;
            if (informationPlant == null) return;
            informationPlant.setRound(Integer.parseInt(binding.edtTraining.getText().toString()));
            dismiss();
            Navigation.findNavController(view).navigate(ListGymPlantFragmentDirections.actionNavListPlantToTrainingFragment(new PlantParcelable(informationPlant)));
        });
    }

    public void setInformationPlant(InformationPlant informationPlant) {
        this.informationPlant = informationPlant;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
