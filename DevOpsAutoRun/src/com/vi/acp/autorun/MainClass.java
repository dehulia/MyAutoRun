package com.vi.acp.autorun;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.fusesource.jansi.AnsiConsole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainClass {
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

	public static void main(String[] args)  {
		WebDriver driver = null;
		AnsiConsole.systemInstall();
		try{
		NodeList nList;
		String argXMLfileName = args[0]; //"AH-IHT5.xml"; // args[0]; //"AH-IHT5.xml"; //  "IC-IHT.xml"; //
		boolean includeinrun = false; //  false;
		XMLParcing myXMLfile = new XMLParcing();
		//this is code to bypass console
		myXMLfile.getMyXmlfile(argXMLfileName, true /*boolean bypassconsole*/);
		nList = myXMLfile.ReadMyXMLFile();
			   
	    Element eElement;
    	for (int temp = 0; temp < nList.getLength(); temp++) {
    		Node nNode = nList.item(temp);
    		eElement = (Element) nNode;
    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if(eElement.getElementsByTagName("includeinrun").item(0).getTextContent().equals("Y")){
				includeinrun = true;
				break;
			}
    	}
	    
	   if(includeinrun){
		   
		    boolean result=false;
		    boolean login_result=false;
		    
		    System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> Setting up system properties..." + ANSI_RESET);
		    System.setProperty("webdriver.chrome.driver",  "C:\\Chromedriver\\chromedriver_win32\\chromedriver.exe");
		  
		    System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> Getting Chrome driver...");
		    driver  = new ChromeDriver();
		    System.out.print(ANSI_RESET);
		    System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> Creating Job object..."  + ANSI_RESET);
		  
		    UpdateOverView updatedb = new UpdateOverView();
		  
		    JobClass j = new JobClass(driver);
    	    login_result = j.login(driver);
	        if (login_result){
	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    		Node nNode = nList.item(temp);
	    		
	    		eElement = (Element) nNode;
	    		
	    		
	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if(eElement.getElementsByTagName("includeinrun").item(0).getTextContent().equals("Y")){
					

	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    			//Run the job only when it is allow in XML IncludeInRun
	    			
	    			result = j.runJob(driver, nNode);
         			//This will update DevOps overview page with the result(job status) and its version
         			updatedb.UpdateOverViewPage(nNode,  String.valueOf(result),argXMLfileName);
	    			
	    		}
	    	 }
	    	}

	    	System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> Executed all jobs..."  + ANSI_RESET);
	        }
	        
	}else{
		System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> No Jobs found include in run ..." + ANSI_RESET );
        }
		}catch(ParserConfigurationException e) {JobClass.logger.info(e.getMessage()); System.out.println("Error occured : ParserConfigurationException");  }
		catch(jdk.internal.org.xml.sax.SAXException e){JobClass.logger.info(e.getMessage() ); System.out.println("Error occured : jdk.internal.org.xml.sax.SAXException");  }
		catch(IOException e){JobClass.logger.info(e.getMessage() ); System.out.println("Error occured : IOException");  }
		catch(InterruptedException e){JobClass.logger.info(e.getMessage() ); System.out.println("Error occured : InterruptedException");  }
		catch(Exception e){JobClass.logger.info(e.getMessage() ); System.out.println("Error occured : Exception");  }
		finally{  if(driver!= null){
	    	    	
	    	JobClass.logger.info(DateTime.getCurrentTimeStamp() + "Close Chrome " ); 	    	 
	    	System.out.println(DateTime.getCurrentTimeStamp() +ANSI_YELLOW + " : Info : -> Close Chrome"  + ANSI_RESET);
	    	driver.close();
	    	JobClass.logger.info(DateTime.getCurrentTimeStamp() + "Release Selenium chromedriver.exe memory" ); 
	    	System.out.println(DateTime.getCurrentTimeStamp() + ANSI_YELLOW + " : Info : -> Release Selenium driver memory"  + ANSI_RESET);
	    	driver.quit();
	    	
			AnsiConsole.systemUninstall();
	    	} 
		}  
	}
	
	

}
