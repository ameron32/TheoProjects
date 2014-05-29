package com.ameron32.conventionnotes.tools;

import android.content.Context;
import android.content.Intent;

import com.ameron32.conventionnotes.CustomApplication;
import com.ameron32.conventionnotes.program.ProgramList;


public class Exporter {
  
  public static void exportProgramNotesAsEmail(Context context) {
    
    String plainTextNotes = ProgramList.getProgram().getPlainTextNotes();
    
    
    
    // send email
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/html");
//    intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
    intent.putExtra(Intent.EXTRA_SUBJECT, ProgramList.CONVENTION_NAME);
    intent.putExtra(Intent.EXTRA_TEXT, plainTextNotes);

    context.startActivity(intent);
  }
}
