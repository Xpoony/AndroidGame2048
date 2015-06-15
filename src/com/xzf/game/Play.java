package com.xzf.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Play extends Activity implements OnGestureListener,
		OnTouchListener {

	RelativeLayout play;

	Button back;

	Button score;

	Button topScore;

	Button[][] blocks;

	String s = "当前分数\n\n";
	String ts = "最高分数\n\n";
	@SuppressWarnings("deprecation")
	GestureDetector mygesture = new GestureDetector(this);
	Gamedata data;

	void setScore() {
		score.setText(s + data.getScore());
	}

	void setTopScore() {
		topScore.setText(ts + data.getTopScore());
	}

	void setValue(int i, int j, int value) {
		if (value != 0)
			blocks[i][j].setText(value + "");
		else
			blocks[i][j].setText("");

		((GradientDrawable) blocks[i][j].getBackground())
				.setColor(getBgColor(value));
	}

	void init() {
		blocks = new Button[4][4];

		blocks[0][0] = (Button) findViewById(R.id.data00);
		blocks[0][1] = (Button) findViewById(R.id.data01);
		blocks[0][2] = (Button) findViewById(R.id.data02);
		blocks[0][3] = (Button) findViewById(R.id.data03);

		blocks[1][0] = (Button) findViewById(R.id.data10);
		blocks[1][1] = (Button) findViewById(R.id.data11);
		blocks[1][2] = (Button) findViewById(R.id.data12);
		blocks[1][3] = (Button) findViewById(R.id.data13);

		blocks[2][0] = (Button) findViewById(R.id.data20);
		blocks[2][1] = (Button) findViewById(R.id.data21);
		blocks[2][2] = (Button) findViewById(R.id.data22);
		blocks[2][3] = (Button) findViewById(R.id.data23);

		blocks[3][0] = (Button) findViewById(R.id.data30);
		blocks[3][1] = (Button) findViewById(R.id.data31);
		blocks[3][2] = (Button) findViewById(R.id.data32);
		blocks[3][3] = (Button) findViewById(R.id.data33);

		reset();

	}

	void reset() {
		setScore();
		if (History.biggerThanMax(data.getScore()))
			topScore.setText(ts + data.getScore());
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				setValue(i, j, data.getData()[i][j]);
			}
	}

	int getBgColor(int value) {
		String BgColor = "";
		switch (value) {
		case 0:
			BgColor = "#CCC0B3";
			break;
		case 2:
			BgColor = "#EEE4DA";
			break;
		case 4:
			BgColor = "#EDE0C8";
			break;
		case 8:
			BgColor = "#F2B179";
			break;
		case 16:
			BgColor = "#F49563";
			break;
		case 32:
			BgColor = "#F5794D";
			break;
		case 64:
			BgColor = "#F55D37";
			break;
		case 128:
			BgColor = "#EEE863";
			break;
		case 256:
			BgColor = "#EDB04D";
			break;
		case 512:
			BgColor = "#ECB04D";
			break;
		case 1024:
			BgColor = "#EB9437";
			break;
		case 2048:
			BgColor = "#EA7821";
			break;
		default:
			BgColor = "#EA7821";
			break;
		}
		return Color.parseColor(BgColor);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.play);
		data = new Gamedata();
		play = (RelativeLayout) findViewById(R.id.play);
		play.setOnTouchListener(this);
		back = (Button) findViewById(R.id.pback);

		score = (Button) findViewById(R.id.score);

		topScore = (Button) findViewById(R.id.topscore);
		topScore.setText(ts + data.getTopScore());

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(Play.this)
						.setTitle("确认返回吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent();
										intent.setClass(Play.this, Start.class);
										Play.this.startActivity(intent);
										Play.this.finish();
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

		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setBackgroundResource(R.drawable.but_press);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					back.setBackgroundResource(R.drawable.but_normal);
					break;
				}
				return false;
			}
		});
		init();
	}

	@Override
	public boolean onDown(MotionEvent arg0) {

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		float x1 = e1.getX(), x2 = e2.getX();
		float y1 = e1.getY(), y2 = e2.getY();

		float x = x1 - x2;
		float y = y1 - y2;

		if (y > 70 && Math.abs(x) < 200)
			data.move(Gamedata.UP);
		else if (y < -70 && Math.abs(x) < 200)
			data.move(Gamedata.DOWN);
		else if (x > 70 && Math.abs(y) < 200)
			data.move(Gamedata.LEFT);
		else if (x < -70 && Math.abs(y) < 200)
			data.move(Gamedata.RIGHT);
		reset();
		if (data.isEnd()) {
			if (History.biggerThanMin(data.getScore()))
					History.setHistory(data.getScore());
			new AlertDialog.Builder(Play.this)
					.setTitle("游戏结束！得分：" + data.getScore() + ",再来一次？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent();
									intent.setClass(Play.this, Play.class);
									Play.this.startActivity(intent);
									Play.this.finish();
								}
							})
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent();
									intent.setClass(Play.this, Start.class);
									Play.this.startActivity(intent);
									Play.this.finish();
								}
							}).show();
		}

		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		return false;

	}

	@Override
	public void onShowPress(MotionEvent arg0) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {

		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return mygesture.onTouchEvent(event);
	}

}