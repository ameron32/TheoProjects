package com.ameron32.conventionnotes;

import java.text.SimpleDateFormat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.conventionnotes.notes.Note;
import com.ameron32.conventionnotes.notes.NoteAdapter;
import com.ameron32.conventionnotes.program.ProgramEvent;
import com.ameron32.conventionnotes.program.Talk;
import com.ameron32.conventionnotes.scripture.ScriptureNote;

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
  private int                id;
  private TextView           header;
  private RelativeLayout     header2;
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
    
  }
  
  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks        mCallbacks      = sDummyCallbacks;
  
  private TextView         startTime;
  private TextView         stopTime;
  
  /**
   * A dummy implementation of the {@link Callbacks} interface that does
   * nothing. Used only when this fragment is not attached to an activity.
   */
  private static Callbacks sDummyCallbacks = new Callbacks() {
                                             
                                             @Override
                                             public void onNextClicked() {}
                                             
                                             @Override
                                             public void onPrevClicked() {}
                                             
                                           };
  
  private TextView         countDown;
  
  private static CountDownTimer   cdt;
  
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
  
  private void initNextPrevButtons(View rootView) {
    Button next = (Button) rootView.findViewById(R.id.button3);
    Button prev = (Button) rootView.findViewById(R.id.button1);
    
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
    
    startTime = (TextView) rootView.findViewById(R.id.textview_start_time);
    stopTime = (TextView) rootView.findViewById(R.id.textview_stop_time);
    countDown = (TextView) rootView.findViewById(R.id.textview_countdown);
  }
  
  private void createListView() {
    
    View rlHeader = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.title_header_view, null, false);
    header = (TextView) rlHeader.findViewById(R.id.textview_title);
    header.setText(ProgramList.getTalk(id).getTitle());
    mNoteListView.addHeaderView(rlHeader);
    
    header2 = ((RelativeLayout) ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_view, null, false));
    mNoteListView.addHeaderView(header2);
  }
  
  private void populateListView() {
    header.setText(ProgramList.getTalk(id).getTitle());
  }
  
  public void showTalk(int talkId) {
    if (talkId >= ProgramList.getTalkCount()) {
      Toast.makeText(getActivity(), "Already at last talk.", Toast.LENGTH_LONG).show();
      return;
    }
    if (talkId < 0) {
      Toast.makeText(getActivity(), "Already at first talk.", Toast.LENGTH_LONG).show();
      return;
    }
    
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
    setText(talk);
    
    cdt = new CountDownTimer(
        ProgramEvent.timeFromNow(System.currentTimeMillis(), talk), 
        1000) {
      
      public void onTick(long millisUntilFinished) {
        countDown.setText(ProgramEvent.convertCountdown(millisUntilFinished/1000));
      }
      
      public void onFinish() {
        countDown.setText("Overtime");
      }
    };
    cdt.start();
    
    startTime.setText(talk.getStartTimeFormatted());
    stopTime.setText(talk.getStopTimeFormatted());
  }
  
  private void setText(Talk talk) {
    ActionBar actionBar = getActivity().getActionBar();
    TextView titleText = (TextView) actionBar.getCustomView().findViewById(R.id.title_text);
    titleText.setText(talk.getMultiLineTitle());
    header.setText(talk.getTitle());
  }
  
  public int getTalkId() {
    return id;
  }
  
  public void addNote(Note note) {
    Talk talk = ProgramList.getTalk(id);
    talk.addNote(note);
    noteAdapter.notifyDataSetChanged();
  }
}
