package com.boxupp.db.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "shellScriptMapping")
public class ShellScriptMapping {
	public final static String SCRIPT_ID_FIELD_NAME = "scriptID";
	public final static String PROJECT_ID_FIELD_NAME = "projectID";
	public final static String MACHINE_ID_FIELD_NAME = "machineID";
	public ShellScriptMapping(	MachineConfigurationBean machineConfig, ShellScriptBean script,
			ProjectBean project) {
		super();
		this.machineConfig = machineConfig;
		this.script = script;
		this.project = project;
	}
	
	public ShellScriptMapping(){
		
	}

	@DatabaseField(canBeNull = false, generatedId = true, useGetSet = true)
	private Integer ID;

	@DatabaseField(foreign = true, columnName =MACHINE_ID_FIELD_NAME, foreignAutoRefresh=true, foreignAutoCreate=true)
	private MachineConfigurationBean machineConfig;
	
	@DatabaseField(foreign = true, columnName = SCRIPT_ID_FIELD_NAME, foreignAutoRefresh=true, foreignAutoCreate=true)
	private ShellScriptBean script ;
	
	@DatabaseField(foreign = true, columnName =PROJECT_ID_FIELD_NAME, foreignAutoRefresh=true, foreignAutoCreate=true)
	private ProjectBean project;
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public MachineConfigurationBean getMachineConfig() {
		return machineConfig;
	}
	public void setMachineConfig(MachineConfigurationBean machineConfig) {
		this.machineConfig = machineConfig;
	}
	public ShellScriptBean getScript() {
		return script;
	}
	public void setScript(ShellScriptBean script) {
		this.script = script;
	}
	public ProjectBean getProject() {
		return project;
	}
	public void setProject(ProjectBean project) {
		this.project = project;
	}
}
