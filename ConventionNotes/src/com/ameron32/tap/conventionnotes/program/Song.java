package com.ameron32.tap.conventionnotes.program;

public class Song extends ProgramEvent {
  private static final long serialVersionUID = 5978776981241022573L;

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
      prayer = true;
      break;
    case "a":
      announcements = true;
      break;
    case "i":
      intermission = true;
      break;
    default:
    }
  }

  public String getSongText() {
    String other = "";
    if (prayer) {
      other = " and Prayer";
    } else if (announcements) {
      other = " and Announcements";
    } else if (intermission) {
      other = " and Intermission";
    }
    return "Song " + number + other; 
  }

  @Override
  public String toString() {
    return "Song [number=" + number + ", prayer=" + prayer + ", announcements=" + announcements + ", intermission="
        + intermission + " SUPER" + super.toString() + "]";
  }
  
  @Override
  public String getExportText() {
    return getProgramTime() + "   " + getSongText();
  }
  
  @Override
  public String getTitleText() {
    return getSongText();
  }

}
