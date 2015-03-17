package com.boxupp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.boxupp.db.DAOProvider;
import com.boxupp.db.beans.AvailableBoxesBean;
import com.boxupp.db.beans.MachineConfigurationBean;
import com.boxupp.db.beans.ProjectBean;

public class AvailableBoxesDAOManager {

	public static Dao<AvailableBoxesBean, Integer> availableBoxesDao = null;
	public static AvailableBoxesDAOManager boxDBManagaer;
	private PreparedQuery<AvailableBoxesBean> queryForAvailableboxes = null;

	public static AvailableBoxesDAOManager getInstance() {
		if (boxDBManagaer == null) {
			boxDBManagaer = new AvailableBoxesDAOManager();
		}
		return boxDBManagaer;
	}

	private AvailableBoxesDAOManager(){
		availableBoxesDao = DAOProvider.getInstance().fetchAvailableBoxesDao();
	}

	public void create(AvailableBoxesBean boxesBean){
		try {
			availableBoxesDao.create(boxesBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public <E> List<E> retireveAvailableBoxes() {
		List<AvailableBoxesBean> availableBoxesList= new ArrayList<AvailableBoxesBean>();
		if (queryForAvailableboxes == null) {
			queryForAvailableboxes =  makeQueryForAvailableboxes();
		}
		try {
			availableBoxesList = availableBoxesDao.query(queryForAvailableboxes);
		} catch (SQLException e) {
			return null;
		}

		if(availableBoxesList == null) 
			return null;
		return (List<E>)availableBoxesList;
	}

	private PreparedQuery<AvailableBoxesBean> makeQueryForAvailableboxes() {		
		try {
			QueryBuilder<AvailableBoxesBean, Integer> queryBuilder =
					availableBoxesDao.queryBuilder();
			return queryBuilder.prepare();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getAvailableBoxesCount(){
		try {
			return availableBoxesDao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}