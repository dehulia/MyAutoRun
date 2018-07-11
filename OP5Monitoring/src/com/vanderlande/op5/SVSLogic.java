package com.vanderlande.op5;

import java.io.File;

public class SVSLogic {

	public static void getSVSVersion(String argService) {
	
		if(argService.contains("Wildfly") || argService.contains("erff")  ){

			String path = ServiceState.getServicePath(argService);

            path = path + "\\standalone\\deployments\\";

			String version = getSvsFileInfo(path);

			String status = ServiceState.getServiceStatus(argService);

			System.out.print( version + argService + " " + status );
		
		}else  {
			
			 System.out.print( argService + "cannot handle by this command ");
		
		}
	}
	
	public static void getSVSPath(String argUser) {
		
		if(argUser.contains("SVS")){
		
			String version = getSvsFileInfo(argUser);
			
			String status = ServiceState.getServiceStatus(argUser);
			
			System.out.print(version + ", " + argUser + " " + status );
		
		}else  {
			
			System.out.print( argUser + "cannot handle by this command ");
		
		}
	}
	
	
    private static String getSvsFileInfo(String filepath)
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
