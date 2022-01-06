package com.example.pinnedheaderlistview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pinnedheaderlistview.R;

/**
 * 基本功能：左侧Adapter
 */
public class LeftListAdapter extends BaseAdapter {
    private String[] leftStr;
    boolean[] flagArray;
    private Context context;

    public LeftListAdapter(Context context, String[] leftStr, boolean[] flagArray) {
        this.leftStr = leftStr;
        this.context = context;
        this.flagArray = flagArray;
    }

    @Override
    public int getCount() {
        return leftStr.length;
    }

    @Override
    public Object getItem(int arg0) {
        return leftStr[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.left_list_item, null);
            holder.left_list_item = arg1.findViewById(R.id.left_list_item);
            holder.ll = arg1.findViewById(R.id.ll);
            holder.tv_bg = (TextView) arg1.findViewById(R.id.tv_bg);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        holder.updataView(arg0);
        return arg1;
    }

    private class Holder {
        private TextView left_list_item;
        private LinearLayout ll;
        private TextView tv_bg;

        public void updataView(final int position) {
            left_list_item.setText(leftStr[position]);
            if (flagArray[position]) {
                ll.setBackgroundColor(Color.WHITE);//点击的设置为白色
                left_list_item.setTextColor(left_list_item.getResources().getColor(R.color.red));//字体颜色橘色
                tv_bg.setVisibility(View.VISIBLE);//左侧背景显示
            } else {
                ll.setBackgroundColor(Color.TRANSPARENT);//没点击的设置为透明
                left_list_item.setTextColor(Color.BLACK);//字体颜色黑色
                tv_bg.setVisibility(View.GONE);//左侧背景隐藏
            }
        }

    }
}
