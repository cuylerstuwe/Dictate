package net.devemperor.dictate.rewording;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import net.devemperor.dictate.R;

import java.util.List;

public class PromptsKeyboardAdapter extends RecyclerView.Adapter<PromptsKeyboardAdapter.RecyclerViewHolder> {

    private final List<PromptModel> data;
    private final AdapterCallback callback;

    public interface AdapterCallback {
        void onItemClicked(Integer position);
    }

    public PromptsKeyboardAdapter(List<PromptModel> data, AdapterCallback callback) {
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prompts_keyboard, parent, false);
        return new RecyclerViewHolder(view);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        final MaterialButton promptBtn;
        final ProgressBar promptPb;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            promptBtn = itemView.findViewById(R.id.prompts_keyboard_btn);
            promptPb = itemView.findViewById(R.id.prompts_keyboard_pb);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        PromptModel model = data.get(position);
        if (model.getName() == null) {
            holder.promptBtn.setText("");
            holder.promptBtn.setForeground(AppCompatResources.getDrawable(holder.promptBtn.getContext(), R.drawable.ic_baseline_add_24));
            holder.promptBtn.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), PromptsOverviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            });
        } else {
            holder.promptBtn.setText(model.getName());
            holder.promptBtn.setForeground(null);
            holder.promptBtn.setOnClickListener(v -> callback.onItemClicked(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<PromptModel> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeAllExcept(int position) {
        for (int i = data.size() - 1; i >= 0; i--) {
            if (i != position) {
                data.remove(i);
            }
        }
        notifyDataSetChanged();
    }
}
