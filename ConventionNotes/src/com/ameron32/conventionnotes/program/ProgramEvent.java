package com.ameron32.conventionnotes.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProgramEvent {
  
  private static SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("M/d/yy h:mm a", Locale.getDefault());
  private static SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm", Locale.getDefault());
  
  private static long             JULY_4_12AM     = 1404450000000L;
  private static long             ONE_DAY         = 86400000L;
  private static long             CONVENTION_DATE = JULY_4_12AM;
  
  // TODO: remove DEBUG time offset when functional
  private static long DEBUG_TIME_OFFSET = ONE_DAY * 44;

  private String      id;

  private long        startTime;
  private long        stopTime;
  
  /**
   * 
   * @param startDayAndTime
   *          - must have "Day,Ti:me"
   */
  public void setProgram(String startDayAndTime, String endDayAndTime) {
    
    startTime = processDayAndTime(startDayAndTime);
    stopTime = processDayAndTime(endDayAndTime);
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
    
    long time = CONVENTION_DATE;
    
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
    return "ProgramEvent [id=" + id + ", startTime=" + FULL_FORMAT.format(startTime) + ", stopTime=" + FULL_FORMAT.format(stopTime) + "]";
  }
  
  
  
  
  public long getStartTime() {
    return startTime;
  }
  
  public String getStartTimeFormatted() {
    return FULL_FORMAT.format(getStartTime());
  }
  
  public long getStopTime() {
    return stopTime;
  }
  
  public String getStopTimeFormatted() {
    return FULL_FORMAT.format(getStopTime());
  }

  public static long timeFromNow(long currentTimeMillis, Talk talk) {
    return talk.getStopTime() - currentTimeMillis;
  }
  
  private static final int ONE_DAY_IN_SECONDS = 60*60*24;
  private static final int ONE_HOUR_IN_SECONDS = 60*60;
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
    
    return ((days > 0) ? days + "d " : "") +
        ((hours > 0) ? hours + ":" : "0:") + 
        ((minutes > 0) ? minutes + " " : "0 ") + 
        seconds + "s";
  }
}
