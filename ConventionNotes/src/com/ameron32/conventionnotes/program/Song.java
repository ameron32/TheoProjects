package com.ameron32.conventionnotes.program;

public class Song extends ProgramEvent {
  
  int     number;
  boolean prayer;
  boolean announcements;
  boolean intermission;
  
  public Song(int number, String addon) {
    super();
    this.number = number;
    postAddon(addon);
  }
  
  public Song(int number) {
    super();
    this.number = number;
    postAddon(null);
  }
  
  private void postAddon(String addon) {
    prayer = false;
    announcements = false;
    intermission = false;
    
    if (addon == null || addon == "") return;
    
    switch (addon) {
    case "p":
      
      break;
    case "a":
      
      break;
    case "i":
      
      break;
    default:
    }
  }

  public String getSongText() {
    String other = "";
    if (prayer) {
      other = " and prayer";
    } else if (announcements) {
      other = " and announcements";
    } else if (intermission) {
      other = " and intermission";
    }
    return "Song " + number + other; 
  }

  @Override
  public String toString() {
    return "Song [number=" + number + ", prayer=" + prayer + ", announcements=" + announcements + ", intermission="
        + intermission + " SUPER" + super.toString() + "]";
  }
  
  
}
