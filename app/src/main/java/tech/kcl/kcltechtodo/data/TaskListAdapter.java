package tech.kcl.kcltechtodo.data;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import tech.kcl.kcltechtodo.R;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {

	/*======*
	 * Data *
	 *======*/

	private Activity activity;
	private ArrayList<Task> tasks = null;

	public TaskListAdapter(Activity activity, ArrayList<Task> tasks) {
		this.activity = activity;
		this.tasks = tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	/*====================*
	 * Adapter components *
	 *====================*/

	@Override
	public int getCount() {
		return tasks == null ? 0 : tasks.size();
	}

	@Override
	public Object getItem(int position) {
		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		Task t = (Task) getItem(position);
		return t == null ? -1 : t.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// recycle a view if possible
		View view = convertView;
		if (view == null) {
			view = activity.getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
		}

		// get view components
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView dueDate = (TextView) view.findViewById(R.id.due_date);

		// get task
		Task task = (Task) getItem(position);

		// set text
		title.setText(task.getTitle());
		dueDate.setText(task.getDueDate().toString("d MMM"));

		// return the finished view
		return view;
	}
}
