package tech.kcl.kcltechtodo.data;

import android.content.ContentValues;
import android.database.Cursor;
import org.joda.time.DateTime;

public class Task {

	private long id;
	private String title;
	private String notes;
	private DateTime dueDate;
	private boolean complete;

	/*==============*
	 * Constructors *
	 *==============*/

	public Task(String title, String notes, DateTime dueDate, boolean complete) {
		this.title = title;
		this.notes = notes;
		this.dueDate = dueDate;
		this.complete = complete;
	}

	public Task(Cursor input) throws IllegalArgumentException {
		id = input.getLong(input.getColumnIndexOrThrow(Db.id));
		title = input.getString(input.getColumnIndexOrThrow(Db.title));
		notes = input.getString(input.getColumnIndexOrThrow(Db.notes));
		dueDate = new DateTime(input.getLong(input.getColumnIndexOrThrow(Db.dueDate)));
		complete = input.getInt(input.getColumnIndexOrThrow(Db.complete)) == 1;
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

	public ContentValues getContentValues() {
		ContentValues output = new ContentValues();
		output.put(Db.id, id);
		output.put(Db.title, title);
		output.put(Db.notes, notes);
		output.put(Db.dueDate, dueDate.getMillis());
		output.put(Db.complete, complete ? 1 : 0);
		return output;
	}

	public interface Db {
		String _tableName = "tasks";
		String id = "id";
		String title = "title";
		String notes = "notes";
		String dueDate = "due_date";
		String complete = "complete";
	}
}
