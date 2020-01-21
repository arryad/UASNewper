package com.example.uas_newper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;

import java.util.ArrayList;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder> {
    private ArrayList<ItemModel> listItem;
    private Context context;

    public ListNewsAdapter(ArrayList<ItemModel> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }


    @NonNull
    @Override
    public ListNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistnews, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(!listItem.get(position).getPic().equals("-")){
            Glide.with(context).load(listItem.get(position).getPic()).into(holder.imgItem);
        }
        final String name= listItem.get(position).getNama();
        final String desc = listItem.get(position).getDeskripsi();
        final String tgl = listItem.get(position).getDeadline();
        final String ads = listItem.get(position).getEmail();
        final String asd = listItem.get(position).getPic();



        holder.txtName.setText(name);
        holder.txtDesc.setText(desc);
        holder.txtTgl.setText(tgl);
        holder.imgItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imgItemMenu);
//                popupMenu.inflate(R.menu.menu_list_user);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
//                                Intent intent = new Intent(context, EditUserActivity.class);
//                                intent.putExtra("data", listItem.get(position));
//                                context.startActivity(intent);
//                                Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.action_delete:
                                Toast.makeText(context, "Delete Button", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem, imgItemMenu;
        private TextView txtName, txtDesc, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemMenu = itemView.findViewById(R.id.img_item_menu);
            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
        }
    }

}
