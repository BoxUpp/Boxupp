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
package com.boxupp.init;

import java.awt.EventQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.boxupp.AppContextBuilder;
import com.boxupp.JettyServer;
import com.boxupp.ToolConfigurationReader;
import com.boxupp.beans.Config;
import com.boxupp.db.DBConnectionManager;
import com.boxupp.utilities.Utilities;
import com.boxupp.ws.VagrantConsole;

import com.boxupp.ws.MachineStatus;


public class Boxupp {
	
	private static Logger logger = LogManager.getLogger(Boxupp.class.getName());

	public static void main(String[] args) throws Exception {
		
		Utilities.getInstance().createRequiredFoldersIfNotExists();
		ToolConfigurationReader toolConfig = new ToolConfigurationReader();
		Config conf = toolConfig.getConfiguration();
		AppContextBuilder appContextBuilder = new AppContextBuilder();
		DBConnectionManager connectionManager = DBConnectionManager.getInstance();
		if(connectionManager != null){
			connectionManager.checkForProviderEntries();
		}
		
//		String jettyPort = PropertyReader.getInstance().getProperty("port");
		String jettyPort = conf.getSetting().getPortNumber();
		
		final JettyServer jettyServer;
		jettyServer = (jettyPort != null) ? new JettyServer(Integer.parseInt(jettyPort)) : new JettyServer(); 
		
		HandlerCollection contexts = new HandlerCollection();
		
		HandlerList list = new HandlerList();
//		org.eclipse.jetty.util.log.
		
//		SocketManager socketManagerObject = new SocketManager();
		WebSocketHandler wsHandler = new WebSocketHandler(){
			@Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(VagrantConsole.class);
            }
		};
		ContextHandler handler = new ContextHandler();
		handler.setHandler(wsHandler);
		handler.setContextPath("/vagrantConsole/");
		

		
		WebSocketHandler machineStatusWSHandler = new WebSocketHandler(){
			@Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(MachineStatus.class);
            }
		};
		ContextHandler machineStatusHandler = new ContextHandler();
		machineStatusHandler.setHandler(machineStatusWSHandler);
		machineStatusHandler.setContextPath("/machineStatus/");		

		
		list.setHandlers(new Handler[] {
				appContextBuilder.getStaticResourceHandler(),
				appContextBuilder.getWebAppHandler(),

				handler, machineStatusHandler		
		});
		
		contexts.setHandlers(new Handler[] {list} );

		
		jettyServer.setHandler(contexts);
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				try {
					jettyServer.start();
//					logger.debug("Server started");
				} catch (Exception e) {

					
					logger.error("Problem starting server "+ e.getMessage());

				}
			}
		};
		EventQueue.invokeLater(runner);
	}
}