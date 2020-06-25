package erqal.job.keyboard;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import erqal.job.R;

public class KeyboardUtil {
	protected static final int HIDE_SYSTEM_KEYBOARD = 1;
	protected static final int RESTART_INPUT_METHOD_SERVER = 2;
	protected static final int SWITCH_TO_SYSTEM_KEYBOARD = 3;
	protected static final int ADJUST_LAYOUT = 4;
	public static EditText mEtc;
	private static Context context = null;
	private SearchInterface inter = null;
	private static LatinKeyboardView keyboardView;
	private static LatinKeyboard k0;// 维文小写
	private static LatinKeyboard k1;// 维文大写
	private static LatinKeyboard k2;// 英文小写
	private static LatinKeyboard k3;// 英文大写
	private static LatinKeyboard k4;// 维文数字和符号
	private static LatinKeyboard k5;// 英文符号
	private static LatinKeyboard k6;// 符号小写
	private static LatinKeyboard k7;// 符号大写
	// public static Typeface myTypeface;
	public boolean isnun = false;// 是否数据键盘
	public boolean isupper = false;// 是否大写
	public int pressedCode;
	public int INPUT_TYPE = 0;
	public int TARGET_INPUT_TYPE = 0;
	public int CURRENT_INPUT_TYPE = 0;
	public static final int INPUT_UYGHUR_SMALL = 0;// 维文小写
	public static final int INPUT_UYGHUR_LARGE = 1;// 维文大写
	public static final int INPUT_ENGLISH_SMALL = 2;// 英文小写
	public static final int INPUT_ENGLISH_LARGE = 3;// 英文大写
	public static final int INPUT_NUMBER = 4;// 数字
	public static final int INPUT_CHINA = 5;// 中文
	public static final int INPUT_SYMBOL = 6;// 符号小写
	public static final int INPUT_SYMBOL_LARGE = 7;// 符号大写
	public static final int ENTER_TYPE_SEARCH = 1001;// 搜索
	public static final int ENTER_TYPE_RETURN = 1002;// 换行
	private int enter_type = ENTER_TYPE_SEARCH;// 默认
	private boolean isUyLarge = false;
	private static int ADJUST_ORDER = 0;
	private static final int ADJUST_ORDER_PUSH = 1;
	private static final int ADJUST_ORDER_PULL = 2;

	/**
	 * 系统键盘正处于显示状态
	 */
	public static boolean showingSystemKeyboard = false;
	/**
	 *
	 */
	private static int deltaY = 0;
	/**
	 * 切换语言面板
	 */
	private static RelativeLayout switchLanguageBarView;
	private static TextView switch2Ch;
	private static TextView switch2Ug;
	private static ImageView hideInputMethod;
	/**
	 * keyboard布局
	 */
	private static View mainView;
	public interface SearchInterface {
		public void ActivitySearch(); // 搜索按钮事件
	}

	public void setChSelectedState() {
		switch2Ch.setSelected(true);
		switch2Ug.setSelected(false);
	}

	public void setUgSelectedState() {
		switch2Ch.setSelected(false);
		switch2Ug.setSelected(true);
	}

	public KeyboardUtil(final Activity ctx,
			final EditText edit,View mainView) {
		if (Global.typeface == null) {
			Global.typeface = Typeface.createFromAsset(ctx.getAssets(),
					"Alp_ekran.ttf");
		}
		// myTypeface=Global.typeface;
		KeyboardUtil.context = ctx.getApplicationContext();
		KeyboardUtil.mEtc = edit;
		// 键盘布局文件的初始化
		k0 = new LatinKeyboard(ctx, R.xml.uyghur_keyboard);
		k1 = new LatinKeyboard(ctx, R.xml.uyghur_large_keyboard);
		k2 = new LatinKeyboard(ctx, R.xml.english_keyboard);
		k3 = new LatinKeyboard(ctx, R.xml.english_large_keyboard);
		k4 = new LatinKeyboard(ctx, R.xml.uyghur_number_keyboard);
		k5 = new LatinKeyboard(ctx, R.xml.english_number_keyboard);
		mEtc.setOnFocusChangeListener(focusChangeListener);
		init(mainView,ctx);

	}



