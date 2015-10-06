package tech.kcl.kcltechtodo.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import tech.kcl.kcltechtodo.constants.C;

import java.util.ArrayList;

/**
 * This class handles all interaction with this app's database.
 * It is responsible for creating the database, inserting records and retrieving them.
 */
public class DbHelper extends SQLiteOpenHelper {

	/*=======================*
	 * Constructor and setup *
	 *=======================*/

	public DbHelper(Context context) {
		super(context, C.DB_NAME, null, C.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// execute a query to create the task database
		db.execSQL("CREATE TABLE " + Task.Db._tableName + " (" +
				Task.Db.id + " INTEGER PRIMARY KEY," +
				Task.Db.title + " TEXT," +
				Task.Db.notes + " TEXT," +
				Task.Db.dueDate + " INTEGER," +
				Task.Db.complete + " INTEGER DEFAULT 0" +
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// this method would be used to handle upgrades,
		// if we need to update the database in future versions of the app
	}

	/*==============*
	 * Task methods *
	 *==============*/

	/**
	 * Saves a task into the database by inserting or updating the record.
	 *
	 * @param t the task to save
	 */
	public void saveTask(Task t) {
		// check that the task is not null
		if (t == null) return;

		// get a copy of the database that we're allowed to write to
		SQLiteDatabase db = getWritableDatabase();
		if (db == null) return;

		// save the task in the database
		db.insertWithOnConflict(Task.Db._tableName, null, t.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
	}

	/**
	 * Gets a list of tasks from the database, in due-date descending order.
	 *
	 * @param uncompletedOnly {@code true} to fetch only uncompleted tasks, {@code false} otherwise
	 * @return a due-date descending list of tasks
	 */
	public ArrayList<Task> getTasks(boolean uncompletedOnly) {
		// get a copy of the database that we're allowed to read from
		SQLiteDatabase db = getReadableDatabase();
		if (db == null) return null;

		// send a query to the database for tasks
		Cursor result = db.query(
				Task.Db._tableName,
				null,
				uncompletedOnly ? Task.Db.complete + " = 0" : null,
				null,
				null,
				null,
				Task.Db.dueDate + " DESC"
		);

		// loop over the result to get an output
		ArrayList<Task> output = new ArrayList<>();
		if (result == null || !result.moveToFirst()) return output;
		do {
			output.add(new Task(result));
		} while (result.moveToNext());

		// we're finished with this cursor now, so we can close it
		result.close();

		// return the finished result
		return output;
	}

	/**
	 * Gets a single task from the database.
	 *
	 * @param taskId the ID of the task to retrieve
	 * @return the task, or {@code null} on failure
	 */
	public Task getTask(long taskId) {
		// get a copy of the database that we're allowed to read from
		SQLiteDatabase db = getReadableDatabase();
		if (db == null) return null;

		// search for the task
		Cursor result = db.query(
				Task.Db._tableName,
				null,
				Task.Db.id + " = ?",
				new String[]{taskId + ""},
				null,
				null,
				null
		);

		// convert the query result to a task
		if (result.moveToFirst()) {
			return new Task(result);
		} else {
			return null;
		}
	}
}
