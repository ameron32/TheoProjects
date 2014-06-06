package com.ameron32.conventionnotes.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.ameron32.conventionnotes.R;

public class MExpandableListAdapter implements ExpandableListAdapter {
  
  private Context                             _context;
  private List<String>                        listDataHeader;           // header
                                                                         // titles
  // child data in format of header title, child title
  private HashMap<String, List<ProgramEvent>> listDataChild;
  public static final int                     POSITION_NOT_FOUND = -777;
  private List<ProgramEvent>                  events;
  
  public MExpandableListAdapter(Context context) {
    
    events = ProgramList.getEvents();
    prepareListData();
    this._context = context;
  }
  
  // ////////////
  
  public ProgramEvent getItem(int groupPosition, int childPosition) {
    
    return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    
  }
  
  public int getPosition(ProgramEvent event) {
    int size = events.size();
    for (int i = 0; i < size; i++) {
      if (events.get(i).hashCode() == event.hashCode()) { return i; }
    }
    return POSITION_NOT_FOUND;
  }
  
  private void prepareListData() {
    listDataHeader = new ArrayList<String>();
    listDataChild = new HashMap<String, List<ProgramEvent>>();
    
    // Adding child data
    listDataHeader.add("Friday, July 4, 2014");
    listDataHeader.add("Saturday, July 5, 2014");
    listDataHeader.add("Sunday, July 6, 2014");
    
    List<ProgramEvent> fridayTalks = new ArrayList<ProgramEvent>();
    List<ProgramEvent> saturdayTalks = new ArrayList<ProgramEvent>();
    List<ProgramEvent> sundayTalks = new ArrayList<ProgramEvent>();
    
    for (ProgramEvent e : events) {
      
      if (e.getGroupDate().equals("Friday, July 4")) {
        fridayTalks.add(e);
      }
      else
        if (e.getGroupDate().equals("Saturday, July 5")) {
          saturdayTalks.add(e);
        }
        else {
          sundayTalks.add(e);
        }
      
    }
    
    listDataChild.put(listDataHeader.get(0), fridayTalks); // Header, Child data
    listDataChild.put(listDataHeader.get(1), saturdayTalks);
    listDataChild.put(listDataHeader.get(2), sundayTalks);
  }
  
  // ///////////
  
  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
  }
  
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }
  
  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
      ViewGroup parent) {
    
    final ProgramEvent childEvent = (ProgramEvent) getChild(groupPosition, childPosition);
    
    if (convertView == null) {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.m_list_item, null);
    }
    
    TextView txtEventTime = (TextView) convertView.findViewById(R.id.talk_time_text);
    TextView txtEventName = (TextView) convertView.findViewById(R.id.talk_title_text);
    
    try {
      if (childEvent instanceof Talk) {
        txtEventName.setText(((Talk) childEvent).getMultiLineTitle(false));
        txtEventTime.setText(((Talk) childEvent).getProgramTime());
      }
      else
        if (childEvent instanceof Song) {
          txtEventName.setText(((Song) childEvent).getSongText());
          txtEventTime.setText(((Song) childEvent).getProgramTime());
        }
        else
          if (childEvent instanceof Music) {
            txtEventName.setText(((Music) childEvent).getMusicText());
            txtEventTime.setText(((Music) childEvent).getProgramTime());
          }
    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }
    return convertView;
  }
  
  @Override
  public int getChildrenCount(int groupPosition) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
  }
  
  @Override
  public Object getGroup(int groupPosition) {
    return this.listDataHeader.get(groupPosition);
  }
  
  @Override
  public int getGroupCount() {
    return this.listDataHeader.size();
  }
  
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }
  
  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    String headerTitle = (String) getGroup(groupPosition);
    if (convertView == null) {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.m_list_group, null);
    }
    
    TextView lblListHeader = (TextView) convertView.findViewById(R.id.talk_group_text);
//    lblListHeader.setTypeface(null, Typeface.BOLD);
    lblListHeader.setText(headerTitle);
    
    return convertView;
  }
  
  @Override
  public boolean hasStableIds() {
    return false;
  }
  
  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
  
  @Override
  public boolean areAllItemsEnabled() {
    // TODO Auto-generated method stub
    return true;
  }
  
  @Override
  public long getCombinedChildId(long groupId, long childId) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public long getCombinedGroupId(long groupId) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public void onGroupCollapsed(int groupPosition) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void onGroupExpanded(int groupPosition) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void registerDataSetObserver(DataSetObserver observer) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void unregisterDataSetObserver(DataSetObserver observer) {
    // TODO Auto-generated method stub
    
  }
  
}