package com.vanderlande.op5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServiceState {

	static final String started = "Installed and running";
	static final String stopped = "Installed but stopped";
	static final String Unknown = "Service not installed";
	static final String err = "Error while getting service status";
	
	public static String getServiceStatus(String serviceName) {

		  try {
		    Process process = new ProcessBuilder("C:\\Windows\\System32\\sc.exe", "query" , serviceName ).start();
		    InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);

		    String line;
		    String scOutput = "";

		    // Append the buffer lines into one string
		    while ((line = br.readLine()) != null) {
		        scOutput +=  line + "\n" ;
		    }

		    if (scOutput.contains("STATE")) {
		        if (scOutput.contains("RUNNING")) {
		        	return  started;
		        } else {
		        	return stopped ;
		        }       
		    } else {
		    	return Unknown ;
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
			  return err ;
		  } 

		}
	
	public static String getServicePath(String serviceName) {
         
		//run below command line query to get the service path using java code
		//reg query "HKLM\System\CurrentControlSet\Services\<serviceName>" /v "ImagePath"

	    String dosCommand = "reg query \"HKLM\\System\\CurrentControlSet\\Services\\" + serviceName + "\" /v \"ImagePath\" ";
        
        String result="";
        try
        {
            final Process process = Runtime.getRuntime().exec(dosCommand);
            final InputStream in = process.getInputStream();
            int ch;
            String output="";
            while((ch = in.read()) != -1)
            {
            	output = output + (char)ch ;
            }
            
             //- 18 is for removing \bin\service\amd64
             // +15 is to remove REG_EXPAND_SZ (need to test this code in all environment)
            
             result = output.substring(output.indexOf("REG_EXPAND_SZ") + 15 , output.lastIndexOf("\\") - 18 );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		return result.trim();

		}
}
