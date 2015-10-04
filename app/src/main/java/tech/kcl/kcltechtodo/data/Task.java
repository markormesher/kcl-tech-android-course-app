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
		this.id = System.currentTimeMillis();
		this.title = title;
		this.notes = notes;
		this.dueDate = dueDate;
		this.complete = complete;
	}

	public Task(Cursor input) throws IllegalArgumentException {
		id = input.getLong(input.getColumnIndexOrThrow(Fields.id));
		title = input.getString(input.getColumnIndexOrThrow(Fields.title));
		notes = input.getString(input.getColumnIndexOrThrow(Fields.notes));
		dueDate = new DateTime(input.getLong(input.getColumnIndexOrThrow(Fields.dueDate)));
		complete = input.getInt(input.getColumnIndexOrThrow(Fields.complete)) == 1;
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
		output.put(Fields.id, id);
		output.put(Fields.title, title);
		output.put(Fields.notes, notes);
		output.put(Fields.dueDate, dueDate.getMillis());
		output.put(Fields.complete, complete ? 1 : 0);
		return output;
	}

	public interface Fields {
		String id = "id";
		String title = "title";
		String notes = "notes";
		String dueDate = "due_date";
		String complete = "complete";
	}
}
