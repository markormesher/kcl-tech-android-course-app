package tech.kcl.kcltechtodo.data;

import org.joda.time.DateTime;

public class Task {

	private long id;
	private String title;
	private String notes;
	private DateTime dueDate;
	private boolean complete;

	/*=============*
	 * Constructor *
	 *=============*/

	public Task(String title, String notes, DateTime dueDate, boolean complete) {
		this.title = title;
		this.notes = notes;
		this.dueDate = dueDate;
		this.complete = complete;
	}

	/*=====================*
	 * Getters and setters *
	 *=====================*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	/*================*
	 * Database stuff *
	 *================*/

	// TODO
}
