package edu.pku.gg.gosplash.search.view;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import edu.pku.gg.gosplash.R;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.ui.GoToast;
import edu.pku.gg.gosplash.common.utils.TypefaceUtils;
import edu.pku.gg.gosplash.photo.adapter.PhotoAdapter;
import edu.pku.gg.gosplash.search.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ISearchView,AppBarLayout.OnOffsetChangedListener{
    
    private Toolbar mToolbar;
    private MaterialSearchView searchView;
    private TextView searchQuery;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Button backBtn;

    private SearchPresenter mSearchPresenter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private AVLoadingIndicatorView loadingView;
    private List<Photo> photos = new ArrayList<>();

    private int page = 0;
    private String query;
    private PhotoAdapter mPhotosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent=getIntent();

        query = intent.getStringExtra("query");

        init();

    }

    @Override
    public void onStart(){
        super.onStart();

        if(mSearchPresenter == null) {
            mSearchPresenter = new SearchPresenter(this);
            mSearchPresenter.start();
            loadData(query);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        appBarLayout.addOnOffsetChangedListener(this);

        if(mSearchPresenter == null) {
            mSearchPresenter = new SearchPresenter(this);
        }

        if(photos.size() != 0){
            loadingView.hide();
            swipeToLoadLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void init(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 0;
                photos.clear();
                mSearchPresenter.start();
                searchQuery.setText(query.toUpperCase());
                loadData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                backBtn.setVisibility(View.INVISIBLE);
                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
            }

            @Override
            public void onSearchViewClosed() {
                backBtn.setVisibility(View.VISIBLE);
                //Do some magic
            }
        });


        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);

        backBtn = (Button)findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchQuery = (TextView)findViewById(R.id.search_query);
        TypefaceUtils.setTypeface(this, searchQuery);
        searchQuery.setText(query.toUpperCase());

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        mEmptyLayout = (RelativeLayout)findViewById(R.id.empty_layout);
        mEmptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmptyLayout.setVisibility(View.INVISIBLE);
                loadingView.show();
                loadData(query);
            }
        });

        loadingView = (AVLoadingIndicatorView)findViewById(R.id.avi);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                photos.clear();
                loadData(query);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData(query);
            }
        });

        initAdapter();

    }


    @Override
    public void initAdapter(){
        mPhotosAdapter = new PhotoAdapter(this,photos);
        mRecyclerView.setAdapter(mPhotosAdapter);
    }

    private void loadData(String query){
        if(!this.query.equals(query)){
            this.query = query;
        }
        mSearchPresenter.searchPhotos(query, page + 1);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        swipeToLoadLayout.setRefreshEnabled(i == 0);
    }


    @Override
    public void setPresenter(@NonNull SearchPresenter presenter){
        mSearchPresenter = presenter;
    }

    @Override
    public void showLoading(){
        loadingView.show();
    }

    @Override
    public void loadingSuccess(List<Photo> list){
        photos.addAll(list);
        page++;
        mPhotosAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.hide();
                swipeToLoadLayout.setVisibility(View.VISIBLE);
            }
        },1000);
    }

    @Override
    public void loadingFailed(){
        loadingView.hide();
        if(photos.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        }else{
            ++page;
            if(page == 1){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                },100);
                GoToast.makeText(this, GoToast.ToastType.REFRESH_FAILED, Toast.LENGTH_SHORT).show();
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                },100);
                GoToast.makeText(this, GoToast.ToastType.LOADMORE_FAILED, Toast.LENGTH_SHORT).show();
            }
        }

    }



    @Override
    public void loadingMoreSuccess(List<Photo> list) {
        loadingView.hide();
        swipeToLoadLayout.setVisibility(View.VISIBLE);
        if (list.size() == 0) {
            GoToast.makeText(this, GoToast.ToastType.NOMORE, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }, 100);
        } else {
            page++;
            if (page == 1) {
                photos.clear();
                photos.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 100);
                GoToast.makeText(this, GoToast.ToastType.REFRESH_SUCCESS, Toast.LENGTH_SHORT).show();
                mPhotosAdapter.notifyDataSetChanged();
            } else {
                photos.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 100);
                GoToast.makeText(this, GoToast.ToastType.LOADMORE_SUCCESS, Toast.LENGTH_SHORT).show();
                mPhotosAdapter.notifyItemRangeChanged(photos.size() - list.size(), photos.size());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


}
