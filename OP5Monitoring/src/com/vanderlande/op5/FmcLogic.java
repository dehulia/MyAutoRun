package com.vanderlande.op5;

public class FmcLogic {
	
	public static String getFmcVersion(String servicename , String servicepath) { //(String argUser) {
		// TODO Auto-generated method stub
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
}
