package com.ameron32.tap.conventionnotes.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.util.Log;

import com.ameron32.tap.conventionnotes.CustomApplication;
import com.ameron32.tap.conventionnotes.notes.Program;


public class Serializer {

  private static final String FILE_EXTENSION = ".program";
  private static final String PROGRAM_NAME = "myProgram";
  public static final String PROGRAM_FILENAME = PROGRAM_NAME + FILE_EXTENSION;
  
  public static void storeProgram(Program program) throws IOException {
    final Context c = CustomApplication.getCustomAppContext();
    FileOutputStream fos = c.openFileOutput(PROGRAM_FILENAME, Context.MODE_PRIVATE);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(program);
    oos.close();
    Log.i(Serializer.class.getSimpleName(), "save successful");
  }
  
  public static Program loadProgram() throws FileNotFoundException, StreamCorruptedException, IOException, ClassNotFoundException, ClassCastException {
    final Context c = CustomApplication.getCustomAppContext();
    FileInputStream fis = c.openFileInput(PROGRAM_FILENAME);
    ObjectInputStream ois = new ObjectInputStream(fis);
    Object readObject = ois.readObject();
    Program program = (Program) readObject;
    Log.i(Serializer.class.getSimpleName(), "load successful");
    return program;
  }
}
