package edu.pku.gg.gosplash.collection.view;

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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import edu.pku.gg.gosplash.R;
import edu.pku.gg.gosplash.collection.presenter.CollectionPresenter;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.ui.CircleImageView;
import edu.pku.gg.gosplash.common.ui.GoToast;
import edu.pku.gg.gosplash.common.utils.TypefaceUtils;
import edu.pku.gg.gosplash.photo.adapter.PhotoAdapter;

public class CollectionActivity extends AppCompatActivity implements ICollectionView,AppBarLayout.OnOffsetChangedListener{

    private Toolbar mToolbar;
    private TextView collectionTitle;
    private TextView collectionDescrption;
    private CircleImageView avatar;
    private TextView avatarInfo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    private CollectionPresenter mCollectionPresenter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private AVLoadingIndicatorView loadingView;
    private List<Photo> photos = new ArrayList<>();

    private int page = 0;
    private int id = 0;
    private String avatarUrl;
    private String description;
    private String title;
    private String userName;
    private boolean curated;

    private PhotoAdapter mPhotosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);

        Intent intent=getIntent();

        curated = intent.getBooleanExtra("curated",false);
        id = intent.getIntExtra("id", 0);
        avatarUrl = intent.getStringExtra("avatar");
        description = intent.getStringExtra("description");
        title = intent.getStringExtra("title");
        userName = intent.getStringExtra("user");

        init();

    }

    @Override
    public void onStart(){
        super.onStart();

        if(mCollectionPresenter == null) {
            mCollectionPresenter = new CollectionPresenter(this);
            mCollectionPresenter.start();
            loadData(curated);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        appBarLayout.addOnOffsetChangedListener(this);

        if(mCollectionPresenter == null) {
            mCollectionPresenter = new CollectionPresenter(this);
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

        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);

        collectionTitle = (TextView)findViewById(R.id.item_collection_title);
        collectionDescrption = (TextView)findViewById(R.id.item_collection_descrption);
        avatar = (CircleImageView)findViewById(R.id.avatar);
        avatarInfo = (TextView)findViewById(R.id.info);
        TypefaceUtils.setTypeface(this, avatarInfo);
        TypefaceUtils.setTypeface(this, collectionDescrption);

        collectionTitle.setText(title);
        collectionDescrption.setText(description);
        avatarInfo.setText("by "+userName);
        Glide.with(this).load(avatarUrl).crossFade().into(avatar);

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
                loadData(curated);
            }
        });

        loadingView = (AVLoadingIndicatorView)findViewById(R.id.avi);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData(curated);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData(curated);
            }
        });

        if (curated){
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }

        initAdapter();

    }


    @Override
    public void initAdapter(){
        mPhotosAdapter = new PhotoAdapter(this,photos);
        mRecyclerView.setAdapter(mPhotosAdapter);
    }

    private void loadData(boolean curated){
         if(curated){
             mCollectionPresenter.getCuratedCollectionPhotos(id, page + 1);
         }else{
             mCollectionPresenter.getCollectionPhotos(id, page + 1);
         }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        swipeToLoadLayout.setRefreshEnabled(i == 0);
    }


    @Override
    public void setPresenter(@NonNull CollectionPresenter presenter){
        mCollectionPresenter = presenter;
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

}
