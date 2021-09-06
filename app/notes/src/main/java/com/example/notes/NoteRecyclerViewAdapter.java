package com.example.notes;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.notes.databinding.FragmentNoteShortBinding;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;

    public NoteRecyclerViewAdapter(List<Note> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentNoteShortBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(position + 1));
        holder.mContentView.setText(mValues.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final TextView mContentView;
        public Note mItem;

        public ViewHolder(FragmentNoteShortBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NoteActivity.class);
            intent.putExtra(NoteActivity.INDEX, this.getAbsoluteAdapterPosition());
            view.getContext().startActivity(intent);
         }
    }
}