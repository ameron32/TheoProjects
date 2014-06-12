package com.ameron32.tap.conventionnotes.scripture;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.ameron32.tap.conventionnotes.tools.Testing;

public class ScriptureFinderKris {
  
  Context                     c;
  String                      fileName;
  String                      bookName;
  String                      chapterText;
  File                        chapterFile;
  int                         chapter;
  int                         verse;
  private static final String endFileMarker = "<div class=\"groupFootnote\">";
  
  public String findScriptures(Context c, String bookName, int chapter, int[] verses)
      throws ScriptureNotFoundError {
    
    Testing.startTest("wholeVerseKris");
    
    this.c = c;
    this.chapter = chapter;
    this.bookName = bookName;
    fileName = getFileName();
    StringBuilder sb = new StringBuilder();
    boolean versesNotFound = false;
    try {
      
      // TODO-- undo my changes
      // readChapterTextA();
      
      // TODO-- undo my changes
      readChapterTextB();
      
      for (int verse : verses) {
        sb.append(FindScripture(c, bookName, chapter, verse));
      }
    }
    catch (ScriptureNotFoundError e) {
      if (e instanceof ChapterNotFoundError) {
        Toast.makeText(c, "Chapter not found.", Toast.LENGTH_LONG).show();
        throw e;
      }
      else
        if (e instanceof BookNotFoundError) {
          Toast.makeText(c, "Book not found.", Toast.LENGTH_LONG).show();
          throw e;
        }
        else {
          versesNotFound = true;
        }
    }
    if (versesNotFound) {
      Toast.makeText(c, "Some verses not found.", Toast.LENGTH_LONG).show();
    }
    
    Testing.endTest("wholeVerseKris");
    String complete = sb.toString();
    Testing.report(complete);
    return complete;
  }
  
