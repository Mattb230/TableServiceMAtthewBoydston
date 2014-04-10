package edu.tableservice.data;

import java.util.ArrayList;

/**
 * Represents a Check for a table, and contains a list of items
 * that have been ordered
 * 
 */
public class Check {
	
	private long id;
	private String tableName;
	private ArrayList<MenuItem> mItems = new ArrayList<MenuItem>();
	private boolean hasBeenSigned = false;
	
	public static class MenuItem {
		
		public String name;
		public Amount cost;
		
		public MenuItem(String itemDescription, double cost) {
			this.name = itemDescription;
			this.cost = new Amount(cost);
		}
	}

	public Check(long id, String tableName) {
		this.id = id;
		this.tableName = tableName;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		// The ArrayAdapter uses toString to get the text to display in the list item
		// We override toString here to display the table name
		return tableName;
	}
	
	public String getTableItems() {
		String line = "";
		for (MenuItem item : mItems) {
			line += (item.name + ". ");
		}
		return line;
	}

	public void addItem(String itemDescription, double cost) {
		mItems.add(new MenuItem(itemDescription, cost));
		
	}
	
	public String getTableName() {
		return tableName;
	}

	public Amount getSubtotal() {
		double total = 0;
		for (MenuItem item : mItems) {
			total += item.cost.getRawValue();
		}
		
		return new Amount(total);
	}
	
	public void markAsSigned() {
		hasBeenSigned = true;
	}
	
	public int getItemCount() {
		return mItems.size();
	}
	
	public MenuItem getMenuItemAt(int index) {
		return mItems.get(index);
	}
}