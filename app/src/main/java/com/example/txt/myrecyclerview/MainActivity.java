package com.example.txt.myrecyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private CustomRecycleView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        mRecyclerView = (CustomRecycleView) findViewById(R.id.id_recyclerview_horizontal);
        mAdapter = new GalleryAdapter(this,mDatas);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置布局管理器
        //瀑布流式
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        //线性布局
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                mRecyclerView.checkAutoAdjust(position);
                mRecyclerView.smoothToCenter(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mDatas.remove(position);
//                mAdapter.setDatas(mDatas);
                mAdapter.notifyItemRemoved(position);
            }
        });

        mAdapter.setOnItemSelectListener(new GalleryAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
//                linearLayoutManager.scrollToPositionWithOffset(position,350);
                mRecyclerView.smoothToCenter(position);
            }
        });
        mRecyclerView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("abc", "hasfocus:" + hasFocus);
                if (hasFocus) {
                    if (mRecyclerView.getChildCount() > 0) {
                        linearLayoutManager.scrollToPositionWithOffset(0, 0);
                        mRecyclerView.getChildAt(0).requestFocus();
                    }
                }
            }
        });

        /*mRecyclerView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("abc","onKey-----:"+keyCode);
                if(keyCode==22){
                    mRecyclerView.scrollBy(100, 0);
//                    mRecyclerView.getChildAt(2).requestFocus();
//                    mRecyclerView.scrollToPosition(linearLayoutManager.findFirstVisibleItemPosition()+1);
                }else if(keyCode==21){

                }
                return false;
            }
        });*///item获得焦点后，rv不能响应OnKeyListener事件了
    }

    private void initDatas()
    {
        mDatas = new ArrayList<Integer>(Arrays.asList(R.mipmap.osd_blue_hl,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.osd_hd_hl));
    }
}
