package com.boxupp.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import com.boxupp.db.DAOProvider;
import com.boxupp.db.beans.GitRepoBean;
import com.boxupp.db.beans.LocalGitRepoBean;
import com.boxupp.responseBeans.StatusBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;

public class LocalGitRepoDAOManager implements DAOImplInterface{
	private static Logger logger = LogManager.getLogger(LocalGitRepoDAOManager.class.getName());
	protected static Dao<LocalGitRepoBean, Integer> localGitRepoDao = null;
	private static LocalGitRepoDAOManager localGitRepoManager = null;
	
	public static LocalGitRepoDAOManager getInstance(){
		if(localGitRepoManager == null){
			localGitRepoManager = new LocalGitRepoDAOManager();
		}
		return localGitRepoManager;
		
	}
	
	private  LocalGitRepoDAOManager() {
		localGitRepoDao = (Dao<LocalGitRepoBean, Integer>) DAOProvider.getInstance().fetchDao(LocalGitRepoBean.class);
		
	}

	@Override
	public StatusBean create(JsonNode newData) {
		LocalGitRepoBean localGitRepoBean  = null;
		Gson gitRepoData = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss").create();
		localGitRepoBean = gitRepoData.fromJson(newData.toString(), LocalGitRepoBean.class);
		StatusBean statusBean = new StatusBean();
		try {
			localGitRepoDao.create(localGitRepoBean);
		} catch (SQLException e) {
			logger.error("Error saving a Local Git Repo  : " + e.getMessage());
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error saving a Local Git repo : "+e.getMessage());
			e.printStackTrace();
		}
		statusBean.setStatusCode(0);
		statusBean.setData(localGitRepoBean);
		statusBean.setStatusMessage("Local Git Repo saved successfully");
		return statusBean;
	}

	

	@Override
	public StatusBean update(JsonNode updatedData) {

		LocalGitRepoBean localGitRepoBean  = null;
		Gson gitRepoData = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss").create();
		localGitRepoBean = gitRepoData.fromJson(updatedData.toString(), LocalGitRepoBean.class);
		StatusBean statusBean = new StatusBean();
		
		try {
			localGitRepoDao.update(localGitRepoBean);
			statusBean.setStatusCode(0);
		} catch (SQLException e) {
			statusBean.setStatusMessage("Error updating Local  git Repo : "+e.getMessage());
			statusBean.setStatusCode(1);
		}
		statusBean.setStatusMessage("Local Git Repo updated successfully");
		return statusBean;
	}

	@Override
	public StatusBean delete(String gitRepoID) {
		StatusBean statusBean = new StatusBean();
		try {
			localGitRepoDao.deleteById(Integer.parseInt(gitRepoID));
			statusBean.setStatusCode(0);
			statusBean.setStatusMessage("Local Git Repo data deleted successfully");
		} catch (NumberFormatException e) {
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error in deleting Local git repo data : "+e.getMessage());
		} catch (SQLException e) {
			statusBean.setStatusCode(1);
			statusBean.setStatusMessage("Error in deleting Local git repo data : "+e.getMessage());
		}
		return statusBean;
	}
	

	@Override
	public<T>T read(String localGitRepoID) {
		LocalGitRepoBean localGitRepo = null;
		try{
			localGitRepo = localGitRepoDao.queryForId(Integer.parseInt(localGitRepoID));
		}catch(SQLException e){
			logger.error("Error querying the Local git repo from DB : " + e.getMessage());
		}
		return (T) localGitRepo;
	}
	

}
