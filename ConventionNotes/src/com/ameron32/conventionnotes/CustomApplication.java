package com.ameron32.conventionnotes;

import android.app.Application;
import android.content.Context;

public class CustomApplication extends Application {
  
  public static final String VERSION = "005a";
  
  private static Context context;
  
  public void onCreate() {
    context = getApplicationContext();
  }
  
  public static Context getCustomAppContext() {
    return context;
  }
}
