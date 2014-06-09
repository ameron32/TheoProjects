package com.ameron32.tap.conventionnotes.program;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.ameron32.tap.conventionnotes.notes.Program;

public class ProgramEvent implements Serializable {
  
  private static final long       serialVersionUID     = 3937235085748036431L;
  
  public static final String      EXPORT_EVENT_HEADER  = "\n*********************";
  public static final String      EXPORT_EVENT_DIVIDER = "\n***";
  
  private static SimpleDateFormat FULL_FORMAT          = new SimpleDateFormat("M/d/yy h:mm a", Locale.getDefault());
  private static SimpleDateFormat FORMAT               = new SimpleDateFormat("H:mm", Locale.getDefault());
  private static SimpleDateFormat PROGRAM_FORMAT       = new SimpleDateFormat("h:mm a", Locale.getDefault());
  
  private static long             ONE_DAY              = 86400000L;
  
  private String                  id;
  
  private long                    startTime;
  private long                    stopTime;
  
  private Program                 parent;
  
  /**
   * 
   * @param startDayAndTime
   *          - must have "Day,Ti:me"
   */
  public void setProgram(String startDayAndTime, String endDayAndTime) {
    
    startTime = processDayAndTime(startDayAndTime);
    stopTime = processDayAndTime(endDayAndTime);
  }
  
  public void setParent(Program parent) {
    this.parent = parent;
  }
  
  private long processDayAndTime(String dayAndTime) {
    
    String[] ab = dayAndTime.split(",");
    String sDay = ab[0];
    String sTime = ab[1];
    long time = getTime(sTime);
    
    time += addDays(sDay);
    
    return time;
  }
  
  private long getTime(String time) {
    
    long lTime = 0;
    try {
      lTime = FORMAT.parse(time).getTime();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return lTime - 21600000L;
  }
  
  private long addDays(String day) {
    
    long time = parent.getDate();
    
    switch (day) {
    case "Sunday":
      time += ONE_DAY;
      time += ONE_DAY;
      break;
    case "Saturday":
      time += ONE_DAY;
      break;
    case "Friday":
    default:
      // no days added
    }
    
    return time;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
  @Override
  public String toString() {
    return "ProgramEvent [id=" + id + ", startTime=" + FULL_FORMAT.format(startTime) + ", stopTime="
        + FULL_FORMAT.format(stopTime) + "]";
  }
  
  public String getExportText() {
    return "Event: " + getId();
  }
  
  // CODE BELOW
  
  // generates the formating for the method below
  private static SimpleDateFormat GROUP_DATE_FORMAT = new SimpleDateFormat("EEEE',' MMMM d", Locale.getDefault());
  
  // use this to access the grouping date
  // should display as:
  // Friday, July 4
  // tell me if it doesn't
  public String getGroupDate() {
    return GROUP_DATE_FORMAT.format(getStartTime());
  }
  
  public String getProgramTime() {
    return PROGRAM_FORMAT.format(getStartTime());
  }
  
  public long getStartTime() {
    return startTime;
  }
  
  public String getStartTimeFormatted() {
    return PROGRAM_FORMAT.format(getStartTime());
  }
  
  public long getStopTime() {
    return stopTime;
  }
  
  public String getStopTimeFormatted() {
    return PROGRAM_FORMAT.format(getStopTime());
  }
  
  public static long timeFromNow(long currentTimeMillis, Talk talk) {
    return talk.getStopTime() - currentTimeMillis;
  }
  
  private static final int ONE_DAY_IN_SECONDS    = 60 * 60 * 24;
  private static final int ONE_HOUR_IN_SECONDS   = 60 * 60;
  private static final int ONE_MINUTE_IN_SECONDS = 60;
  
  public static String convertCountdown(long secondsRemaining) {
    long days = 0;
    long hours = 0;
    long minutes = 0;
    long seconds = 0;
    
    if (secondsRemaining > ONE_DAY_IN_SECONDS) {
      days = secondsRemaining / ONE_DAY_IN_SECONDS;
      secondsRemaining = secondsRemaining % ONE_DAY_IN_SECONDS;
    }
    if (secondsRemaining > ONE_HOUR_IN_SECONDS) {
      hours = secondsRemaining / ONE_HOUR_IN_SECONDS;
      secondsRemaining = secondsRemaining % ONE_HOUR_IN_SECONDS;
    }
    if (secondsRemaining > ONE_MINUTE_IN_SECONDS) {
      minutes = secondsRemaining / ONE_MINUTE_IN_SECONDS;
      secondsRemaining = secondsRemaining % ONE_MINUTE_IN_SECONDS;
    }
    seconds = secondsRemaining;
    
    return ((days > 0) ? days + "d " : "") + ((hours > 0) ? hours + ":" : "0:")
        + ((minutes > 0) ? minutes + " " : "0 ") + seconds + "s";
  }
}
