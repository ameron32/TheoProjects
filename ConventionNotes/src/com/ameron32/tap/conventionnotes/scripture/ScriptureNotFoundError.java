package com.ameron32.tap.conventionnotes.scripture;

public abstract class ScriptureNotFoundError extends Error {
	private static final long serialVersionUID = -3931084948382145054L;	
}

final class BookNotFoundError extends ScriptureNotFoundError{
	private static final long serialVersionUID = -8881445011563319283L;
	public String book;
}

final class ChapterNotFoundError extends ScriptureNotFoundError{
	private static final long serialVersionUID = -8011856512920611745L;
	public int chapter;
}

final class VerseNotFoundError extends ScriptureNotFoundError{
	private static final long serialVersionUID = -8546975579336574522L;
	public int verse;
}