  private String FindScripture(Context c, String bookName, int chapter, int verse)
      throws ScriptureNotFoundError {
    String s = "";
    try {
      this.verse = verse;
      
      // TODO-- undo my changes
      // s = getVerseA();
      
      // TODO-- undo my changes
      s = getVerseB();
    }
    catch (ScriptureNotFoundError e) {
      throw e;
    }
    return s;
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
      Toast.makeText(c, bookName + "" + String.valueOf(chapter) + ":" + String.valueOf(verse) + " not found.", Toast.LENGTH_LONG).show();
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
    
    if (bookName.equals("GENESIS")) return "GE";
    if (bookName.equals("EXODUS")) return "EX";
    if (bookName.equals("LEVITICUS")) return "LE";
    if (bookName.equals("NUMBERS")) return "NU";
    if (bookName.equals("DEUTERONOMY")) return "DE";
    if (bookName.equals("JOSHUA")) return "JOS";
    if (bookName.equals("JUDGES")) return "JG";
    if (bookName.equals("RUTH")) return "RU";
    if (bookName.equals("1 SAMUEL")) return "1SA";
    if (bookName.equals("2 SAMUEL")) return "2SA";
    if (bookName.equals("1 KINGS")) return "1KI";
    if (bookName.equals("2 KINGS")) return "2KI";
    if (bookName.equals("1 CHRONICLES")) return "1CH";
    if (bookName.equals("2 CHRONICLES")) return "2CH";
    if (bookName.equals("EZRA")) return "EZR";
    if (bookName.equals("NEHEMIAH")) return "NE";
    if (bookName.equals("ESTHER")) return "ES";
    if (bookName.equals("JOB")) return "JOB";
    if (bookName.equals("PSALMS")) return "PS";
    if (bookName.equals("PROVERBS")) return "PR";
    if (bookName.equals("ECCLESIASTES")) return "EC";
    if (bookName.equals("SONG OF SOLOMON")) return "CA";
    if (bookName.equals("ISAIAH")) return "ISA";
    if (bookName.equals("JEREMIAH")) return "JER";
    if (bookName.equals("LAMENTATIONS")) return "LA";
    if (bookName.equals("EZEKIEL")) return "EZE";
    if (bookName.equals("DANIEL")) return "DA";
    if (bookName.equals("HOSEA")) return "HOS";
    if (bookName.equals("JOEL")) return "JOE";
    if (bookName.equals("AMOS")) return "AM";
    if (bookName.equals("OBADIAH")) return "OB";
    if (bookName.equals("JONAH")) return "JON";
    if (bookName.equals("MICAH")) return "MIC";
    if (bookName.equals("NAHUM")) return "NAH";
    if (bookName.equals("HABAKUK")) return "HAB";
    if (bookName.equals("ZEPHENIAH")) return "ZEP";
    if (bookName.equals("HAGAI")) return "HAG";
    if (bookName.equals("ZECHARIAH")) return "ZEC";
    if (bookName.equals("MALACHI")) return "MAL";
    if (bookName.equals("MATTHEW")) return "MT";
    if (bookName.equals("MARK")) return "MR";
    if (bookName.equals("LUKE")) return "LU";
    if (bookName.equals("JOHN")) return "JOH";
    if (bookName.equals("ACTS")) return "AC";
    if (bookName.equals("ROMANS")) return "RO";
    if (bookName.equals("1 CORINTHIANS")) return "1CO";
    if (bookName.equals("2 CORINTHIANS")) return "2CO";
    if (bookName.equals("GALATIANS")) return "GAL";
    if (bookName.equals("EPHESIANS")) return "EPH";
    if (bookName.equals("PHILIPIANS")) return "PHP";
    if (bookName.equals("COLOSSIANS")) return "COL";
    if (bookName.equals("1 THESSALONIANS")) return "1TH";
    if (bookName.equals("2 THESSALONIANS")) return "2TH";
    if (bookName.equals("1 TIMOTHY")) return "1TI";
    if (bookName.equals("2 TIMOTHY")) return "2TI";
    if (bookName.equals("TITUS")) return "TIT";
    if (bookName.equals("PHILEMON")) return "PHM";
    if (bookName.equals("HEBREWS")) return "HEB";
    if (bookName.equals("JAMES")) return "JAS";
    if (bookName.equals("1 PETER")) return "1PE";
    if (bookName.equals("2 PETER")) return "2PE";
    if (bookName.equals("1 JOHN")) return "1JO";
    if (bookName.equals("2 JOHN")) return "2JO";
    if (bookName.equals("3 JOHN")) return "3JO";
    if (bookName.equals("JUDE")) return "JUD";
    if (bookName.equals("REVELATION")) return "RE";
    BookNotFoundError bnf = new BookNotFoundError();
    bnf.book = bookName;
    Toast.makeText(c, bookName + "" + String.valueOf(chapter) + ":" + String.valueOf(verse) + " not found.", Toast.LENGTH_LONG).show();
    throw bnf;
  }
  
