package com.ciklumtask.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ciklumtask.CustomApplication;
import com.ciklumtask.networking.ApiClient;
import com.ciklumtask.networking.Item;
import com.ciklumtask.networking.ParsedModel;
import com.ciklumtask.networking.ParsedModelImpl;
import com.ciklumtask.util.DebugLogger;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

public class PageLoader extends AsyncTaskLoader<ParsedModel> {

    private static final String TAG = PageLoader.class.getSimpleName();
    private static final int INITIAL_PAGE = 0;
    private static final int INVALID = -1;

    @Inject
    public ApiClient mApiClient;

    private final ConcurrentLinkedQueue<Integer> mPagesLinkedQueue;
    private final CacheData mCacheData;

    public PageLoader(Context context) {
        super(context);
        CustomApplication.getAppComponent().inject(this);
        mPagesLinkedQueue = new ConcurrentLinkedQueue<>();
        mPagesLinkedQueue.add(INITIAL_PAGE);
        mCacheData = new CacheData();
    }

    @Override
    public ParsedModel loadInBackground() {
        DebugLogger.d(TAG, "loadInBackground");
        Integer page = mPagesLinkedQueue.poll();
        if (page != null) {
            return mApiClient.call(page);
        } else {
            return new ParsedModelImpl(INVALID, Collections.<Item>emptyList(), false);
        }
    }

    @Override
    public void deliverResult(ParsedModel data) {
        mCacheData.setData(data);
        super.deliverResult(mCacheData.getParsedModel());
    }

    @Override
    protected void onStartLoading() {
        DebugLogger.d(TAG, "start loading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        DebugLogger.d(TAG, "stop loading");
        cancelLoad();
    }

    @Override
    protected void onReset() {
        DebugLogger.d(TAG, "reset");
        stopLoading();
    }

    public void loadPage(int page) {
        DebugLogger.d(TAG, "load page: " + page);
        if (!mPagesLinkedQueue.contains(page)) {
            mPagesLinkedQueue.add(page);
            forceLoad();
        }
    }
}
