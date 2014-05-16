package com.ameron32.conventionnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class NoteAdapter extends BaseAdapter {
  
  private LayoutInflater inflater;
  private Context context;
  private Talk talk;

  public NoteAdapter(Context context, Talk talk) {
    this.context = context;
    this.talk = talk;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return talk.getNotes().size();
  }
  
  @Override
  public Note getItem(int position) {
    return talk.getNote(position);
  }
  
  @Override
  public long getItemId(int position) {
    return position;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    NoteViewHolder holder = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.standard_note, parent, false);
      holder = new NoteViewHolder();
      holder.noteTextView = (TextView) convertView.findViewById(R.id.textview_note);
      convertView.setTag(holder);
    } else {
      holder = (NoteViewHolder) convertView.getTag();
    }
    
//    holder.noteTextView = (TextView) convertView.findViewById(R.id.textView1);
    holder.noteTextView.setText(talk.getNote(position).getNote());
    
    return convertView;
  }
  
  public void addNote(Note note) {
    talk.addNote(note);
  }
  
  public static class NoteViewHolder {
    public TextView noteTextView;
  }
}
