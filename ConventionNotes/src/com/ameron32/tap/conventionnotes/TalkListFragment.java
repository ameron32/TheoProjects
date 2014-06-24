package com.ameron32.tap.conventionnotes;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ameron32.tap.conventionnotes.program.MExpandableListAdapter;
import com.ameron32.tap.conventionnotes.program.ProgramAdapter;
import com.ameron32.tap.conventionnotes.program.ProgramEvent;
import com.ameron32.tap.conventionnotes.program.ProgramList;
import com.ameron32.tap.conventionnotes.program.Talk;

/**
 * A list fragment representing a list of Talks. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link TalkDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TalkListFragment extends Fragment implements OnChildClickListener, OnGroupClickListener {
  
  // MICAH ADDED TWO FIELDS:
  ExpandableListView          expListView;
  MExpandableListAdapter      listAdapter;

  /**
   * The serialization (saved instance state) Bundle key representing the
   * activated item position. Only used on tablets.
   */
  private static final String STATE_ACTIVATED_Talk = "activated_position";
  
  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks           mCallbacks               = sDummyCallbacks;
  
  /**
   * The current activated item position. Only used on tablets.
   */
  private int                 mActivatedTalk       = ListView.INVALID_POSITION;
  
  /**
   * A callback interface that all activities containing this fragment must
   * implement. This mechanism allows activities to be notified of item
   * selections.
   */
  public interface Callbacks {
    
    /**
     * Callback for when an item has been selected.
     */
    public void onItemSelected(String id);
  }
  
  /**
   * A dummy implementation of the {@link Callbacks} interface that does
   * nothing. Used only when this fragment is not attached to an activity.
   */
  private static Callbacks sDummyCallbacks = new Callbacks() {
                                             
                                             @Override
                                             public void onItemSelected(String id) {}
                                           };
  
  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  
  // private final boolean alreadyCalled = false;

  public TalkListFragment() {}
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      int state = savedInstanceState.getInt(STATE_ACTIVATED_Talk);
      if (state != ListView.INVALID_POSITION) {
        // Restore the activated item position.
        mActivatedTalk = state;
      }
    }
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
    // BEGIN UNDER CONSTRUCTION

    setHasOptionsMenu(true);
    // return super.onCreateView(inflater, container, savedInstanceState);
    View talkView = LayoutInflater.from(getActivity()).inflate(R.layout.m_fragment_talk_list, null);
    // talkView.setOnV
    listAdapter = new MExpandableListAdapter(getActivity());
    
    expListView = (ExpandableListView) talkView.findViewById(android.R.id.list);
    expListView.setAdapter(listAdapter);
    listAdapter.setView(expListView);

    expListView.setOnChildClickListener(this);
    expListView.setOnGroupClickListener(this);
    
    expListView.setDivider(new ColorDrawable(Color.parseColor("#EFEFEF")));
    expListView.setChildDivider(new ColorDrawable(Color.parseColor("#EFEFEF")));
    expListView.setDividerHeight(12);
    expListView.setBackgroundColor(Color.parseColor("#EFEFEF"));
    
    expListView.setGroupIndicator(getActivity().getResources().getDrawable(R.drawable.list_item_selector1));

    return talkView;
    
    // END UNDER CONSTRUCTION
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.list, menu);
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    // Restore the previously serialized activated item position.
    if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_Talk)) {
      // setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_Talk));
      setActivatedTalk(savedInstanceState.getInt(STATE_ACTIVATED_Talk));
    }
  }
  
  @Override
  public void onResume() {
    super.onResume();
  }
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    
    // Activities containing this fragment must implement its callbacks.
    if (!(activity instanceof Callbacks)) { throw new IllegalStateException("Activity must implement fragment's callbacks."); }
    
    mCallbacks = (Callbacks) activity;
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    
    // Reset the active callbacks interface to the dummy implementation.
    mCallbacks = sDummyCallbacks;
  }
  
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mActivatedTalk != ListView.INVALID_POSITION) {
      // Serialize and persist the activated item position.
      outState.putInt(STATE_ACTIVATED_Talk, mActivatedTalk);
    }
  }
  
  /**
   * Turns on activate-on-click mode. When this mode is on, list items will be
   * given the 'activated' state when touched.
   */
  public void setActivateOnItemClick(boolean activateOnItemClick) {
    // When setting CHOICE_MODE_SINGLE, ListView will automatically
    // give items the 'activated' state when touched.
    
    expListView.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
  }
  
  public void setActivatedTalk(int talkId) {
    int position = listAdapter.getPosition(ProgramList.getTalk(talkId));
    if (position != ProgramAdapter.POSITION_NOT_FOUND) {
      setCurrentTalk(talkId);
      listAdapter.setActivatedChild(talkId);
    }
  }

  
  @Override
  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
    ProgramEvent event = listAdapter.getItem(groupPosition, childPosition);
    if (event instanceof Talk) {
      Talk talk = (Talk) event;
      listAdapter.setActivatedChild(groupPosition, childPosition);
      mCallbacks.onItemSelected(String.valueOf(talk.getTalkNumber()));
      return true;
    }
    else {
      Toast.makeText(getActivity(), "Only Talks can be selected.", Toast.LENGTH_SHORT).show();
      return false;
    }
  }
  
  @Override
  public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
    return false;
  }

  private void setCurrentTalk(int talkId) {
    mActivatedTalk = talkId;
    listAdapter.setCurrentTalk(ProgramList.getTalk(talkId));
    redraw();
  }

  public void redraw() {
    expListView.invalidateViews();
    listAdapter.notifyDataSetChanged();
  }
}