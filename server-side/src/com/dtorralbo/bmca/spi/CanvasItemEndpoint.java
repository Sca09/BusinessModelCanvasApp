package com.dtorralbo.bmca.spi;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.dtorralbo.bmca.CanvasItem;
import com.dtorralbo.bmca.PMF;
import com.dtorralbo.bmca.channel.BoardUpdateService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@Api(name = "bmca",
	version = "v1")
public class CanvasItemEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "Item.list")
	public CollectionResponse<CanvasItem> listCanvasItem(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<CanvasItem> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(CanvasItem.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<CanvasItem>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (CanvasItem obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<CanvasItem> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "Item.get")
	public CanvasItem getCanvasItem(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		CanvasItem canvasitem = null;
		try {
			canvasitem = mgr.getObjectById(CanvasItem.class, id);
		} finally {
			mgr.close();
		}
		return canvasitem;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param canvasitem the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "Item.add")
	public CanvasItem insertCanvasItem(CanvasItem canvasitem) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if(canvasitem.getId() != null) {
				if (containsCanvasItem(canvasitem)) {
					throw new EntityExistsException("Object already exists");
				}
			}
			mgr.makePersistent(canvasitem);
		} finally {
			mgr.close();
		}
		
		BoardUpdateService.addCanvasItemNotification(canvasitem.getId());
		
		return canvasitem;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param canvasitem the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "Item.update")
	public CanvasItem updateCanvasItem(CanvasItem canvasitem) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsCanvasItem(canvasitem)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(canvasitem);
		} finally {
			mgr.close();
		}
		
		BoardUpdateService.updateCanvasItemNotification(canvasitem.getId());
		
		return canvasitem;
	}
	
	/**
	 * This method is used for updating the category of an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param canvasitem the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "Item.changeCategory", httpMethod = HttpMethod.POST)
	public CanvasItem changeCanvasItemCategory(@Named("id") Long id, @Named("category") String newCategory) {
		PersistenceManager mgr = getPersistenceManager();
		CanvasItem canvasitem = null;
		try {
			canvasitem = mgr.getObjectById(CanvasItem.class, id);
			
			canvasitem.setCategory(newCategory);
			
			mgr.makePersistent(canvasitem);
		} finally {
			mgr.close();
		}
		return canvasitem;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "Item.delete")
	public void removeCanvasItem(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			CanvasItem canvasitem = mgr.getObjectById(CanvasItem.class, id);
			mgr.deletePersistent(canvasitem);
		} finally {
			mgr.close();
		}
		
		BoardUpdateService.deleteCanvasItemNotification(id);
	}

	private boolean containsCanvasItem(CanvasItem canvasitem) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(CanvasItem.class, canvasitem.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
}
