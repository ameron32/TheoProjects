package com.ameron32.tap.conventionnotes.tools;

import android.content.Context;
import android.content.Intent;

import com.ameron32.tap.conventionnotes.notes.Program;
import com.ameron32.tap.conventionnotes.program.ProgramList;

public class Exporter {
  
  public static void exportProgramNotesAsEmail(Context context, Program program) {
    
    String plainTextNotes = ProgramList.getProgram().getPlainTextNotes();
    
    // send email
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/html");
    // intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
    intent.putExtra(Intent.EXTRA_SUBJECT, program.getDate());
    intent.putExtra(Intent.EXTRA_TEXT, plainTextNotes);

    context.startActivity(intent);
  }
}
