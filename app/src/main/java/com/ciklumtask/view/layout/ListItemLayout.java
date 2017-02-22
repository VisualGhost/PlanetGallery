package com.ciklumtask.view.layout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.ciklumtask.R;
import com.ciklumtask.util.DebugLogger;
import com.ciklumtask.view.PaginationListener;
import com.ciklumtask.view.adapter.PageAdapter;


public class ListItemLayout extends RelativeLayout {

    private static final String TAG = ListItemLayout.class.getSimpleName();
    private static final int THRESHOLD = 1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private PaginationListener mPaginationListener;

    private int mCurrentPage;
    private boolean mIsPageNotFound;
    private boolean mIsWaitingItems;

    public ListItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.list_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initScrollListener();
    }

    private void initScrollListener() {
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    if (isNeedMoreItems() && !mIsWaitingItems) {
                        mIsWaitingItems = true;
                        DebugLogger.d(TAG, "Load next page");
                        if (mPaginationListener != null) {
                            mPaginationListener.loadPage(mCurrentPage);
                        }
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private boolean isNeedMoreItems() {
        int totalItemCount = mLayoutManager.getItemCount();
        int pastVisibleItems = mLayoutManager.findLastVisibleItemPosition();

        return !mIsPageNotFound && pastVisibleItems >= (totalItemCount - THRESHOLD);
    }

    public void setPaginationListener(PaginationListener paginationListener) {
        mPaginationListener = paginationListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DebugLogger.d(TAG, "onDetachedFromWindow");
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mPaginationListener = null;
    }

    public void setAdapter(PageAdapter pageAdapter) {
        mRecyclerView.setAdapter(pageAdapter);
    }

    public void setLoadedPage(int page) {
        boolean isUpToDateData = mCurrentPage <= page;
        DebugLogger.d(TAG, "old page: " + mCurrentPage + ", current page: " + page);
        mIsWaitingItems = !isUpToDateData;
        mCurrentPage = page;
    }

    public void setPageNotFound(boolean isPageNotFound) {
        mIsPageNotFound = isPageNotFound;
    }
}
