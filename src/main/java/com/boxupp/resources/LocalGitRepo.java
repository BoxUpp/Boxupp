package com.boxupp.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;

import com.boxupp.responseBeans.StatusBean;
import com.boxupp.utilities.LocalRepoUtilities;

@Path("/localGitRepo/")
public class LocalGitRepo {

	@GET
	@Path("/getBranches")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getBranchList(@Context HttpServletRequest request) {
		return LocalRepoUtilities.getInstance().fetchBranches(request);
	}
	
	@POST
	@Path("/commmit")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public StatusBean commitOnRemoteRepo(JsonNode param) {
		return LocalRepoUtilities.getInstance().commitOnRemoteRepo(param);
	}
}
