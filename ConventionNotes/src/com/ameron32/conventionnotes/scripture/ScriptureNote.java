package com.ameron32.conventionnotes.scripture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;

import com.ameron32.conventionnotes.MainActivity;
import com.ameron32.conventionnotes.notes.Note;
import com.ameron32.conventionnotes.scripture.Books.Book;

public class ScriptureNote extends Note {
  
  private static final long serialVersionUID = -3401984122423046983L;
  
  public String             book;
  public int                chapter;
  public int[]              verses;
  
  public String             scriptureText;
  
  public ScriptureNote(String note) {
    super(note);
    
    Scripture s = null;
    try {
      s = extractScripture(note);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    loadScriptureText(s);
  }
  
  public ScriptureNote(Scripture scripture) {
    super(scripture);
    this.book = scripture.book;
    this.chapter = scripture.chapter;
    this.verses = scripture.verses;
    loadScriptureText(scripture);
  }
  
  private void loadScriptureText(Scripture s) {
    if (s == null) { return; }
    
    this.book = s.book;
    this.chapter = s.chapter;
    this.verses = s.verses;
    
    ScriptureFinder sf = new ScriptureFinder();
    
    if (this.type == Note.Type.Scripture) {
      try {
        scriptureText = sf.findScriptures(MainActivity.getContext(), s.book, s.chapter, s.verses);
      }
      catch (BookNotFoundError e) {
        e.printStackTrace();
      }
      catch (ChapterNotFoundError e) {
        e.printStackTrace();
      }
      catch (VerseNotFoundError e) {
        e.printStackTrace();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * FORMAT: CCC # #-#, Examples: JAS 1 1,2 REV 21 3-5 MAT 24 3-11,14,45-47
   */
  private Scripture extractScripture(String note)
      throws Exception {
    
    String book;
    int chapter;
    int[] verses;
    
    String[] bcv = note.split(" ");
    String sVerses = bcv[bcv.length - 1];
    String sChapter = bcv[bcv.length - 2];
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bcv.length - 2; i++) {
      sb.append(bcv[i]);
      sb.append(" ");
    }
    book = sb.toString().trim().substring(1).replace(".", "")
        .toUpperCase(Locale.getDefault());
    
    Log.i(this.getClass().getSimpleName(), "book was trimmed as: " + book);
    
    book = determineBook(book);
    Log.i(this.getClass().getSimpleName(), "book was found as: " + book);
    
    Scripture scripture = new Scripture(book, Integer.valueOf(sChapter), convert(extractVerseBlocks(sVerses)));
    Log.i("Scripture", scripture.toString());
    return scripture;
    
  }
  
  private List<Integer> extractVerseBlocks(final String codedVerses) {
    List<Integer> poolVerses = new ArrayList<>();
    if (codedVerses.contains(",")) { // multiblocks
      String[] verseBlocks = codedVerses.split(",");
      
      for (String block : verseBlocks) {
        poolVerses.addAll(extractVerses(block));
      }
    }
    else {
      poolVerses.addAll(extractVerses(codedVerses));
    }
    return poolVerses;
  }
  
  private List<Integer> extractVerses(final String codedVerses) {
    String sStartVerse;
    String sEndVerse;
    
    if (codedVerses.contains("-")) { // verses
      String[] verses = codedVerses.split("-");
      sStartVerse = verses[0];
      sEndVerse = verses[1];
    }
    else { // 1verse
      String verse = codedVerses;
      sStartVerse = sEndVerse = verse;
    }
    
    int startVerse = Integer.valueOf(sStartVerse);
    int endVerse = Integer.valueOf(sEndVerse);
    List<Integer> poolVerses = new ArrayList<>();
    for (int i = startVerse; i < endVerse + 1; i++) {
      poolVerses.add(i);
    }
    
    return poolVerses;
  }
  
  private int[] convert(final List<Integer> from) {
    
    final int[] c = new int[from.size()];
    for (int i = 0; i < from.size(); i++) {
      c[i] = from.get(i);
    }
    
    final Set<Integer> filter = new TreeSet<>();
    for (int i = 0; i < c.length; i++) {
      filter.add(c[i]);
    }
    
    Iterator<Integer> it = filter.iterator();
    final int[] d = new int[filter.size()];
    int i = 0;
    while (it.hasNext()) {
      d[i] = it.next();
      i++;
    }
    
    return d;
  }
  
  @Override
  public String toString() {
    return "ScriptureNote [book=" + book + ", chapter=" + chapter + ", verses=" + Arrays.toString(verses) + " | "
        + super.toString() + "]";
  }
  
  @Override
  public String getExportText() {
    return super.getExportText();
  }
  
  /**
   * FORCED TO UPPERCASE
   */
  private String determineBook(String userInput) {
    List<Book> books = Books.getBooks();
    for (Book book : books) {
      String bestGuess = book.getBestGuess(userInput);
      if (bestGuess != null) { return bestGuess; }
    }
    return null;
  }
  
}
