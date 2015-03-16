package com.boxupp.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.boxupp.db.beans.LocalGitRepoBean;
import com.boxupp.responseBeans.StatusBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class LocalRepoUtilities {
	private static Logger logger = LogManager.getLogger(LocalRepoUtilities.class.getName());
	private static LocalRepoUtilities localRepoUtilities = null;
	
	
	public static LocalRepoUtilities getInstance() {
		if (localRepoUtilities == null) {
			try {
				localRepoUtilities = new LocalRepoUtilities();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return localRepoUtilities;
	}

	public List<String> fetchBranches(HttpServletRequest request){
		String username = request.getParameter("remoteRepoURI").split("@")[0]; 
		String password = request.getParameter("password");
		   List<String> branchList = new ArrayList<String>();
		   List<Ref> call;
		   Git git = null;
		   CredentialsProvider cp = new UsernamePasswordCredentialsProvider(username, password);

		   File localRepo = new File(CommonProperties.getInstance().getRemoteRepoClonePath());
		   try {
			   if(localRepo.exists()){
				   CloneCommand cc = new CloneCommand().setCredentialsProvider(cp).setDirectory(localRepo).setURI(request.getParameter("remoteRepoURI"));
				   git =  cc.call();
				  
			   }else{
				  git =   Git.open( new File( CommonProperties.getInstance().getRemoteRepoClonePath() + "/.git" ) );
			   }
			   call = git.branchList().setListMode(ListMode.REMOTE).call();
		           
	          for (Ref ref : call) {
				branchList.add(ref.getName().split("\\/")[ ref.getName().split("\\/").length-1]);
			  }
			}catch (Exception e) {
				logger.error("Error in fetching repo branch list "+e);
			}
	       	return branchList;
	   	}

	

	public StatusBean commitOnRemoteRepo(JsonNode commitData) {
		StatusBean stBean = new StatusBean();
		Gson projectData = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss").create();
		LocalGitRepoBean localRepoBean = projectData.fromJson(commitData.toString(), LocalGitRepoBean.class);
	    String username = localRepoBean.getRemoteRepoURI().split("@")[0]; 
        File cloneDir  = new File(CommonProperties.getInstance().getRemoteRepoClonePath()); 

	    try {   
	    // credentials
	        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(username, localRepoBean.getPassword());
	        File vagrantpath = new File(CommonProperties.getInstance().getRemoteRepoClonePath()+localRepoBean.getPath());
			Git git =   Git.open( new File( CommonProperties.getInstance().getRemoteRepoClonePath() + "/.git" ) );
			String projectDir =  Utilities.getInstance().fetchActiveProjectDirectory(Integer.parseInt(commitData.get("userID").getTextValue()));
			String vagrantfilePath = projectDir + OSProperties.getInstance().getOSFileSeparator() + 
				OSProperties.getInstance().getVagrantFileName();
			Utilities.getInstance().copyFile(new File(vagrantfilePath), vagrantpath);
			//git.checkout().setName(localRepoBean.getBranch()).setUpstreamMode(SetupUpstreamMode.TRACK).call();
			
			//add	
	        AddCommand ac = git.add();
	        ac.addFilepattern(localRepoBean.getPath());
	        ac.call();
	       
	        // commit
	        CommitCommand commit = git.commit();
	        commit.setMessage(localRepoBean.getComment());
	        commit.call();
	        
	        // push
	        PushCommand pc = git.push();
	        pc.setCredentialsProvider(cp).setRemote("origin").setForce(true).setPushAll();
	        Iterator<PushResult> it = pc.call().iterator();
	          if(it.hasNext()){
	               System.out.println(it.next().toString());
	          }
	          if(cloneDir.exists()) Utilities.getInstance().deleteFile(cloneDir);
	        stBean.setStatusCode(0);
	        stBean.setStatusMessage("Commited Successfully");
	    }catch(Exception e){
	    	logger.error("Error in commiting code on remote repo :"+e);
	    	stBean.setStatusCode(1);
	    	stBean.setStatusMessage("Error in commiting code on remote repo ");
	    }
		return stBean;

	}
	
	public static void main(String[] args) {
		/*String name = "paxgit";
		String password = "paxcel!@2345";
		String url = "paxgit@172.16.0.46:/home/paxgit/boxupp";*/
		String name = "ashachhikara";
		String password = "Jhajjar1979";
		String url = "https://github.com/ashachhikara/box.git";
        // credentials
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name, password);
        // clone
        File dir = new File("/tmp/a");
        CloneCommand cc = new CloneCommand()
                .setCredentialsProvider(cp)
                .setDirectory(dir)
                .setURI(url);
        Git git = null;
		try {
			//git = Git.open( new File( "/tmp/abf" + "/.git" ) );
			git = cc.call();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        // add
        AddCommand ac = git.add();
        ac.addFilepattern(".");
        try {
            ac.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        // commit
        CommitCommand commit = git.commit()
       .setMessage("push war");
        try {
            commit.call();
        } catch (Exception e) {
            e.printStackTrace();
        } // push
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp)
                .setForce(true)
                .setPushAll();
        try {
            Iterator<PushResult> it = pc.call().iterator();
            if(it.hasNext()){
                System.out.println(it.next().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
     // cleanup
        if(dir.exists()){
        Utilities.getInstance().deleteFile(dir);
        }
	}

	
}
