package com.ameron32.conventionnotes.program;


public class Music extends ProgramEvent {
  private static final long serialVersionUID = 5598677320804092336L;

  public String getMusicText() {
    return "Music";
  }
  
  @Override
  public String toString() {
    return "Music ["+ " SUPER" + super.toString() + "]";
  }

  @Override
  public String getExportText() {
    return getProgramTime() + "   " + getMusicText();
  }
  
  
  
}
