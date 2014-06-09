package com.ameron32.tap.conventionnotes.scripture;

import java.util.ArrayList;
import java.util.List;

public class Books {
  
  static List<Book> books;
  public static List<Book> getBooks() {
    initBooks();
    return books;
  }
  
  static void initBooks() {
    if (books == null) {
      books = new ArrayList<>();
      
      books.add(new Book("GENESIS", "GE"));
      books.add(new Book("EXODUS", "EX"));
      books.add(new Book("LEVITICUS", "LE"));
      books.add(new Book("NUMBERS", "NU"));
      books.add(new Book("DEUTERONOMY", "DE"));
      books.add(new Book("JOSHUA", "JOS"));
      books.add(new Book("JUDGES", "JG"));
      books.add(new Book("RUTH", "RU"));
      books.add(new Book("1 SAMUEL", "1SA"));
      books.add(new Book("2 SAMUEL", "2SA"));
      books.add(new Book("1 KINGS", "1KI"));
      books.add(new Book("2 KINGS", "2KI"));
      books.add(new Book("1 CHRONICLES", "1CH"));
      books.add(new Book("2 CHRONICLES", "2CH"));
      books.add(new Book("EZRA", "EZR"));
      books.add(new Book("NEHEMIAH", "NE"));
      books.add(new Book("ESTHER", "ES"));
      books.add(new Book("JOB", "JOB"));
      books.add(new Book("PSALMS", "PS"));
      books.add(new Book("PROVERBS", "PR"));
      books.add(new Book("ECCLESIASTES", "EC"));
      books.add(new Book("SONG OF SOLOMON", "CA"));
      books.add(new Book("ISAIAH", "ISA"));
      books.add(new Book("JEREMIAH", "JER"));
      books.add(new Book("LAMENTATIONS", "LA"));
      books.add(new Book("EZEKIEL", "EZE"));
      books.add(new Book("DANIEL", "DA"));
      books.add(new Book("HOSEA", "HOS"));
      books.add(new Book("JOEL", "JOE"));
      books.add(new Book("AMOS", "AM"));
      books.add(new Book("OBADIAH", "OB"));
      books.add(new Book("JONAH", "JON"));
      books.add(new Book("MICAH", "MIC"));
      books.add(new Book("NAHUM", "NAH"));
      books.add(new Book("HABAKKUK", "HAB"));
      books.add(new Book("ZEPHANIAH", "ZEP"));
      books.add(new Book("HAGGAI", "HAG"));
      books.add(new Book("ZECHARIAH", "ZEC"));
      books.add(new Book("MALACHI", "MAL"));
      books.add(new Book("MATTHEW", "MT"));
      books.add(new Book("MARK", "MR"));
      books.add(new Book("LUKE", "LU"));
      books.add(new Book("JOHN", "JOH"));
      books.add(new Book("ACTS", "AC"));
      books.add(new Book("ROMANS", "RO"));
      books.add(new Book("1 CORINTHIANS", "1CO"));
      books.add(new Book("2 CORINTHIANS", "2CO"));
      books.add(new Book("GALATIANS", "GAL"));
      books.add(new Book("EPHESIANS", "EPH"));
      books.add(new Book("PHILIPPIANS", "PHP"));
      books.add(new Book("COLOSSIANS", "COL"));
      books.add(new Book("1 THESSALONIANS", "1TH"));
      books.add(new Book("2 THESSALONIANS", "2TH"));
      books.add(new Book("1 TIMOTHY", "1TI"));
      books.add(new Book("2 TIMOTHY", "2TI"));
      books.add(new Book("TITUS", "TIT"));
      books.add(new Book("PHILEMON", "PHM"));
      books.add(new Book("HEBREWS", "HEB"));
      books.add(new Book("JAMES", "JAS"));
      books.add(new Book("1 PETER", "1PE"));
      books.add(new Book("2 PETER", "2PE"));
      books.add(new Book("1 JOHN", "1JO"));
      books.add(new Book("2 JOHN", "2JO"));
      books.add(new Book("3 JOHN", "3JO"));
      books.add(new Book("JUDE", "JUD"));
      books.add(new Book("REVELATION", "RE"));
    }
  }
  
  public static String getPrimaryAbbreviation(String sBook) {
    for (Book book : books) {
      if (book.getTitle().equalsIgnoreCase(sBook)) {
        return book.getPrimaryAbbreviation();
      }
    }
    return null;
  }
  
  
  
  public static class Book {
    
    public static final int PRIMARY_ABBREVIATION = 0;
    
    private String   bookTitle;
    private String[] knownAbbreviations;
    
    public Book(String bookTitle, String... knownAbbreviations) {
      initBooks();
      this.bookTitle = bookTitle;
      this.knownAbbreviations = knownAbbreviations;
    }
    
    /*
     * FORCED TO UPPERCASE
     */
    public String getBestGuess(String userInput) {
      /*
       * If the user wrote the first few letters of the book, give them the
       * book;
       */
      if (bookTitle.startsWith(userInput)) { 
        return bookTitle;
      }
      
      /*
       * If the user wrote the abbreviation, give them the book;
       */
      for (String abbr : knownAbbreviations) {
        if (abbr.startsWith(userInput)) { 
          return bookTitle; 
        }
      }
      
      return null;
    }
    
    public String getPrimaryAbbreviation() {
      return knownAbbreviations[PRIMARY_ABBREVIATION];
    }
    
    public String getTitle() {
      return bookTitle;
    }
  }
}
