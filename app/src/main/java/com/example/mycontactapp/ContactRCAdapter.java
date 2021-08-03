package com.example.mycontactapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactRCAdapter extends RecyclerView.Adapter<ContactRCAdapter.ContactViewHolder> {
   private ArrayList<Contact>contacts;
   private OnRecyclerViewItemClickListener listener;

    public ContactRCAdapter(ArrayList<Contact> contacts, OnRecyclerViewItemClickListener listener) {
        this.contacts = contacts;
        this.listener=listener;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public OnRecyclerViewItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contact,null,false);
        ContactViewHolder viewHolder=new ContactViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact c=contacts.get(position);
        if(c.getImage() !=null && !c.getImage().isEmpty())
        holder.imge.setImageURI(Uri.parse(c.getImage()));
        else{
            holder.imge.setImageResource(R.drawable.profilepng);
        }
        holder.tv_name.setText(c.getName());
        holder.tv_phone.setText(c.getPhone());
        holder.imge.setTag(c.getId());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView imge;
        TextView tv_name,tv_phone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            imge=itemView.findViewById(R.id.imageView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id= (int) imge.getTag();
                    listener.onItemClick(id);

                }
            });

        }
    }
}
