package com.ameron32.conventionnotes.program;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ameron32.conventionnotes.CustomApplication;
import com.ameron32.conventionnotes.notes.Program;
import com.ameron32.conventionnotes.tools.Serializer;

public class ProgramList {
  
  /**
   * for debug purposes, DEBUG OFFSET in ProgramEvent.class set
   */
  public static final String CONVENTION_NAME = "Keep Seeking First God's Kingdom!";
  private static final long  JULY_4_12AM     = 1404450000000L;
//  private static int         ONE_DAY         = 86400000;
  private static final long  JUNE_6_12AM     = 1402030800000L;
  // TODO: Change for Mikey
  public static final long   CONVENTION_DATE = JUNE_6_12AM;
  
  private static Program     program;
  
  public static Program getProgram() {
    return program;
  }
  
  public static List<Talk> getTalks() {
    init();
    return program.getTalks();
  }
  
  public static void setTalks(List<Talk> talks) {
    init();
    program.setTalks(talks);
  }
  
  public static List<ProgramEvent> getEvents() {
    init();
    return program.getEvents();
  }
  
  public static void setEvents(List<ProgramEvent> events) {
    init();
    program.setEvents(events);
  }
  
  public static void saveProgram()
      throws IOException {
    Serializer.storeProgram(program);
  }
  
  /**
   * Create all Convention Talks (static)
   */
  private static void init() {
    if (program != null) {
      // no need to initialize
      return;
    }
    
    final Context c = CustomApplication.getCustomAppContext();
    boolean isProgramFound = findProgram(c);
    if (isProgramFound) {
      // restore instead of build new
      return;
    }
    
    Log.i("STATIC", "begin! -- context= " + c);
    
    program = new Program(CONVENTION_NAME, CONVENTION_DATE);
    
    if (getEvents().isEmpty() && getTalks().isEmpty()) {
      
      addToProgram(new Music());
      addToProgram(new Song(40, "p"));
      addToProgram(new Talk("Chairman's Address: ", "Meet Some Who Are", "Seeking First God's Kingdom!"));
      addToProgram(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "Jehovah's Throne"));
      addToProgram(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "Jesus' Throne"));
      addToProgram(new Talk("Symposium: Appreciating the Thrones", "of Divine Rulership", "The Thrones of the 144,000"));
      addToProgram(new Song(108, "a"));
      addToProgram(new Talk("A Century of Kingdom Rule", "Contrasted With", "a Century of Satan's Rule"));
      addToProgram(new Talk("Keynote Address: Highly Esteem", "Present Kingdom Blessings!"));
      addToProgram(new Talk("How Babylon the Great Has", "\"Shut Up the Kingdom\""));
      addToProgram(new Song(75, "i"));
      
