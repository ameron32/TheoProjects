package com.ameron32.tap.conventionnotes.scripture;


public class Scripture {
  public String book;
  public int chapter;
  public int[] verses;
  
  
  
  public Scripture(String book, int chapter, int[] verses) {
    super();
    this.book = book;
    this.chapter = chapter;
    this.verses = verses;
  }

  public void setVerses(int... verses) {
    this.verses = verses;
  }
  
  public String getNote() {
    String sVerses = "";
    for (int v = 0; v < verses.length; v++) {
      if (v != 0) {
        sVerses += ",";
      }  
      sVerses += v;
    }
    return book + " " + chapter + " " + sVerses; 
  }
}
