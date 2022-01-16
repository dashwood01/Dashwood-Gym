package com.dashwood.dashwoodgym.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.database.SyncDatabase;
import com.dashwood.dashwoodgym.databinding.RecItemGymPlantBinding;
import com.dashwood.dashwoodgym.diff.DiffGymPlantCallback;
import com.dashwood.dashwoodgym.handler.HandlerReturnValue;
import com.dashwood.dashwoodgym.inf.InformationPlant;
import com.dashwood.dashwoodgym.log.T;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecItemGymPlant extends RecyclerView.Adapter<AdapterRecItemGymPlant.ViewHolder> {

    private final AsyncListDiffer<InformationPlant> asyncListDiffer =
            new AsyncListDiffer<>(this, new DiffGymPlantCallback());
    private final FragmentActivity fragmentActivity;

    public AdapterRecItemGymPlant(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecItemGymPlantBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InformationPlant informationPlant = asyncListDiffer.getCurrentList().get(position);
        holder.binding.txtName.setText(informationPlant.getName());
        holder.binding.txtTimeOfWork.setText(fragmentActivity.getString(R.string.txt_text_work_time, HandlerReturnValue.secondToTime(informationPlant.getWorkTimeAsSec())));
        holder.binding.txtTimeOfRest.setText(fragmentActivity.getString(R.string.txt_text_rest_time, HandlerReturnValue.secondToTime(informationPlant.getRestTimeAsSec())));
        holder.binding.btnDelete.setOnClickListener(v -> {
            if (!SyncDatabase.getDatabase(fragmentActivity).deletePlant(informationPlant.getId()))
                return;
            List<InformationPlant> informationPlantList = new ArrayList<>(SyncDatabase.getDatabase(fragmentActivity).getAllPlants());
            updateList(informationPlantList);
        });
    }

    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size();
    }

    public void sendItems(List<InformationPlant> list) {
        asyncListDiffer.submitList(list);
    }

    private void updateList(List<InformationPlant> list) {
        asyncListDiffer.submitList(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final RecItemGymPlantBinding binding;

        public ViewHolder(@NonNull RecItemGymPlantBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
