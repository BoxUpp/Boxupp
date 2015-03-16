package com.boxupp.db.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "local_git_repo_details")
public class LocalGitRepoBean {
	
	public String getLocalRepoPath() {
		return localRepoPath;
	}

	public void setLocalRepoPath(String localRepoPath) {
		this.localRepoPath = localRepoPath;
	}

	@DatabaseField(canBeNull = false, generatedId = true, useGetSet = true)
	private Integer localGitRepoID;
	
	@DatabaseField(useGetSet = true)
	private String remoteRepoURI;
	
	@DatabaseField(useGetSet = true)
	private String localRepoPath;
	
	@DatabaseField(useGetSet = true)
	private  String password;

	@DatabaseField(useGetSet = true)
	private String branch;

	@DatabaseField(useGetSet = true)
	private String path;

	@DatabaseField(useGetSet = true)
	private String comment;

	
	public Integer getLocalGitRepoID() {
		return localGitRepoID;
	}

	public void setLocalGitRepoID(Integer localGitRepoID) {
		this.localGitRepoID = localGitRepoID;
	}

	public String getRemoteRepoURI() {
		return remoteRepoURI;
	}

	public void setRemoteRepoURI(String remoteRepoURI) {
		this.remoteRepoURI = remoteRepoURI;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	
}
