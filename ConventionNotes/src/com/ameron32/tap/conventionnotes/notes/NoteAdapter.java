package com.ameron32.tap.conventionnotes.notes;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ameron32.conventionnotes.R;
import com.ameron32.tap.conventionnotes.program.Talk;
import com.ameron32.tap.conventionnotes.scripture.ScriptureNote;

public class NoteAdapter extends BaseAdapter {
  
  private LayoutInflater inflater;
  // private Context context;
  private Talk           talk;
  
  public NoteAdapter(Context context, Talk talk) {
    // this.context = context;
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
      convertView = inflater.inflate(R.layout.note_standard, parent, false);
      holder = new NoteViewHolder();
      
      holder.forScripture = (LinearLayout) convertView.findViewById(R.id.linearlayout_for_scripture);
      holder.noteTextView = (TextView) convertView.findViewById(R.id.textview_note);
      holder.inputTextView = (TextView) convertView.findViewById(R.id.textview_input);
      holder.scriptureTextView = (TextView) convertView.findViewById(R.id.textview_scripture);
      
      convertView.setTag(holder);
    }
    else {
      holder = (NoteViewHolder) convertView.getTag();
    }
    
    Spanned noteSpan = null;
    String noteText = talk.getNote(position).getNote();
    if (note instanceof ScriptureNote) {
      ScriptureNote sNote = ((ScriptureNote) note);
      // String combined = sNote.getNote() + "\n" +
      //
      // sNote.scriptureText.toString();
      // SpannedString spannedString = new SpannedString(combined);
      String text = sNote.scriptureText;
      if (text != null) {
        noteSpan = Html.fromHtml(text);
      }
      else {
        noteSpan = Html.fromHtml("Error parsing scripture text.");
      }
    }
    
    if (note instanceof ScriptureNote) {
      holder.noteTextView.setText("");
      holder.noteTextView.setVisibility(View.GONE);
      holder.inputTextView.setText(noteText);
      holder.forScripture.setVisibility(View.VISIBLE);
      holder.scriptureTextView.setText(noteSpan);
    }
    else {
      holder.noteTextView.setVisibility(View.VISIBLE);
      holder.noteTextView.setText(noteText);
      holder.forScripture.setVisibility(View.GONE);
    }
    
    return convertView;
  }
  
  public void addNote(Note note) {
    if (note.getNote().startsWith("!")) {
      note.setNote(note.getNote().substring(1));
      talk.addImportantNote(new ImportantNote(note));
    } else 
    if (note.getNote().startsWith("$")) {
      note.setNote(note.getNote().substring(1));
      talk.addSpeakerNote(new SpeakerNote(note));
    } else {
      talk.addNote(note);
    }
  }
  
  public void removeNote(int position) {
    talk.removeNote(position);
  }
  
  public static class NoteViewHolder {
    
    public LinearLayout forScripture;
    public TextView     noteTextView;
    public TextView     inputTextView;
    public View         spacer;
    public TextView     scriptureTextView;
  }
}
