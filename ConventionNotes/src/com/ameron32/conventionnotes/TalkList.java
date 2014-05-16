package com.ameron32.conventionnotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TalkList {
  
  public static List<Talk> talks = new ArrayList<Talk>();
  
  /**
   * Create all Convention Talks (static)
   */
  static {
    talks.add(new Talk("Chairman's Address: ", "Meet Some Who Are", "Seeking First God's Kingdom!"));
    talks.add(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "Jehovah's Throne"));
    talks.add(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "Jesus' Throne"));
    talks.add(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "The Thrones of the 144,000"));
    talks.add(new Talk("A Century of Kingdom Rule", "Contrasted With", "a Century of Satan's Rule"));
    talks.add(new Talk("Keynote Address: Highly Esteem", "Present Kingdom Blessings!"));
    talks.add(new Talk("How Babylon the Great Has", "\"Shut Up the Kingdom\""));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Recreation"));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Eating and Drinking"));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Concerns About Health"));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Courtship and Weddings"));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Family Ties"));
    talks.add(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Material Pursuits"));
    talks.add(new Talk("Drama: \"Do Not Give", "the Devil an Opportunity\""));
    talks.add(new Talk("\"Sacred Secrets of the Kingdom\"", "Progressively Revealed"));
    talks.add(new Talk("Teach Your Children", "to Love God's Kingdom!"));
    talks.add(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Sexually Immoral\" People"));
    talks.add(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Idolaters\""));
    talks.add(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Greedy People\""));
    talks.add(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Revilers\""));
    talks.add(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those \"Who Are Poor\""));
    talks.add(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those Who \"Become as Young Children\""));
    talks.add(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those \"Persecuted for", "Righteousness' Sake\""));
    talks.add(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those Who Help Christ's Brothers"));
    talks.add(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "From House to House"));
    talks.add(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "Informally"));
    talks.add(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "Publicly"));
    talks.add(new Talk("Baptism: Jehovah \"Will Help You\"", "Inherit the Kingdom"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Move Where the Need Is Greater"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Learn a New Language"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Reach Out to Be a Ministerial Servant"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Reach Out to Be an Elder"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Enroll as a Pioneer"));
    talks.add(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Attend the School", "for Kingdom Evangelizers"));
    talks.add(new Talk("Enter the Kingdom", "Maimed, Lame, or One-Eyed"));
    talks.add(new Talk("As Kingdom Subjects,", "Remain \"No Part of This World\"!"));
    talks.add(new Talk("Sound Drama:", "\"Jehovah Is the Only True God\""));
    talks.add(new Talk("Jehovah's Organization-", "100 Years of Seeking First", "God's Established Kingdom"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "A World Without Satan"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "One Language"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Perfect Health"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Complete Harmony With the Animals"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Paradise Earth"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "New Scrolls"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Association With", "Multitudes of Resurrected Ones"));
    talks.add(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Ability to Praise Jehovah Perfectly"));
    talks.add(new Talk("Public Bible Discourse:", "Earth's New Ruler-Who Really Qualifies?"));
    talks.add(new Talk("Summary of The Watchtower"));
    talks.add(new Talk("Stay on \"the Highway of Holiness\"", "Into God's Kingdom!"));
    talks.add(new Talk("Drama: \"Not One Word Has Failed\""));
    talks.add(new Talk("\"Never Be Anxious\"-", "Keep Seeking First God's Kingdom"));
  }
  
  /**
   * Add random notes
   */
  static {
    for (Talk talk : talks) {
      Random r = new Random();
      int numberOfNotesToAdd = r.nextInt(7);
      for (int i = 0; i < numberOfNotesToAdd; i++) {
        talk.addNote(Note.createNote("Note"+i+":" + talk.getTitle().substring(0,7)));
      }
    }
  }
  
  public static Talk getTalk(int number) {
    return talks.get(number);
  }
  
  public static int getTalkCount() {
    return talks.size();
  }
}
