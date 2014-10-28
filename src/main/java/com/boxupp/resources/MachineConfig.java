package com.boxupp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;

import com.boxupp.dao.MachineConfigDAOManager;
import com.boxupp.db.beans.MachineConfigurationBean;
import com.boxupp.responseBeans.StatusBean;
@Path("/machineConfig/")
public class MachineConfig {
	@POST 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public StatusBean saveMachineConfiguration(JsonNode mappings) {
		return MachineConfigDAOManager.getInstance().create(mappings);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MachineConfigurationBean getMachineConfig(@PathParam("id") String machineId) {
		return MachineConfigDAOManager.getInstance().read(machineId);
	}
	
	@POST 
	@Path("/updateMachineConfig")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public StatusBean updateMachineConfiguration( JsonNode updatedmachineConfigData) {
		return MachineConfigDAOManager.getInstance().update(updatedmachineConfigData);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public StatusBean deleteMachineConfiguration(@PathParam("id") String machineId) {
		return MachineConfigDAOManager.getInstance().delete(machineId);
	}
	
	
}
