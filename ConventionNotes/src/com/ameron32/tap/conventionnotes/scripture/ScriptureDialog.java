package com.ameron32.tap.conventionnotes.scripture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ameron32.conventionnotes.R;

public class ScriptureDialog extends DialogFragment {

  private OnScriptureGeneratedListener mListener;
  private View mRootView;
  
  /**
   * Use this factory method to create a new instance of this fragment using the
   * provided parameters.
   * 
   * @param param1
   *          Parameter 1.
   * @param param2
   *          Parameter 2.
   * @return A new instance of fragment ScriptureDialog.
   */
  // : Rename and change types and number of parameters
  public static ScriptureDialog newInstance(String param1, String param2) {
    ScriptureDialog fragment = new ScriptureDialog();
//    Bundle args = new Bundle();
//    args.putString(ARG_PARAM1, param1);
//    args.putString(ARG_PARAM2, param2);
//    fragment.setArguments(args);
    return fragment;
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_scripture_dialog, container, false);
    initViews(mRootView);
    return mRootView;
  }
  
  private void initViews(View rootView) {
    // TODO: init views
  }

  // TODO: USE ME!
  public void generateScripture(Scripture scripture) {
    if (mListener != null) {
      mListener.onScriptureGenerated(scripture);
    }
  }
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnScriptureGeneratedListener) activity;
    }
    catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
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
  public interface OnScriptureGeneratedListener {
    
    public void onScriptureGenerated(Scripture scripture);
  }
  
  
  
  
  
  
  
  
  /*
   * : Rename parameter arguments, choose names that match the fragment
   * initialization parameters, e.g. ARG_ITEM_NUMBER
   */
  // private static final String ARG_PARAM1 = "param1";
  // private static final String ARG_PARAM2 = "param2";
  
  /* : Rename and change types of parameters */
  // private String mParam1;
  // private String mParam2;
  public ScriptureDialog() {
    // Required empty public constructor
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // mParam1 = getArguments().getString(ARG_PARAM1);
      // mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }  
}
