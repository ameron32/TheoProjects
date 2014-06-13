package com.ameron32.tap.conventionnotes;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.tap.conventionnotes.notes.Note;
import com.ameron32.tap.conventionnotes.notes.NoteAdapter;
import com.ameron32.tap.conventionnotes.program.ProgramEvent;
import com.ameron32.tap.conventionnotes.program.ProgramList;
import com.ameron32.tap.conventionnotes.program.Talk;
import com.ameron32.tap.conventionnotes.scripture.ScriptureNote;
import com.ameron32.tap.conventionnotes.tools.Testing;

/**
 * A fragment representing a single Talk detail screen.
 */
public class TalkDetailFragment extends Fragment {
  
  private static boolean     DEBUG       = true;
  
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";
  
  /**
   * The dummy content this fragment is presenting.
   */
  private View               mRootView;
  private ListView           mNoteListView;
  private int                id          = -1;
  private TextView           headerTitleText;
  // private RelativeLayout header2;
  private NoteAdapter        noteAdapter;
  
  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public TalkDetailFragment() {}
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.detail, menu);
  }
  
  /**
   * A callback interface that all activities containing this fragment must
   * implement. This mechanism allows activities to be notified of item
   * selections.
   */
  public interface Callbacks {
    
    /**
     * Next talk
     */
    public void onNextClicked();
    
    /**
     * Prev talk
     */
    public void onPrevClicked();
    
    /**
     * Save
     */
    public void onSave();
    
    /**
     * 
     */
    public void onNoteLongPressed(AdapterView<?> parent, View view, int position, long id, Note note);
  }
  
  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks             mCallbacks      = sDummyCallbacks;
  
  // private TextView startTime;
  // private TextView stopTime;
  
  /**
   * A dummy implementation of the {@link Callbacks} interface that does
   * nothing. Used only when this fragment is not attached to an activity.
   */
  private static Callbacks      sDummyCallbacks = new Callbacks() {
                                                  
                                                  @Override
                                                  public void onNextClicked() {}
                                                  
                                                  @Override
                                                  public void onPrevClicked() {}
                                                  
                                                  @Override
                                                  public void onSave() {}
                                                  
                                                  @Override
                                                  public void onNoteLongPressed(AdapterView<?> parent, View view,
                                                      int position, long id, Note note) {};
                                                  
                                                };
  
  private TextView              countDown;
  
  private static CountDownTimer cdt;
  
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_talk_detail, container, false);
    mNoteListView = (ListView) mRootView.findViewById(R.id.talk_detail);
    createListView();
    initNextPrevButtons(mRootView);
    initOtherViews(mRootView);
    return mRootView;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.ic_save) {
      mCallbacks.onSave();
      return true;
    }
    if (item.getItemId() == R.id.action_camera) {
      Intent toCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
      Toast.makeText(getActivity(), "[Video Camera] Recordings stored in Gallery.", Toast.LENGTH_LONG).show();
      addNote(Note.createNote("---VIDEO---"));
      startActivity(toCamera);
    }
    return super.onOptionsItemSelected(item);
  }
  
  private void initNextPrevButtons(View rootView) {
    ImageButton next = (ImageButton) rootView.findViewById(R.id.image_button_next_talk);
    ImageButton prev = (ImageButton) rootView.findViewById(R.id.image_button_previous_talk);
    
    next.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        mCallbacks.onNextClicked();
      }
    });
    prev.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        mCallbacks.onPrevClicked();
      }
    });
  }
  
  private void initOtherViews(View rootView) {
    
    // startTime = (TextView) rootView.findViewById(R.id.textview_start_time);
    // stopTime = (TextView) rootView.findViewById(R.id.textview_stop_time);
    countDown = (TextView) rootView.findViewById(R.id.textview_countdown);
    countDown.setTextColor(Color.parseColor("#989898"));
    
  }
  
  private void createListView() {
    mNoteListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    
    View headerCore = (((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_view_core, null, false));
    mNoteListView.addHeaderView(headerCore);
    
    headerTitleText = (TextView) headerCore.findViewById(R.id.textview_title);
    headerTitleText.setTextColor(getResources().getColor(R.color.TextAccent));
  }
  
  private void populateListView() {
    setTalkTitleInHeader(ProgramList.getTalk(id));
  }
  
  public void showTalk(int talkId) {
    
    if (cdt != null) {
      cdt.cancel();
    }
    
    Talk talk = ProgramList.getTalk(talkId);
    id = talkId;
    
    noteAdapter = new NoteAdapter(getActivity(), talk);
    populateListView();
    mNoteListView.setAdapter(noteAdapter);
    mNoteListView.setOnItemClickListener(new OnItemClickListener() {
      
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == -1) return;
        
        Note note = noteAdapter.getItem((int) id);
        if (note instanceof ScriptureNote) {
          if (DEBUG) {
            Toast.makeText(getActivity(), note.toString(), Toast.LENGTH_LONG).show();
          }
        }
        else {
          if (DEBUG) {
            Toast.makeText(getActivity(), note.getNote() + "--clicked", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
    mNoteListView.setOnItemLongClickListener(new OnItemLongClickListener() {
      
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == -1) return false;
        
        mCallbacks.onNoteLongPressed(parent, view, position, id, noteAdapter.getItem((int) id));
        return true;
      }
      
    });
    setText(talk);
    
    View controls = mRootView.findViewById(R.id.relativelayout_controls_frame);
    controls.setVisibility(View.VISIBLE);

    cdt = new CountDownTimer(ProgramEvent.timeFromNow(System.currentTimeMillis(), talk), 1000) {
      
      @Override
      public void onTick(long millisUntilFinished) {
        countDown.setText(ProgramEvent.convertCountdown(millisUntilFinished / 1000));
      }
      
      @Override
      public void onFinish() {
        countDown.setText("Overtime");
      }
    };
    cdt.start();
    
    // startTime.setText(talk.getStartTimeFormatted());
    // stopTime.setText(talk.getStopTimeFormatted());
  }
  
  private void setText(Talk talk) {
    ActionBar actionBar = getActivity().getActionBar();
    TextView titleText = (TextView) actionBar.getCustomView().findViewById(R.id.title_text);
    titleText.setText(talk.getMultiLineTitle(true));
    setTalkTitleInHeader(talk);
  }
  
  private void setTalkTitleInHeader(Talk talk) {
    Testing.Log.i("TalkTitleColor", "start");
    headerTitleText.setText(talk.getMultiLineTitle(false));
    
    Testing.Log.i("TalkTitleColor", "talk.getGroupDate(): " + talk.getGroupDate());

    int talkTitleColor = 0;
    switch (talk.getGroupDate()) {
    case "Friday, July 4":
      talkTitleColor = getResources().getColor(R.color.friday_talk_title);
      break;
    case "Saturday, July 5":
      talkTitleColor = getResources().getColor(R.color.saturday_talk_title);
      break;
    case "Sunday, July 6":
      talkTitleColor = getResources().getColor(R.color.sunday_talk_title);
      break;
    default:
      talkTitleColor = getResources().getColor(R.color.sunday_talk_title);
      // break;
    }
    Testing.Log.i("TalkTitleColor", "color: " + talkTitleColor);
    
    headerTitleText.setTextColor(talkTitleColor);
  }
  
  public int getTalkId() {
    return id;
  }
  
  public void addNote(Note note) {
    if (noteAdapter != null) {
      // Talk talk = ProgramList.getTalk(id);
      // talk.addNote(note);
      noteAdapter.addNote(note);
      noteAdapter.notifyDataSetChanged();
    }
    else {
      Toast.makeText(getActivity(), "Error: Select a talk and try again.", Toast.LENGTH_SHORT).show();
    }
  }
  
  public void deleteNote(int position, Note note) {
    if (noteAdapter != null) {
      Talk talk = ProgramList.getTalk(id);
      int indexToRemove = talk.getNotes().indexOf(note);
      // talk.removeNote(indexToRemove);
      noteAdapter.removeNote(indexToRemove);
      
      noteAdapter.notifyDataSetChanged();
    }
    else {
      Toast.makeText(getActivity(), "Error: Select a talk and try again.", Toast.LENGTH_SHORT).show();
    }
  }
}
