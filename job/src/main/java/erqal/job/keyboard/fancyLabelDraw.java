/*
 * Copyright (C) 2009 Alejandro Grijalba
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package erqal.job.keyboard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* This class draws our fancy keys.
 * Each key has typically 5 characters layed out inside.
 * There are several methods, each one implement a different look:
 * 	- Numbers on the left
 *  - Numbers in the center
 *  - Numbers big behind
 */

class fancyLabelDraw extends Drawable {
	int width;
	int height;
	Rect bounds;
	LatinKeyboard.LatinKey key;
	static float horizontalPadding = (float) 0.95;
	static float verticalPadding = (float) 0.50;

	// auxiliary variables
	static char[] letter = new char[1];
	static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	static float tW0, tMX, tH0, tMY, tM0;

	public fancyLabelDraw(LatinKeyboard.LatinKey aKey) {

		key = aKey;
		mPaint.setTypeface(Global.typeface);
		mPaint.setAntiAlias(true);
		// 使用抗锯齿效果提高Paint质量
		mPaint.setSubpixelText(true);
		bounds = new Rect();
	}

	@Override
	public void draw(Canvas canvas) {
		drawNumBehind(canvas);
	}

	private void drawNumBehind(Canvas canvas) {
		// TODO Auto-generated method stub

		if (key.fancyLabel != null) {
			float keyX = key.width * horizontalPadding / 2;
			float keyY = key.height * verticalPadding / 2;
			mPaint.setColor(Color.rgb(55, 71, 79));
			// Set size and correct it
			float textSize = key.height * verticalPadding / 2;
			mPaint.setTextSize(textSize);
			Paint.FontMetrics pfm = mPaint.getFontMetrics();
			textSize = textSize * textSize / (pfm.descent - pfm.ascent);
			mPaint.setTextSize(textSize);
			pfm = mPaint.getFontMetrics();

			tH0 = (pfm.ascent - pfm.descent);

			float tMY0, tMX0;

			// main symbol
			mPaint.setTextSize(textSize * 2);
			mPaint.getTextBounds(key.fancyLabel.toString(), 0, key.fancyLabel
					.toString().length(), bounds);
			tMX0 = (bounds.right + bounds.left) / (float) 2.0;
			tMY = (pfm.ascent + pfm.descent) / (float) 2.0;
			tMY0 = (bounds.top + bounds.bottom) / (float) 2.0;
			// mPaint.setTypeface(Typeface.DEFAULT_BOLD);

			canvas.drawText(key.fancyLabel.toString(), keyX - tMX0,
					keyY - tMY0, mPaint);

		}
	}





	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBounds(Rect r) {
		int kk = 3 + 2;
		kk = kk + 3;
	}

	@Override
	public int getIntrinsicWidth() {
		return (int) (key.width * horizontalPadding);
		// return 10;//width;
	}

	// cada vez que se dibuja
	@Override
	public int getIntrinsicHeight() {
		return (int) (key.height * verticalPadding);
	}

	@Override
	public int getMinimumWidth() {
		int kk = 3 + 2;
		return kk;
	}

	/*
	 * @Override public void setBounds(int left, int top, int right, int bottom)
	 * { }
	 */

	@Override
	public void onBoundsChange(Rect newBounds) {
		bounds.set(newBounds);
	}
}
