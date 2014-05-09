package com.dtorralbo.bmca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.appspot.businessmodelcanvasapp.bmca.Bmca;
import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.appspot.businessmodelcanvasapp.bmca.model.CollectionResponseCanvasItem;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

public class CanvasItemsManager {
	
	private static CanvasItemsManager managerSingleton = null; 
	
	private static Bmca service = null; 
	
	private static HashMap<String, List<CanvasItem>> canvasItemsSingleton = null;
	
	public synchronized static CanvasItemsManager getInstance(Context context) {
		if(service == null) {
			Bmca.Builder builder = new Bmca.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			service = builder.build();
		}
		
		if(managerSingleton == null) {
			managerSingleton = new CanvasItemsManager();
		}
		
		return managerSingleton;
	}
	
	/* PUBLIC SERVICES */
	public HashMap<String, List<CanvasItem>> getCanvasItemsInstance(){
		if(canvasItemsSingleton == null) {
			canvasItemsSingleton = new HashMap<String, List<CanvasItem>>();
		}
		
		return canvasItemsSingleton;
	}
	
	
	public void listCanvasItems(OnCanvasItemsListedListener onCanvasItemsListedListener) {
		if(canvasItemsSingleton == null) {
			ListCanvasItemTask task = new ListCanvasItemTask(onCanvasItemsListedListener);
			task.execute();
		} else {
			onCanvasItemsListedListener.onCanvasItemsListed(canvasItemsSingleton);
		}
	}
	
	public void forceListCanvasItems(OnCanvasItemsListedListener onCanvasItemsListedListener) {
		ListCanvasItemTask task = new ListCanvasItemTask(onCanvasItemsListedListener);
		task.execute();
	}
	
	public void addCanvasItem(CanvasItem item, OnCanvasItemAddedListener onCanvasItemAddedListener) {
		AddCanvasItemTask task = new AddCanvasItemTask(onCanvasItemAddedListener);
		task.execute(item);
	}
	
	public void updateCanvasItem(CanvasItem item, OnCanvasItemUpdatedListener onCanvasItemUpdatedListener) {
		UpdateCanvasItemTask task = new UpdateCanvasItemTask(onCanvasItemUpdatedListener);
		task.execute(item);
	}
	
	public void deleteCanvasItem(Long id, OnCanvasItemDeletedListener onCanvasItemDeletedListener) {
		DeleteCanvasItemTask task = new DeleteCanvasItemTask(onCanvasItemDeletedListener);
		task.execute(id);
	}
	
	
	/* ASYNCHRONOUS TASK */
	
	private class ListCanvasItemTask extends AsyncTask<Void, Void, HashMap<String, List<CanvasItem>>> {

		OnCanvasItemsListedListener onCanvasItemsListedListener = null;
		
		public ListCanvasItemTask(OnCanvasItemsListedListener onCanvasItemsListedListener) {
			this.onCanvasItemsListedListener = onCanvasItemsListedListener;
		}
		
