package com.ameron32.conventionnotes.scripture;

import android.text.Spanned;

/**
 * It turns out I can't interface a static method. That's fine. Basically,
 * static or not this is what it needs to be able to do.
 * 
 * Look at the method for a more detailed description of the need.
 * 
 * See this link:
 * http://www.grokkingandroid.com/android-quick-tip-formatting-text-with-html-fromhtml/
 * ... for more detailed explanation of what we hope to do here.
 * 
 * @author klemeilleur
 * 
 */
public interface ScriptureParser {
  
  /**
   * Here's the meat and potatoes. This method should find the scripture within
   * the appropriate XHTML file and pull a string of the scripture including all
   * HTML markup text. This String result is NOT ready for the user.
   * 
   * @param book
   *          - While an enum might be better... I don't have one yet. If you
   *          wanna do an enum instead, that would be fine.
   * @param chapter
   *          - numerical value of the chapter needed. throws exception if
   *          chapter isn't valid in that book.
   * @param verse
   *          - numerical value of a single verse. throws exception if verse
   *          isn't valid that chapter/book.
   * @return Unformatted String including markup text (HTML tags) for
   *         processing.
   */
  public String getScripture(String book, int chapter, int verse);
  
  /**
   * Likely, this method calls a for loop on getScripture() of each verse in the
   * array. This method then combines all verses into one long String (including
   * HTML tags/markup text) and returns it.
   * 
   * @param book
   *          - While an enum might be better... I don't have one yet. If you
   *          wanna do an enum instead, that would be fine.
   * @param chapter
   *          - numerical value of the chapter needed. throws exception if
   *          chapter isn't valid in that book.
   * @param verses
   *          - array of numerical values of a series of verses. throws
   *          exception if verse isn't valid that chapter/book.
   * @return Unformatted String including markup text (HTML tags) for
   *         processing.
   */
  public String getScriptures(String book, int chapter, int[] verses);
  
  /**
   * If you don't understand how to do this, leave the method empty. This method
   * should take the String containing markup text/HTML tags from above and
   * convert the data into a Spanned using the Html.fromHtml(String) static
   * method provided in the android.text.Html class.
   * 
   * @param spannedString - Dirty String from above including all HTML tags and markup text.
   * @return Formatted Spanned text ready to display to the user.
   */
  public Spanned format(String spannedString);
}
