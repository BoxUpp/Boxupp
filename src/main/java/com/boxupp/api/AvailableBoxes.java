package com.boxupp.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.boxupp.addon.RetrieveBoxes;
import com.boxupp.dao.AvailableBoxesDAOManager;
import com.boxupp.db.beans.AvailableBoxesBean;

@Path("/availableBoxes/")

public class AvailableBoxes {
	
	@POST
	@Path("/getAvailableBoxesList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AvailableBoxesBean> getAvailableBoxes(){
			return RetrieveBoxes.getInstance().getAvailableBoxesList();
	}
	
}
