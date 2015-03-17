package com.boxupp.db.beans;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "availableBoxesList")
public class AvailableBoxesBean  {
	
	@DatabaseField(useGetSet = true,dataType = DataType.LONG_STRING,columnDefinition = "VARCHAR(700) DEFAULT 'text' NOT NULL")
	private String boxName;

	@DatabaseField(useGetSet = true)
	private String boxProvider;
	
	@DatabaseField(useGetSet = true,unique=true,dataType = DataType.LONG_STRING,columnDefinition = "VARCHAR(700) DEFAULT 'text' NOT NULL")
	private String boxUrl;
	
	@DatabaseField(useGetSet = true)
	private String boxSize;
		
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public String getBoxProvider() {
		return boxProvider;
	}
	public void setBoxProvider(String boxProvider) {
		this.boxProvider = boxProvider;
	}
	public String getBoxUrl() {
		return boxUrl;
	}
	public void setBoxUrl(String boxUrl) {
		this.boxUrl = boxUrl;
	}
	public String getBoxSize() {
		return boxSize;
	}
	public void setBoxSize(String boxSize) {
		this.boxSize = boxSize;
	}	
}
