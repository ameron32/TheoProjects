package com.ameron32.conventionnotes.notes;

import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ameron32.conventionnotes.R;
import com.ameron32.conventionnotes.program.Talk;
import com.ameron32.conventionnotes.scripture.ScriptureNote;


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
    Note note = getItem(position);
    NoteViewHolder holder = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.standard_note, parent, false);
      holder = new NoteViewHolder();
      holder.noteTextView = (TextView) convertView.findViewById(R.id.textview_note);
      convertView.setTag(holder);
    } else {
      holder = (NoteViewHolder) convertView.getTag();
    }
    
    Spanned noteSpan = null;
    String noteText = talk.getNote(position).getNote();
    if (note instanceof ScriptureNote) {
      ScriptureNote sNote = ((ScriptureNote) note);
//      String combined = sNote.getNote() + "\n" +
//          
//          sNote.scriptureText.toString();
//      SpannedString spannedString = new SpannedString(combined);
      noteSpan = sNote.scriptureText;
    }
//    holder.noteTextView = (TextView) convertView.findViewById(R.id.textView1);
    holder.noteTextView.setText((noteSpan != null) ? noteSpan : noteText);
    
    
    return convertView;
  }
  
  public void addNote(Note note) {
    talk.addNote(note);
  }
  
  public static class NoteViewHolder {
    public TextView noteTextView;
  }
}
