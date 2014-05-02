package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.adapters.CanvasItemAdapter;

public class CanvasItemListFragment extends Fragment {

	public static final String ARG_SECTION_NAME = "section_name";
	
	LinearLayout rootView;
	private Activity activity;
	private ListView itemsListView;
	private String category;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = (LinearLayout) inflater.inflate(R.layout.item_list, container, false);

		if(getArguments() != null) {
			String categorySent = getArguments().getString(ARG_SECTION_NAME);
		
			if(categorySent != null) {
				category = categorySent;
			}
		}
		
		List<CanvasItem> listCanvasItems = getListCanvasItem();
		CanvasItemAdapter adapter = new CanvasItemAdapter(activity, R.layout.canvas_item, listCanvasItems);
		
		itemsListView = (ListView) rootView.findViewById(R.id.item_list_view);
		itemsListView.setAdapter(adapter);
		
		return rootView;
	}

	private List<CanvasItem> getListCanvasItem() {
		
		HashMap<String, List<CanvasItem>> canvasItems = CanvasItemsManager.getCanvasItemsInstance(activity);
		
		List<CanvasItem> items = canvasItems.get(category);
		
		if(items == null) {
			items = new ArrayList<CanvasItem>();
		}
		
		return items;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
}
