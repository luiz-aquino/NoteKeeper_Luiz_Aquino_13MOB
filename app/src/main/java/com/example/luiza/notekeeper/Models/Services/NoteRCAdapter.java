package com.example.luiza.notekeeper.Models.Services;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luiza.notekeeper.Models.Note;
import com.example.luiza.notekeeper.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by luiza on 2017-09-14.
 */

public class NoteRCAdapter extends RecyclerView.Adapter<NoteRCAdapter.ViewHolder> {
    List<Note> notes;
    private SimpleDateFormat dateFormat;
    private MyOnClickListener listener;

    public NoteRCAdapter(List<Note> notes, MyOnClickListener clickListener) {
        this.notes = notes;
        this.listener = clickListener;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.tvDate.setText(dateFormat.format(currentNote.getCreateDate()));
        holder.tvNoteInfo.setText(currentNote.getNote());

        holder.bind(notes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvDate;
        TextView tvNoteInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_note);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_note);
            tvNoteInfo = (TextView) itemView.findViewById(R.id.tv_note_info);

            Linkify.addLinks(tvNoteInfo, Linkify.ALL);
        }

        public void bind(final Note note, final MyOnClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ItemClicked(note);
                }
            });
        }
    }

    public interface MyOnClickListener {
        void ItemClicked(Note note);
    }

    public void updateDataSet(List<Note> notes){
        this.notes = notes;
        this.notifyDataSetChanged();
    }
}
