package com.ameron32.conventionnotes.tools;

import java.util.HashMap;

import android.util.Log;


public class Testing {
  static final HashMap<String, Timer> times = new HashMap<>();
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
      Log.e("TESTS", "did you start the test?");
      return;
    }
    
    long stop = System.nanoTime();
    timer.stop(stop);
    Log.i("TESTS:" + methodName, "nanos: " + timer.elapsed);
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
    Log.i(Testing.class.getSimpleName(), msg);
  }
}