		@Override
		protected HashMap<String, List<CanvasItem>> doInBackground(Void... params) {
			HashMap<String, List<CanvasItem>> result = null;
			
			try {
				CollectionResponseCanvasItem itemsCollection = service.item().list().execute();
				
				List<CanvasItem> items = itemsCollection.getItems();
				
				result = getItemsMap(items);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(HashMap<String, List<CanvasItem>> result) {
			canvasItemsSingleton = result;
			this.onCanvasItemsListedListener.onCanvasItemsListed(canvasItemsSingleton);
		}
	}
	
	private class AddCanvasItemTask extends AsyncTask<CanvasItem, Void, HashMap<String, List<CanvasItem>>> {

		OnCanvasItemAddedListener onCanvasItemAddedListener = null;
		
		public AddCanvasItemTask(OnCanvasItemAddedListener onCanvasItemAddedListener) {
			this.onCanvasItemAddedListener = onCanvasItemAddedListener;
		}

		@Override
		protected HashMap<String, List<CanvasItem>> doInBackground(CanvasItem... canvasItems) {
			HashMap<String, List<CanvasItem>> result = null;
			
			try {
			
				for(CanvasItem item : canvasItems) {
					service.item().add(item).execute();
				}
				
				CollectionResponseCanvasItem itemsCollection = service.item().list().execute();
				
				List<CanvasItem> items = itemsCollection.getItems();
				
				result = getItemsMap(items);
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(HashMap<String, List<CanvasItem>> result) {
			canvasItemsSingleton = result;
			this.onCanvasItemAddedListener.onCanvasItemAdded(canvasItemsSingleton);
		}
	}
	
	private class UpdateCanvasItemTask extends AsyncTask<CanvasItem, Void, HashMap<String, List<CanvasItem>>> {

		OnCanvasItemUpdatedListener onCanvasItemUpdatedListener = null;
		
		public UpdateCanvasItemTask(OnCanvasItemUpdatedListener onCanvasItemUpdatedListener) {
			this.onCanvasItemUpdatedListener = onCanvasItemUpdatedListener;
		}
		
		@Override
		protected HashMap<String, List<CanvasItem>> doInBackground(CanvasItem... canvasItems) {
			HashMap<String, List<CanvasItem>> result = null;
			
			try {
			
				for(CanvasItem item : canvasItems) {
					service.item().update(item).execute();
				}
				
				CollectionResponseCanvasItem itemsCollection = service.item().list().execute();
				
				List<CanvasItem> items = itemsCollection.getItems();
				
				result = getItemsMap(items);
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(HashMap<String, List<CanvasItem>> result) {
			canvasItemsSingleton = result;
			this.onCanvasItemUpdatedListener.onCanvasItemUpdated(canvasItemsSingleton);
		}
	}
	
	private class DeleteCanvasItemTask extends AsyncTask<Long, Void, HashMap<String, List<CanvasItem>>> {

		OnCanvasItemDeletedListener onCanvasItemDeletedListener = null;
		
		public DeleteCanvasItemTask(OnCanvasItemDeletedListener onCanvasItemDeletedListener) {
			this.onCanvasItemDeletedListener = onCanvasItemDeletedListener;
		}
		
		@Override
		protected HashMap<String, List<CanvasItem>> doInBackground(Long... ids) {
			HashMap<String, List<CanvasItem>> result = null;
			
			try {
			
				for(Long id : ids) {
					service.item().delete(id).execute();
				}
				
				CollectionResponseCanvasItem itemsCollection = service.item().list().execute();
				
				List<CanvasItem> items = itemsCollection.getItems();
				
				result = getItemsMap(items);
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(HashMap<String, List<CanvasItem>> result) {
			canvasItemsSingleton = result;
			this.onCanvasItemDeletedListener.onCanvasItemDeleted(canvasItemsSingleton);
		}
	}
	
	
	private HashMap<String, List<CanvasItem>> getItemsMap(List<CanvasItem> items) {

		HashMap<String, List<CanvasItem>> itemsMap = new HashMap<String, List<CanvasItem>>();
		
		if(items != null) {
			for(CanvasItem item : items) {
				
				String category = item.getCategory();
				
				List<CanvasItem> list = itemsMap.get(category);
				
				if(list == null) {
					list = new ArrayList<CanvasItem>();
				}
				
				list.add(item);
				
				itemsMap.put(category, list);
			}
		}
		
		return itemsMap;
	}
	
	
	/* INTERFACES FOR CALLBACKS */
	public interface OnCanvasItemsListedListener {
		void onCanvasItemsListed(HashMap<String, List<CanvasItem>> canvasItems);
	}
	
	public interface OnCanvasItemAddedListener {
		void onCanvasItemAdded(HashMap<String, List<CanvasItem>> canvasItems);
	}
	
	public interface OnCanvasItemUpdatedListener {
		void onCanvasItemUpdated(HashMap<String, List<CanvasItem>> canvasItems);
	}
	
	public interface OnCanvasItemDeletedListener {
		void onCanvasItemDeleted(HashMap<String, List<CanvasItem>> canvasItems);
	}
}
