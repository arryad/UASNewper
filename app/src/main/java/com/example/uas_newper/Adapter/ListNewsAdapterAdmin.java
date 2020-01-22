package com.example.uas_newper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uas_newper.EditPostActivity;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.example.uas_newper.TampilanBerita;

import java.util.ArrayList;

public class ListNewsAdapterAdmin extends RecyclerView.Adapter<ListNewsAdapterAdmin.ViewHolder> {
    private ArrayList<ItemModel> listItem;
    private Context context;
    private ItemListener listener;


    public ListNewsAdapterAdmin(ArrayList<ItemModel> listItem, Context context, ItemListener listener) {
        this.listItem = listItem;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListNewsAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistnews, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (!listItem.get(position).getPic().equals("-")) {
            Glide.with(context).load(listItem.get(position).getPic()).into(holder.imgItem);
        }
        final String judul = listItem.get(position).getJudul();
        final String desc = listItem.get(position).getDeskripsi();
        final String tgl = listItem.get(position).getDeadline();
        final String ads = listItem.get(position).getEmail();
        final String asd = listItem.get(position).getPic();

        holder.txtJudul.setText(judul);
        holder.txtTgl.setText(tgl);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TampilanBerita.class);
                intent.putExtra("data", listItem.get(position));
                context.startActivity(intent);
            }
        });
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
                                Intent intent = new Intent(context, EditPostActivity.class);
                                intent.putExtra("data", listItem.get(position));
                                context.startActivity(intent);
                                Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.action_delete:
                                DialogInterface.OnClickListener dialOnClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                listener.deleteItem(listItem.get(position));
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Apakah kamu yakin?").setPositiveButton("Ya", dialOnClickListener)
                                        .setNegativeButton("Tidak", dialOnClickListener).show();

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
        private TextView txtJudul, txtDesc, txtTgl;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemMenu = itemView.findViewById(R.id.img_item_menu);
            imgItem = itemView.findViewById(R.id.img_item);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
            cardView = itemView.findViewById(R.id.card_item);
        }
    }

    public interface ItemListener {
        void deleteItem(ItemModel item);
    }
}
