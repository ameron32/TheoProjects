package com.ameron32.conventionnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ameron32.conventionnotes.notes.Note;
import com.ameron32.conventionnotes.notes.NoteAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link NotetakingFragment.NoteCallbacks} interface to handle
 * interaction events. Use the {@link NotetakingFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class NotetakingFragment extends Fragment {
  
  private static final String KEY_SAVED_EDITOR_TEXT = "keysavededitortext";
  private String savedEditorText;
  
  private NoteCallbacks mListener;
  private View mRootView;
  private EditText editNote;
  private ImageButton buttonAdd;
  private ImageButton buttonAddScripture;
  
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
//    Bundle args = new Bundle();
//    args.putString(ARG_PARAM1, param1);
//    args.putString(ARG_PARAM2, param2);
//    fragment.setArguments(args);
    return fragment;
  }
  
  public NotetakingFragment() {
    // Required empty public constructor
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
//      mParam1 = getArguments().getString(ARG_PARAM1);
//      mParam2 = getArguments().getString(ARG_PARAM2);
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
        String note = editNote.getText().toString();
        addNote(Note.createNote(note));
        resetEditor();
      }
    });
    
    buttonAddScripture = (ImageButton) mRootView.findViewById(R.id.image_button_add_scripture);
    buttonAddScripture.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        addScripture();
      }
    });
  }
  
  private void restoreSavedEditorText() {
    editNote.setText(savedEditorText);
  }
  
  private void resetEditor() {
    editNote.setText("");    
  }

  public void addNote(Note note) {
    if (mListener != null) {
      mListener.onAddNote(note);
    } else {
      Log.d(this.getClass().getSimpleName(), "mListener failed: null");
    }
  }
  
  public void addScripture() {
    if (mListener != null) {
      mListener.onAddScripture();
    } else {
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
  
  public void requestUser(final AdapterView<?> parent, final View view, final int position, final long id, final Note note) {
    final View selectorLL = mRootView.findViewById(R.id.selector_parent_ll);
    selectorLL.setVisibility(View.VISIBLE);
    
    Button edit = (Button) selectorLL.findViewById(R.id.selector_edit_button);
    Button delete = (Button) selectorLL.findViewById(R.id.selector_delete_button);
    OnClickListener ocl = new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        switch(v.getId()) {
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
//  private static final String           ARG_PARAM1 = "param1";
//  private static final String           ARG_PARAM2 = "param2";
  
  // : Rename and change types of parameters
//  private String                        mParam1;
//  private String                        mParam2;
  
  
}
