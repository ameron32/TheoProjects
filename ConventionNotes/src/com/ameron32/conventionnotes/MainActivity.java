package com.ameron32.conventionnotes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.conventionnotes.notes.Note;
import com.ameron32.conventionnotes.scripture.*;

/**
 * An activity representing the represents a a list of Talks and its details in
 * a Sliding Pane.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link TalkListFragment} and the item details (if present) is a
 * {@link TalkDetailFragment}.
 * <p>
 * This activity also implements the required {@link TalkListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class MainActivity extends FragmentActivity implements TalkListFragment.Callbacks, TalkDetailFragment.Callbacks, NotetakingFragment.NoteCallbacks, ScriptureDialog.OnScriptureGeneratedListener {
  
  private SlidingPaneLayout mSlidingLayout;
  private ActionBar         mActionBar;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // List items should be given the
    // 'activated' state when touched.
    ((TalkListFragment) getSupportFragmentManager().findFragmentById(R.id.talk_list)).setActivateOnItemClick(true);
    
    mActionBar = getActionBar();
    mActionBar.setCustomView(R.layout.title_view);
    mActionBar.setDisplayShowCustomEnabled(true);
    
    mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
    
    mSlidingLayout.setPanelSlideListener(new SliderListener());
    mSlidingLayout.openPane();
    
    mSlidingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new FirstLayoutListener());
    
    // TODO: If exposing deep links into your app, handle intents here.
  }
  
  /**
   * Callback method from {@link TalkListFragment.Callbacks} indicating that the
   * item with the given ID was selected.
   */
  @Override
  public void onItemSelected(String id) {
    
    // Show the detail view in this activity by
    // adding or replacing the detail fragment using a
    // fragment transaction.
    
    // ***********************************************
    // SwitchTalks
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
    talkFragment.showTalk(Integer.decode(id));
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that the
   * the next talk button was clicked.
   */
  @Override
  public void onNextClicked() {
    
    // 
    
    // ***********************************************
    // SwitchTalks
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    talkFragment.showTalk(talkFragment.getTalkId() + 1);
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that the
   * the previous talk button was clicked.
   */
  @Override
  public void onPrevClicked() {
    
    // 
    
    // ***********************************************
    // SwitchTalks
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    talkFragment.showTalk(talkFragment.getTalkId() - 1);
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  /**
   * Callback method from {@link NoteTakingFragment.NoteCallbacks} indicating that the
   * the a note should be passed.
   */
  @Override
  public void onAddNote(Note note) {
    
    // 
    
    // ***********************************************
    // SwitchTalks
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    talkFragment.addNote(note);
  }
  
  /**
   * Callback method from {@link NoteTakingFragment.NoteCallbacks} indicating that the
   * a scripture dialog should be opened.
   */
  @Override
  public void onAddScripture() {
    // 
    
    // ***********************************************
    // SwitchTalks
    FragmentManager fm = getSupportFragmentManager();
    ScriptureDialog createScriptureDialog = new ScriptureDialog();
    createScriptureDialog.show(fm, "fragment_create_scripture");
  }
  
  @Override
  public void onScriptureGenerated(Scripture scripture) {
    onAddNote(Note.createNote(scripture));
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    /*
     * The action bar up action should open the slider if it is currently
     * closed, as the left pane contains content one level up in the navigation
     * hierarchy.
     */
    if (item.getItemId() == android.R.id.home && !mSlidingLayout.isOpen()) {
      mSlidingLayout.openPane();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
  
  /**
   * This panel slide listener updates the action bar accordingly for each panel
   * state.
   */
  private class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {
    
    @Override
    public void onPanelOpened(View panel) {
//      Toast.makeText(panel.getContext(), "Opened", Toast.LENGTH_SHORT).show();
      
      panelOpened();
    }
    
    @Override
    public void onPanelClosed(View panel) {
//      Toast.makeText(panel.getContext(), "Closed", Toast.LENGTH_SHORT).show();
      
      panelClosed();
    }
    
    @Override
    public void onPanelSlide(View view, float v) {}
  }
  
  @SuppressLint("NewApi")
  private void panelClosed() {
    mActionBar.setDisplayHomeAsUpEnabled(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      mActionBar.setHomeButtonEnabled(true);
    }
    
    getSupportFragmentManager().findFragmentById(R.id.content_pane).setHasOptionsMenu(true);
    getSupportFragmentManager().findFragmentById(R.id.talk_list).setHasOptionsMenu(false);
  }
  
  @SuppressLint("NewApi")
  private void panelOpened() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      mActionBar.setHomeButtonEnabled(false);
    }
    TextView titleText = (TextView) mActionBar.getCustomView().findViewById(R.id.title_text);
    titleText.setText("Select a talk...");
    mActionBar.setDisplayHomeAsUpEnabled(false);
    
    if (mSlidingLayout.isSlideable()) {
      getSupportFragmentManager().findFragmentById(R.id.content_pane).setHasOptionsMenu(false);
      getSupportFragmentManager().findFragmentById(R.id.talk_list).setHasOptionsMenu(true);
    }
    else {
      getSupportFragmentManager().findFragmentById(R.id.content_pane).setHasOptionsMenu(true);
      getSupportFragmentManager().findFragmentById(R.id.talk_list).setHasOptionsMenu(false);
    }
  }
  
  /**
   * This global layout listener is used to fire an event after first layout
   * occurs and then it is removed. This gives us a chance to configure parts of
   * the UI that adapt based on available space after they have had the
   * opportunity to measure and layout.
   */
  @SuppressLint("NewApi")
  private class FirstLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    
    @Override
    public void onGlobalLayout() {
      
      if (mSlidingLayout.isSlideable() && !mSlidingLayout.isOpen()) {
        panelClosed();
      }
      else {
        panelOpened();
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        mSlidingLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
      else {
        mSlidingLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
      }
    }
  }
  
  public static void log(String tag, String message) {
    Log.d("MainActivity|"+tag, message);
  }
}
