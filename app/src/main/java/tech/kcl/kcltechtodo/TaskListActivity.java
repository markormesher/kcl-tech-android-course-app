package tech.kcl.kcltechtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TaskListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the layout of this activity
		setContentView(R.layout.activity_task_list);

		// set the string for the title bar
		setTitle(getString(R.string.task_list_activity_title));
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
				//TODO: send to create activity
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}