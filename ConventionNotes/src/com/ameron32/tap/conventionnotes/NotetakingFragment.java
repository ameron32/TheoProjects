package com.ameron32.tap.conventionnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ameron32.tap.conventionnotes.notes.Note;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link NotetakingFragment.NoteCallbacks} interface to handle interaction
 * events. Use the {@link NotetakingFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class NotetakingFragment extends Fragment {
  
  private static final String KEY_SAVED_EDITOR_TEXT = "keysavededitortext";
  private String              savedEditorText;
  
  private NoteCallbacks       mListener;
  private View                mRootView;
  private EditText            editNote;
  private ImageButton         buttonAdd;
  private ImageButton         buttonAddScripture;
  
  /**
   * Use this factory method to create a new instance of this fragment using the
   * provided parameters.
   * 
   * @param param1
   *          Parameter 1.
   * @param param2
   *          Parameter 2.
   * @return A new instance of fragment NotetakingFragment.
   */
  // : Rename and change types and number of parameters
  public static NotetakingFragment newInstance(String param1, String param2) {
    NotetakingFragment fragment = new NotetakingFragment();
    // Bundle args = new Bundle();
    // args.putString(ARG_PARAM1, param1);
    // args.putString(ARG_PARAM2, param2);
    // fragment.setArguments(args);
    return fragment;
  }
  
  public NotetakingFragment() {
    // Required empty public constructor
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // mParam1 = getArguments().getString(ARG_PARAM1);
      // mParam2 = getArguments().getString(ARG_PARAM2);
      savedEditorText = savedInstanceState.getString(KEY_SAVED_EDITOR_TEXT);
      // apply String to EditText after View
    }
  }
  
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    savedEditorText = editNote.getText().toString();
    outState.putString(KEY_SAVED_EDITOR_TEXT, savedEditorText);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_notetaking, container, false);
    initEditor();
    restoreSavedEditorText();
    return mRootView;
  }
  
  private void initEditor() {
    editNote = (EditText) mRootView.findViewById(R.id.edit_text_note_editor);
    buttonAdd = (ImageButton) mRootView.findViewById(R.id.image_button_add_note);
    buttonAdd.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        pressAddNote();
      }
    });
    
    buttonAddScripture = (ImageButton) mRootView.findViewById(R.id.image_button_add_scripture);
    buttonAddScripture.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        addScripture();
      }
    });
    
    hw = new HintWatcher(getActivity());
    editNote.addTextChangedListener(hw);
    editNote.setOnKeyListener(new OnKeyListener() {
      
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        
        if (event != null && v.getId() == R.id.edit_text_note_editor) {
          // if shift key is down, then we want to insert the '\n' char in the
          // TextView;
          // otherwise, the default action is to send the message.
          if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
              if (event.isShiftPressed()) {
                pressAddNote();
                return true;
              }
            }
          }
        }
        return false;
      }
    });
  }
  
  private void pressAddNote() {
    String note = editNote.getText().toString();
    addNote(Note.createNote(note));
    resetEditor();
  }
  private HintWatcher hw;
  
  private void restoreSavedEditorText() {
    editNote.setText(savedEditorText);
  }
  
  private void resetEditor() {
    editNote.setText("");
  }
  
  public void addNote(Note note) {
    if (mListener != null) {
      mListener.onAddNote(note);
    }
    else {
      Log.d(this.getClass().getSimpleName(), "mListener failed: null");
    }
  }
  
  public void addScripture() {
    if (mListener != null) {
      mListener.onAddScripture();
    }
    else {
      Log.d(this.getClass().getSimpleName(), "mListener failed: null");
    }
  }
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (NoteCallbacks) activity;
    }
    catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement NoteCallbacks");
    }
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  
  public void requestUser(final AdapterView<?> parent, final View view, final int position, final long id,
      final Note note) {
    final View selectorLL = mRootView.findViewById(R.id.selector_parent_ll);
    selectorLL.setVisibility(View.VISIBLE);
    
    Button edit = (Button) selectorLL.findViewById(R.id.selector_edit_button);
    Button delete = (Button) selectorLL.findViewById(R.id.selector_delete_button);
    OnClickListener ocl = new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
        case R.id.selector_edit_button:
          editNote(note);
          break;
        case R.id.selector_delete_button:
          deleteNote((int) id, note);
          break;
        }
        selectorLL.setVisibility(View.GONE);
      }
    };
    edit.setOnClickListener(ocl);
    delete.setOnClickListener(ocl);
  }
  
  private void editNote(Note note) {
    mListener.onEditNote(note);
  };
  
  private void deleteNote(int position, Note note) {
    mListener.onDeleteNote(position, note);
  };
  
  public static class HintWatcher implements TextWatcher {
    
    private final Activity activity;
    
    public HintWatcher(Activity activity) {
      this.activity = activity;
    }
    
    boolean toggle = false;
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      Log.i("onTextChanged", "s=" + s + " start=" + start + " before=" + before + " count=" + count);
      
      /**
       * Logic: if the first character BECOMES '@', switch to Scripture mode. if
       * the first character CEASES TO BE '@', switch back to Note mode. getting
       * there was a lot of if statements.
       */
      if (s == null) return;
      
      if (s.length() == 0) {
        if (toggleOff()) {
          goNoteMode();
        }
        return;
      }
      
      switch (s.charAt(0)) {
      case '@':
        if (toggleOn()) {
          goScriptureMode();
        }
        return;
      case '$':
        if (toggleOn()) {
          goSpeakerMode();
        }
        return;
      case '!':
        if (toggleOn()) {
          goImportantMode();
        }
        return;
      }
      //
      // if (s.charAt(0) == '@') {
      // if (toggleOn()) {
      // goScriptureMode();
      // }
      // return;
      // }
      //
      // if (s.charAt(0) == '$') {
      // if (toggleOn()) {
      // goSpeakerMode();
      // }
      // return;
      // }
      //
      // if (s.charAt(0) == '!') {
      // if (toggleOn()) {
      // goSpeakerMode();
      // }
      // return;
      // }
      
      // if (s != null) {
      // if (s.length() != 0) {
      // if (s.charAt(0) == '@') {
      // if (toggleOn()) {
      // goScriptureMode();
      // }
      // } else if (s.charAt(0) == '~') {
      // if (toggleOn()) {
      // goSpeakerMode();
      // }
      // }
      // }
      // }
      // if (s != null) {
      // if (s.length() != 0) {
      // if (s.charAt(0) != '@') {
      // if (toggleOff()) {
      // goNoteMode();
      // }
      // } else if (s.charAt(0) != '~') {
      // if (toggleOn()) {
      // goSpeakerMode();
      // }
      // }
      // }
      // if (s.length() == 0) {
      // if (toggleOff()) {
      // goNoteMode();
      // }
      // }
      // }
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    
    @Override
    public void afterTextChanged(Editable s) {}
    
    private boolean toggleOn() {
      if (!toggle) {
        toggle = true;
        Log.i("onTextChanged", "ON");
        return true;
      }
      return false;
    }
    
    private boolean toggleOff() {
      if (toggle) {
        toggle = false;
        Log.i("onTextChanged", "OFF");
        return true;
      }
      return false;
    }
    
    private void goNoteMode() {
      TextView hintText = (TextView) activity.findViewById(R.id.text_view_note_editor_hint);
      hintText.setText(activity.getResources().getString(R.string.text_view_note_editor_hint));
    }
    
    private void goScriptureMode() {
      TextView hintText = (TextView) activity.findViewById(R.id.text_view_note_editor_hint);
      hintText.setText("Enter a Scripture: (e.g. @HABAKKUK 2 2)");
    }
    
    private void goSpeakerMode() {
      TextView hintText = (TextView) activity.findViewById(R.id.text_view_note_editor_hint);
      hintText.setText("Enter the name of the Speaker:");
    }
    
    private void goImportantMode() {
      TextView hintText = (TextView) activity.findViewById(R.id.text_view_note_editor_hint);
      hintText.setText("Type your important note: (it will appear at the top)");
    }
  }
  
  /**
   * This interface must be implemented by activities that contain this fragment
   * to allow an interaction in this fragment to be communicated to the activity
   * and potentially other fragments contained in that activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface NoteCallbacks {
    
    public void onAddNote(Note note);
    
    public void onAddScripture();
    
    public void onDeleteNote(int position, Note note);
    
    public void onEditNote(Note note);
  }
  
  // : Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  // private static final String ARG_PARAM1 = "param1";
  // private static final String ARG_PARAM2 = "param2";
  
  // : Rename and change types of parameters
  // private String mParam1;
  // private String mParam2;
  
}
