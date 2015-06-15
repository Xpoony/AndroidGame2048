package com.xzf.game;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Start extends Activity {

	Button sbut;
	Button hbut;
	Button ebut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start);
		sbut = (Button) findViewById(R.id.sbut);
		hbut = (Button) findViewById(R.id.hbut);
		ebut = (Button) findViewById(R.id.ebut);

		sbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(Start.this, Play.class);
				Start.this.startActivity(intent);
				Start.this.finish();
			}
		});
		sbut.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					sbut.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					sbut.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});

		hbut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(Start.this, History.class);
				Start.this.startActivity(intent);
				Start.this.finish();
			}
		});

		hbut.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					hbut.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					hbut.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});

		ebut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				new AlertDialog.Builder(Start.this)
						.setTitle("确认退出吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Start.this.finish();
										System.exit(0);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();
			}
		});
		ebut.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					ebut.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					ebut.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
