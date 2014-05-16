package com.ameron32.conventionnotes;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TalkAdapter extends BaseAdapter {
  
  private Context context;
  private List<Talk> talks;
  private LayoutInflater inflater;

  public TalkAdapter(Context context) {
    this.context = context;
    talks = TalkList.talks;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return talks.size();
  }
  
  @Override
  public Talk getItem(int position) {
    return talks.get(position);
  }
  
  @Override
  public long getItemId(int position) {
    return position;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    TalkViewHolder holder = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.talk_list_item, parent, false);
      holder = new TalkViewHolder();
      holder.title = (TextView) convertView.findViewById(R.id.talk_title_text);
      convertView.setTag(holder);
    } else {
      holder = (TalkViewHolder) convertView.getTag();
    }
    
    holder.title.setText(getItem(position).getMultiLineTitle());
    
    return convertView;
  }
  
  public static class TalkViewHolder {
    public TextView title;
  }
  
}
