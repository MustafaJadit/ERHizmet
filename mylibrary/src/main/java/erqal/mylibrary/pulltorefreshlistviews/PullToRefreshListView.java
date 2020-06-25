package erqal.mylibrary.pulltorefreshlistviews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import erqal.mylibrary.R;


public class PullToRefreshListView<T> extends LinearLayout
        implements SwipeRefreshLayout.OnRefreshListener, OnScrollListener {

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态״̬
    public int mState = STATE_NONE;

    private ListView mListView;
    private ListBaseAdapter<T> mAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected EmptyLayout mErrorLayout;

    private RefreshListener refreshListener;
    private LoadMoreListener loadMoreListener;

    private View footerView;

    private boolean isLoadMoreEnabled = false;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    private OnTouchListener touchListener;

    public void setTouchDelegate(OnTouchListener listener) {
        this.touchListener = listener;
    }


    public void setPulltoRefreshEnabled(boolean enabled) {
        if (enabled) {
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     * 此函数必须setAdapter之前调用
     *
     * @param enabled
     */
    public void setLoadMoreEnabled(boolean enabled) {
        if (enabled) {
            if (footerView == null && mAdapter != null) {
                footerView = LayoutInflater.from(this.getContext())
                        .inflate(R.layout.swiperefresh_list_cell_footer, null);
                // footerView.setVisibility(View.INVISIBLE);
                mListView.addFooterView(footerView, null, false);
                mListView.setFooterDividersEnabled(false);
                mAdapter.setmFooterView(footerView);
            }

        }
        this.isLoadMoreEnabled = enabled;
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.swiperefresh_fragment_pull_refresh_listview, this);
        mListView = (ListView) viewGroup.findViewById(R.id.listview);

        mSwipeRefreshLayout = (SwipeRefreshLayout) viewGroup.findViewById(R.id.swiperefreshlayout);
        mErrorLayout = (EmptyLayout) viewGroup.findViewById(R.id.error_layout);
        mListView.setEmptyView(mErrorLayout);

        initView();
    }

    public void refresh() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }

    }

    public void initView() {
        mSwipeRefreshLayout.setEnabled(false);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black, R.color.red, R.color.black);

        mErrorLayout.setOnLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mSwipeRefreshLayout.isEnabled()) {
                    if (mErrorLayout.getErrorState() == EmptyLayout.NETWORK_ERROR) {
                        if (!checkNetwork()) {
                            return;
                        }
                    }
                    mState = STATE_REFRESH;
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    refreshListener.OnRefresh();
                }

            }
        });

        if (mAdapter != null) {

            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        }
        mListView.setOnScrollListener(this);


        mSwipeRefreshLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (touchListener != null) {
                    return touchListener.onTouch(v, event);
                }
                return false;
            }
        });
    }

    public void setFirstLoadingView() {
        if (mErrorLayout.getErrorState() == EmptyLayout.NETWORK_ERROR) {
            if (!checkNetwork()) {
                return;
            }
        }
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }

    public EmptyLayout getErrorLayout() {
        return mErrorLayout;
    }

    public void setDividerHeight(int height/* pix 为单位 */) {
        mListView.setDividerHeight(height);
    }

    public void setLoadingState() {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }

    @SuppressWarnings("unchecked")
    public void setAdapter(BaseAdapter adapter) {
        if (adapter != null) {
            this.mAdapter = (ListBaseAdapter<T>) adapter;
            if (isLoadMoreEnabled) {
                footerView = LayoutInflater.from(this.getContext()).inflate(R.layout.swiperefresh_list_cell_footer,
                        null);
                // footerView.setVisibility(View.INVISIBLE);
                mListView.addFooterView(footerView, null, false);
                mListView.setFooterDividersEnabled(false);
                mAdapter.setmFooterView(footerView);
            }
            mListView.setAdapter(adapter);
            if (mAdapter.getData().isEmpty()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }

    }

    public void setNoDataLayout(int type) {
        if (mErrorLayout.getErrorState() != type)
            mErrorLayout.setErrorType(type);
    }

    public void setNetworkErrorLayout() {
        if (mErrorLayout.getErrorState() != EmptyLayout.NETWORK_ERROR)
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
    }
    public void setLoadingLayout(){
        if (mErrorLayout.getErrorState() != EmptyLayout.NETWORK_LOADING)
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }


    public void executeOnRefreshError(String error) {
        // 检查网络
        if (checkNetwork()) {
            if (mAdapter.getDataSize() == 0) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }

        } else {

            if (mErrorLayout.getErrorState() == EmptyLayout.NETWORK_LOADING) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }
        }

        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
        if (mListView.getFooterViewsCount() == 0) {
            mListView.addFooterView(footerView);
        }

    }

    private boolean checkNetwork() {
        try {
            ConnectivityManager cwjManager = (ConnectivityManager) this.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cwjManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable())
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;

    }

    public ListView getRefreshableView() {
        return mListView;
    }

    public void setListView(ListView listView) {
        this.mListView = listView;
    }

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();

    }

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        if (mState == STATE_NONE || mState == STATE_NOMORE) {
            // 设置顶部正在刷新
            // mListView.setSelection(0);
            setSwipeRefreshLoadingState();
            mState = STATE_REFRESH;
            if (refreshListener != null) {
                refreshListener.OnRefresh();

            }
            mAdapter.setFooterView(ListBaseAdapter.STATE_OTHER);

        } else {
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(true);
            }

        }

    }

    public void setNeworkErrorState() {
        if (mAdapter.getState() != ListBaseAdapter.STATE_NETWORK_ERROR) {
            mAdapter.setFooterView(ListBaseAdapter.STATE_NETWORK_ERROR);
            mState = STATE_NONE;
        }
    }

    // 完成刷新
    public void executeOnRefreshSucceed() {
        this.mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
        mAdapter.setFooterView(ListBaseAdapter.STATE_OTHER);
    }

    /**
     * 设置顶部正在加载的状态
     */
    private void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    public void executeOnNoData() {
        this.mErrorLayout.setErrorType(EmptyLayout.NODATA);
        setSwipeRefreshLoadedState();
        mState = STATE_NOMORE;
        mAdapter.setFooterView(ListBaseAdapter.STATE_NO_DATA);
    }

    /**
     * 设置顶部加载完毕的状态
     */
    private void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     *
     */
    public void loadMoreEnded() {
        mState = STATE_NONE;
        mAdapter.setFooterView(ListBaseAdapter.STATE_OTHER);
    }

    /**
     *
     */
    public void noMoreData() {
        mState = STATE_NOMORE;
        mAdapter.setFooterView(ListBaseAdapter.STATE_NO_MORE);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;

        if (mAdapter == null || !isLoadMoreEnabled || mState != STATE_NONE)
            return;


        boolean scrollEnd = false;
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (mAdapter.getDataSize() != 0 && lastVisibleItem == totalItemCount)
            scrollEnd = true;
        if (scrollEnd && mAdapter.getState() != ListBaseAdapter.STATE_NO_MORE) {
            if (mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                if (!checkNetwork())
                    return;
            }
            mState = STATE_LOADMORE;
            mAdapter.setFooterView(ListBaseAdapter.STATE_LOAD_MORE);
            loadMoreListener.OnLoadMore();


        }


    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public LoadMoreListener getLoadMoreListener() {
        return loadMoreListener;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface RefreshListener {
        void OnRefresh();

    }

    public interface LoadMoreListener {
        void OnLoadMore();

    }

    public boolean isLoadingMore() {
        return mState == STATE_LOADMORE;
    }

    public void setState(int state) {
        mState = state;
    }


}
