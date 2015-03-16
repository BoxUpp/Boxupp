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
package com.boxupp.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/uploadHandler")
public class UploadHandler {
	
	private static Logger logger = LogManager.getLogger(UploadHandler.class.getName());
	private static String selectedModule = "";
	private static boolean isModuleDefined;
	
	@POST
	@Path("/destination")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void setDestinationFolder(@Context HttpServletRequest request, @Context HttpServletResponse response){
		String destinationFolder = request.getParameter("loc");
		if(destinationFolder != null){
			UploadHandler.selectedModule = destinationFolder;
			UploadHandler.isModuleDefined = true;
		}else{
			UploadHandler.isModuleDefined = false;
		}
	}
	
	/*@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream inputStream,
		@FormDataParam("file") FormDataContentDisposition file) throws IOException{

		String response = "";
		try {
			String fileSeparator = OSProperties.getInstance().getOSFileSeparator();
		  String moduleDirectoryPath = "";
		  if(UploadHandler.isModuleDefined){
			  moduleDirectoryPath = Utilities.getInstance().fetchActiveProjectDirectory()+
					  				fileSeparator+"modules"+fileSeparator+UploadHandler.selectedModule+fileSeparator+
					  				"files"+fileSeparator;
		  }else{
			  moduleDirectoryPath = Utilities.getInstance().fetchActiveProjectDirectory()+fileSeparator+"files"+fileSeparator;
		  }
		  new File(moduleDirectoryPath).mkdirs();
		  final String FILE_DESTINATION = moduleDirectoryPath + file.getFileName();
		  File f = new File(FILE_DESTINATION);
			   
		  OutputStream outputStream = new FileOutputStream(f);
		  int size = 0;
		  byte[] bytes = new byte[1024];
		  while ((size = inputStream.read(bytes)) != -1) {
		   outputStream.write(bytes, 0, size);
		  }
		  outputStream.flush();
		  outputStream.close();
		  response = "File uploaded " + FILE_DESTINATION;
		} catch (Exception e) {
		  e.printStackTrace();
		}
	  return Response.status(200).entity(response).build();
	}*/

	/*@POST
	@Path("/parseVagrantFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadVagrantFile(@FormDataParam("dataFile") InputStream inputStream,
		@FormDataParam("dataFile") FormDataContentDisposition file) throws IOException{
		
		VagrantParser parser = new VagrantParser();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String data = "";
			  data = br.readLine();
			  while(data != null){
				  parser.addData(data);
				  data = br.readLine();
			  }
			  br.close();
			} 
		catch (Exception e) {
			  e.printStackTrace();
		}
		BoxuppVMData vmData = new BoxuppVMData();
		vmData = parser.fetchConfigurations();
		Gson gSon = new Gson();
		String parsedOutput = gSon.toJson(vmData);
		VagrantParserOutput output = new VagrantParserOutput();
		if(parser.fetchConfigurations().getVmData().size()>0){
			output.setStatusCode(0);
			output.setValidVagrantFile(true);
			output.setParsedData(parsedOutput);
		}
		return gSon.toJson(output);
	}*/
}
