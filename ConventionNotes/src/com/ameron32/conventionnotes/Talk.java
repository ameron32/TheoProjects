package com.ameron32.conventionnotes;

import java.util.ArrayList;
import java.util.List;

public class Talk {
  
  private String[] titleLines;
  private List<Note> notes = new ArrayList<Note>();
  private StringBuilder sb;
  
  public Talk(String... title) {
    this.titleLines = title;
  }

  public Note getNote(int position) {
    return getNotes().get(position);
  }
  
  public List<Note> getNotes() {
    return notes;
  }
  
  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public String getTitle() {
    sb = new StringBuilder();
    for (int i = 0; i < titleLines.length; i++) {
      String line = titleLines[i];
      if (i != 0) {
        sb.append(" ");
      }
      sb.append(line);
    }
    return sb.toString();
  }
  
  public String getMultiLineTitle() {
    sb = new StringBuilder();
    for (int i = 0; i < titleLines.length; i++) {
      String line = titleLines[i];
      if (i != 0) {
        sb.append("\n");
        sb.append("  ");
      }
      sb.append(line);
    }
    return sb.toString();
  }

  public void setTitle(String... title) {
    this.titleLines = title;
  }
  
  public void addNote(Note note) {
    notes.add(note);
  }
}
