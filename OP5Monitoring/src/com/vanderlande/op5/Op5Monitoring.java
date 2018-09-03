package com.vanderlande.op5;

import java.sql.SQLException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Op5Monitoring {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
	    
		try {
	        String argFunction = "CHK_FOLDER"; //args[0]; // "DB" ;   FMC, DB, SVS, MSG "SVS" ; "MSG";  "TABLESPACE"; "CHK_DISK";
	        String argUser = "C:\\Test\\Files" ; //args[1]; // "ASRHB"; //args[1]; "nscp";// "WMCDB";//
	        
	        if(argFunction.equals("FMC"))
	        {
	        	//call FMC logic
	        	String output = "";
	    		NodeList nList;
	    		XMLParcing myXMLfile = new XMLParcing();
	    		nList = myXMLfile.ReadMyXMLFile(argFunction);
	    		
	        	if (!argUser.isEmpty()){
	        		
	        	    Element eElement;
	    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    	    		Node nNode = nList.item(temp);
	    	    		eElement = (Element) nNode;

	    	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    	    			
	    	    		if(eElement.getElementsByTagName("argname").item(0).getTextContent().equals(argUser.toString())){ 
	    	    				String servicename = eElement.getElementsByTagName("serviceName").item(0).getTextContent();
	    	    				String servicepath = eElement.getElementsByTagName("Path").item(0).getTextContent();
	    	    				output = output + FmcLogic.getFmcVersion(servicename,servicepath) + " "; 
	    	    				break;
	    	    			}
	    	    		}
	    	    	}
	    	    	System.out.print(output);
	             }
	        }else if (argFunction.equals("DB"))
	        {
	    		NodeList nList;
	    		XMLParcing myXMLfile = new XMLParcing();
	    		nList = myXMLfile.ReadMyXMLFile(argFunction);
	    		
	        	//Call db logic
	        	if (!argUser.isEmpty()){
	        		
	        		
	        	    Element eElement;
	    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    	    		Node nNode = nList.item(temp);
	    	    		eElement = (Element) nNode;

	    	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    	    			if(eElement.getElementsByTagName("user").item(0).getTextContent().equals(argUser.toString()))
	    	    			//Run the check only when it is available in the 
	    	    			{ 
	    	    				String user = eElement.getElementsByTagName("user").item(0).getTextContent();
	    	    				String pwd = eElement.getElementsByTagName("password").item(0).getTextContent();
	    	    				String sid = eElement.getElementsByTagName("sid").item(0).getTextContent();
	    	    				String port = eElement.getElementsByTagName("port").item(0).getTextContent();
	    	    				String host = eElement.getElementsByTagName("host").item(0).getTextContent();
	    	    				//System.out.print(user + pwd +  sid + port + host);
	    	    				System.out.print(dbOracleLogic.DbLocic(user,pwd,sid,port,host)); 
	    	    				break;
	    	    			}
	    	    		}
	    	    	}
		        }
	        	
	        }else if (argFunction.equals("SVS")){
	        	//call SVS logic

	    		NodeList nList;
	    		XMLParcing myXMLfile = new XMLParcing();
	    		nList = myXMLfile.ReadMyXMLFile(argFunction);
	    		
	        	if (!argUser.isEmpty()){
	        		
	        	    Element eElement;
	    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    	    		Node nNode = nList.item(temp);
	    	    		eElement = (Element) nNode;

	    	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    	    			
	    	    		if(eElement.getElementsByTagName("argname").item(0).getTextContent().equals(argUser.toString())){ 
	    	    				String servicename = eElement.getElementsByTagName("servicename").item(0).getTextContent();
	    	    				SVSLogic.getSVSVersion(servicename);
	    	    				break;
	    	    			}
	    	    		}
	    	    	}
	             }
	        }else  if (argFunction.equals("MSG")){
	        	//call MSG logic
	        	String output = "";
	        	
	    		NodeList nList;
	    		XMLParcing myXMLfile = new XMLParcing();
	    		nList = myXMLfile.ReadMyXMLFile(argFunction);
	    		
	        	if (!argUser.isEmpty()){
	        		
	        	    Element eElement;
	    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    	    		Node nNode = nList.item(temp);
	    	    		eElement = (Element) nNode;

	    	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    	    			
	    	    		if(eElement.getElementsByTagName("argname").item(0).getTextContent().equals(argUser.toString())){ 
	    	    				String servicename = eElement.getElementsByTagName("serviceName").item(0).getTextContent();
	    	    				String servicepath = eElement.getElementsByTagName("Path").item(0).getTextContent();
	    	    				output = output + MSGLogic.getMSGVersion(servicename,servicepath) + " "; 
	    	    				break;
	    	    			}
	    	    		}
	    	    	}
	    	    	System.out.print(output);
	             }
	        	
		}else  if (argFunction.equals("TABLESPACE")){
    		NodeList nList;
    		XMLParcing myXMLfile = new XMLParcing();
    		nList = myXMLfile.ReadMyXMLFile("DB");
    		
        	//Call db logic
        	if (!argUser.isEmpty()){
        		
        		
        	    Element eElement;
    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
    	    		Node nNode = nList.item(temp);
    	    		eElement = (Element) nNode;

    	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    	    			if(eElement.getElementsByTagName("user").item(0).getTextContent().equals(argUser.toString()))
    	    			//Run the check only when it is available in the 
    	    			{ 
    	    				String user = eElement.getElementsByTagName("user").item(0).getTextContent();
    	    				String pwd = eElement.getElementsByTagName("password").item(0).getTextContent();
    	    				String sid = eElement.getElementsByTagName("sid").item(0).getTextContent();
    	    				String port = eElement.getElementsByTagName("port").item(0).getTextContent();
    	    				String host = eElement.getElementsByTagName("host").item(0).getTextContent();
    	    				try{
    	    					pwd = eElement.getElementsByTagName("SYSpassword").item(0).getTextContent();
    	    				}catch(Exception e)
    	    				{
        	    				pwd = "g1"+ sid;
    	    				}
    	    				System.out.print("TableSpace @ " + user + dbOracleLogic.TableSpace("sys as sysdba", pwd ,sid,port,host)); 
    	    				break;
    	    			}
    	    		}
    	    	}
	        }
        	
        }else if(argFunction.equals("WS"))
        { 	    
        
        	//call FMC logic
        	
        	String output = "";
        	
    		NodeList nList;
    		XMLParcing myXMLfile = new XMLParcing();
    		nList = myXMLfile.ReadMyXMLFile(argFunction);
        	if (!argUser.isEmpty()){
        	    Element eElement;
    	    	for (int temp = 0; temp < nList.getLength(); temp++) {
    	    		Node nNode = nList.item(temp);
    	    		eElement = (Element) nNode;
    	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    	    			if(eElement.getElementsByTagName("argname").item(0).getTextContent().equals(argUser.toString())){ 
    	    			String WSpath = eElement.getElementsByTagName("Path").item(0).getTextContent();
    	    			output = WSLogic.getWSVersion(WSpath) + " "; 
    	    			break;
    	    			}
    	    		}
    	    	}
    	    	System.out.print(output);
             }  
        }else if(argFunction.equals("CHK_DISK"))
        {       	
        	Integer Warning = 11;
        	Integer Critical= 6 ;

    		NodeList nList;
    		XMLParcing myXMLfile = new XMLParcing();
    		nList = myXMLfile.ReadMyXMLFile(argFunction);
     
    		try{
    		Element eElement;
    		
        	Node nNode = nList.item(0);
	    	eElement = (Element) nNode;
	    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Warning = Integer.parseInt(eElement.getElementsByTagName("Warning").item(0).getTextContent());
				Critical = Integer.parseInt(eElement.getElementsByTagName("Critical").item(0).getTextContent());
                }
    		}catch(Exception e)
    		{
    			Warning = 11;
    			Critical= 6 ;
    		}
    		 CheckDisk callDISK = new CheckDisk();
    		String output = callDISK.CheckWindowsDisk(Warning, Critical);
        
    		if (output.length() == 0)
        	    output ="Healthy" ;
        
        	System.out.print(output); 
        
        }else if(argFunction.equals("CHK_FOLDER"))
        {       	
        	/* This section will check if the folder containing some file
        	 * then it will copy the file and move it to processed directory 
        	 * and fail the service*/
        	String folder_path = "C:\\Test\\Files";

    		NodeList nList;
    		XMLParcing myXMLfile = new XMLParcing();
    		nList = myXMLfile.ReadMyXMLFile(argFunction);
     
    		try{
    		Element eElement;
    		
        	Node nNode = nList.item(0);
	    	eElement = (Element) nNode;
	    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    		folder_path = eElement.getElementsByTagName("Path").item(0).getTextContent();
                }
    		}catch(Exception e)
    		{
    			folder_path = "C:\\Test\\Files";
    		}
    		
    		LogFoundInFolder log = new LogFoundInFolder();
    		
    		boolean output = log.CheckLogFolder(folder_path);
        
    		if (output){
    			//File(s) found at folder_path 
    			System.out.print("ActiveMQ error files found at " + folder_path); 
    		}else{
    			//file NOT found at folder_path 
        	System.out.print("Healthy"); 
    		}
        }else
	        	System.out.print("Invalid Argument : - " + argFunction );
	        
	    }
	    catch (ArrayIndexOutOfBoundsException e){
	        System.out.print("ArrayIndexOutOfBoundsException caught");
	    }
		catch (SQLException e){
			System.out.print("SQLException caught");
		}
	    finally {
                 
	    }
	}
}
