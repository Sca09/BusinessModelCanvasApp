package com.dtorralbo.bmca;

public enum Category {

	KEY_PARTNERS (0, "Key Partners"),
	KEY_ACTIVITIES (1, "Key Activities"),
	KEY_RESOURCES (2, "Key Resources"),
	VALUE_PROPOSITIONS (3, "Value Propositions"),
	CUSTOMER_RELATIONSHIPS (4, "Customer Relationships"),
	CHANNELS (5, "Channels"),
	CUSTOMER_SEGMENTS (6, "Customer Segments"),
	COST_STRUCTURE (7, "Cost Structure"),
	REVENUE_STREAMS (8, "Revenue Streams");
	
	private int index;
	private String name;
	
	Category(int index, String name){
		this.setIndex(index);
		this.setName(name);
	}

	public static Category getCategory(int index) {
		for (Category category : Category.values()) {
			if (category.getIndex() == index) {
				return category;
			}
		}
		
		return null;
	}
	
	public static Category getCategory(String name) {
		for (Category category : Category.values()) {
			if (category.getName().equalsIgnoreCase(name)) {
				return category;
			}
		}
		
		return null;
	}
	
	public static int getCount() {
		return Category.values().length;
	}
	
	public static String getNameByIndex(int index) {
		return Category.getCategory(index).getName();
	}
	
	public static int getIndexByName(String name) {
		return Category.getCategory(name).getIndex();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
