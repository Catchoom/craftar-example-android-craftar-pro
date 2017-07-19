// com.craftar.craftarexamples is free software. You may use it under the MIT license, which is copied
// below and available at http://opensource.org/licenses/MIT
//
// Copyright (c) 2014 Catchoom Technologies S.L.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
// Software, and to permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
// PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
// FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.

package com.catchoom.craftarsdkexamples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class LaunchersActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_launchers);
		
		// Setup howto links
		findViewById(R.id.howto_link).setOnClickListener(this);
		findViewById(R.id.howto_link_ar_programmatically).setOnClickListener(this);
		findViewById(R.id.howto_link_ar_from_craftar).setOnClickListener(this);
		findViewById(R.id.howto_link_recognition_only).setOnClickListener(this);
		findViewById(R.id.howto_link_on_device_ar).setOnClickListener(this);

		// Setup example links
		findViewById(R.id.play_ar_programmatically).setOnClickListener(this);
		findViewById(R.id.play_ar_from_craftar).setOnClickListener(this);
		findViewById(R.id.play_recognition_only).setOnClickListener(this);
		findViewById(R.id.play_on_device_ar).setOnClickListener(this);
		
		// Setup bottom Links
		findViewById(R.id.imageButton_logo).setOnClickListener(this);
		findViewById(R.id.button_signUp).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		/* Check if clicked on title or howto links */
		Intent launchHowto = null;
		switch(v.getId()){
			case R.id.howto_link:
				launchHowto = new Intent(this, HowToActivity.class);
				launchHowto.putExtra(HowToActivity.HOWTO_LAYOUT_EXTRA, R.layout.activity_howto);
				break;
			case R.id.howto_link_ar_programmatically:
				launchHowto = new Intent(this, HowToActivity.class);
				launchHowto.putExtra(HowToActivity.HOWTO_LAYOUT_EXTRA, R.layout.activity_howto_ar_programmatically);
				break;
			case R.id.howto_link_ar_from_craftar:
				launchHowto = new Intent(this, HowToActivity.class);
				launchHowto.putExtra(HowToActivity.HOWTO_LAYOUT_EXTRA, R.layout.activity_howto_ar_from_craftar);
				break;
			case R.id.howto_link_recognition_only:
				launchHowto = new Intent(this, HowToActivity.class);
				launchHowto.putExtra(HowToActivity.HOWTO_LAYOUT_EXTRA, R.layout.activity_howto_recognition_only);
				break;
			case R.id.howto_link_on_device_ar:
				launchHowto = new Intent(this, HowToActivity.class);
				launchHowto.putExtra(HowToActivity.HOWTO_LAYOUT_EXTRA, R.layout.activity_howto_on_device_ar);
				break;
		}

		if (launchHowto != null) {
			startActivity(launchHowto);
			return;
		}

		/* Check if clicked on play links */

		Intent playExampleIntent = null;

		switch(v.getId()){
			case R.id.play_ar_programmatically:
				playExampleIntent = new Intent(this, OnDeviceIRandARProgrammaticallyActivity.class);
				break;
			case R.id.play_ar_from_craftar:
				playExampleIntent = new Intent(this, ARWithOnDeviceIRActivity.class);
				break;
			case R.id.play_recognition_only:
				playExampleIntent = new Intent(this, OnDeviceRecognitionActivity.class);
				break;
			case R.id.play_on_device_ar:
				playExampleIntent = new Intent(this, OnDeviceARActivity.class);
				break;
		}

		if (playExampleIntent != null) {
			final Intent finalPlayExampleIntent = playExampleIntent;
			checkPermissionAndAskIfNotGranted(new Runnable() {
				@Override
				public void run() {
					startActivity(finalPlayExampleIntent);
				}
			});
			return;
		}

		/* Check if clicked on bottom links */
		Intent launchWebView = null;

		switch(v.getId()){
			case R.id.imageButton_logo:
				launchWebView = new Intent(this, WebActivity.class);
				launchWebView.putExtra(WebActivity.WEB_ACTIVITY_URL, "http://catchoom.com/product/?utm_source=CraftARExamplesApp&amp;utm_medium=Android&amp;utm_campaign=HelpWithAPI");
				break;
			case R.id.button_signUp:
				launchWebView = new Intent(this, WebActivity.class);
				launchWebView.putExtra(WebActivity.WEB_ACTIVITY_URL, "https://crs.catchoom.com/try-free?utm_source=CraftARExamplesApp&amp;utm_medium=Android&amp;utm_campaign=HelpWithAPI");
				break;
		}


		if (launchWebView != null) {
			startActivity(launchWebView);
			return;
		}
	}


	private static final int CAMERA_PERMISSION = 0;

	private Runnable doWhenGranted;

	public void checkPermissionAndAskIfNotGranted(Runnable doWhenGranted) {
		this.doWhenGranted = doWhenGranted;
		if (PermissionChecker.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					android.Manifest.permission.CAMERA)) {
				showExplanation("Camera access", "This app needs to use the camera to demostrate the SDK's capabilities", android.Manifest.permission.CAMERA, CAMERA_PERMISSION);
			} else {
				requestPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION);
			}
		} else {
			doWhenGranted.run();
		}
	}

	private void showExplanation(String title,
								 String message,
								 final String permission,
								 final int permissionRequestCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						requestPermission(permission, permissionRequestCode);
					}
				});
		builder.create().show();
	}

	private void requestPermission(String permissionName, int permissionRequestCode) {
		ActivityCompat.requestPermissions(this,
				new String[]{permissionName}, permissionRequestCode);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case CAMERA_PERMISSION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					doWhenGranted.run();
				} else {
					Toast.makeText(getApplicationContext(), "Sorry, without camera permission, the examples will not work.", Toast.LENGTH_SHORT).show();
				}
				return;
			}
		}

	}
}
