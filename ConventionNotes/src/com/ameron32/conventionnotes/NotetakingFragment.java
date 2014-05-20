package com.ameron32.conventionnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link NotetakingFragment.NoteCallbacks} interface to handle
 * interaction events. Use the {@link NotetakingFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class NotetakingFragment extends Fragment {
  
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String           ARG_PARAM1 = "param1";
  private static final String           ARG_PARAM2 = "param2";
  
  // TODO: Rename and change types of parameters
  private String                        mParam1;
  private String                        mParam2;
  
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
  // TODO: Rename and change types and number of parameters
  public static NotetakingFragment newInstance(String param1, String param2) {
    NotetakingFragment fragment = new NotetakingFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }
  
  public NotetakingFragment() {
    // Required empty public constructor
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_notetaking, container, false);
    initEditor();
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
  
  private void resetEditor() {
    editNote.setText("");    
  }

  // TODO: Rename method, update argument and hook method into UI event
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
    
    // TODO: Update argument type and name
    public void onAddNote(Note note);
    
    public void onAddScripture();
  }
  
}
