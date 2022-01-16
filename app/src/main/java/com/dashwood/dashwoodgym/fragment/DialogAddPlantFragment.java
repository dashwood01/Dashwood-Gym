package com.dashwood.dashwoodgym.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.database.SyncDatabase;
import com.dashwood.dashwoodgym.databinding.FragmentDialogAddPlantBinding;
import com.dashwood.dashwoodgym.handler.HandlerEditText;
import com.dashwood.dashwoodgym.listener.OnAddPlant;
import com.google.android.material.textfield.TextInputLayout;

public class DialogAddPlantFragment extends DialogFragment {
    private FragmentDialogAddPlantBinding binding;
    private OnAddPlant listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogAddPlantBinding.inflate(inflater, container, false);
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
            TextInputLayout[] inputLayouts = {binding.layoutName, binding.layoutWorkTime, binding.layoutRestTime};
            EditText[] editTexts = {binding.edtName, binding.edtWorkTime, binding.edtRestTime};
            if (HandlerEditText.isEmpty(inputLayouts, editTexts, getString(R.string.toast_error_empty)))
                return;
            listener.onAddPlant(SyncDatabase.getDatabase(requireContext()).insertPlants(binding.edtName.getText().toString(),
                    binding.edtWorkTime.getText().toString(), binding.edtRestTime.getText().toString()));
            dismiss();
        });
    }

    public void setListener(OnAddPlant listener) {
        this.listener = listener;
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
