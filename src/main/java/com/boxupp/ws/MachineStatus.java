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
package com.boxupp.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.boxupp.responseBeans.VagrantStatus;
import com.boxupp.utilities.Utilities;
import com.boxupp.vagrant.VagrantCommandProcessor;
import com.google.gson.Gson;

@WebSocket(maxIdleTime=1500000)
public class MachineStatus implements OutputConsole{
	
	private static Logger logger = LogManager.getLogger(MachineStatus.class.getName());
	private RemoteEndpoint remote;
	private static Gson gson = new Gson();
	
	@OnWebSocketConnect
    public void onConnect(Session session) {
		logger.info(session.getRemote()+ " : connected");
		this.remote = session.getRemote();
    }

	@OnWebSocketMessage
    public void onMessage(Session session,String command) throws IOException, InterruptedException {
		Integer userID = Integer.parseInt(command);
        VagrantCommandProcessor shellProcessor = new VagrantCommandProcessor();
//		String location = Utilities.getInstance().fetchActiveProjectDirectory(userID);
        String location = Utilities.getInstance().fetchActiveProjectDirectory(userID);
       
//			shellProcessor.executeVagrantFile(location,command,userID, this);
	       	 List<VagrantStatus> statusList = shellProcessor.checkAllMachineStatus(location);
        	// remote.sendString(gson.toJson(statusList));
   
    }
	
	@OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.info("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        logger.error("Error: " + t.getMessage());
    }

	@Override
	public void pushOutput(String data) {
		
		
	}

	@Override
	public void pushError(String data) {
		
		
	}

	@Override
	public void pushDataTermination() {
	}
	
	public void commitOutput(List<VagrantStatus> statusList){
		try{
			remote.sendString(gson.toJson(statusList));
		}
		catch(IOException e){
			logger.error("Error committing output to console : "+e.getMessage());
		}
	}

	@Override
	public void pustOutPut(List<VagrantStatus>statusList) {
		
		commitOutput(statusList);
		
	}

}
