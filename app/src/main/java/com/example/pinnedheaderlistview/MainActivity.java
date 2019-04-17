package com.example.pinnedheaderlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pinnedheaderlistview.adapter.LeftListAdapter;
import com.example.pinnedheaderlistview.adapter.MainSectionedAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isScroll = true;
    private LeftListAdapter adapter;
    private String[] leftStr = new String[]{"推荐", "火锅", "家常菜", "烧烤", "日本料理", "四川冒菜",
            "兰州拉面 "};
    private boolean[] flagArray = {true, false, false, false, false, false, false};
    private String[][] rightStr = new String[][]{{"狗不理包子1", "狗不理包子2", "狗不理包子3"},
            {"火锅1", "火锅2", "火锅3"},
            {"家常菜1", "家常菜2", "家常菜3"},
            {"BB霜", "洗面奶", "面膜"},
            {"佳洁士牙膏", "飘柔洗头膏", "立白洗衣液"},
            {"护手霜", "暖手宝", "耳暖"},
            {"帽子", "围脖", "手套"}
    };
    private ListView left_listview, pinnedListView;

    private boolean isShowOrNot = false;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowOrNot == false) {
                    left_listview.setVisibility(View.GONE);
                    iv.setBackgroundResource(R.mipmap.off_img);
                    isShowOrNot = true;
                } else {
                    left_listview.setVisibility(View.VISIBLE);
                    iv.setBackgroundResource(R.mipmap.on_img);
                    isShowOrNot = false;
                }
            }
        });

        pinnedListView = findViewById(R.id.pinnedListView);
        left_listview = findViewById(R.id.left_listview);
        final MainSectionedAdapter sectionedAdapter = new MainSectionedAdapter(MainActivity.this, leftStr, rightStr);
        pinnedListView.setAdapter(sectionedAdapter);
        adapter = new LeftListAdapter(MainActivity.this, leftStr, flagArray);
        left_listview.setAdapter(adapter);
        left_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                isScroll = false;

                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);

            }

        });


        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            left_listview.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            left_listview.setSelection(0);
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == left_listview.getLastVisiblePosition()) {
//                            z = z + 3;
                            left_listview.setSelection(z);
                        }
                        if (x == left_listview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            left_listview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            left_listview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
