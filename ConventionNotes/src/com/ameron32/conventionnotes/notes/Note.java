package com.ameron32.conventionnotes.notes;

import android.util.Log;

import com.ameron32.conventionnotes.scripture.Scripture;
import com.ameron32.conventionnotes.scripture.ScriptureNote;

public class Note {
  
  protected Note.Type type;
  private String      note;
  
  public static Note createNote(Scripture scripture) {
    return new ScriptureNote(scripture);
  }
  
  public static Note createNote(String note) {
    Note.Type type = determineNoteType(note);;
    if (type == Note.Type.Scripture) {
      return new ScriptureNote(note);
    } else {
      return new Note(note);
    }
  }
  
  protected Note(String note) {
    this.type = determineNoteType(note);
    this.setNote(note);
  }
  
  protected Note(Scripture scripture) {
    this.type = Note.Type.Scripture;
    this.setNote(scripture.getNote());
  }
  
  public String getNote() {
    Log.d(this.getClass().getSimpleName(), this.toString());
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public enum Type {
    Standard, Scripture
  }
  
  private static Note.Type determineNoteType(String note) {
    if (note.startsWith("@")) {
      return Note.Type.Scripture;
    }
    else {
      return Note.Type.Standard;
    }
  }

  @Override
  public String toString() {
    return "Note [type=" + type + ", note=" + note + "]";
  }
  
  
}
