package com.ameron32.conventionnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ameron32.conventionnotes.program.ProgramAdapter;
import com.ameron32.conventionnotes.program.ProgramEvent;
import com.ameron32.conventionnotes.program.ProgramList;
import com.ameron32.conventionnotes.program.Talk;

/**
 * A list fragment representing a list of Talks. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link TalkDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TalkListFragment extends ListFragment {
  
  /**
   * The serialization (saved instance state) Bundle key representing the
   * activated item position. Only used on tablets.
   */
  private static final String STATE_ACTIVATED_POSITION = "activated_position";
  
  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks           mCallbacks               = sDummyCallbacks;
  
  /**
   * The current activated item position. Only used on tablets.
   */
  private int                 mActivatedPosition       = ListView.INVALID_POSITION;
  
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

  private ProgramAdapter mProgramAdapter;
  
  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public TalkListFragment() {}
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    mProgramAdapter = new ProgramAdapter(getActivity());
    setListAdapter(mProgramAdapter);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
    setHasOptionsMenu(true);
    return super.onCreateView(inflater, container, savedInstanceState);
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.list, menu);
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    // Restore the previously serialized activated item position.
    if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
      setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
    }
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
  public void onListItemClick(ListView listView, View view, int position, long id) {
    super.onListItemClick(listView, view, position, id);
    
    // Notify the active callbacks interface (the activity, if the
    // fragment is attached to one) that an item has been selected.
    ProgramEvent event = mProgramAdapter.getItem(position);
    if (event instanceof Talk) {
      Talk talk = (Talk) event;
      mCallbacks.onItemSelected(String.valueOf(talk.getTalkNumber()));
    } else {
      Toast.makeText(getActivity(), "Not a Talk", Toast.LENGTH_SHORT).show();
    }
  }
  
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mActivatedPosition != ListView.INVALID_POSITION) {
      // Serialize and persist the activated item position.
      outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
    }
  }
  
  /**
   * Turns on activate-on-click mode. When this mode is on, list items will be
   * given the 'activated' state when touched.
   */
  public void setActivateOnItemClick(boolean activateOnItemClick) {
    // When setting CHOICE_MODE_SINGLE, ListView will automatically
    // give items the 'activated' state when touched.
    getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
  }
  
  public void setActivatedTalk(int talkId) {
    int position = mProgramAdapter.getPosition(ProgramList.getTalk(talkId));
    if (position != ProgramAdapter.POSITION_NOT_FOUND) {
      setActivatedPosition(position);
    }
  }
  
  private void setActivatedPosition(int position) {
    if (position == ListView.INVALID_POSITION) {
      getListView().setItemChecked(mActivatedPosition, false);
    }
    else {
      getListView().setItemChecked(position, true);
    }
    
    mActivatedPosition = position;
  }
}