  private int getBookNumber(String abbrevBookName) {
    if (abbrevBookName.equals("GE")) return 1;
    if (abbrevBookName.equals("EX")) return 2;
    if (abbrevBookName.equals("LE")) return 3;
    if (abbrevBookName.equals("NU")) return 4;
    if (abbrevBookName.equals("DE")) return 5;
    if (abbrevBookName.equals("JOS")) return 6;
    if (abbrevBookName.equals("JG")) return 7;
    if (abbrevBookName.equals("RU")) return 8;
    if (abbrevBookName.equals("1SA")) return 9;
    if (abbrevBookName.equals("2SA")) return 10;
    if (abbrevBookName.equals("1KI")) return 11;
    if (abbrevBookName.equals("2KI")) return 12;
    if (abbrevBookName.equals("1CH")) return 13;
    if (abbrevBookName.equals("2CH")) return 14;
    if (abbrevBookName.equals("EZR")) return 15;
    if (abbrevBookName.equals("NE")) return 16;
    if (abbrevBookName.equals("ES")) return 17;
    if (abbrevBookName.equals("JOB")) return 18;
    if (abbrevBookName.equals("PS")) return 19;
    if (abbrevBookName.equals("PR")) return 20;
    if (abbrevBookName.equals("EC")) return 21;
    if (abbrevBookName.equals("CA")) return 22;
    if (abbrevBookName.equals("ISA")) return 23;
    if (abbrevBookName.equals("JER")) return 24;
    if (abbrevBookName.equals("LA")) return 25;
    if (abbrevBookName.equals("EZE")) return 26;
    if (abbrevBookName.equals("DA")) return 27;
    if (abbrevBookName.equals("HOS")) return 28;
    if (abbrevBookName.equals("JOE")) return 29;
    if (abbrevBookName.equals("AM")) return 30;
    if (abbrevBookName.equals("OB")) return 31;
    if (abbrevBookName.equals("JON")) return 32;
    if (abbrevBookName.equals("MIC")) return 33;
    if (abbrevBookName.equals("NAH")) return 34;
    if (abbrevBookName.equals("HAB")) return 35;
    if (abbrevBookName.equals("ZEP")) return 36;
    if (abbrevBookName.equals("HAG")) return 37;
    if (abbrevBookName.equals("ZEC")) return 38;
    if (abbrevBookName.equals("MAL")) return 39;
    if (abbrevBookName.equals("MT")) return 40;
    if (abbrevBookName.equals("MR")) return 41;
    if (abbrevBookName.equals("LU")) return 42;
    if (abbrevBookName.equals("JOH")) return 43;
    if (abbrevBookName.equals("AC")) return 44;
    if (abbrevBookName.equals("RO")) return 45;
    if (abbrevBookName.equals("1CO")) return 46;
    if (abbrevBookName.equals("2CO")) return 47;
    if (abbrevBookName.equals("GAL")) return 48;
    if (abbrevBookName.equals("EPH")) return 49;
    if (abbrevBookName.equals("PHI")) return 50;
    if (abbrevBookName.equals("COL")) return 51;
    if (abbrevBookName.equals("1TH")) return 52;
    if (abbrevBookName.equals("2TH")) return 53;
    if (abbrevBookName.equals("1TI")) return 54;
    if (abbrevBookName.equals("2TI")) return 55;
    if (abbrevBookName.equals("TIT")) return 56;
    if (abbrevBookName.equals("PHM")) return 57;
    if (abbrevBookName.equals("HEB")) return 58;
    if (abbrevBookName.equals("JAS")) return 59;
    if (abbrevBookName.equals("1PE")) return 60;
    if (abbrevBookName.equals("2PE")) return 61;
    if (abbrevBookName.equals("1JO")) return 62;
    if (abbrevBookName.equals("2JO")) return 63;
    if (abbrevBookName.equals("3JO")) return 64;
    if (abbrevBookName.equals("JUD")) return 65;
    return 66;
  }
  
