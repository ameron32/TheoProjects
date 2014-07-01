package com.ameron32.tap.conventionnotes.tools;

import java.util.HashMap;

public class Testing {
  
  private static final boolean                DEBUG = false;

  private static final HashMap<String, Timer> times = new HashMap<>();

  public static void startTest(String methodName) {
    Timer timer = times.get(methodName);
    long start = System.nanoTime();
    if (timer == null) {
      timer = new Timer(start);
    }
    times.put(methodName, new Timer(start));
  }
  
  public static void endTest(String methodName) {
    Timer timer = times.get(methodName);
    if (timer == null) {
      Testing.Log.e("TESTS", "did you start the test?");
      return;
    }
    
    long stop = System.nanoTime();
    timer.stop(stop);
    Testing.Log.i("TESTS:" + methodName, "nanos: " + timer.elapsed);
  }
  
  private static class Timer {

    long start;
    long stop;
    long elapsed;
    
    public Timer(long start) {
      this.start = start;
    }
    
    public void stop(long stop) {
      this.stop = stop;
      elapsed = this.stop - start;
    }
  }
  
  public static void report(String msg) {
    Testing.Log.i(Testing.class.getSimpleName(), msg);
  }
  
  public static class Log {
    
    public static void e(String fromClass, String msg) {
      if (DEBUG) Log.e(Testing.class.getSimpleName() + "|" + fromClass, msg);
    }

    public static void i(String fromClass, String msg) {
      if (DEBUG) Log.i(Testing.class.getSimpleName() + "|" + fromClass, msg);
    }
    
    public static void d(String fromClass, String msg) {
      if (DEBUG) Log.d(Testing.class.getSimpleName() + "|" + fromClass, msg);
    }
    
    public static void v(String fromClass, String msg) {
      if (DEBUG) Log.v(Testing.class.getSimpleName() + "|" + fromClass, msg);
    }
  }
  
  public static class Exception {
    
    public static void printStackTrace(java.lang.Exception e) {
      e.printStackTrace();
    }
  }
  
  private static boolean justKidding = true;

  public static void crash() {
    if (!justKidding) { throw new RuntimeException("This is a crash!"); }
  }
}
