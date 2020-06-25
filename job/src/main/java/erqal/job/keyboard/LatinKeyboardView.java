/*
 * Copyright (C) 2008-2009 Google Inc.
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

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

/* This class is the View of the keyboard.
 * Currently extends KeyboardView class.
 * It's main duty is to receive touch events.
 */
public class LatinKeyboardView extends KeyboardView {
    static final int KEYCODE_OPTIONS = -100;
    static int direction;
    static boolean sShiftState;
    static boolean sAltState;
    static int screenW, screenH;
    static long downTime = 0;
    public static final String UTF_8 = "UTF-8";

    @Override
    public boolean setShifted(boolean newState) {
        sShiftState = newState;
        if (newState)
            sAltState = false;

        super.setShifted(!(sShiftState || sAltState));

        return super.setShifted(sShiftState || sAltState);
    }

    public void setAlt(boolean newState) {
        sAltState = newState;

        if (newState)
            sShiftState = false;

        super.setShifted(!(sShiftState || sAltState));
        super.setShifted(sShiftState || sAltState);
    }

    public void setNormal() {

        if (sAltState) {
            setShifted(true);
            setShifted(false);
        } else if (sShiftState) {
            setShifted(false);
        }

    }

    public void rotateAltShift() {
        if (sAltState) {
            setShifted(true);
        } else if (sShiftState) {
            setShifted(false);
        } else {
            setAlt(true);
        }
    }

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this.setPreviewEnabled(false);
            Display display = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            screenW = display.getWidth();
            screenH = display.getHeight();
        }

    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            this.setPreviewEnabled(false);
            Display display = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            screenW = display.getWidth();
            screenH = display.getHeight();
        }
    }

    //
    @Override
    protected boolean onLongPress(Key key) {

        if (key.codes[0] == '\n') {
            direction = -1;
            // force launch options activity
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);

            return true;
            // if(Keyboard.KEYCODE_MODE_CHANGE==key.codes[0])
        } else if (Keyboard.KEYCODE_MODE_CHANGE == key.codes[0]) {
            if (Keyboard.KEYCODE_MODE_CHANGE == key.codes[0]) {
                getOnKeyboardActionListener().onKey(2568, null);
                return true;
            }

            return super.onLongPress(key);
        } else {
            switch (key.codes[0]) {
                case 1602:
                    setKeysValue(1580);
                    break;
                case 1603:
                    setKeysValue(1734);
                    break;
                case 1670:
                    setKeysValue(33);
                    break;
                case 1739:
                    setKeysValue(64);
                    break;
                case 1744:
                    setKeysValue(35);
                    break;
                case 1585:
                    setKeysValue(36);
                    break;
                case 1578:
                    setKeysValue(37);
                    break;
                case 1610:
                    setKeysValue(94);
                    break;
                case 1735:
                    setKeysValue(38);
                    break;
                case 1709:
                    setKeysValue(42);
                    break;
                case 1608:
                    setKeysValue(40);
                    break;
                case 1662:
                    setKeysValue(41);
                    break;
                case 1726:
                    setKeysValue(45);
                    break;
                case 1587:
                    setKeysValue(8592);
                    break;
                case 1583:
                    setKeysValue(1688);
                    break;
                case 1575:
                    setKeysValue(1601);
                    break;
                case 1749:
                    setKeysValue(1711);
                    break;
                case 1609:
                    setKeysValue(1582);
                    break;
                case 1604:
                    setKeysValue(1574);
                    break;
                case 1586:
                    setKeysValue(8230);
                    break;
                case 1588:
                    setKeysValue(187);
                    break;
                case 1594:
                    setKeysValue(171);
                    break;
                case 1736:
                    setKeysValue(1567);
                    break;
                case 1576:
                    setKeysValue(1563);
                    break;
                case 1606:
                    setKeysValue(34);
                    break;
                case 1605:
                    setKeysValue(1548);
                    break;
                case 1574:
                    setKeysValue(1574);
                    break;
            }

        }

        if (key.codes[0] != 32 && key.codes[0] != -5) {

            return true;

        } else {

            return super.onLongPress(key);

        }

    }

    public void setKeysValue(int codes) {
        getOnKeyboardActionListener().onKey(codes, null);
    }

}
