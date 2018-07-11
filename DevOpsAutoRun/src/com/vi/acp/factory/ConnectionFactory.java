package com.vi.acp.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.vi.acp.autorun.DateTime;

public class ConnectionFactory
{
	
 String driverClassName="oracle.jdbc.OracleDriver";

 String connectionUrl="jdbc:oracle:thin:@10.160.80.36:1521:XE";
 String dbUser="devops";
 String dbPwd="devops";
 
 private static ConnectionFactory connectionFactory=null;
 
 private ConnectionFactory()
 {
  try
  {
	  System.out.println(DateTime.getCurrentTimeStamp() +" : Info : Load DB Drivers to update overview page");
      Class.forName(driverClassName);
  }
  catch(ClassNotFoundException e)
  {
   e.printStackTrace();
  }
 }
 
 public Connection getConnection() throws SQLException
 {
  Connection conn=null;
  conn=DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
  return conn;
 }
 
 public static ConnectionFactory getInstance()
 {
  if(connectionFactory==null)
  {
   connectionFactory=new ConnectionFactory();
  }
  return connectionFactory;
 }
}
