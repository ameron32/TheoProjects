package com.ameron32.conventionnotes.program;

import java.util.List;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ameron32.conventionnotes.R;

public class ProgramAdapter extends BaseAdapter {
  public static final int IDTAG = 426842684;
  public static final int POSITION_NOT_FOUND = -777;
  
//  private Context context;
  private List<ProgramEvent> events;
  private LayoutInflater inflater;

  public ProgramAdapter(Context context) {
//    this.context = context;
    events = ProgramList.getEvents();
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return events.size();
  }
  
  @Override
  public ProgramEvent getItem(int position) {
    return events.get(position);
  }
  
  @Override
  public long getItemId(int position) {
    return position;
  }
  
  public int getPosition(ProgramEvent event) {
    int size = events.size();
    for (int i = 0; i < size; i++) {
      if (events.get(i).hashCode() == event.hashCode()) {
        return i;
      }
    }
    return POSITION_NOT_FOUND;
  }

  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ProgramEvent event = getItem(position);
    TalkViewHolder holder = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.talk_list_item, parent, false);
      holder = new TalkViewHolder();
      
      holder.time = (TextView) convertView.findViewById(R.id.talk_time_text);
      holder.title = (TextView) convertView.findViewById(R.id.talk_title_text);
      
      convertView.setTag(holder);
      convertView.setTag(IDTAG, getItem(position).getId());
    } else {
      holder = (TalkViewHolder) convertView.getTag();
    }
    
    if (event instanceof Talk) {
      convertView.setMinimumHeight(120);
    } else {
      convertView.setMinimumHeight(50);
    }
    
    String textToDisplay = "";
    if (event instanceof Talk) {
      textToDisplay += ((Talk) event).getMultiLineTitle(true);
    }
    if (event instanceof Music) {
      textToDisplay += ((Music) event).getMusicText();
    }
    if (event instanceof Song) {
      textToDisplay += ((Song) event).getSongText();
    }
    
    holder.time.setText(event.getProgramTime());
    holder.title.setText(textToDisplay);
    
    return convertView;
  }
  
  public static class TalkViewHolder {
    public TextView title;
    public TextView time;
  }
  
}
