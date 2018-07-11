package com.vanderlande.op5;

import java.io.File;


/*
 * HostGateway_WoW C:\ACP\GTW\host-gateway.exe
   MBA-WMC         C:\ACP\WMC-MBA\mba-wmc.exe
   MessageRouter   C:\ACP\MSG-MBE\message-router.exe
   ActiveMQ        C:\ACP\ActiveMQ-5.14.5\apache-activemq-5.14.5\bin\win64\wrapper.exe

 * */
public class MSGLogic {

	public static String getMSGVersion(String servicename , String servicepath) {
		String version ="" , status = "";

		if (servicepath.contains (".exe"))
			version = FileVersionInfo.getFileVersionEXE(servicepath);
		else if (servicepath.contains (".jar"))
			version = FileVersionInfo.getJarFileVersionJAR(servicepath);
			
		status = ServiceState.getServiceStatus(servicename);
		
		if (version.contains("Version not found"))
			return "";
		else
			return version +", "+ servicename + " : "+ status;
	}
	
	public static void getMSGPath(String argUser) {
		

		
			String version = getMSGFileInfo(argUser);
			
			String status = ServiceState.getServiceStatus(argUser);
			
			System.out.print(version + ", " + argUser + " " + status );
		
	}
	
	
    private static String getMSGFileInfo(String filepath)
    {
    	String  fileName = "";

    	File[] files = new File(filepath).listFiles();

    	for(File file : files)
    	{

    		fileName = fileName.toString() + filepath + file.getName() + ", ";
    	}
            return fileName;
    }
    
	
}

