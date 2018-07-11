package com.vi.acp.autorun;

import java.io.Console;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import jdk.internal.org.xml.sax.SAXException;

public class XMLParcing {


    
	File file;
	String filepath;
	NodeList nList = null, nList_password =null, nList_user=null;
	public static String username;
	public static String password;
	
	public void getMyXmlfile(String xmlFileName, boolean bypassconsole) throws ParserConfigurationException, SAXException, IOException{
		
    
		
	//Check if file exist or not.
	String filepath = System.getProperty("user.dir") + "\\"+ xmlFileName;
	
	System.out.println(filepath);
	
	file = new File(filepath);
	if(bypassconsole){
	if(file.exists()) {
		 Console c = System.console();
		    if (c == null) {
		        System.err.println("No console.");
		        System.exit(1);
		    }
		System.out.println("WARNING : DevOps Deployment will start with this file...");
		String response = c.readLine("Sure with file \"" + filepath + "\" ? (Enter \"Y/y\" or \"N\"): ");
		if ( !(response.toString().equals("Y") || response.toString().equals("y"))){	
			System.out.println("Expecting \"Y/y\" to proceed further.");
			System.exit(0);
		   }
	}else{
		System.out.println("File \""+ filepath + "\" Not exists.");
		System.exit(0);
	   }
	}
	}
	
	NodeList ReadMyXMLFile(){
				
	//this is code to bypass console
	//filepath = System.getProperty("user.dir")+ "/"+ "AH_IHT1_config.xml";
	//file = new File(filepath);

	    try {

	    	File fXmlFile = file;
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);

	    	doc.getDocumentElement().normalize();
	    	
			username = doc.getDocumentElement().getElementsByTagName("j_user").item(0).getTextContent();
			password = doc.getDocumentElement().getElementsByTagName("j_password").item(0).getTextContent();
  	    	
			nList = doc.getElementsByTagName("job");

	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
	    return (nList);
	      }
	
	String getUserName(){
		
		return username;
	}
	
	String getPassword(){
		return password;
		
	}
}
