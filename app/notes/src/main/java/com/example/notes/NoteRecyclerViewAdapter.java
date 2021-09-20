package com.example.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.notes.databinding.FragmentNoteShortBinding;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private NoteClickListener clickListener;

    public interface NoteClickListener {
        void onClick(int position);
        void onLongClick(int position, View view);
    }

    public NoteRecyclerViewAdapter(NoteClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentNoteShortBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<NotesList.Item> list = NotesList.getInstance();
        holder.mItem = list.get(position).data;
        holder.mIdView.setText(String.valueOf(position + 1));
        holder.mContentView.setText(list.get(position).data.getName());
    }

    @Override
    public int getItemCount() {
        return NotesList.getInstance().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        public final TextView mIdView;
        public final TextView mContentView;
        public Note mItem;

        public ViewHolder(FragmentNoteShortBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(this.getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onLongClick(this.getAbsoluteAdapterPosition(), view);
            return true;
        }
    }

}