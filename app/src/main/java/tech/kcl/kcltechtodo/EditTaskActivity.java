package tech.kcl.kcltechtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.joda.time.DateTime;
import tech.kcl.kcltechtodo.data.DbHelper;
import tech.kcl.kcltechtodo.data.Task;

public class EditTaskActivity extends AppCompatActivity {

	// view components
	private ProgressBar loadingIcon;
	private ViewGroup mainContent;
	private EditText titleInput;
	private DatePicker dueDateInput;
	private EditText notesInput;

	// activity state
	private boolean createNew = true;
	private long editId = 0;

	/*================*
	 * Activity setup *
	 *================*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// edit or create?
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey("task_id")) {
			createNew = false;
			editId = extras.getLong("task_id");
		}

		// set the layout of this activity
		setContentView(R.layout.activity_edit_task);

		// set the string for the title bar
		setTitle(getString(createNew ? R.string.edit_task_activity_create_title : R.string.edit_task_activity_edit_title));

		// find UI components
		loadingIcon = (ProgressBar) findViewById(R.id.loading_icon);
		mainContent = (ViewGroup) findViewById(R.id.main_content);
		titleInput = (EditText) findViewById(R.id.title_input);
		dueDateInput = (DatePicker) findViewById(R.id.due_date_input);
		notesInput = (EditText) findViewById(R.id.notes_input);
		Button saveButton = (Button) findViewById(R.id.save_button);

		// set up the date picker
		dueDateInput.setCalendarViewShown(false);

		// set a click listener on the save button
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveButtonClicked();
			}
		});

		// set initial UI
		toggleBusyUi(true);
		loadTask();
	}

	/**
	 * Sets the UI to either a "loading" state or a "data" stage, based on {@code busyRefreshing}.
	 */
	private void toggleBusyUi(final boolean busy) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (busy) {
					loadingIcon.setVisibility(View.VISIBLE);
					mainContent.setVisibility(View.GONE);
				} else {
					loadingIcon.setVisibility(View.GONE);
					mainContent.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	/*==================*
	 * Task interaction *
	 *==================*/

	/**
	 * This will load the task and set the UI as "not busy".
	 */
	private void loadTask() {
		// check whether we are creating or editing a task
		if (createNew) {
			// nothing to load, so we're done
			toggleBusyUi(false);
		} else {
			// load the task on a new thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					DbHelper dbHelper = new DbHelper(getApplicationContext());
					final Task task = dbHelper.getTask(editId);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							taskLoaded(task);
						}
					});
				}
			}).start();
		}
	}

	/**
	 * This is called when the task has finished loading.
	 *
	 * @param task the task that was loaded, or {@code null}
	 */
	private void taskLoaded(Task task) {
		if (task == null) {
			// the task failed to load
			Toast.makeText(
					getApplicationContext(),
					R.string.edit_task_activity_load_failed,
					Toast.LENGTH_LONG
			).show();
			onBackPressed();
			finish();
		} else {
			// put the task details into the UI
			titleInput.setText(task.getTitle());
			notesInput.setText(task.getNotes());
			dueDateInput.init(
					task.getDueDate().getYear(),
					task.getDueDate().getMonthOfYear(),
					task.getDueDate().getDayOfMonth(),
					null
			);

			// update the UI to show that we're not busy anymore
			toggleBusyUi(false);
		}
	}

	/**
	 * This is called when the save button is clicked.
	 */
	private void saveButtonClicked() {
		// get inputs
		String title = titleInput.getText().toString().trim();
		String notes = notesInput.getText().toString().trim();
		DateTime dueDate = new DateTime(
				dueDateInput.getYear(),
				dueDateInput.getMonth() + 1,
				dueDateInput.getDayOfMonth(),
				23, 59, 59
		);

		// check title
		if (title.length() == 0) {
			Toast.makeText(
					getApplicationContext(),
					R.string.edit_task_activity_no_title_error,
					Toast.LENGTH_LONG
			).show();
			return;
		}

		// check date
		if (dueDate.isBeforeNow()) {
			Toast.makeText(
					getApplicationContext(),
					R.string.edit_task_activity_past_date_error,
					Toast.LENGTH_LONG
			).show();
			return;
		}

		// make a new task object
		final Task task = new Task(title, notes, dueDate, false);

		// set the ID
		if (createNew) {
			task.setId(System.currentTimeMillis());
		} else {
			task.setId(editId);
		}

		// save it in the database, on a new thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				DbHelper dbHelper = new DbHelper(getApplicationContext());
				dbHelper.saveTask(task);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								getApplicationContext(),
								R.string.edit_task_activity_task_saved,
								Toast.LENGTH_LONG
						).show();
						finish();
					}
				});
			}
		}).start();
	}

}