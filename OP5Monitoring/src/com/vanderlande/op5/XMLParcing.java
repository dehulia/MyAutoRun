package com.vanderlande.op5;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;


public class XMLParcing {

	File file;
	String filepath;
	NodeList nList = null, nList_password =null, nList_user=null;
	
	
	NodeList ReadMyXMLFile(String condition){
	    try {
	    	
	    	String filepath = System.getProperty("user.dir") + "/acpItmcMonitoring.xml";
	       // System.out.print(filepath);

	        file = new File(filepath);
	    	File fXmlFile = file;

	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);

	    	doc.getDocumentElement().normalize();
	    	
	    	if (condition.equals("DB"))
	    		nList = doc.getElementsByTagName("db");
			else if (condition.equals("MSG"))
				nList = doc.getElementsByTagName("msg");
			else if (condition.equals("FMC"))
				nList = doc.getElementsByTagName("fmc");
			else if (condition.equals("WS"))
				nList = doc.getElementsByTagName("ws");
			else if (condition.equals("CHK_DISK"))
				nList = doc.getElementsByTagName("CHK_DISK");
			else if (condition.equals("SVS"))
				nList = doc.getElementsByTagName("svs");
	    	} catch (Exception e) {
	    	e.printStackTrace();
	        }
	    return (nList);
	      }
	
}
