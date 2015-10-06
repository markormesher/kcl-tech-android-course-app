package tech.kcl.kcltechtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import tech.kcl.kcltechtodo.data.DbHelper;
import tech.kcl.kcltechtodo.data.Task;
import tech.kcl.kcltechtodo.data.TaskListAdapter;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

	// view components
	private ProgressBar loadingIcon;
	private ListView listView;

	// list view state
	private ArrayList<Task> tasks = new ArrayList<>();
	private TaskListAdapter listAdapter;
	private boolean busyRefreshing = false;

	/*================*
	 * Activity setup *
	 *================*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the layout of this activity
		setContentView(R.layout.activity_task_list);

		// set the string for the title bar
		setTitle(getString(R.string.task_list_activity_title));

		// find UI components
		loadingIcon = (ProgressBar) findViewById(R.id.loading_icon);
		listView = (ListView) findViewById(R.id.list_view);

		// set up adapter
		listAdapter = new TaskListAdapter(this, tasks);
		listView.setAdapter(listAdapter);

		// carry out an initial refresh
		refreshTasks();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.task_list_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.create_task:
				Toast.makeText(getApplicationContext(), "This works!", Toast.LENGTH_LONG).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Sets the UI to either a "loading" state or a "data" stage, based on {@code busyRefreshing}.
	 */
	private void toggleBusyUi() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (busyRefreshing) {
					loadingIcon.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				} else {
					loadingIcon.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	/*==================*
	 * Task interaction *
	 *==================*/

	/**
	 * This will re-load the list of tasks from the database.
	 */
	private void refreshTasks() {
		// don't do anything if we're already refreshing
		if (busyRefreshing) return;

		// show that we're now busy
		busyRefreshing = true;
		toggleBusyUi();

		// start loading, on a new thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				// open the database
				DbHelper dbHelper = new DbHelper(getApplicationContext());

				// load a new list of tasks
				tasks = dbHelper.getTasks(true);

				// set new data on the adapter
				listAdapter.setTasks(tasks);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listAdapter.notifyDataSetInvalidated();
					}
				});

				// show that we're no longer busy
				busyRefreshing = false;
				toggleBusyUi();
			}
		}).start();
	}

}