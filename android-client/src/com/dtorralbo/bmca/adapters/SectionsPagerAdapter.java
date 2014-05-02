package com.dtorralbo.bmca.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dtorralbo.bmca.CanvasItemListFragment;
import com.dtorralbo.bmca.Category;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	public static final String ARG_SECTION_NAME = "section_name";
	
	private Context context;
	
	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new CanvasItemListFragment();
		Bundle args = new Bundle();
		args.putString(ARG_SECTION_NAME, Category.getNameByIndex(position));
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public int getItemPosition(Object object) {
		
		Fragment fragment = (Fragment) object;
		
		fragment.getActivity();
		
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return Category.getCount();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return Category.getNameByIndex(position);
	}
	
	public void notifyDataSetChanged(String currentTimeZone){
		
//		if(listTimeZones.indexOf(currentTimeZone) == 0) {
//			notifyDataSetChanged();
//		}
	}
}
