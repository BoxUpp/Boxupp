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
package com.boxupp.db;

import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boxupp.db.beans.ProjectBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class CreateDB {
	
	
	private static Logger logger = LogManager.getLogger(CreateDB.class.getName());
	
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		
			ConnectionSource connectionSource = new JdbcConnectionSource(DerbyConfig.JDBC_URL);
			Dao<ProjectBean, Integer> projectDao = DaoManager.createDao(connectionSource, ProjectBean.class);
//			ProjectBean bean = new ProjectBean("Trial","Akshay");
//			bean.setCreationTime(Date.valueOf("2014-09-17"));
//			projectDao.create(bean);
			connectionSource.close();
			TableUtils.createTable(connectionSource, ProjectBean.class);
		
	}
}
