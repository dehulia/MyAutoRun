package com.vanderlande.op5;
import java.io.IOException;
import java.io.InputStream;
public class CheckDisk {

	Integer  i=0,j=0,k=0,check_int;
	
	//final static Integer Warning = 10 ; // 10% free space is a warning 
	//final static Integer Critical = 5; // 5% free space is a Critical
	String  scOutput=""; 
    String   output ="";
    String  returnWhenNotFound = "Disk info not found";

	final String commandStr ="cmd /c for /f \"tokens=1-3\" %a in ('WMIC LOGICALDISK GET FreeSpace^,Name^,Size ^|"
										+ "FINDSTR /I /V \"Name\"') do @echo wsh.echo \"%b\" ^& \" free = \" ^& "
										+ "FormatNumber((FormatNumber^(cdbl^(%a^)/1024/1024/1024^)/FormatNumber^"
										+ "(cdbl^(%c^)/1024/1024/1024^))*100)^& \"%\" > %temp%\\tmp.vbs & @if not \"%c\"==\"\" @echo"
										+ "( & @cscript //nologo %temp%\\tmp.vbs & del %temp%\\tmp.vbs";
	
	public String CheckWindowsDisk(Integer Warning,Integer Critical) {
		
		Process process;
    	try{
	    	process = Runtime.getRuntime().exec(commandStr);
	    	final InputStream in = process.getInputStream();
	        int ch;
	        while((ch = in.read()) != -1)  {scOutput = scOutput + (char)ch ;}
		  }catch (IOException e){
 			return returnWhenNotFound;
 			}
    		//scOutput.replace(",", ".");
         
        while(i >= 0){
        	 i = scOutput.indexOf(":", i);
        	 if(i<0) break;
        	 //else if(!(j==0)) 
        		// output = output + ", ";
        	 j = scOutput.indexOf("=", i);
        	 k = scOutput.indexOf("%", j);
        	 check_int = Math.round((Float.parseFloat(scOutput.substring(j+1, k-3).trim())));
   	       	 if( check_int < Warning)
   	       	    {
	   	       	if(check_int < Critical)
	    	    {
	    		 output = output + scOutput.substring(i-1, i) + ":Free=";
	    		 output = output + scOutput.substring(j+1, k-3)+ "% ! CRITICAL !";
	    		 }else{
   	       		 output = output + scOutput.substring(i-1, i) + ":Free=" ;
        		 output = output + scOutput.substring(j+1, k-3) + "% WARNING ";
        		 }
   	       	    }
        	 i=j; j=k;
         }
         return output;
	}
}
