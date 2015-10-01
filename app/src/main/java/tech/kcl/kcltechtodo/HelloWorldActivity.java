package tech.kcl.kcltechtodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*======================================================================

** Activities **

This is an Activity, one of the basic building blocks of an Android app.
An Activity can be thought of as a distinct "page" in your app. They
have layouts made up of many Views (taken from an XML resource), they
can handle user interactions, and your user can navigate between them.

Activities have a fairly complex lifecycle that they travel through as
they are created and destroyed. We will cover the four main events that
an Activity can go through: create, resume, pause and stop.

======================================================================*/

public class HelloWorldActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
	}

}