package eventbus.co.rxapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eventbus.co.rxapplication.R;
import eventbus.co.rxapplication.model.Repo;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {

    private List<Repo> repos = new ArrayList<>();

    public ResAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repo repo = repos.get(position);

        if (repo != null){
            holder.txt_name.setText(repo.getName());
            holder.txt_description.setText(repo.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView txt_name, txt_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_description = itemView.findViewById(R.id.txt_description);

        }
    }
}
