package com.ameron32.conventionnotes.notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ameron32.conventionnotes.program.ProgramEvent;
import com.ameron32.conventionnotes.program.Talk;

public class Program implements Serializable {
  
  private static final long  serialVersionUID = 1644819538761112826L;
  
  private List<ProgramEvent> events           = new ArrayList<>();
  private List<Talk>         talks            = new ArrayList<>();
  
  public List<Talk> getTalks() {
    return talks;
  }
  
  public void setTalks(List<Talk> talks) {
    this.talks = talks;
  }
  
  public List<ProgramEvent> getEvents() {
    return events;
  }
  
  public void setEvents(List<ProgramEvent> events) {
    this.events = events;
  }
}