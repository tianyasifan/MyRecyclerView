package com.example.txt.myrecyclerview;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by txt on 2015/11/11.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private List<Integer> mDatas;
    private List<Integer> heights;
    private int currentPosition;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }

    public interface OnItemSelectListener{
        void onItemSelect(View view,int position);
    }

    private OnItemClickListener mListener;
    private OnItemSelectListener mSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener listener){
        mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public GalleryAdapter(Context context,List<Integer> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        getRandomHeight(mDatas.size());
    }

    public void setDatas(List datas){
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rv, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
        holder.mTxt = (TextView)view.findViewById(R.id.id_index_gallery_item_text);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = heights.get(position%mDatas.size());
        holder.itemView.setLayoutParams(params);
        holder.mImg.setImageResource(mDatas.get(position % mDatas.size()));
        holder.mTxt.setText(""+position);

        holder.itemView.setFocusable(true);
        holder.itemView.setTag(position);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("adapter", "hasfocus:" + position + "--" + hasFocus);
                if(hasFocus){
                    currentPosition = (int)holder.itemView.getTag();
                    mSelectListener.onItemSelect(holder.itemView,currentPosition);
                }
            }
        });
        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(v,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return mDatas.size();
    }


    private void getRandomHeight(int size){
        heights = new ArrayList<>();
        for(int i=0;i<size;i++){
            heights.add((int)(200+Math.random()*400));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImg;
        TextView mTxt;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
