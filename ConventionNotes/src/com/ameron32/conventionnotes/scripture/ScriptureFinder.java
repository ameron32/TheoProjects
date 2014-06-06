package com.ameron32.conventionnotes.scripture;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.ameron32.conventionnotes.tools.Testing;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

public class ScriptureFinder {
  
  Context                       c;
  String                        fileName;
  String                        bookName;
  String                        chapterText;
  File                          chapterFile;
  int                           chapter;
  ArrayList<String>             verseArray;
  String[]                      allowedTags;
  private static final String[] defaultAllowedTags = { "<span ", "</span>", "<sup>", "<strong>", "<p ", "</sup>",
      "</strong>", "</p>"                         };
  
  public void setAllowedTags(String[] tags) {
    allowedTags = tags;
  }
  
  private static final String endFileMarker = "<div class=\"groupFootnote\">";
  
  public String findScriptures(Context c, String bookName, int chapter, int[] verses)
      throws ScriptureNotFoundError {
    
    // String result = new ScriptureFinderKris().findScriptures(c, bookName,
    // chapter, verses);
    // if (result != null) {
    // return result;
    // }
    
    allowedTags = defaultAllowedTags;
    
    Testing.startTest("wholeVerseReadA");
    
    // assign local values
    this.c = c;
    this.chapter = chapter;
    this.bookName = bookName;
    fileName = getFileName();
    
    // create StringBuilder for returning final compilation of all verses
    StringBuilder sb = new StringBuilder();
    
    // init variable to detect whether some verses can't be returned
    boolean versesNotFound = false;
    
    try {
      // read the entire chapter text
      // this puts all lines of the chapter into the verseArray variable
      
      readChapterText();
      sb.append("<strong>" + bookName + " " + String.valueOf(chapter) + "</strong>: ");
      
      for (int i = 0; i < verses.length; i++) {
        
        if ((i > 0) && (verses[i] != verses[i - 1] + 1)) {
          sb.append(insertGap());
        }
        
        // go through each verse, locate it, and append it to the StringBuilder
        sb.append(getVerse(verses[i]));
        //
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
    
    Testing.endTest("wholeVerseReadA");
    
    return sb.toString();
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
    if (bookName.equals("HABAKKUK")) return "HAB";
    if (bookName.equals("ZEPHANIAH")) return "ZEP";
    if (bookName.equals("HAGGAI")) return "HAG";
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
    if (bookName.equals("PHILIPPIANS")) return "PHP";
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
  
  private void readChapterText() {
    
    InputStreamReader is = new InputStreamReader(openBibleFile());
    BufferedReader bsr = new BufferedReader(is);
    verseArray = new ArrayList<String>();
    String currentVerseInLoop;
    try {
      while ((currentVerseInLoop = bsr.readLine()) != null)
        verseArray.add(currentVerseInLoop);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private String getVerse(int v)
      throws ScriptureNotFoundError {
    
    ArrayList<Integer> included = new ArrayList<Integer>();
    String start = getStartVerseMarkerString(v);
    String end = getEndVerseMarkerString(v);
    
    for (int i = 0; i < verseArray.size(); i++) {
      if (verseArray.get(i).contains(start)) {
        if (verseArray.get(i).contains(end)) {
          included.add(i);
          i++;
        }
        else while (!verseArray.get(i).contains(end) && !verseArray.get(i).contains(endFileMarker)) {
          included.add(i);
          i++;
        }
        i = verseArray.size();
      }
    }// end for
    
    StringBuilder sb = new StringBuilder();
    for (Integer i : included) {
      if (included.indexOf(i) > 0) {
        sb.append(" " + verseArray.get(i));
      }
      else sb.append(verseArray.get(i));
      
    }
    return cleanupVerse(sb.toString(), v);
  }
  
  private String cleanupVerse(String rawVerse, int v) {
    
    String rep1 = "<sup>";
    String rep2 = "</sup>";
    String with1 = "<sup><small>";
    String with2 = "</small></sup>";
    rawVerse = rawVerse.replaceAll(rep1, with1);
    rawVerse = rawVerse.replaceAll(rep2, with2);
    
    int start = rawVerse.indexOf(getStartVerseMarkerString(v));
    if (start > -1) {
      String rep = rawVerse.substring(0, start);
      rawVerse = rawVerse.replace(rep, "");
    }
    
    int end = rawVerse.indexOf(getEndVerseMarkerString(v));
    if (end > -1) {
      String rep = rawVerse.substring(end);
      rawVerse = rawVerse.replace(rep, "");
    }
    
    ArrayList<String> verseParts = new ArrayList<String>();
    String currentChar = "";
    String nextChar = "";
    StringBuilder piece = new StringBuilder();
    
    for (int i = 0; i < rawVerse.length(); i++) {
      currentChar = String.valueOf(rawVerse.charAt(i));
      if (i < rawVerse.length() - 2) nextChar = String.valueOf(rawVerse.charAt(i + 1));
      
      piece.append(currentChar);
      
      if (currentChar.equals(">") || (nextChar.equals("<"))) {
        verseParts.add(piece.toString());
        piece.delete(0, piece.length());
      }
      else
        if (i == rawVerse.length() - 1) {
          verseParts.add(piece.toString());
        }
    }
    
    boolean[] remove = new boolean[verseParts.size()];
    
    for (String part : verseParts) {
      if (part.contains("<") || part.contains(">")) {
        for (String aTag : allowedTags) {
          if (part.contains(aTag)) {
            remove[verseParts.indexOf(part)] = false;
            break;
          }
          remove[verseParts.indexOf(part)] = true;
        }
      }
      else {
        remove[verseParts.indexOf(part)] = false;
      }
    }
    
    for (int i = remove.length - 1; i >= 0; i--) {
      if (remove[i]) {
        verseParts.remove(i);
      }
    }
    
    StringBuilder sb = new StringBuilder();
    for (String finalPart : verseParts) {
      sb.append(finalPart);
    }
    
    return sb.toString();
    
  }
  
  private String cleanupVerseC(String rawVerse, int v) {
    
    boolean inATag = true;
    String prevChar = "";
    String currentChar = "";
    boolean[] removeChar = new boolean[rawVerse.length()];
    
    for (int i = 0; i < rawVerse.length(); i++) {
      
      if (i > 0) {
        prevChar = rawVerse.substring(i - 1, i);
      }
      currentChar = rawVerse.substring(i, i + 1);
      
      if (currentChar.equals("<")) {
        inATag = true;
      }
      else
        if (prevChar.equals(">") && (!currentChar.equals("<"))) {
          inATag = false;
        }
      
      if (inATag) {
        removeChar[i] = true;
      }
      else removeChar[i] = false;
      
    }
    
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < removeChar.length; i++) {
      if (!removeChar[i]) {
        sb.append(rawVerse.charAt(i));
      }
    }
    
    return sb.toString();
  }
  
  private String cleanupVerseB(String rawVerse, int v) {
    
    int frontStartIndex = rawVerse.indexOf("<p id");
    int frontEndIndex = rawVerse.indexOf("verse" + String.valueOf(v) + "\"></span>");
    
    char[] frontCharsToRemove = new char[frontEndIndex - frontStartIndex];
    rawVerse.getChars(frontStartIndex, frontEndIndex, frontCharsToRemove, 0);
    String frontStringToRemove = String.valueOf(frontCharsToRemove);
    
    rawVerse = rawVerse.replace(frontStringToRemove, "");
    rawVerse = rawVerse.replace("verse" + String.valueOf(v) + "\"></span>", "");
    
    String rep1 = "<sup>";
    String rep2 = "</sup>";
    String with1 = "<sup><small>";
    String with2 = "</small></sup>";
    rawVerse = rawVerse.replaceAll(rep1, with1);
    rawVerse = rawVerse.replaceAll(rep2, with2);
    
    if (rawVerse.contains(getEndVerseMarkerString(v))) {
      int endStartIndex = rawVerse.indexOf(getEndVerseMarkerString(v));
      int endEndIndex = rawVerse.length();
      char[] endCharsToRemove = new char[endEndIndex - endStartIndex];
      rawVerse.getChars(endStartIndex, endEndIndex, endCharsToRemove, 0);
      String endStringToRemove = String.valueOf(endCharsToRemove);
      rawVerse = rawVerse.replace(endStringToRemove, "");
      // verseText = verseText.replace("<span id=", "");
    }
    
    // Remove footnote tags.
    while (rawVerse.indexOf("\"footnote") != -1) {
      int startF = rawVerse.indexOf("\"footnote");
      int endF = rawVerse.substring(startF).indexOf("\">");
      rawVerse = rawVerse.replace(rawVerse.substring(startF, startF + endF + 2), "");
    }
    
    // Remove footnote href tags.
    while (rawVerse.indexOf("<a epub") != -1) {
      int startF = rawVerse.indexOf("<a epub");
      int endF = rawVerse.substring(startF).indexOf("\">");
      rawVerse = rawVerse.replace(rawVerse.substring(startF, startF + endF + 2), "");
    }
    
    // Remove page tags.
    while (rawVerse.indexOf("<span id=\"page") != -1) {
      int startP = rawVerse.indexOf("<span id=\"page");
      int endP = rawVerse.substring(startP).indexOf("\">");
      rawVerse = rawVerse.replace(rawVerse.substring(startP, startP + endP + 2), "");
    }
    rawVerse = rawVerse.replace("<span id=", "");
    return rawVerse;
  }
  
  private String insertGap() {
    return "<p>...</p>";
  }
  
  private String getStartVerseMarkerString(int v) {
    String s = "<span id=\"chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(v) + "\"></span>";
    return s;
  }
  
  private String getEndVerseMarkerString(int v) {
    
    String s = "<span id=\"chapter" + String.valueOf(chapter) + "_verse" + String.valueOf(v + 1) + "\"></span>";
    return s;
    
  }
  
  // Start Kris's methods:
  /*
   * private void readChapterTextA() { Testing.startTest("readChapterTextA");
   * 
   * InputStreamReader is = new InputStreamReader(openBibleFile());
   * BufferedReader bsr = new BufferedReader(is); StringBuilder sb = new
   * StringBuilder(); try { while (loop(sb.toString(), endFileMarker)) {
   * Testing.startTest("rct/bsr.read"); sb.append((char) bsr.read()); //
   * Testing.endTest("rct/bsr.read"); } bsr.close(); is.close(); } catch
   * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
   * chapterText = sb.toString();
   * 
   * Testing.endTest("readChapterTextA"); }
   * 
   * private void readChapterTextB() { Testing.startTest("readChapterTextB");
   * 
   * InputStreamReader is = new InputStreamReader(openBibleFile());
   * BufferedReader bsr = new BufferedReader(is); StringBuilder sb = new
   * StringBuilder(); String aux = ""; try { // while (loop(sb.toString(),
   * endFileMarker)) { // Testing.startTest("rct/bsr.read"); // sb.append((char)
   * bsr.read()); // Testing.endTest("rct/bsr.read"); // } while ((aux =
   * bsr.readLine()) != null) { sb.append(aux); } bsr.close(); is.close(); }
   * catch (IOException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } chapterText = sb.toString();
   * 
   * Testing.endTest("readChapterTextB"); }
   * 
   * private boolean loop(String from, String endFileMarker) {
   * Testing.startTest("loop"); boolean a = !from.contains(endFileMarker);
   * 
   * // Testing.endTest("loop"); return a; }
   * 
   * private String getVerseB(int v) throws ScriptureNotFoundError {
   * Testing.startTest("getVerseB");
   * 
   * final String startVerseMark = getStartVerseMarkerString(v); final String
   * endVerseMark = getEndVerseMarkerString(v); final StringBuilder
   * verseTextBuilder = new StringBuilder(); String verseText = ""; int
   * verseCharMarker = 0; // Read to the start of the verse. // edit Kris int
   * LOCATION_FAILED = -1; int startLocation =
   * chapterText.indexOf(startVerseMark); if (startLocation == LOCATION_FAILED)
   * { VerseNotFoundError vnf = new VerseNotFoundError(); vnf.verse = v; throw
   * vnf; } startLocation += startVerseMark.length(); verseCharMarker =
   * startLocation ;
   * 
   * 
   * // * Otherwise, clear the string, and now read from where you are to the //
   * * beginning of the verse.
   * 
   * verseCharMarker++; verseText = ""; verseTextBuilder.replace(0,
   * verseTextBuilder.length(), ""); // edit Kris final String substring =
   * chapterText.substring(startLocation); int endLocation =
   * substring.indexOf(endVerseMark); if (endLocation == LOCATION_FAILED) {
   * VerseNotFoundError vnf = new VerseNotFoundError(); vnf.verse = v; throw
   * vnf; } endLocation -= 10; verseCharMarker = endLocation;
   * 
   * verseText = substring.substring(0, endLocation);
   * 
   * // verseText.replaceAll(endVerseMark, ""); Log.i("Verse Text",
   * verseText.toString());
   * 
   * Testing.endTest("getVerseB"); return verseText; }
   */
  
}