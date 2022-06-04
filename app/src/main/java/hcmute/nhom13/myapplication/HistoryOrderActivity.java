package hcmute.nhom13.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import hcmute.nhom13.myapplication.adapter.ViewPagerAdapter;

public class HistoryOrderActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager2 mViewPage;
    ArrayList<String> tabLayoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        tabLayoutList =new ArrayList<>();
        tabLayoutList.add("All");
        tabLayoutList.add("Done");
        tabLayoutList.add("Draft");
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPage);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        mViewPage.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(mTabLayout, mViewPage,
                (tab, position) -> tab.setText(tabLayoutList.get(position))
        ).attach();
    }
}