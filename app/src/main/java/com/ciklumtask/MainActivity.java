package com.ciklumtask;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.ciklumtask.loader.PageLoader;
import com.ciklumtask.networking.ParsedModel;
import com.ciklumtask.util.DebugLogger;
import com.ciklumtask.view.PaginationListener;
import com.ciklumtask.view.adapter.PageAdapter;
import com.ciklumtask.view.layout.ListItemLayout;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ParsedModel>,
        PaginationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PAGE_LOADER_ID = 1;

    private PageAdapter mPageAdapter;
    private ListItemLayout mListItemLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPageAdapter = new PageAdapter();
        mListItemLayout = (ListItemLayout) findViewById(R.id.list_item_layout_id);
        mListItemLayout.setAdapter(mPageAdapter);
        mListItemLayout.setPaginationListener(this);
        getSupportLoaderManager().initLoader(PAGE_LOADER_ID, null, this);
    }

    @Override
    public Loader<ParsedModel> onCreateLoader(int id, Bundle args) {
        return new PageLoader(getApplication());
    }

    @Override
    public void onLoadFinished(Loader<ParsedModel> loader, ParsedModel data) {
        DebugLogger.d(TAG, "Is 404: " + data.isPageNotFound() + ", items: " + data.getItems().size());
        mPageAdapter.setItemList(data.getItems());
        mListItemLayout.setLoadedPage(data.getPage());
        mListItemLayout.setPageNotFound(data.isPageNotFound());
    }

    @Override
    public void onLoaderReset(Loader<ParsedModel> loader) {
        // empty
    }

    @Override
    public void loadPage(int page) {
        Loader<ParsedModel> loader = getSupportLoaderManager().getLoader(PAGE_LOADER_ID);
        PageLoader pageLoader = (PageLoader) loader;
        pageLoader.loadPage(page);
    }
}
