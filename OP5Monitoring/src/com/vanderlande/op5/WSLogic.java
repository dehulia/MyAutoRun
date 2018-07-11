package com.vanderlande.op5;

public class WSLogic {
	
	public static String getWSVersion(String servicepath) { //(String argUser) {
		// TODO Auto-generated method stub
				String version ="";
				if (servicepath.contains (".exe"))
					version = FileVersionInfo.getFileVersionEXE(servicepath);
				else if (servicepath.contains (".jar"))
					version = FileVersionInfo.getJarFileVersionJAR(servicepath);
				else if ( servicepath.contains (".dll"))
					version = FileVersionInfo.getJarFileVersionDLL(servicepath);
				if (version.contains("Version not found"))
					return "";
				else
					return version ;				
	}
}

