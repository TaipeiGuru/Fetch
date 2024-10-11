package com.example.fetch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Adapter class documentation: https://developer.android.com/develop/ui/views/layout/recyclerview
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    // Creates new view
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    // Replace contents of the view
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.listId.setText(String.valueOf(item.getListId()));
        holder.name.setText(item.getName());
        holder.id.setText(String.valueOf(item.getId()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Binds Item data to ItemView
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView listId, name, id;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            listId = itemView.findViewById(R.id.text_list_id);
            name = itemView.findViewById(R.id.text_name);
            id = itemView.findViewById(R.id.text_id);
        }
    }
}
