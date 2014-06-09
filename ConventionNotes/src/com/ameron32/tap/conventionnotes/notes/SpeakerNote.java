package com.ameron32.tap.conventionnotes.notes;

public class SpeakerNote extends Note {
  
  private static final long serialVersionUID = 9203833835557304423L;
  
  public SpeakerNote(Note note) {
    super(note.getNote());
  }
  
}
