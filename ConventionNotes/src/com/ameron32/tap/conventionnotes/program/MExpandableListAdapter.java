package com.ameron32.tap.conventionnotes.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ameron32.tap.conventionnotes.R;

public class MExpandableListAdapter extends BaseExpandableListAdapter {
  
  private final Context                       _context;
  private List<String>                        listDataHeader;                  // header
  private ExpandableListView                  expListView;
  
  // titles
  // child data in format of header title, child title
  private HashMap<String, List<ProgramEvent>> listDataChild;
  public static final int                     POSITION_NOT_FOUND   = -777;
  private final List<ProgramEvent>            events;
  
  private int                                 currentChild         = 0;
  private int                                 currentGroup         = 0;
  
  private static final String                 heading1Color        = "#CEC9DA";
  private static final String                 heading2Color        = "#C9D0DA";
  private static final String                 heading3Color        = "#C9DAD7";
  private static final String                 backgroundColor      = "#EFEFEF";
  private static final String                 songColor            = "#C18531";
  private static final String                 group1TextColor      = "#34245D";
  private static final String                 group2TextColor      = "#24285D";
  private static final String                 group3TextColor      = "#244C5D";
  private static final String                 highlightedItemColor = "#DFDFDF";
  
  public MExpandableListAdapter(Context context) {
    
    events = ProgramList.getEvents();
    prepareListData();
    this._context = context;
  }
  
  public void setCurrentTalk(ProgramEvent e) {
    
    if (e instanceof Talk) {
      int talkNumber = ((Talk) e).getTalkNumber();
      int groupCount = getGroupCount();
      for (int i = 0; i < groupCount; i++) {
        int childCount = getChildrenCount(i);
        for (int j = 0; j < childCount; j++) {
          if ((ProgramEvent) getChild(i, j) instanceof Talk) {
            if (((Talk) getChild(i, j)).getTalkNumber() == talkNumber) {
              setActivatedChild(currentGroup, currentChild);
            }
          }
        }
      }
    }
    
  }
  
  public void setListPosition(int childPosition, int groupPosition) {
    this.currentChild = childPosition;
    this.currentGroup = groupPosition;
  }
  
  public void setView(ExpandableListView thisInstance) {
    expListView = thisInstance;
  }

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
  
  public void setActivatedChild(int groupPosition, int childPosition) {
    currentGroup = groupPosition;
    currentChild = childPosition;
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
    
    txtEventName.setBackgroundColor(Color.parseColor(backgroundColor));
    txtEventTime.setBackgroundColor(Color.parseColor(backgroundColor));

    // All Events know their name and time
    txtEventName.setText(childEvent.getTitleText());
    txtEventTime.setText(childEvent.getProgramTime());
    try {
      if (childEvent instanceof Talk) {
        txtEventTime.setTextColor(getGroupColor(groupPosition));
        
        if ((childPosition == currentChild) && (groupPosition == currentGroup)) {
          setChildHighlighted(convertView);
        }
      }
      
      if (childEvent instanceof Song) {
        txtEventTime.setTextColor(Color.parseColor(songColor));
        
      }
      
      if (childEvent instanceof Music) {
        txtEventTime.setTextColor(getGroupColor(groupPosition));
      }
    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }

    return convertView;
  }
  
  private int getGroupColor(int group) {
    if (group == 0) {
      return Color.parseColor(group1TextColor);
    }
    else
      if (group == 1) {
        return Color.parseColor(group2TextColor);
      }
      else return Color.parseColor(group3TextColor);
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
    lblListHeader.setText(headerTitle);
    
    if (groupPosition == 0) {
      convertView.setBackgroundColor(Color.parseColor(heading1Color));
      lblListHeader.setTextColor(Color.parseColor(group1TextColor));
      expListView.setGroupIndicator(_context.getResources().getDrawable(R.drawable.list_item_selector1));
    }
    else
      if (groupPosition == 1) {
        convertView.setBackgroundColor(Color.parseColor(heading2Color));
        lblListHeader.setTextColor(Color.parseColor(group2TextColor));

      }
      else {
        convertView.setBackgroundColor(Color.parseColor(heading3Color));
        lblListHeader.setTextColor(Color.parseColor(group3TextColor));

      }
    return convertView;
  }
  
  private void setChildHighlighted(View convertView) {
    boolean highlightON = true;
    
    if (highlightON) {
      TextView txtEventTime = (TextView) convertView.findViewById(R.id.talk_time_text);
      TextView txtEventName = (TextView) convertView.findViewById(R.id.talk_title_text);
      
      txtEventName.setBackgroundColor(Color.parseColor(highlightedItemColor));
      txtEventTime.setBackgroundColor(Color.parseColor(highlightedItemColor));
    }
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