      addToProgram(new Music());
      addToProgram(new Song(27));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Recreation"));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Eating and Drinking"));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Concerns About Health"));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Courtship and Weddings"));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Family Ties"));
      addToProgram(new Talk("Symposium: What Must Be", "Kept in Second Place?", "Material Pursuits"));
      addToProgram(new Song(70, "a"));
      addToProgram(new Talk("Drama: \"Do Not Give", "the Devil an Opportunity\""));
      addToProgram(new Talk("\"Sacred Secrets of the Kingdom\"", "Progressively Revealed"));
      addToProgram(new Talk("Teach Your Children", "to Love God's Kingdom!"));
      addToProgram(new Song(88, "p"));
      
      addToProgram(new Music());
      addToProgram(new Song(12, "p"));
      addToProgram(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Sexually Immoral\" People"));
      addToProgram(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Idolaters\""));
      addToProgram(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Greedy People\""));
      addToProgram(new Talk("Symposium: Who Will Not", "Inherit God's Kingdom?", "\"Revilers\""));
      addToProgram(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those \"Who Are Poor\""));
      addToProgram(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those Who ", "\"Become as Young Children\""));
      addToProgram(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those \"Persecuted for", "Righteousness' Sake\""));
      addToProgram(new Talk("Symposium: Who Will Inherit", "God's Kingdom?", "Those Who Help Christ's Brothers"));
      addToProgram(new Song(92, "a"));
      addToProgram(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "From House to House"));
      addToProgram(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "Informally"));
      addToProgram(new Talk("Symposium: The Kingdom \"Good News", "Has to Be Preached First\"!", "Publicly"));
      addToProgram(new Talk("Baptism: Jehovah \"Will Help You\"", "Inherit the Kingdom"));
      addToProgram(new Song(60, "i"));
      
      addToProgram(new Music());
      addToProgram(new Song(95));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Move Where the Need Is Greater"));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Learn a New Language"));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Reach Out to Be a Ministerial Servant"));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Reach Out to Be an Elder"));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Enroll as a Pioneer"));
      addToProgram(new Talk("Symposium: Pursue Goals That Can", "Help You Seek First the Kingdom", "Attend the School", "for Kingdom Evangelizers"));
      addToProgram(new Song(85, "a"));
      addToProgram(new Talk("Enter the Kingdom", "Maimed, Lame, or One-Eyed"));
      addToProgram(new Talk("As Kingdom Subjects,", "Remain \"No Part of This World\"!"));
      addToProgram(new Talk("Sound Drama:", "\"Jehovah Is the Only True God\""));
      addToProgram(new Talk("Jehovah's Organization-", "100 Years of Seeking First", "God's Established Kingdom"));
      addToProgram(new Song(103, "p"));
      
      addToProgram(new Music());
      addToProgram(new Song(30, "p"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "A World Without Satan"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "One Language"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Perfect Health"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Complete Harmony With the Animals"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Paradise Earth"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "New Scrolls"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Association With", "Multitudes of Resurrected Ones"));
      addToProgram(new Talk("Symposium:", "Anticipate Future Kingdom Blessings!", "Ability to Praise Jehovah Perfectly"));
      addToProgram(new Song(134, "a"));
      addToProgram(new Talk("Public Bible Discourse:", "Earth's New Ruler-", "Who Really Qualifies?"));
      addToProgram(new Talk("Summary of The Watchtower"));
      addToProgram(new Song(99, "i"));
      
      addToProgram(new Music());
      addToProgram(new Song(16));
      addToProgram(new Talk("Stay on \"the Highway of Holiness\"", "Into God's Kingdom!"));
      addToProgram(new Talk("Drama: \"Not One Word Has Failed\""));
      addToProgram(new Song(132, "a"));
      addToProgram(new Talk("\"Never Be Anxious\"-", "Keep Seeking First God's Kingdom"));
      addToProgram(new Song(91, "p"));
      
      String[] timesA = new String[] { "Friday,9:20", "Friday,9:30", "Friday,9:30", "Friday,9:40", "Friday,9:40",
          "Friday,10:20", "Friday,10:20", "Friday,10:35", "Friday,10:35", "Friday,10:50", "Friday,10:50",
          "Friday,11:10", "Friday,11:10", "Friday,11:20", "Friday,11:20", "Friday,11:45", "Friday,11:45",
          "Friday,12:15", "Friday,12:15", "Friday,13:25", "Friday,13:25", "Friday,13:35", "Friday,13:35",
          "Friday,13:40", "Friday,13:40", "Friday,14:00", "Friday,14:00", "Friday,14:10", "Friday,14:10",
          "Friday,14:20", "Friday,14:20", "Friday,14:30", "Friday,14:30", "Friday,14:40", "Friday,14:40",
          "Friday,14:50", "Friday,14:50", "Friday,15:00", "Friday,15:00", "Friday,15:10", "Friday,15:10",
          "Friday,16:00", "Friday,16:00", "Friday,16:20", "Friday,16:20", "Friday,16:55", "Friday,16:55",
          "Saturday,9:20", "Saturday,9:20", "Saturday,9:30", "Saturday,9:30", "Saturday,9:40", "Saturday,9:40",
          "Saturday,9:50", "Saturday,9:50", "Saturday,10:00", "Saturday,10:00", "Saturday,10:10", "Saturday,10:10",
          "Saturday,10:20", "Saturday,10:20", "Saturday,10:30", "Saturday,10:30", "Saturday,10:40", "Saturday,10:40",
          "Saturday,10:50", "Saturday,10:50", "Saturday,11:00", "Saturday,11:00", "Saturday,11:10", "Saturday,11:10",
          "Saturday,11:20", "Saturday,11:20", "Saturday,11:30", "Saturday,11:30", "Saturday,11:45", "Saturday,11:45",
          "Saturday,12:15", "Saturday,12:15", "Saturday,13:35", "Saturday,13:35", "Saturday,13:45", "Saturday,13:45",
          "Saturday,13:50", "Saturday,13:50", "Saturday,14:00", "Saturday,14:00", "Saturday,14:10", "Saturday,14:10",
          "Saturday,14:20", "Saturday,14:20", "Saturday,14:30", "Saturday,14:30", "Saturday,14:40", "Saturday,14:40",
          "Saturday,14:50", "Saturday,14:50", "Saturday,15:00", "Saturday,15:00", "Saturday,15:20", "Saturday,15:20",
          "Saturday,15:45", "Saturday,15:45", "Saturday,16:15", "Saturday,16:15", "Saturday,16:55", "Saturday,16:55",
          "Sunday,9:20", "Sunday,9:20", "Sunday,9:30", "Sunday,9:30", "Sunday,9:40", "Sunday,9:40", "Sunday,9:50",
          "Sunday,9:50", "Sunday,10:05", "Sunday,10:05", "Sunday,10:15", "Sunday,10:15", "Sunday,10:25",
          "Sunday,10:25", "Sunday,10:35", "Sunday,10:35", "Sunday,10:45", "Sunday,10:45", "Sunday,10:55",
          "Sunday,10:55", "Sunday,11:10", "Sunday,11:10", "Sunday,11:20", "Sunday,11:20", "Sunday,11:50",
          "Sunday,11:50", "Sunday,12:20", "Sunday,12:20", "Sunday,13:35", "Sunday,13:35", "Sunday,13:45",
          "Sunday,13:45", "Sunday,13:50", "Sunday,13:50", "Sunday,14:10", "Sunday,14:10", "Sunday,14:40",
          "Sunday,14:40", "Sunday,14:50", "Sunday,14:50", "Sunday,15:50", "Sunday,15:50", "Sunday,15:55", };
      List<String> times = Arrays.asList(timesA);
      
      for (int i = 0; i < times.size() - 1; i = i + 2) {
        ProgramEvent event = getEvents().get(i / 2);
        event.setProgram(times.get(i), times.get(i + 1));
        event.setId(String.valueOf(i / 2));
      }
      
      for (ProgramEvent event : getEvents()) {
        System.out.println(event.toString());
      }
    }
  }
  
  private static boolean findProgram(final Context c) {
    
    try {
      program = Serializer.loadProgram();
      Log.i(ProgramList.class.getSimpleName(), "found program successfully");
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    // TODO-- fix this
    return false;
  }
  
  public static Talk getTalk(int number) {
    
    return getTalks().get(number);
  }
  
  public static int getTalkCount() {
    return getTalks().size();
  }
  
  private static boolean addToProgram(ProgramEvent event) {
    
    event.setParent(program);
    boolean added = getEvents().add(event);
    if (event instanceof Talk) {
      Talk talk = (Talk) event;
      talk.setTalkNumber(getTalks().size());
      getTalks().add(talk);
    }
    return added;
  }
}
