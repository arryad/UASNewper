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

import com.example.uas_newper.R;
import com.example.uas_newper.admin.EditUserActivity;
import com.example.uas_newper.admin.ListUserActivity;

import java.util.ArrayList;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {
    private ArrayList<Account> listItem;
    private Context context;

    public ListUserAdapter(ArrayList<Account> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistlogin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String name = listItem.get(position).getName();
        final String email = listItem.get(position).getEmail();
        final String level = listItem.get(position).getLevel();

        holder.txtName.setText(name);
        holder.txtEmail.setText(email);
        holder.txtLevel.setText(level);
        holder.imgItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imgItemMenu);
                popupMenu.inflate(R.menu.menu_list_user);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Intent intent = new Intent(context, EditUserActivity.class);
                                intent.putExtra("data", listItem.get(position));
                                context.startActivity(intent);
                                Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show();
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
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem, imgItemMenu;
        private TextView txtName, txtEmail, txtLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemMenu = itemView.findViewById(R.id.img_item_menu);
            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_name);
            txtLevel = itemView.findViewById(R.id.txt_level);
            txtEmail = itemView.findViewById(R.id.txt_email);
        }
    }

}
