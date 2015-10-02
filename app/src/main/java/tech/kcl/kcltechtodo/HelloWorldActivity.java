package tech.kcl.kcltechtodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*======================================================================

** Activities **

This is an activity, one of the basic building blocks of an Android app.
An activity can be thought of as a distinct "page" in your app. They
have layouts made up of many Views (taken from an XML resource), they
can handle user interactions, and your user can navigate between them.

Activities have a fairly complex lifecycle that they travel through as
they are created and destroyed. We will cover the four main events that
an Activity can go through in the lecture, but here's a summary:

onCreate: the activity has been created for the first time, but may not
          be visible to the user yet

onResume: the activity is now "on top" and can be seen by the user

onPause:  the activity has been obscured by something (another app
          opening, the user pressing "Home", a phone call, etc.)

onStop:   the activity has been stopped and will terminate completely

The most important thing to know about the lifecycle is that an activity
can exist in the background without being fully closed. For example, a
user is using your app and then receives a phone call - the call covers
your activity, but it isn't fully closed; it's "paused". It will be
"resumed" when their call ends.

======================================================================*/

public class HelloWorldActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
	}

}