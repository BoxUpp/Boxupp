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
/*package com.boxupp.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class VagrantChildBean implements java.io.Serializable{
	
	private String box;
	private String hostName;
	private String machineName;
	private String boxURL;
	private String networkIP;
	private int ram = 384;
	private boolean guiRequired = false;
	private String provisionerName = "";
	private int cpuExecutionCap = 100;
	private ArrayList<String> scripts;
	private boolean isShellMapped = false;
	private HashMap<String,String> syncedFolders;
	private HashMap<Integer,Integer> portForwarding;

	
	public boolean isShellMapped() {
		return isShellMapped;
	}

	public void setShellMapped(boolean isShellMapped) {
		this.isShellMapped = isShellMapped;
	}

	public ArrayList<String> getScripts() {
		return scripts;
	}

	public void setScripts(ArrayList<String> scripts) {
		this.scripts = scripts;
	}

	public int getCpuExecutionCap() {
		return cpuExecutionCap;
	}

	public void setCpuExecutionCap(int cpuExecutionCap) {
		this.cpuExecutionCap = cpuExecutionCap;
	}

	public String getProvisionerName() {
		return provisionerName;
	}

	public void setProvisionerName(String provisionerName) {
		this.provisionerName = provisionerName;
	}
	
	public boolean isGuiRequired() {
		return guiRequired;
	}

	public void setGuiRequired(boolean guiRequired) {
		this.guiRequired = guiRequired;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public String getBox() {
		return box;
	}
	
	public HashMap<String, String> getSyncedFolders() {
		return syncedFolders;
	}

	public void setSyncedFolders(HashMap<String, String> syncedFolders) {
		this.syncedFolders = syncedFolders;
	}

	public HashMap<Integer, Integer> getPortForwarding() {
		return portForwarding;
	}

	public void setPortForwarding(HashMap<Integer, Integer> portForwarding) {
		this.portForwarding = portForwarding;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getBoxURL() {
		return boxURL;
	}
	public void setBoxURL(String boxURL) {
		this.boxURL = boxURL;
	}
	public String getNetworkIP() {
		return networkIP;
	}
	public void setNetworkIP(String networkIP) {
		this.networkIP = networkIP;
	}
	
	
	
	public static void main(String args[]){
		VagrantChildBean childBean = new VagrantChildBean();
		childBean.setBox("CentOS");
		childBean.setBoxURL("http://www.google.com");
		childBean.setHostName("akshay.master.machine");
		childBean.setMachineName("Akshay_Home");
		childBean.setNetworkIP("192.168.111.111");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("hello", "trial");
		map.put("hello1", "trial1");
		childBean.setSyncedFolders(map);
		
		
		VagrantChildBean childBean1 = new VagrantChildBean();
		childBean1.setBox("CentOS1");
		childBean1.setBoxURL("http://www.rediff.com");
		childBean1.setHostName("akshay.agent.machine");
		childBean1.setMachineName("Akshay_secondary");
		childBean1.setNetworkIP("192.168.111.121");
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("hello", "trial");
		map1.put("hello1", "trial1");
		childBean1.setSyncedFolders(map1);
		
		
		ArrayList<VagrantChildBean> childBeans = new ArrayList<VagrantChildBean>();
		childBeans.add(childBean);
		childBeans.add(childBean1);
		VagrantParentBean parentBean = new VagrantParentBean();
		parentBean.setChildStructures(childBeans);
		
		VelocityEngine ve = VelocityInit.getVelocityInstance();
		Template template = VelocityInit.getTemplate(ve);
		VelocityContext context = new VelocityContext();
		context.put("parent", parentBean);
		try{
			BufferedWriter outputBuffer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"\\final.data")));
			template.merge(context, outputBuffer);
			outputBuffer.close();
		}
		catch(ResourceNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace(); 
		} catch(ParseErrorException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
*/