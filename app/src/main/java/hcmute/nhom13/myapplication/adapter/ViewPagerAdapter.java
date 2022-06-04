package hcmute.nhom13.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.nhom13.myapplication.fragment.DraftFragment;
import hcmute.nhom13.myapplication.fragment.DoneFragment;
import hcmute.nhom13.myapplication.fragment.AllOrderFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AllOrderFragment();
            case 1:
                return new DoneFragment();
            case 2:
                return new DraftFragment();
            default:
                return new AllOrderFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }




}
