package com.xzf.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class History extends Activity {

	static File sdCardDir = Environment.getExternalStorageDirectory();
	static String path = "";

	Button reset;
	Button hback;

	TableRow[] rows;

	Button[] hscore;

	boolean flag = true;

	void reset() {
		
		try {

			File f = new File(path);
			if (f.exists()) {
				f.delete();
				f.createNewFile();
				flag = false;
			}
			refresh();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static List<Integer> getHistory() {
		List<Integer> scores = new ArrayList<Integer>();

		try {
			path = sdCardDir.getCanonicalPath() + "/2048history";
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
				return scores;
			}
			FileInputStream fis = new FileInputStream(path);

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				scores.add(Integer.parseInt(line));
			}
			Collections.sort(scores);
			br.close();
			fis.close();
		} catch (IOException e) {
		}

		return scores;
	}

	public static int getTop() {
		List<Integer> scores = getHistory();
		int size = scores.size();
		if (size != 0)
			return scores.get(size - 1);
		else
			return 0;
	}

	public static boolean biggerThanMax(int val) {
		List<Integer> scores = getHistory();
		if (scores.size() != 0)
			if (val > scores.get(scores.size()-1))
				return true;
			else
				return false;
		else
			return true;
	}
	
	public static boolean biggerThanMin(int val) {
		List<Integer> scores = getHistory();
		if (scores.size() != 0)
			if (val > scores.get(0))
				return true;
			else
				return false;
		else
			return true;
	}

	public static void setHistory(int score) {
		try {
			path = sdCardDir.getCanonicalPath() + "/2048history";
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
			} else {
				List<Integer> scores = getHistory();
				boolean fl = true;
				for (int i = 0; i < scores.size(); i++) {
					if (score == scores.get(i))
						fl = false;
				}
				if (fl&&scores.size()!=0) {
					scores.remove(0);
					scores.add(Integer.valueOf(score));
					Collections.sort(scores);
					f.delete();
					f.createNewFile();
					FileWriter writer = new FileWriter(path, true);
					for (int i = scores.size() - 1; i >= 0; i--) {
						writer.write(scores.get(i) + "\r\n");
					}
					writer.close();
				}
				else {
					FileWriter writer = new FileWriter(path, true);
					writer.write(score + "\r\n");
					writer.close();
				}

			}
		} catch (IOException e) {

		}
	}

	void init() {

		rows = new TableRow[10];

		hscore = new Button[10];

		rows[0] = (TableRow) findViewById(R.id.row1);
		rows[1] = (TableRow) findViewById(R.id.row2);
		rows[2] = (TableRow) findViewById(R.id.row3);
		rows[3] = (TableRow) findViewById(R.id.row4);
		rows[4] = (TableRow) findViewById(R.id.row5);
		rows[5] = (TableRow) findViewById(R.id.row6);
		rows[6] = (TableRow) findViewById(R.id.row7);
		rows[7] = (TableRow) findViewById(R.id.row8);
		rows[8] = (TableRow) findViewById(R.id.row9);
		rows[9] = (TableRow) findViewById(R.id.row10);

		hscore[0] = (Button) findViewById(R.id.hscore1);
		hscore[1] = (Button) findViewById(R.id.hscore2);
		hscore[2] = (Button) findViewById(R.id.hscore3);
		hscore[3] = (Button) findViewById(R.id.hscore4);
		hscore[4] = (Button) findViewById(R.id.hscore5);
		hscore[5] = (Button) findViewById(R.id.hscore6);
		hscore[6] = (Button) findViewById(R.id.hscore7);
		hscore[7] = (Button) findViewById(R.id.hscore8);
		hscore[8] = (Button) findViewById(R.id.hscore9);
		hscore[9] = (Button) findViewById(R.id.hscore10);

		refresh();

	}

	void refresh(){
		List<Integer> scores = getHistory();
		if (scores.size() == 0)
			flag = false;
		for (int i = scores.size(); i < 10; i++) {
			rows[i].setVisibility(View.GONE);
		}
		if (flag) {
			int n = 0;
			for (int i = scores.size() - 1; i >= 0; i--) {
				hscore[n++].setText(scores.get(i) + "");
			}
		} else
			Toast.makeText(History.this, "暂无数据", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.history);
		try {
			path = sdCardDir.getCanonicalPath() + "/2048history";
		} catch (IOException e) {

		}
		init();
		reset = (Button) findViewById(R.id.reset);

		hback = (Button) findViewById(R.id.hback);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(History.this)
						.setTitle("确认重置排行榜吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (flag) {
											reset();
											Toast.makeText(History.this,
													"重置成功", Toast.LENGTH_SHORT)
													.show();
										}
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
		reset.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					reset.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					reset.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});
		hback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(History.this, Start.class);
				History.this.startActivity(intent);
				History.this.finish();
			}
		});
		hback.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					hback.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					hback.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});

	}
}
