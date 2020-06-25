package erqal.mylibrary.pulltorefreshlistviews;

import android.database.DataSetObserver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import erqal.mylibrary.R;

public class ListBaseAdapter<T> extends BaseAdapter {
	public static final int STATE_EMPTY_ITEM = 0;
	public static final int STATE_LOAD_MORE = 1;
	public static final int STATE_NO_MORE = 2;
	public static final int STATE_NO_DATA = 3;
	public static final int STATE_LESS_ONE_PAGE = 4;
	public static final int STATE_NETWORK_ERROR = 5;
	public static final int STATE_OTHER = 6;
	public int state = STATE_LOAD_MORE;
	private int itemLength = 0;

	private final static String ERQAL_CAREER = "erqal_career";

	protected int _loadmoreText;
	protected int _loadFinishText;
	protected int _noDateText;
	private FooterView holder = new FooterView();

	private void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	protected ArrayList<T> mDatas;

	public ListBaseAdapter(ArrayList<T> list) {
		if (list != null) {
			mDatas = list;
			itemLength = list.size();
		} else {
			Log.e(ERQAL_CAREER, "list！=null");
		}
		_loadmoreText = R.string.loading;
		_loadFinishText = R.string.loading_no_more;
		_noDateText = R.string.error_view_no_data;
	}

	@Override
	public int getCount() {
		switch (getState()) {
			case STATE_EMPTY_ITEM:
				return getDataSize();
			case STATE_NETWORK_ERROR:
			case STATE_LOAD_MORE:
				return getDataSize();
			case STATE_NO_DATA:
				return 1;
			case STATE_NO_MORE:
				return getDataSize();
			case STATE_LESS_ONE_PAGE:
				return getDataSize();
			default:
				break;
		}
		return getDataSize();
	}

	public int getDataSize() {
		if (mDatas != null)
			return mDatas.size();
		return 0;
	}

	@Override
	public T getItem(int arg0) {
		if (mDatas != null) {
			if (mDatas.size() > arg0) {
				return mDatas.get(arg0);
			}
		}

		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void setData(ArrayList<T> data) {
		mDatas = data;

	}

	public ArrayList<T> getData() {
		return mDatas;
	}

	public void clear() {
		mDatas.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return null;
	}


	private View mFooterView;

	public View getFooterView() {
		return this.mFooterView;
	}

	protected void setText(TextView textView, String text, boolean needGone) {
		if (text == null || TextUtils.isEmpty(text)) {
			if (needGone) {
				textView.setVisibility(View.GONE);
			}
		} else {
			textView.setText(text);
		}
	}

	protected void setText(TextView textView, String text) {
		setText(textView, text, false);
	}

	public void setmFooterView(View mFooterView) {
		if (mFooterView != null) {
			holder.progress = (ProgressBar) mFooterView.findViewById(R.id.progressbar);
			holder.text = (TextView) mFooterView.findViewById(R.id.text);
		}
		this.mFooterView = mFooterView;
	}

	public void setFooterView(int state) {
		if (this.state == state)
			return;
		this.state=state;
		switch (this.state) {
			case STATE_LOAD_MORE:
				mFooterView.setVisibility(View.VISIBLE);
				holder.progress.setVisibility(View.VISIBLE);
				holder.text.setVisibility(View.VISIBLE);
				holder.text.setText(_loadmoreText);
				break;
			case STATE_NO_MORE:
				mFooterView.setVisibility(View.VISIBLE);
				holder.progress.setVisibility(View.GONE);
				holder.text.setVisibility(View.VISIBLE);
				holder.text.setText(_loadFinishText);
				break;
			case STATE_EMPTY_ITEM:
				holder.progress.setVisibility(View.GONE);
				mFooterView.setVisibility(View.VISIBLE);
				holder.text.setText(_noDateText);
				break;
			case STATE_NETWORK_ERROR:
				mFooterView.setVisibility(View.VISIBLE);
				holder.progress.setVisibility(View.GONE);
				holder.text.setVisibility(View.VISIBLE);
				holder.text.setText(R.string.error_network);
				break;
			case STATE_LESS_ONE_PAGE:
				mFooterView.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	private static class FooterView {
		TextView text;
		ProgressBar progress;
	}

	@Override
	public void notifyDataSetChanged() {
		if (mDatas != null) {
			if (state == STATE_NO_MORE) {
				if (itemLength > mDatas.size())
					this.setFooterView(STATE_OTHER);
			}
			itemLength = mDatas.size();
		}
		super.notifyDataSetChanged();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerDataSetObserver(observer);
		observer.onChanged();
	}
}
