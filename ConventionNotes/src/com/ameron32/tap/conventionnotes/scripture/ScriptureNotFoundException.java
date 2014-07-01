package com.ameron32.tap.conventionnotes.scripture;

public abstract class ScriptureNotFoundException extends Exception {
	private static final long serialVersionUID = -3931084948382145054L;	
}

final class BookNotFoundException extends ScriptureNotFoundException{
	private static final long serialVersionUID = -8881445011563319283L;
	public String book;
}

final class ChapterNotFoundException extends ScriptureNotFoundException{
	private static final long serialVersionUID = -8011856512920611745L;
	public int chapter;
}

final class VerseNotFoundException extends ScriptureNotFoundException{
	private static final long serialVersionUID = -8546975579336574522L;
	public int verse;
}
