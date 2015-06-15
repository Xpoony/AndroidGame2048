package com.xzf.game;


import java.util.Random;
import android.graphics.Point;


public class Gamedata {
	public final static int UP = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int RIGHT = 4;
	private int score;
	private int topScore;
	private int[][] data;

	public Gamedata() {
		score = 0;
		topScore = History.getTop();
		data = new int[4][4];
		init();
	}

	public int getScore() {
		return score;
	}

	public int getTopScore() {
		return topScore;
	}

	public void setTopScore(int topScore) {
		this.topScore = topScore;
	}

	public int[][] getData() {
		return data;
	}

	public void init() {
		if (new Random().nextInt(10) > 7)
			newAblock();
		newAblock();
		newAblock();
	}

	public void getSocre(int sv) {
		score += sv;
	}

	public void removeBlank_UP() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				int n = j;
				while (n > 0 && data[n - 1][i] == 0) {
					data[n - 1][i] = data[n][i];
					data[n][i] = 0;
					n--;

				}
			}
	}

	public void removeBlank_DOWN() {
		for (int i = 0; i < 4; i++)
			for (int j = 2; j >= 0; j--) {
				int n = j;
				while (n < 3 && data[n + 1][i] == 0) {
					data[n + 1][i] = data[n][i];
					data[n][i] = 0;
					n++;

				}
			}
	}

	public void removeBlank_LEFT() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				int n = j;
				while (n > 0 && data[i][n - 1] == 0) {
					data[i][n - 1] = data[i][n];
					data[i][n] = 0;
					n--;

				}
			}
	}

	public void removeBlank_RIGHT() {
		for (int i = 0; i < 4; i++)
			for (int j = 2; j >= 0; j--) {
				int n = j;
				while (n < 3 && data[i][n + 1] == 0) {
					data[i][n + 1] = data[i][n];
					data[i][n] = 0;
					n++;

				}
			}
	}

	public boolean isCanMove(int aspect) {
		switch (aspect) {
		case UP:
			for (int i = 0; i < 4; i++)
				if ((data[3][i] != 0 || data[2][i] != 0 || data[1][i] != 0)
						&& data[0][i] == 0)
					return true;
				else if ((data[3][i] != 0 || data[2][i] != 0)
						&& data[1][i] == 0)
					return true;
				else if ((data[3][i] != 0) && data[2][i] == 0)
					return true;
			break;
		case DOWN:
			for (int i = 0; i < 4; i++)
				if ((data[0][i] != 0 || data[2][i] != 0 || data[1][i] != 0)
						&& data[3][i] == 0)
					return true;
				else if ((data[0][i] != 0 || data[1][i] != 0)
						&& data[2][i] == 0)
					return true;
				else if ((data[0][i] != 0) && data[1][i] == 0)
					return true;
			break;
		case LEFT:
			for (int i = 0; i < 4; i++)
				if ((data[i][3] != 0 || data[i][2] != 0 || data[i][1] != 0)
						&& data[i][0] == 0)
					return true;
				else if ((data[i][3] != 0 || data[i][2] != 0)
						&& data[i][1] == 0)
					return true;
				else if ((data[i][3] != 0) && data[i][2] == 0)
					return true;
			break;
		case RIGHT:
			for (int i = 0; i < 4; i++)
				if ((data[i][0] != 0 || data[i][2] != 0 || data[i][1] != 0)
						&& data[i][3] == 0)
					return true;
				else if ((data[i][0] != 0 || data[i][1] != 0)
						&& data[i][2] == 0)
					return true;
				else if ((data[i][0] != 0) && data[i][1] == 0)
					return true;
			break;
		}

		return false;
	}

	public void move(int aspect) {
		boolean flag=isCanMove(aspect);
		switch (aspect) {
		case UP:
			removeBlank_UP();
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 3; j++) {
					if (data[j + 1][i] == 0)
						continue;
					else if (data[j + 1][i] != data[j][i])
						continue;
					else {
						data[j][i] *= 2;
						getSocre(data[j][i]);
						data[j + 1][i] = 0;
						removeBlank_UP();
						
						flag=true;
					}
				}
			break;
		case DOWN:
			removeBlank_DOWN();
			for (int i = 0; i < 4; i++)
				for (int j = 3; j > 0; j--) {
					if (data[j - 1][i] == 0)
						continue;
					else if (data[j - 1][i] != data[j][i])
						continue;
					else {
						data[j][i] *= 2;
						getSocre(data[j][i]);
						data[j - 1][i] = 0;
						removeBlank_DOWN();
						
						flag=true;
					}
				}
			break;
		case LEFT:
			removeBlank_LEFT();
			for (int[] d : data)
				for (int i = 0; i < 3; i++) {
					if (d[i] == 0)
						continue;
					else if (d[i] != d[i + 1])
						continue;
					else {
						d[i] *= 2;
						getSocre(d[i]);
						d[i + 1] = 0;
						removeBlank_LEFT();
						
						flag=true;
					}
				}
			break;
		case RIGHT:
			removeBlank_RIGHT();
			for (int[] d : data)
				for (int i = 3; i > 0; i--) {
					if (d[i] == 0)
						continue;
					else if (d[i] != d[i - 1])
						continue;
					else {
						d[i] *= 2;
						getSocre(d[i]);
						d[i - 1] = 0;
						removeBlank_LEFT();
						flag=true;
					}
				}
			break;
		}
		if(flag)
			newAblock();

	}

	public boolean isEnd() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (data[i][j] == 0)
					return false;
				else {
					if (i != 0)
						if (data[i][j] == data[i - 1][j])
							return false;
					if (i != 3)
						if (data[i][j] == data[i + 1][j])
							return false;
					if (j != 0)
						if (data[i][j] == data[i][j - 1])
							return false;
					if (j != 3)
						if (data[i][j] == data[i][j + 1])
							return false;
				}

			}
		return true;
	}

	public int getZerosum() {
		int sum = 0;
		for (int[] d : data)
			for (int i = 0; i < 4; i++)
				if (d[i] == 0)
					sum++;
		return sum;
	}

	public Point getZeroBlock() {
		Point p = null;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (data[i][j] == 0)
					p = new Point(i, j);
		return p;
	}

	public void newAblock() {
		if (getZerosum() > 1) {
			int x = new Random().nextInt(4);
			int y = new Random().nextInt(4);
			while (data[x][y] != 0) {
				x = new Random().nextInt(4);
				y = new Random().nextInt(4);
			}
			if (new Random().nextInt(10) < 8)
				data[x][y] = 2;
			else
				data[x][y] = 4;

		} else {
			Point p = getZeroBlock();
			if (new Random().nextInt(10) < 8)
				data[p.x][p.y] = 2;
			else
				data[p.x][p.y] = 4;
		}

	}
}
