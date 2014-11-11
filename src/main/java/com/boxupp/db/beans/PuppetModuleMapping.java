package com.boxupp.db.beans;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "puppetModuleMapping")
public class PuppetModuleMapping {
	public final static String MODULE_ID_FIELD_NAME = "moduleID";
	public final static String PROJECT_ID_FIELD_NAME = "projectID";
	public final static String MACHINE_ID_FIELD_NAME = "machineID";

	public PuppetModuleMapping(	MachineConfigurationBean machineConfig,	PuppetModuleBean puppetModule, ProjectBean project) {
		this.machineConfig = machineConfig;
		this.puppetModule = puppetModule;
		this.project = project;
	}

	@DatabaseField(canBeNull = false, generatedId = true, useGetSet = true)
	private Integer ID;

	@DatabaseField(foreign = true, useGetSet= true, columnName = MACHINE_ID_FIELD_NAME, foreignAutoRefresh=true, foreignAutoCreate=true)
	private MachineConfigurationBean machineConfig;

	@DatabaseField(foreign = true, useGetSet= true, columnName = MODULE_ID_FIELD_NAME, foreignAutoRefresh=true, foreignAutoCreate=true)
	private PuppetModuleBean puppetModule;
	
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

	public PuppetModuleBean getPuppetModule() {
		return puppetModule;
	}

	public void setPuppetModule(PuppetModuleBean puppetModule) {
		this.puppetModule = puppetModule;
	}
	public ProjectBean getProject() {
		return project;
	}
	public void setProject(ProjectBean project) {
		this.project = project;
	}
	public PuppetModuleBean getPuppetModule() {
		return puppetModule;
	}

	public void setPuppetModule(PuppetModuleBean puppetModule) {
		this.puppetModule = puppetModule;
	}
	
	
}
