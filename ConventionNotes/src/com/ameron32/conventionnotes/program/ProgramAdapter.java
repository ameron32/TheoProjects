package com.ameron32.conventionnotes.program;

import java.util.List;

import com.ameron32.conventionnotes.ProgramList;
import com.ameron32.conventionnotes.R;
import com.ameron32.conventionnotes.R.id;
import com.ameron32.conventionnotes.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProgramAdapter extends BaseAdapter {
  public static final int IDTAG = 426842684;
  
//  private Context context;
  private List<ProgramEvent> events;
  private LayoutInflater inflater;

  public ProgramAdapter(Context context) {
//    this.context = context;
    events = ProgramList.events;
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

  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ProgramEvent event = getItem(position);
    TalkViewHolder holder = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.talk_list_item, parent, false);
      holder = new TalkViewHolder();
      holder.title = (TextView) convertView.findViewById(R.id.talk_title_text);
      convertView.setTag(holder);
      convertView.setTag(IDTAG, getItem(position).getId());
    } else {
      holder = (TalkViewHolder) convertView.getTag();
    }
    
    if (event instanceof Talk) {
      convertView.setMinimumHeight(80);
    } else {
      convertView.setMinimumHeight(30);
    }
    
    String textToDisplay = "";
    if (event instanceof Talk) {
      textToDisplay = ((Talk) event).getMultiLineTitle();
    }
    if (event instanceof Music) {
      textToDisplay = ((Music) event).getMusicText();
    }
    if (event instanceof Song) {
      textToDisplay = ((Song) event).getSongText();
    }
      
    holder.title.setText(textToDisplay);
    
    return convertView;
  }
  
  public static class TalkViewHolder {
    public TextView title;
  }
  
}
