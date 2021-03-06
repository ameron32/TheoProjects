package com.ameron32.tap.conventionnotes.program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ameron32.tap.conventionnotes.notes.ImportantNote;
import com.ameron32.tap.conventionnotes.notes.Note;
import com.ameron32.tap.conventionnotes.notes.SpeakerNote;

public class Talk extends ProgramEvent {
  
  private static final long    serialVersionUID = -5743232377202758745L;
  
  private static StringBuilder sb;
  
  private int                  talkNumber;
  private String[]             titleLines;
  private List<Note>           notes            = new ArrayList<Note>();
  
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
  
  public String getMultiLineTitle(boolean withSpaces) {
    sb = new StringBuilder();
    for (int i = 0; i < titleLines.length; i++) {
      String line = titleLines[i];
      if (i != 0) {
        sb.append("\n");
        if (withSpaces) {
          sb.append("  ");
        }
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
  
  public void removeNote(int position) {
    notes.remove(position);
  }
  
  public void setTalkNumber(int talkNumber) {
    this.talkNumber = talkNumber;
  }
  
  public int getTalkNumber() {
    return talkNumber;
  }
  
  @Override
  public String toString() {
    return "Talk [talkNumber=" + talkNumber + ", titleLines=" + Arrays.toString(titleLines) + ", notes=" + notes
        + " SUPER" + super.toString() + "]";
  }
  
  @Override
  public String getExportText() {
    return getProgramTime() + "   " + getMultiLineTitle(true) + "\n" + getNoteExportText();
  }
  
  private String getNoteExportText() {
    sb = new StringBuilder();
    sb.append(Note.EXPORT_HEADER);
    sb.append(Note.EXPORT_NOTE_SEPARATOR);
    for (Note n : notes) {
      sb.append("\n");
      sb.append(n.getExportText());
      sb.append(Note.EXPORT_NOTE_SEPARATOR);
    }
    sb.append(Note.EXPORT_FOOTER);
    return sb.toString();
  }
  
  @Override
  public String getTitleText() {
    return getMultiLineTitle(false);
  }

  public void addSpeakerNote(Note note) {
    note.setNote("Speaker: " + note.getNote());
    notes.add(addAtPosition(NotePriority.Speaker), note);
  }
  
  public void addImportantNote(Note note) {
    note.setNote(note.getNote());
    notes.add(addAtPosition(NotePriority.Important), note);
  }
  
  private int addAtPosition(Talk.NotePriority priority) {
    for (int i = 0; i < notes.size(); i++) {
      switch (priority) {
      case Speaker:
        if (!(notes.get(i) instanceof SpeakerNote)) { return i; }
        break;
      case Important:
        if (!(notes.get(i) instanceof ImportantNote || notes.get(i) instanceof SpeakerNote)) { return i; }
        break;
      default:
        // do nothing
      }
    }
    
    return notes.size();
  }
  
  private enum NotePriority {
    Speaker, Important, Standard
  }
}
