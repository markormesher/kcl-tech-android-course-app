package tech.kcl.kcltechtodo.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {

	/*======*
	 * Data *
	 *======*/

	private Context context;
	private ArrayList<Task> tasks = null;

	public TaskListAdapter(Context context, ArrayList<Task> tasks) {
		this.context = context;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setText(((Task) getItem(position)).getTitle());
		return textView;
	}
}
