package com.ameron32.conventionnotes.notes;


public class ImportantNote extends Note {
  private static final long serialVersionUID = -8669667089440820587L;

  protected ImportantNote(Note note) {
    super(note.getNote());
  }
}
