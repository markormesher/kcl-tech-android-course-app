package tech.kcl.kcltechtodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the layout of this activity
		setContentView(R.layout.activity_task_list);

		// set the string for the title bar
		setTitle(getString(R.string.task_list_activity_title));
	}

}