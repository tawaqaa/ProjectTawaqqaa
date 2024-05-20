
package com.touqa.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.touqa.app.R;
import com.touqa.app.databinding.ItemViewAlertBinding;
import com.touqa.app.model.Report;

import java.util.List;
import java.util.Objects;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<Report> reports;
    private static Context context;

    public ReportAdapter(List<Report> reports, Context context) {

        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewAlertBinding binding = ItemViewAlertBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.bind(report);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemViewAlertBinding binding;

        ViewHolder(ItemViewAlertBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Report report) {
            binding.street.setText(report.getNameStreet());
            binding.time.setText(report.getTime());
            binding.height.setText(report.getHeight());
            binding.status.setText(report.getStatus());
            if (Objects.equals(report.getStatus(), "خطر")) {
                binding.getRoot().setBackground(context.getDrawable(R.drawable.corner_danger));
                binding.imgStatus.setImageDrawable(context.getDrawable(R.drawable.ic_danger));
            }
        }
    }
}
