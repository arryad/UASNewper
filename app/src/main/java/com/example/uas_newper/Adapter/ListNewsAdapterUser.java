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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.example.uas_newper.TampilanBerita;
import com.example.uas_newper.TampilanBerita1;

import java.util.ArrayList;

public class ListNewsAdapterUser extends RecyclerView.Adapter<ListNewsAdapterUser.ViewHolder> {
    private ArrayList<ItemModel> listItem;
    private Context context;

    public ListNewsAdapterUser(ArrayList<ItemModel> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }


    @NonNull
    @Override
    public ListNewsAdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistnews_u, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(!listItem.get(position).getPic().equals("-")){
            Glide.with(context).load(listItem.get(position).getPic()).into(holder.imgItem);
        }
        final String judul= listItem.get(position).getJudul();
        final String desc = listItem.get(position).getDeskripsi();
        final String tgl = listItem.get(position).getDeadline();
        final String ads = listItem.get(position).getEmail();
        final String asd = listItem.get(position).getPic();

        holder.txtJudul.setText(judul);
        holder.txtTgl.setText(tgl);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TampilanBerita1.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", listItem.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imgItem, imgItemMenu;
        private TextView txtJudul, txtDesc, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemMenu = itemView.findViewById(R.id.img_item_menu);
            imgItem = itemView.findViewById(R.id.img_item);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
            cardView = itemView.findViewById(R.id.card_item);
        }
    }

}
