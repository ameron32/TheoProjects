package com.ameron32.tap.conventionnotes;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.tap.conventionnotes.notes.Note;
import com.ameron32.tap.conventionnotes.program.ProgramList;
import com.ameron32.tap.conventionnotes.scripture.Scripture;
import com.ameron32.tap.conventionnotes.scripture.ScriptureDialog;
import com.ameron32.tap.conventionnotes.tools.Exporter;
import com.ameron32.tap.conventionnotes.tools.Testing;

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
    NotetakingFragment.NoteCallbacks, ScriptureDialog.OnScriptureGeneratedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {
  
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
    mActionBar.setCustomView(R.layout.custom_action_bar_title_view);
    mActionBar.setDisplayShowCustomEnabled(true);
    
    mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
    
    mSlidingLayout.setPanelSlideListener(new SliderListener());
    mSlidingLayout.openPane();
    
    mSlidingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new FirstLayoutListener());
    
    if (savedInstanceState != null) {
      currentTalkId = savedInstanceState.getString(KEY_CURRENT_TALK_ID);
    }
    // TODO: If exposing deep links into your app, handle intents here.
  }
  
  // @Override
  // protected void onRestoreInstanceState(Bundle savedInstanceState) {
  // super.onRestoreInstanceState(savedInstanceState);
  // // restore current talk
  // Testing.Log.v(getClass().getSimpleName(), "onRestoreInstanceState");
  // currentTalkId = savedInstanceState.getString(KEY_CURRENT_TALK_ID);
  // }

  public static final String FONT_SIZE = "FONT_SIZE";
  private String             fontSizePref;

  @Override
  protected void onStart() {
    super.onStart();
    
    setFont();

    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    pref.registerOnSharedPreferenceChangeListener(this);
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
    saveSettings();
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
  
  private void saveSettings() {
    SharedPreferences settings = getSharedPreferences("com.ameron32.tap.conventionnotes", Context.MODE_PRIVATE);
    SharedPreferences.Editor settingsEdit = settings.edit();
    settingsEdit.putString("FONT_SIZE", fontSizePref);
    settingsEdit.commit();
  }
  
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Testing.Log.v(getClass().getSimpleName(), "onSaveInstanceState");
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
  
  @Override
  public void onDeleteNote(int position, Note note) {
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    talkFragment.deleteNote(position, note);
  }
  
  @Override
  public void onEditNote(Note note) {
    // TODO++ edit functionality or --remove
    Toast.makeText(MainActivity.this, "Edit not functional", Toast.LENGTH_SHORT).show();
  }
  
  private void selectItem(String id) {
    // TalkDetailFragment talkFragment = ((TalkDetailFragment)
    // getSupportFragmentManager().findFragmentById(R.id.content_pane));
    // Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
    // currentTalkId = id;
    // talkFragment.showTalk(Integer.decode(id));
    selectTalk(Integer.decode(id));
    
    showNotetaker();
  }
  
  private void selectTalk(int newTalkId) {
    if (!isBlocked(newTalkId)) {
      TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
      TalkListFragment programFragment = ((TalkListFragment) getSupportFragmentManager().findFragmentById(R.id.talk_list));
      // ((TalkListFragment)
      // getSupportFragmentManager().findFragmentById(R.id.talk_list)).setActivatedTalk(newTalkId);
      currentTalkId = String.valueOf(newTalkId);
      talkFragment.showTalk(newTalkId);
      programFragment.setActivatedTalk(newTalkId);
    }
  }

  private void showNotetaker() {
    TalkDetailFragment talkFragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    if (talkFragment.getTalkId() != -1) {
      findViewById(R.id.linearlayout_fragment_holder).setVisibility(View.VISIBLE);
    }
  }
  
  /**
   * Callback method from {@link TalkDetailFragment.Callbacks} indicating that
   * the the next talk button was clicked.
   */
  @Override
  public void onNoteLongPressed(AdapterView<?> parent, View view, int position, long id, Note note) {
    
    //
    
    // ***********************************************
    // SwitchTalks
    
    NotetakingFragment ntf = ((NotetakingFragment) getSupportFragmentManager().findFragmentById(R.id.note_editor));
    ntf.requestUser(parent, view, position, id, note);
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
  
  public static final int REQUEST_SETTINGS = 3;

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
      Exporter.exportProgramNotesAsEmail(MainActivity.this, ProgramList.getProgram());
    }
    // if (item.getItemId() == R.id.action_settings) {
    // Toast.makeText(MainActivity.this, "Settings not functional",
    // Toast.LENGTH_SHORT).show();
    // Intent i = new Intent(MainActivity.this, SettingsActivity.class);
    // startActivityForResult(i, REQUEST_SETTINGS);
    // }
    return super.onOptionsItemSelected(item);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    
    // super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_SETTINGS) {
      if (resultCode == RESULT_OK) {
        onSettingsClosed();
      }
    }
  }
  
  private void onSettingsClosed() {
    Toast.makeText(MainActivity.this, "onSettingsClosed()", Toast.LENGTH_SHORT).show();
    // TODO++ need doing?
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
    TextView titleText = (TextView) mActionBar.getCustomView().findViewById(R.id.title_text);
    TalkDetailFragment fragment = ((TalkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_pane));
    String talkTitle = ProgramList.getTalk(fragment.getTalkId()).getMultiLineTitle(true);
    titleText.setText(talkTitle);
    
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
    Testing.Log.d("MainActivity|" + tag, message);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Testing.Log.v("PREFERENCE CHANGE", "[" + key + "]");
    if (key.equalsIgnoreCase(FONT_SIZE)) {
      setFont();
    }
  }
  
  private void setFont() {
    // Enclose everything in a try block so we can just
    // use the default view if anything goes wrong.
    try {
      // Get the font size value from SharedPreferences.
      SharedPreferences settings = getSharedPreferences("com.ameron32.tap.conventionnotes", Context.MODE_PRIVATE);
      
      // Get the font size option. We use "FONT_SIZE" as the key.
      // Make sure to use this key when you set the value in SharedPreferences.
      // We specify "Medium" as the default value, if it does not exist.
      fontSizePref = settings.getString("FONT_SIZE", "Medium");
      
      // Select the proper theme ID.
      // These will correspond to your theme names as defined in themes.xml.
      int themeID = R.style.FontSizeMedium;
      if (fontSizePref == "Small") {
        themeID = R.style.FontSizeSmall;
      }
      else
        if (fontSizePref == "Large") {
          themeID = R.style.FontSizeLarge;
        }
        else
          if (fontSizePref == "XLarge") {
            themeID = R.style.FontSizeLarge;
          }
          else
            if (fontSizePref == "XXLarge") {
              themeID = R.style.FontSizeLarge;
            }
      
      // Set the theme for the activity.
      setTheme(themeID);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
