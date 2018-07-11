package com.vi.acp.autorun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.vi.acp.factory.ConnectionFactory;

public class UpdateOverView {
	
    Connection connection = null;
    ResultSet rs = null;
    
	
	private Connection getConnection() throws SQLException
	  {
	   Connection conn;
	   conn = ConnectionFactory.getInstance().getConnection();
	   return conn;
	  }
	
	
	public void UpdateOverViewPage(Node nNode, String result, String filename) {
		
		Statement statement=null;
    
		Element eElement = (Element) nNode;
		
		try{
			 connection = getConnection();
			 statement = connection.createStatement();
			 String queryString = "insert into JAVA_PIPELINE_JOB ( ipaddress ,Component , release_tag, result, deployment_user, project_name, env_name,sprint ) values (?,?,?,?,?,?,?,?) ";
			 

      	     JobClass.logger.info( " OverView Query : " + queryString);

      	     JobClass.logger.info(" Component : " +eElement.getElementsByTagName("component").item(0).getTextContent());

      	     JobClass.logger.info( " IP Address : " + eElement.getElementsByTagName("ip_address").item(0).getTextContent());
      	     
      	     JobClass.logger.info(" Release_tag : " +eElement.getElementsByTagName("release_tag").item(0).getTextContent());
	      	 JobClass.logger.info(" Result : check the job status" );
	      	 JobClass.logger.info(" User Name : " + System.getProperty("user.name"));
	      	 JobClass.logger.info( " Project_name : " + eElement.getElementsByTagName("project_name").item(0).getTextContent());
	      	 JobClass.logger.info( " Filename : " + filename);
	      	 JobClass.logger.info(" Print : " + eElement.getElementsByTagName("sprint").item(0).getTextContent());
	      	
	      	 
	      	 
			 PreparedStatement pst = null;
			 pst = connection.prepareStatement(queryString);
			 pst.setString(1,  eElement.getElementsByTagName("ip_address").item(0).getTextContent());
			 pst.setString(2,  eElement.getElementsByTagName("component").item(0).getTextContent());
             pst.setString(3, eElement.getElementsByTagName("release_tag").item(0).getTextContent());
             pst.setString(4, result);
             pst.setString(5, System.getProperty("user.name"));
             pst.setString(6, eElement.getElementsByTagName("project_name").item(0).getTextContent());
             pst.setString(7, filename);
             pst.setString(8, eElement.getElementsByTagName("sprint").item(0).getTextContent());
             int insertquery = pst.executeUpdate();
             
      	     System.out.println(DateTime.getCurrentTimeStamp() + " : Info : Updated OverView DB successfull ");
      	     
			 JobClass.logger.info(DateTime.getCurrentTimeStamp() + " : Info : Updated OverView DB successfull ");
		}
	
		 catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally
		{
			if (statement !=null)  try{statement.close();}catch(SQLException ignore){};
			if (connection !=null)  try{connection.close();}catch(SQLException ignore){};
		}
		
	}
	

}
