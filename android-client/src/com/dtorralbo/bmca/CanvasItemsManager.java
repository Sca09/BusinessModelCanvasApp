package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;

public class CanvasItemsManager {

	private static HashMap<String, List<CanvasItem>> canvasItemsSingleton = null;
	
	public synchronized static HashMap<String, List<CanvasItem>> getCanvasItemsInstance(Context context) {
		
		if(canvasItemsSingleton == null) {
			canvasItemsSingleton = getCanvasItems(context);
		}
		
		return canvasItemsSingleton;
	}

	
	public static HashMap<String, List<CanvasItem>> getCanvasItems(Context context) {
		
		HashMap<String, List<CanvasItem>> canvasItemsSingleton = new HashMap<String, List<CanvasItem>>();
		
		List<CanvasItem> keyPartnersList = new ArrayList<CanvasItem>();
		
		CanvasItem item1 = new CanvasItem();
		item1.setId(1L);
		item1.setTitle("title1");
		item1.setDescription("Description1");
		item1.setCategory(Category.KEY_PARTNERS.getName());
		item1.setAuthor("davidtorralbo@gmail.com");
		keyPartnersList.add(item1);
		
		CanvasItem item2 = new CanvasItem();
		item2.setId(2L);
		item2.setTitle("title2");
		item2.setDescription("Description2");
		item2.setCategory(Category.KEY_PARTNERS.getName());
		item2.setAuthor("davidtorralbo@gmail.com");
		keyPartnersList.add(item2);
		
		
		List<CanvasItem> keyActivitiesList = new ArrayList<CanvasItem>();
		
		CanvasItem item3 = new CanvasItem();
		item3.setId(3L);
		item3.setTitle("title3");
		item3.setDescription("Lorem ipsum dolor sit amet, sollicitudin massa lobortis sed a mus quisque, ac neque, ante euismod nascetur aliquam ornare sagittis risus.");
		item3.setCategory(Category.KEY_ACTIVITIES.getName());
		item3.setAuthor("davidtorralbo@gmail.com");
		keyActivitiesList.add(item3);
		
		canvasItemsSingleton.put(Category.KEY_PARTNERS.getName(), keyPartnersList);
		canvasItemsSingleton.put(Category.KEY_ACTIVITIES.getName(), keyActivitiesList);
		
		return canvasItemsSingleton;
	}
	
	public static HashMap<String, List<CanvasItem>> addCanvasItem(CanvasItem canvasItem) {
		String canvasItemCategory = canvasItem.getCategory();
		List<CanvasItem> listCanvasItem = canvasItemsSingleton.get(canvasItemCategory);
		if(listCanvasItem == null) {
			listCanvasItem = new ArrayList<CanvasItem>();
		}
		
		//TODO creating fake id for testing
		if(canvasItem.getId() == null) {
			Long id = new Random().nextLong();
			canvasItem.setId(id);
		}
		//TODO creating fake id for testing
		
		listCanvasItem.add(canvasItem);
		
		canvasItemsSingleton.put(canvasItemCategory, listCanvasItem);
		
		return canvasItemsSingleton;
	}
	
	public static HashMap<String, List<CanvasItem>> updateCanvasItem(CanvasItem canvasItem) {
		Collection<List<CanvasItem>> values = canvasItemsSingleton.values();
		
		for(List<CanvasItem> list : values) {
			if(list != null) {
				for(CanvasItem item : list) {
					if(item.getId().equals(canvasItem.getId())){
						list.remove(item);
						break;
					}
				}			
			}
		}
		
		String canvasItemCategory = canvasItem.getCategory();
		List<CanvasItem> listCanvasItem = canvasItemsSingleton.get(canvasItemCategory);
		if(listCanvasItem == null) {
			listCanvasItem = new ArrayList<CanvasItem>();
		}
		
		//TODO creating fake id for testing
		if(canvasItem.getId() == null) {
			Long id = new Random().nextLong();
			canvasItem.setId(id);
		}
		//TODO creating fake id for testing
		
		listCanvasItem.add(canvasItem);
		
		canvasItemsSingleton.put(canvasItemCategory, listCanvasItem);
		
		return canvasItemsSingleton;
	}
	
	public static HashMap<String, List<CanvasItem>> deleteCanvasItem(Long canvasItemId) {
		Collection<List<CanvasItem>> values = canvasItemsSingleton.values();
		
		for(List<CanvasItem> list : values) {
			if(list != null) {
				for(CanvasItem item : list) {
					if(item.getId().equals(canvasItemId)){
						list.remove(item);
						break;
					}
				}			
			}
		}
		
		return canvasItemsSingleton;
	} 
	
}