  /**
   * @author Micah
   */
  private void readChapterTextA() {
    Testing.startTest("readChapterTextA");
    
    InputStreamReader is = new InputStreamReader(openBibleFile());
    BufferedReader bsr = new BufferedReader(is);
    StringBuilder sb = new StringBuilder();
    try {
      while (loop(sb.toString(), endFileMarker)) {
        Testing.startTest("rct/bsr.read");
        sb.append((char) bsr.read());
        // Testing.endTest("rct/bsr.read");
      }
      bsr.close();
      is.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    chapterText = sb.toString();
    
    Testing.endTest("readChapterTextA");
  }
  
  /**
   * @author Kris
   */
  private void readChapterTextB() {
    Testing.startTest("readChapterTextB");
    
    InputStreamReader is = new InputStreamReader(openBibleFile());
    BufferedReader bsr = new BufferedReader(is);
    StringBuilder sb = new StringBuilder();
    String aux = "";
    try {
      // while (loop(sb.toString(), endFileMarker)) {
      // Testing.startTest("rct/bsr.read");
      // sb.append((char) bsr.read());
      // Testing.endTest("rct/bsr.read");
      // }
      while ((aux = bsr.readLine()) != null) {
        sb.append(aux);
      }
      bsr.close();
      is.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    chapterText = sb.toString();
    
    Testing.endTest("readChapterTextB");
  }
  
  private boolean loop(String from, String endFileMarker) {
    Testing.startTest("loop");
    boolean a = !from.contains(endFileMarker);
    
    // Testing.endTest("loop");
    return a;
  }
  
  /**
   * @author Kris
   */
  private String getVerseB()
      throws ScriptureNotFoundError {
    Testing.startTest("getVerseB");
    
    final String startVerseMark = getStartVerseMarkerString();
    final String endVerseMark = getEndVerseMarkerString();
    final StringBuilder verseTextBuilder = new StringBuilder();
    String verseText = "";
    int verseCharMarker = 0;
    // Read to the start of the verse.
    // edit Kris
    int LOCATION_FAILED = -1;
    int startLocation = chapterText.indexOf(startVerseMark);
    if (startLocation == LOCATION_FAILED) {
      VerseNotFoundError vnf = new VerseNotFoundError();
      vnf.verse = verse;
      throw vnf;
    }
    startLocation += startVerseMark.length();
    verseCharMarker = startLocation;
    
    /*
     * Otherwise, clear the string, and now read from where you are to the
     * beginning of the verse.
     */
    verseCharMarker++;
    verseText = "";
    verseTextBuilder.replace(0, verseTextBuilder.length(), "");
    // edit Kris
    final String substring = chapterText.substring(startLocation);
    int endLocation = substring.indexOf(endVerseMark);
    if (endLocation == LOCATION_FAILED) {
      VerseNotFoundError vnf = new VerseNotFoundError();
      vnf.verse = verse;
      throw vnf;
    }
    endLocation -= 10;
    verseCharMarker = endLocation;
    
    verseText = substring.substring(0, endLocation);
    
    //
    Testing.startTest("getVerseB/replaceText");
    final String rep1 = "<sup>";
    final String rep2 = "</sup>";
    final String with1 = "<sup><small>";
    final String with2 = "</small></sup>";
//    verseText = verseText.replaceAll(endVerseMark, "");
    verseText = verseText.replaceAll(rep1, with1);
    verseText = verseText.replaceAll(rep2, with2);
    Testing.endTest("getVerseB/replaceText");
    Log.i("Verse Text", verseText.toString());
    
    Testing.endTest("getVerseB");
    return verseText;
  }
  
  /**
   * @author Micah
   */
  private String getVerseA()
      throws ScriptureNotFoundError {
    Testing.startTest("getVerseA");
    
    String startVerseMark = getStartVerseMarkerString();
    String endVerseMark = getEndVerseMarkerString();
    StringBuilder verseTextBuilder = new StringBuilder();
    String verseText = "";
    int verseCharMarker = 0;
    // Read to the start of the verse.
    for (int i = 0; i < chapterText.length();) {
      while (!verseText.contains(startVerseMark)) {
        verseTextBuilder.append(chapterText.charAt(i));
        verseCharMarker = i;
        i++;
        verseText = verseTextBuilder.toString();
      }
      i++;
    }
    // If you tried to do that, and got to the end of the chapter, throw a
    // VerseNotFound error.
    if (verseText.contains(endFileMarker)) {
      VerseNotFoundError vnf = new VerseNotFoundError();
      vnf.verse = verse;
      throw vnf;
    }
    // Otherwise, clear the string, and now read from where you are to the
    // beginning of the verse.
    verseCharMarker++;
    verseText = "";
    verseTextBuilder.replace(0, verseTextBuilder.length(), "");
    while (!verseText.contains(endVerseMark) && !verseText.contains(endFileMarker)) {
      verseTextBuilder.append(chapterText.charAt(verseCharMarker));
      verseCharMarker++;
      verseText = verseTextBuilder.toString();
    }
    verseText.replaceAll(endVerseMark, "");
    Log.i("Verse Text", verseText.toString());
    
    Testing.endTest("getVerseA");
    return verseText;
  }
  
  private String getStartVerseMarkerString() {
    String s = "chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(verse) + "\"></span>";
    return s;
  }
  
  private String getEndVerseMarkerString() {
    String s = "chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(verse + 1) + "\"></span>";
    return s;
  }
}