	private Activity mActivity;
	public void init(View mainView,Activity activity) {
		mActivity = activity;
		keyboardView = (LatinKeyboardView) activity.findViewById(R.id.uykeyboard_view);
		keyboardView.setEnabled(true);
		keyboardView.setPreviewEnabled(true);
		keyboardView.setOnKeyboardActionListener(listener);
		KeyboardUtil.mainView = mainView;
		findView();
		setListener();
		keyboardView.setKeyboard(k0);
		INPUT_TYPE = INPUT_UYGHUR_SMALL;
	}
	public static void redraw() {
		if (mainView != null && isShowing()) {
			mainView.invalidate();
			mainView.requestLayout();
		}
	}

	public void hideSystemKeyboard() {
		mHandler.sendEmptyMessage(HIDE_SYSTEM_KEYBOARD);
	}
	public void restartInputMethod() {
		mHandler.sendEmptyMessage(RESTART_INPUT_METHOD_SERVER);
	}
	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HIDE_SYSTEM_KEYBOARD: {
				// mEtc.setInputType(InputType.TYPE_NULL);
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEtc.getWindowToken(), 0);
			}
				break;
			case RESTART_INPUT_METHOD_SERVER: {
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.restartInput(mEtc);

			}
				break;
			case SWITCH_TO_SYSTEM_KEYBOARD: {
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			}
				break;
			case ADJUST_LAYOUT:
				break;

			default:
				break;
			}

		};

	};

	private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				//showingSystemKeyboard = false;
				showAllKeyboard();
				hideSystemKeyboard();
				Log.e("sss","-*-*-*-*-*-*");
			}
			else{
				hideCustomKeyboard();
				Log.e("sss", "*********************");
			}
		}
	};

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {

		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		// 当键盘按钮被松开时触发
		@Override
		public void onRelease(int primaryCode) {
		}

		// 当键盘按钮被按下时触发
		@Override
		public void onPress(int primaryCode) {
			if (primaryCode == -5)
				keyboardView.setPreviewEnabled(false);
			else
				keyboardView.setPreviewEnabled(true);
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			// keyCode == KeyEvent.KEYCODE_BACK
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
				// Enter键处理
				if (enter_type == ENTER_TYPE_SEARCH) {
					if (KeyboardUtil.this.inter != null) {
						SearchInterface activity = KeyboardUtil.this.inter;
						activity.ActivitySearch();
					}
					hideCustomKeyboard();
				} else {
					int index = mEtc.getSelectionStart();
					Editable editable = mEtc.getText();
					editable.insert(index, "\n");
				}
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				Editable et = mEtc.getText();
				int m = mEtc.getSelectionStart();
				if (mEtc.getText() != null) {
					if (mEtc.getSelectionStart() == 0
							&& mEtc.getText().toString().length() != 0)
						mEtc.setSelection(1);
					if (mEtc.getSelectionStart() > 0) {
						int index = mEtc.getSelectionStart();
						Editable editable = mEtc.getText();
						editable.delete(index - 1, index);

					}

				}

			} else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换

				onDownShift();

			} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 语言切换

				changeKey();

			} else if (primaryCode == 57419) { // go left

				int start = mEtc.getSelectionStart();
				if (start > 0) {
					mEtc.setSelection(start - 1);
				}

			} else if (primaryCode == 57421) { // go right

				int start = mEtc.getSelectionStart();
				if (start < mEtc.length()) {
					mEtc.setSelection(start + 1);
				}
			}

			else if (primaryCode == 1549) {

				switch (INPUT_TYPE) {

				case INPUT_UYGHUR_SMALL:
					CURRENT_INPUT_TYPE = INPUT_UYGHUR_SMALL;
					TARGET_INPUT_TYPE = INPUT_NUMBER;
					INPUT_TYPE = INPUT_NUMBER;
					// 转换英文小写
					keyboardView.setKeyboard(k4);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
					break;
				case INPUT_UYGHUR_LARGE:
					CURRENT_INPUT_TYPE = INPUT_UYGHUR_LARGE;
					TARGET_INPUT_TYPE = INPUT_NUMBER;
					INPUT_TYPE = INPUT_NUMBER;
					// 转换英文小写
					keyboardView.setKeyboard(k4);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
					break;
				case INPUT_ENGLISH_SMALL:
					CURRENT_INPUT_TYPE = INPUT_ENGLISH_SMALL;
					TARGET_INPUT_TYPE = INPUT_SYMBOL;
					INPUT_TYPE = INPUT_SYMBOL;
					keyboardView.setKeyboard(k5);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
					break;

				case INPUT_ENGLISH_LARGE:

					CURRENT_INPUT_TYPE = INPUT_ENGLISH_LARGE;
					TARGET_INPUT_TYPE = INPUT_SYMBOL;
					INPUT_TYPE = INPUT_SYMBOL;
					keyboardView.setKeyboard(k5);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
					break;

				case INPUT_SYMBOL:
					CURRENT_INPUT_TYPE = INPUT_SYMBOL;
					TARGET_INPUT_TYPE = INPUT_ENGLISH_SMALL;
					INPUT_TYPE = INPUT_ENGLISH_SMALL;
					keyboardView.setKeyboard(k2);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
					break;
				// 维语符号和数字
				case INPUT_NUMBER:
					CURRENT_INPUT_TYPE = INPUT_UYGHUR_SMALL;
					INPUT_TYPE = INPUT_UYGHUR_SMALL;
					TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
					keyboardView.setKeyboard(k0);
					setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
				}

			} else {
				if (isUyLarge) {
					int index = mEtc.getSelectionStart();
					Editable editable = mEtc.getText();
					editable.insert(index,
							Character.toString((char) primaryCode));
					keyboardView.setKeyboard(k0);
					TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
				} else {
					{
						int index = mEtc.getSelectionStart();
						Editable editable = mEtc.getText();

						editable.insert(index,
								Character.toString((char) primaryCode));
					}

				}

			}
		}

	};

	/**
	 * 设置输入法Enter键的行为
	 *
	 * @param enterType
	 */
	public void setEnterType(int enterType) {
		if (enterType == ENTER_TYPE_RETURN || enterType == ENTER_TYPE_SEARCH) {
			enter_type = enterType;
		} else {
			enter_type = ENTER_TYPE_SEARCH;
		}
	}

	/**
	 * 点击Shift 键的处理 根据TARGET_INPUT_TYPE 来判断
	 */
	private void onDownShift() {

		if (TARGET_INPUT_TYPE == INPUT_UYGHUR_SMALL) {
			TARGET_INPUT_TYPE = INPUT_UYGHUR_LARGE;
			keyboardView.setKeyboard(k1);
			isUyLarge = true;
			LatinKeyboardView.sShiftState = true;
		} else if (TARGET_INPUT_TYPE == INPUT_UYGHUR_LARGE) {
			TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
			keyboardView.setKeyboard(k0);
			isUyLarge = false;
			LatinKeyboardView.sShiftState = false;
		} else if (TARGET_INPUT_TYPE == INPUT_ENGLISH_SMALL) {
			TARGET_INPUT_TYPE = INPUT_ENGLISH_LARGE;
			keyboardView.setKeyboard(k3);
			isUyLarge = false;
			LatinKeyboardView.sShiftState = true;
		} else if (TARGET_INPUT_TYPE == INPUT_ENGLISH_LARGE) {
			TARGET_INPUT_TYPE = INPUT_ENGLISH_SMALL;
			keyboardView.setKeyboard(k2);
			isUyLarge = false;
			LatinKeyboardView.sShiftState = false;
		} else if (TARGET_INPUT_TYPE == INPUT_SYMBOL) {

		} else if (TARGET_INPUT_TYPE == INPUT_SYMBOL_LARGE) {

		}

	}

	private void changeKey() {

		switch (INPUT_TYPE) {
		case INPUT_UYGHUR_SMALL:
			keyboardView.setKeyboard(k2);
			TARGET_INPUT_TYPE = INPUT_ENGLISH_SMALL;
			INPUT_TYPE = INPUT_ENGLISH_SMALL;
			setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
			isUyLarge = false;
			break;
		case INPUT_UYGHUR_LARGE:
			// 转换英文小写
			keyboardView.setKeyboard(k2);
			TARGET_INPUT_TYPE = INPUT_ENGLISH_SMALL;
			INPUT_TYPE = INPUT_ENGLISH_SMALL;
			setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
			isUyLarge = false;
			break;

		case INPUT_ENGLISH_SMALL:// -------------------
			keyboardView.setKeyboard(k0);
			TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
			INPUT_TYPE = INPUT_UYGHUR_SMALL;
			setEnterType(KeyboardUtil.ENTER_TYPE_SEARCH);
			isUyLarge = false;
			break;
		case INPUT_ENGLISH_LARGE:
			keyboardView.setKeyboard(k0);
			TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
			INPUT_TYPE = INPUT_UYGHUR_SMALL;
			setEnterType(KeyboardUtil.ENTER_TYPE_SEARCH);
			isUyLarge = false;
			break;

		case INPUT_NUMBER:
			if (CURRENT_INPUT_TYPE == INPUT_UYGHUR_SMALL) {
				TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
				INPUT_TYPE = INPUT_UYGHUR_SMALL;
				setEnterType(KeyboardUtil.ENTER_TYPE_SEARCH);
				keyboardView.setKeyboard(k0);
				isUyLarge = false;
			} else if (CURRENT_INPUT_TYPE == INPUT_UYGHUR_LARGE) {
				TARGET_INPUT_TYPE = INPUT_UYGHUR_LARGE;
				INPUT_TYPE = INPUT_UYGHUR_LARGE;
				setEnterType(KeyboardUtil.ENTER_TYPE_SEARCH);
				keyboardView.setKeyboard(k1);
				isUyLarge = true;
			} else if (CURRENT_INPUT_TYPE == INPUT_ENGLISH_SMALL) {
				keyboardView.setKeyboard(k2);
				TARGET_INPUT_TYPE = INPUT_ENGLISH_SMALL;
				INPUT_TYPE = INPUT_ENGLISH_SMALL;
				setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
				isUyLarge = false;
			} else if (CURRENT_INPUT_TYPE == INPUT_ENGLISH_LARGE) {
				keyboardView.setKeyboard(k3);
				TARGET_INPUT_TYPE = INPUT_ENGLISH_LARGE;
				INPUT_TYPE = INPUT_ENGLISH_LARGE;
				setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
				isUyLarge = false;
			} else if (CURRENT_INPUT_TYPE == INPUT_SYMBOL) {
				keyboardView.setKeyboard(k6);
				TARGET_INPUT_TYPE = INPUT_SYMBOL;
				INPUT_TYPE = INPUT_SYMBOL;
				setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
				isUyLarge = false;
			} else if (CURRENT_INPUT_TYPE == INPUT_SYMBOL_LARGE) {
				keyboardView.setKeyboard(k7);
				TARGET_INPUT_TYPE = INPUT_SYMBOL_LARGE;
				INPUT_TYPE = INPUT_SYMBOL_LARGE;
				setEnterType(KeyboardUtil.ENTER_TYPE_RETURN);
				isUyLarge = false;
			} else {
				// TARGET_INPUT_TYPE = INPUT_UYGHUR_SMALL;
				// INPUT_TYPE = INPUT_UYGHUR_SMALL;
				// keyboardView.setKeyboard(k0);
			}

			break;
		case INPUT_SYMBOL:

			break;
		case INPUT_SYMBOL_LARGE:
			break;
		default:
			break;
		}
	}
	/**
	 * 显示整个键盘包括切换栏和键盘区域
	 */
	@SuppressLint("NewApi")
	public void showAllKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
		switchLanguageBarView.setVisibility(View.VISIBLE);
		keyboardView.setVisibility(View.VISIBLE);
		/* 动画效果 */
		Animation animFadeIn = (AnimationUtils.loadAnimation(context, R.anim.keyboard_slide_in_bottom));
		mainView.startAnimation(animFadeIn);
		animFadeIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				ADJUST_ORDER = ADJUST_ORDER_PUSH;
				mHandler.sendEmptyMessage(ADJUST_LAYOUT);
			}
		});
		}
	}
	/**
	 * 显示自定义软键盘不包括切换栏
	 *
	 */
	@SuppressLint("NewApi")
	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
		}
		deltaY = 0;
		if (keyboardView.getVisibility() == View.VISIBLE) {
			mHandler.sendEmptyMessage(HIDE_SYSTEM_KEYBOARD);
			return;
		}
		// mEtc.setGravity(Gravity.RIGHT);
		keyboardView.setVisibility(View.VISIBLE);
		/* 动画效果 */
		Animation animFadeIn = (AnimationUtils.loadAnimation(context,
				R.anim.animation_fade_in));
		animFadeIn.setDuration(50);
		mainView.startAnimation(animFadeIn);
		animFadeIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				ADJUST_ORDER = ADJUST_ORDER_PUSH;
				mHandler.sendEmptyMessage(ADJUST_LAYOUT);
			}
		});
	}

	/**
	 * 隐藏自定义键盘,不隐藏切换栏
	 *
	 */
	public void hideCustomKeyboard() {
		ADJUST_ORDER = ADJUST_ORDER_PULL;
		keyboardView.setVisibility(View.GONE);
	}

	/**
	 * 隐藏整个键盘（包括切换栏）
	 *
	 * @return
	 */
	public static void destroyKeyboard() {
		if (keyboardView != null && switchLanguageBarView != null
				&& switchLanguageBarView.getVisibility() == View.VISIBLE) {
			ADJUST_ORDER = ADJUST_ORDER_PULL;
			keyboardView.setVisibility(View.GONE);
			switchLanguageBarView.setVisibility(View.GONE);
		}

	}

	public static boolean isShowing() {

		if (switchLanguageBarView != null) {
			if (switchLanguageBarView.getVisibility() == View.GONE
					|| switchLanguageBarView.getVisibility() == View.INVISIBLE) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean isShowSystemKeyboard() {
		return showingSystemKeyboard;
	}

	public void setShowSystemKeyboard(boolean showSystemKeyboard) {
		this.showingSystemKeyboard = showSystemKeyboard;
	}

	private void findView() {
		switchLanguageBarView = (RelativeLayout) mActivity.findViewById(R.id.rl_ChageLanguage);
		switch2Ch = (TextView) mActivity.findViewById(R.id.buttonChange_CH);
		switch2Ug = (TextView) mActivity.findViewById(R.id.buttonChange_UG);
		hideInputMethod = (ImageView) mActivity.findViewById(R.id.buttonChange_hide);

	}

	@SuppressLint("NewApi")
	private void setListener() {
		mEtc.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				if (bottom - top != oldBottom - oldTop) {
					ADJUST_ORDER = ADJUST_ORDER_PUSH;
				}

			}

		});
		mEtc.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		hideInputMethod.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// hideSystemKeyboard();
				// destroyKeyboard();
				hideCustomKeyboard();
				hideSystemKeyboard();
				setShowSystemKeyboard(false);
				switchLanguageBarView.setVisibility(View.INVISIBLE);
			}

		});

		switch2Ug.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// hide system keyboard
				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEtc.getWindowToken(), 0);
				setShowSystemKeyboard(false);
				setUgSelectedState();
				showKeyboard();
				switchLanguageBarView.setVisibility(View.VISIBLE);

			}

		});
		switch2Ch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setChSelectedState();
				setShowSystemKeyboard(true);
				hideCustomKeyboard();
				mHandler.sendEmptyMessage(SWITCH_TO_SYSTEM_KEYBOARD);
				switchLanguageBarView.setVisibility(View.VISIBLE);

			}

		});
	}




}
