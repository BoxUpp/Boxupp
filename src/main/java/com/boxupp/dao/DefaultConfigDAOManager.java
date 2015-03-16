/*******************************************************************************
 *  Copyright 2014 Paxcel Technologies
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *******************************************************************************/
/*package com.boxupp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import com.boxupp.db.DAOProvider;
import com.boxupp.db.beans.DefaultConfigMapping;
import com.boxupp.responseBeans.StatusBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;

public class DefaultConfigDAOManager implements DAOImplInterface{
	private static Logger logger = LogManager.getLogger(DefaultConfigDAOManager.class.getName());
	protected static Dao<DefaultConfigurationBean, Integer> defaultConfigDao = null;
	protected static Dao<DefaultConfigMapping, Integer> defaultConfigMappingDao = null;
	private static DefaultConfigDAOManager defaultConfigDBManager = null;
	
	public static DefaultConfigDAOManager getInstance(){
		if(defaultConfigDBManager == null){
			defaultConfigDBManager = new DefaultConfigDAOManager();
		}
		return defaultConfigDBManager;
		
	}
	
	private DefaultConfigDAOManager(){
		defaultConfigDao = (Dao<DefaultConfigurationBean, Integer>) DAOProvider.getInstance().fetchDao(DefaultConfigurationBean.class);
		defaultConfigMappingDao = (Dao<DefaultConfigMapping, Integer>) DAOProvider.getInstance().fetchDao(DefaultConfigMapping.class);
		
	}

	@Override
	public StatusBean create(JsonNode newData) {
		DefaultConfigurationBean defaultConfigBean  = null;
		Gson defaultConfigData = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss").create();
		defaultConfigBean = defaultConfigData.fromJson(newData.toString(), DefaultConfigurationBean.class);
		StatusBean statusBean = new StatusBean();
		try {
			
			defaultConfigDao.create(defaultConfigBean);
		} catch (SQLException e) {
			logger.error("Error saving a default configuration : " + e.getMessage());
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error saving a default configuration : "+e.getMessage());
			e.printStackTrace();
		}
		statusBean.setStatusCode(0);
		statusBean.setStatusMessage("Default Configuration saved successfully");
		return statusBean;
	}

	
	@Override
	public <E> List<E> read(String id) {
		return null;
		
		
	}

	public <T> T readProviderData(String id) {
		DefaultConfigurationBean defaultConfig = null;
		try{
			defaultConfig = defaultConfigDao.queryForId(Integer.parseInt(id));
		}catch(SQLException e){
			logger.error("Error querying the default Configuration  from DB : " + e.getMessage());
		}
		return (T) defaultConfig;
		
	}

	@Override
	public StatusBean update(JsonNode updatedData) {
		DefaultConfigurationBean defaultConfigBean  = null;
		Gson defaultConfigData = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss").create();
		defaultConfigBean = defaultConfigData.fromJson(updatedData.toString(), DefaultConfigurationBean.class);
		StatusBean statusBean = new StatusBean();
		try {
			defaultConfigDao.update(defaultConfigBean);
		} catch (SQLException e) {
			logger.error("Error updating a default Configuration : " + e.getMessage());
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error updating a default configuration : "+e.getMessage());
			e.printStackTrace();
		}
		statusBean.setStatusCode(0);
		statusBean.setStatusMessage("default configuration updated successfully");
		return statusBean;
	}

	@Override
	public StatusBean delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
*/