package com.ameron32.conventionnotes;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
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
import com.ameron32.conventionnotes.program.ProgramList;
import com.ameron32.conventionnotes.scripture.Scripture;
import com.ameron32.conventionnotes.scripture.ScriptureDialog;
import com.ameron32.conventionnotes.tools.Exporter;
import com.ameron32.conventionnotes.tools.Testing;

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
public class MainActivity extends FragmentActivity implements TalkListFragment.Callbacks, TalkDetailFragment.Callbacks,
    NotetakingFragment.NoteCallbacks, ScriptureDialog.OnScriptureGeneratedListener {
  
  private static final String KEY_CURRENT_TALK_ID = "keycurrenttalkid";
  private String              currentTalkId;
  
  private SlidingPaneLayout   mSlidingLayout;
  private ActionBar           mActionBar;
  
  private static Context      context;
  
  public static Context getContext() {
    return context;
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = CustomApplication.getCustomAppContext();
    
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
    
    if (savedInstanceState != null) {
      // restore current talk
      currentTalkId = savedInstanceState.getString(KEY_CURRENT_TALK_ID);
    }
    // TODO: If exposing deep links into your app, handle intents here.
  }
  
  @Override
  protected void onResume() {
    if (currentTalkId != null) {
      selectItem(currentTalkId);
    }
    super.onResume();
  }
  
  @Override
  protected void onPause() {
    Testing.startTest("PauseDelay");
    super.onPause();
    saveProgram();
  }
  
  private boolean saveProgram() {
    try {
      ProgramList.saveProgram();
      return true;
    }
    catch (IOException e) {
      Toast.makeText(getContext(), "Save failed.", Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }
    return false;
  }
  
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(KEY_CURRENT_TALK_ID, currentTalkId);
  }
  
  @Override
  protected void onDestroy() {
    context = null;
    super.onDestroy();
    Testing.endTest("PauseDelay");
  }
  
  /**
   * Callback method from {@link TalkListFragment.Callbacks} indicating that the
   * item with the given ID was selected.
   */
  @Override
  public void onItemSelected(String id) {
    
    saveProgram();
    
    // Show the detail view in this activity by
    // adding or replacing the detail fragment using a
    // fragment transaction.
    
    // ***********************************************
    // SwitchTalks
    selectItem(id);
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  private void selectItem(String id) {
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
//    Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
    currentTalkId = id;
    talkFragment.showTalk(Integer.decode(id));
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that
   * the the next talk button was clicked.
   */
  @Override
  public void onNextClicked() {
    
    saveProgram();
    
    //
    
    // ***********************************************
    // SwitchTalks
    nextTalk();
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that
   * the the previous talk button was clicked.
   */
  @Override
  public void onPrevClicked() {
    
    saveProgram();
    
    //
    
    // ***********************************************
    // SwitchTalks
    prevTalk();
    
    // ***********************************************
    
    mSlidingLayout.closePane();
  }
  
  private void nextTalk() {
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    int nextTalkId = talkFragment.getTalkId() + 1;
    selectTalk(nextTalkId);
  };
  
  private void prevTalk() {
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    int prevTalkId = talkFragment.getTalkId() - 1;
    selectTalk(prevTalkId);
  };
  
  private void selectTalk(int newTalkId) {
    if (!isBlocked(newTalkId)) {
      TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
      ((TalkListFragment) getSupportFragmentManager().findFragmentById(R.id.talk_list)).setActivatedTalk(newTalkId);
      talkFragment.showTalk(newTalkId);
    }
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that
   * the save button was clicked.
   */
  @Override
  public void onSave() {
    
    boolean saved = saveProgram();
    if (saved) {
      Toast.makeText(MainActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
    }
    //
    
    // ***********************************************
    // SwitchTalks
  }
  
  private boolean isBlocked(int talkId) {
    if (talkId >= ProgramList.getTalkCount()) {
      Toast.makeText(getContext(), "Already at last talk.", Toast.LENGTH_LONG).show();
      return true;
    }
    if (talkId < 0) {
      Toast.makeText(getContext(), "Already at first talk.", Toast.LENGTH_LONG).show();
      return true;
    }
    return false;
  }
  
  /**
   * Callback method from {@link NoteTakingFragment.NoteCallbacks} indicating
   * that the the a note should be passed.
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
   * Callback method from {@link NoteTakingFragment.NoteCallbacks} indicating
   * that the a scripture dialog should be opened.
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
    if (item.getItemId() == R.id.action_export) {
      saveProgram();
      Exporter.exportProgramNotesAsEmail(MainActivity.this);
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
      // Toast.makeText(panel.getContext(), "Opened",
      // Toast.LENGTH_SHORT).show();
      
      panelOpened();
    }
    
    @Override
    public void onPanelClosed(View panel) {
      // Toast.makeText(panel.getContext(), "Closed",
      // Toast.LENGTH_SHORT).show();
      
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
    
    @SuppressWarnings("deprecation")
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
    Log.d("MainActivity|" + tag, message);
  }
}
