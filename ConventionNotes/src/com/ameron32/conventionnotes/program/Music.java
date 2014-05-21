package com.ameron32.conventionnotes.program;


public class Music extends ProgramEvent {

  public String getMusicText() {
    return "Music";
  }
  
  @Override
  public String toString() {
    return "Music ["+ " SUPER" + super.toString() + "]";
  }
  
}
