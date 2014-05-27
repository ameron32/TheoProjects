package com.ameron32.conventionnotes.scripture;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

public class ScriptureFinder {
  
  Context c;
  String  fileName;
  String  bookName;
  File    chapterFile;
  int     chapter;
  int     verse;
  
  public String findScriptures(Context c, String bookName, int chapter, int[] verses)
      throws ScriptureNotFoundError {
    
    StringBuilder sb = new StringBuilder();
    for (int verse : verses) {
      try {
        sb.append(FindScripture(c, bookName, chapter, verse));
      } catch (ScriptureNotFoundError e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }
  
  public String FindScripture(Context c, String bookName, int chapter, int verse)
      throws ScriptureNotFoundError {
    this.c = c;
    this.chapter = chapter;
    this.verse = verse;
    this.bookName = bookName;
    fileName = getFileName();
    return getVerse();
  }
  
  private InputStream openBibleFile()
      throws ChapterNotFoundError {
    InputStream epubInputStream = null;
    AssetManager am = c.getAssets();
    try {
      epubInputStream = am.open(fileName);
    }
    catch (IOException e) {
      ChapterNotFoundError cnf = new ChapterNotFoundError();
      cnf.chapter = chapter;
      e.printStackTrace();
      Toast.makeText(c, bookName + " " + String.valueOf(chapter) + ":" + String.valueOf(verse) + " not found.", Toast.LENGTH_LONG).show();
      throw cnf;
    }
    return epubInputStream;
  }
  
  private String getFileName()
      throws ScriptureNotFoundError {
    String fileName = "";
    String bookAbbrev = getBookAbbrev(bookName);
    int bookNumber = getBookNumber(bookAbbrev);
    int val = 24 + (2 * bookNumber);
    
    if (chapter == 1) {
      if (val > 99) {
        fileName = String.valueOf(val) + "_" + bookAbbrev + "-1.xhtml";
      }
      else fileName = "0" + String.valueOf(val) + "_" + bookAbbrev + "-1.xhtml";
    }
    else {
      
      if (val > 99) {
        fileName = String.valueOf(val) + "_" + bookAbbrev + "-1-split" + String.valueOf(chapter) + ".xhtml";
      }
      else fileName = "0" + String.valueOf(val) + "_" + bookAbbrev + "-1-split" + String.valueOf(chapter) + ".xhtml";
    }
    
    return fileName;
  }
  
  private String getBookAbbrev(String bookName)
      throws BookNotFoundError {
    
    if (bookName.equalsIgnoreCase("GENESIS")) return "GE";
    if (bookName.equalsIgnoreCase("EXODUS")) return "EX";
    if (bookName.equalsIgnoreCase("LEVITICUS")) return "LE";
    if (bookName.equalsIgnoreCase("NUMBERS")) return "NU";
    if (bookName.equalsIgnoreCase("DEUTERONOMY")) return "DE";
    if (bookName.equalsIgnoreCase("JOSHUA")) return "JOS";
    if (bookName.equalsIgnoreCase("JUDGES")) return "JG";
    if (bookName.equalsIgnoreCase("RUTH")) return "RU";
    if (bookName.equalsIgnoreCase("1SAMUEL")) return "1SA";
    if (bookName.equalsIgnoreCase("2SAMUEL")) return "2SA";
    if (bookName.equalsIgnoreCase("1KINGS")) return "1KI";
    if (bookName.equalsIgnoreCase("2KINGS")) return "2KI";
    if (bookName.equalsIgnoreCase("1CHRONICLES")) return "1CH";
    if (bookName.equalsIgnoreCase("2CHRONICLES")) return "2CH";
    if (bookName.equalsIgnoreCase("EZRA")) return "EZR";
    if (bookName.equalsIgnoreCase("NEHEMIAH")) return "NE";
    if (bookName.equalsIgnoreCase("ESTHER")) return "ES";
    if (bookName.equalsIgnoreCase("JOB")) return "JOB";
    if (bookName.equalsIgnoreCase("PSALMS")) return "PS";
    if (bookName.equalsIgnoreCase("PROVERBS")) return "PR";
    if (bookName.equalsIgnoreCase("ECCLESIASTES")) return "EC";
    if (bookName.equalsIgnoreCase("SONG OF SOLOMON")) return "CA";
    if (bookName.equalsIgnoreCase("ISAIAH")) return "ISA";
    if (bookName.equalsIgnoreCase("JEREMIAH")) return "JER";
    if (bookName.equalsIgnoreCase("LAMENTATIONS")) return "LA";
    if (bookName.equalsIgnoreCase("EZEKIEL")) return "EZE";
    if (bookName.equalsIgnoreCase("DANIEL")) return "DA";
    if (bookName.equalsIgnoreCase("HOSEA")) return "HOS";
    if (bookName.equalsIgnoreCase("JOEL")) return "JOE";
    if (bookName.equalsIgnoreCase("AMOS")) return "AM";
    if (bookName.equalsIgnoreCase("OBADIAH")) return "OB";
    if (bookName.equalsIgnoreCase("JONAH")) return "JON";
    if (bookName.equalsIgnoreCase("MICAH")) return "MIC";
    if (bookName.equalsIgnoreCase("NAHUM")) return "NAH";
    if (bookName.equalsIgnoreCase("HABAKUK")) return "HAB";
    if (bookName.equalsIgnoreCase("ZEPHENIAH")) return "ZEP";
    if (bookName.equalsIgnoreCase("HAGAI")) return "HAG";
    if (bookName.equalsIgnoreCase("ZECHARIAH")) return "ZEC";
    if (bookName.equalsIgnoreCase("MALACHI")) return "MAL";
    if (bookName.equalsIgnoreCase("MATTHEW")) return "MT";
    if (bookName.equalsIgnoreCase("MARK")) return "MR";
    if (bookName.equalsIgnoreCase("LUKE")) return "LU";
    if (bookName.equalsIgnoreCase("JOHN")) return "JOH";
    if (bookName.equalsIgnoreCase("ACTS")) return "AC";
    if (bookName.equalsIgnoreCase("ROMANS")) return "RO";
    if (bookName.equalsIgnoreCase("1CORINTHIANS")) return "1CO";
    if (bookName.equalsIgnoreCase("2CORINTHIANS")) return "2CO";
    if (bookName.equalsIgnoreCase("GALATIANS")) return "GAL";
    if (bookName.equalsIgnoreCase("EPHESIANS")) return "EPH";
    if (bookName.equalsIgnoreCase("PHILIPIANS")) return "PHP";
    if (bookName.equalsIgnoreCase("COLOSSIANS")) return "COL";
    if (bookName.equalsIgnoreCase("1THESSALONIANS")) return "1TH";
    if (bookName.equalsIgnoreCase("2THESSALONIANS")) return "2TH";
    if (bookName.equalsIgnoreCase("1TIMOTHY")) return "1TI";
    if (bookName.equalsIgnoreCase("2TIMOTHY")) return "2TI";
    if (bookName.equalsIgnoreCase("TITUS")) return "TIT";
    if (bookName.equalsIgnoreCase("PHILEMON")) return "PHM";
    if (bookName.equalsIgnoreCase("HEBREWS")) return "HEB";
    if (bookName.equalsIgnoreCase("JAMES")) return "JAS";
    if (bookName.equalsIgnoreCase("1 PETER")) return "1PE";
    if (bookName.equalsIgnoreCase("2 PETER")) return "2PE";
    if (bookName.equalsIgnoreCase("1 JOHN")) return "1JO";
    if (bookName.equalsIgnoreCase("2 JOHN")) return "2JO";
    if (bookName.equalsIgnoreCase("3 JOHN")) return "3JO";
    if (bookName.equalsIgnoreCase("JUDE")) return "JUD";
    if (bookName.equalsIgnoreCase("REVELATION")) return "RE";
    
    BookNotFoundError bnf = new BookNotFoundError();
    bnf.book = bookName;
    Toast.makeText(c, bookName + " " + String.valueOf(chapter) + ":" + String.valueOf(verse) + " not found.", Toast.LENGTH_LONG).show();
    throw bnf;
  }
  
  private int getBookNumber(String abbrevBookName) {
    if (abbrevBookName.equalsIgnoreCase("GE")) return 1;
    if (abbrevBookName.equalsIgnoreCase("EX")) return 2;
    if (abbrevBookName.equalsIgnoreCase("LE")) return 3;
    if (abbrevBookName.equalsIgnoreCase("NU")) return 4;
    if (abbrevBookName.equalsIgnoreCase("DE")) return 5;
    if (abbrevBookName.equalsIgnoreCase("JOS")) return 6;
    if (abbrevBookName.equalsIgnoreCase("JG")) return 7;
    if (abbrevBookName.equalsIgnoreCase("RU")) return 8;
    if (abbrevBookName.equalsIgnoreCase("1SA")) return 9;
    if (abbrevBookName.equalsIgnoreCase("2SA")) return 10;
    if (abbrevBookName.equalsIgnoreCase("1KI")) return 11;
    if (abbrevBookName.equalsIgnoreCase("2KI")) return 12;
    if (abbrevBookName.equalsIgnoreCase("1CH")) return 13;
    if (abbrevBookName.equalsIgnoreCase("2CH")) return 14;
    if (abbrevBookName.equalsIgnoreCase("EZR")) return 15;
    if (abbrevBookName.equalsIgnoreCase("NE")) return 16;
    if (abbrevBookName.equalsIgnoreCase("ES")) return 17;
    if (abbrevBookName.equalsIgnoreCase("JOB")) return 18;
    if (abbrevBookName.equalsIgnoreCase("PS")) return 19;
    if (abbrevBookName.equalsIgnoreCase("PR")) return 20;
    if (abbrevBookName.equalsIgnoreCase("EC")) return 21;
    if (abbrevBookName.equalsIgnoreCase("CA")) return 22;
    if (abbrevBookName.equalsIgnoreCase("ISA")) return 23;
    if (abbrevBookName.equalsIgnoreCase("JER")) return 24;
    if (abbrevBookName.equalsIgnoreCase("LA")) return 25;
    if (abbrevBookName.equalsIgnoreCase("EZE")) return 26;
    if (abbrevBookName.equalsIgnoreCase("DA")) return 27;
    if (abbrevBookName.equalsIgnoreCase("HOS")) return 28;
    if (abbrevBookName.equalsIgnoreCase("JOE")) return 29;
    if (abbrevBookName.equalsIgnoreCase("AM")) return 30;
    if (abbrevBookName.equalsIgnoreCase("OB")) return 31;
    if (abbrevBookName.equalsIgnoreCase("JON")) return 32;
    if (abbrevBookName.equalsIgnoreCase("MIC")) return 33;
    if (abbrevBookName.equalsIgnoreCase("NAH")) return 34;
    if (abbrevBookName.equalsIgnoreCase("HAB")) return 35;
    if (abbrevBookName.equalsIgnoreCase("ZEP")) return 36;
    if (abbrevBookName.equalsIgnoreCase("HAG")) return 37;
    if (abbrevBookName.equalsIgnoreCase("ZEC")) return 38;
    if (abbrevBookName.equalsIgnoreCase("MAL")) return 39;
    if (abbrevBookName.equalsIgnoreCase("MT")) return 40;
    if (abbrevBookName.equalsIgnoreCase("MR")) return 41;
    if (abbrevBookName.equalsIgnoreCase("LU")) return 42;
    if (abbrevBookName.equalsIgnoreCase("JOH")) return 43;
    if (abbrevBookName.equalsIgnoreCase("AC")) return 44;
    if (abbrevBookName.equalsIgnoreCase("RO")) return 45;
    if (abbrevBookName.equalsIgnoreCase("1CO")) return 46;
    if (abbrevBookName.equalsIgnoreCase("2CO")) return 47;
    if (abbrevBookName.equalsIgnoreCase("GAL")) return 48;
    if (abbrevBookName.equalsIgnoreCase("EPH")) return 49;
    if (abbrevBookName.equalsIgnoreCase("PHI")) return 50;
    if (abbrevBookName.equalsIgnoreCase("COL")) return 51;
    if (abbrevBookName.equalsIgnoreCase("1TH")) return 52;
    if (abbrevBookName.equalsIgnoreCase("2TH")) return 53;
    if (abbrevBookName.equalsIgnoreCase("1TI")) return 54;
    if (abbrevBookName.equalsIgnoreCase("2TI")) return 55;
    if (abbrevBookName.equalsIgnoreCase("TIT")) return 56;
    if (abbrevBookName.equalsIgnoreCase("PHM")) return 57;
    if (abbrevBookName.equalsIgnoreCase("HEB")) return 58;
    if (abbrevBookName.equalsIgnoreCase("JAS")) return 59;
    if (abbrevBookName.equalsIgnoreCase("1PE")) return 60;
    if (abbrevBookName.equalsIgnoreCase("2PE")) return 61;
    if (abbrevBookName.equalsIgnoreCase("1JO")) return 62;
    if (abbrevBookName.equalsIgnoreCase("2JO")) return 63;
    if (abbrevBookName.equalsIgnoreCase("3JO")) return 64;
    if (abbrevBookName.equalsIgnoreCase("JUD")) return 65;
    return 66;
  }
  
  private String getVerse()
      throws ScriptureNotFoundError {
    
    BufferedReader bsr = new BufferedReader(new InputStreamReader(openBibleFile()));
    
    String chapterEndMark = "<div class=\"groupFootnote\">";
    String s = "";
    String startMark = getStartMarkerString();
    String endMark = getEndMarkerString();
    char v = ' ';
    
    while (!s.contains(startMark)) {
      
      try {
        v = (char) bsr.read();
        s = s.concat(String.valueOf(v));
        
        if (s.contains(chapterEndMark)) {
          Toast.makeText(c, bookName + " " + String.valueOf(chapter) + ":" + String.valueOf(verse) + " not found.", Toast.LENGTH_LONG).show();
          VerseNotFoundError vnf = new VerseNotFoundError();
          vnf.verse = verse;
          throw vnf;
        }
        
      }
      catch (StreamCorruptedException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    s = "";
    while (!s.contains(endMark) && !s.contains(chapterEndMark)) {
      try {
        v = (char) bsr.read();
        s = s.concat(String.valueOf(v));
      }
      catch (StreamCorruptedException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    s.replaceAll(endMark, "");
    Log.i("Verse Text", s);
    return s;
  }
  
  private String getStartMarkerString() {
    String s = "chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(verse) + "\"></span>";
    return s;
  }
  
  private String getEndMarkerString() {
    
    String s = "chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(verse + 1) + "\"></span>";
    return s;
    
  }
  
}
