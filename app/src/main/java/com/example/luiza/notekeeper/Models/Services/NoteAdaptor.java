package com.example.luiza.notekeeper.Models.Services;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luiza.notekeeper.Models.Note;
import com.example.luiza.notekeeper.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdaptor extends BaseAdapter {
    private Context context;
    private List<Note> notes;
    private SimpleDateFormat dateFormat;

    public NoteAdaptor(Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_item_layout, parent, false);
        }

        TextView tvData = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);

        Note current = notes.get(position);

        tvData.setText(dateFormat.format(current.getCreateDate()));
        tvInfo.setText(current.getNote());

        return convertView;
    }

    public void updateNotes(List<Note> notes) {
        this.notes = notes;
        super.notifyDataSetChanged();
    }
}
