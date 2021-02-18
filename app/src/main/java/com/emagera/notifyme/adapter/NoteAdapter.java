package com.emagera.notifyme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emagera.notifyme.R;
import com.emagera.notifyme.entity.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private onItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNotes = getItem(position);
        holder.textViewtitle.setText(currentNotes.getTitle());
        holder.textViewPriority.setText(String.valueOf(currentNotes.getPriority()));
        holder.textViewDescription.setText(currentNotes.getDescription());
    }

//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }

//    public void setNotes(List<Note> notes)
//    {
//        this.notes = notes;
//        notifyDataSetChanged();
//    }

    public Note getNoteAt(int position)
    {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewtitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull final View itemView) {
            super(itemView);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!=null && position != RecyclerView.NO_POSITION)
                    {
                        listener.OnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener
    {
        void OnItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
        this.listener = listener;
    }
}
