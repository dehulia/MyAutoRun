package com.vanderlande.op5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
	
	 String driverClassName="oracle.jdbc.OracleDriver";
 
 private static ConnectionFactory connectionFactory=null;
 
 private ConnectionFactory()
 {
  try
  {
   Class.forName(driverClassName);
  }
  catch(ClassNotFoundException e)
  {
   e.printStackTrace();
   System.out.println("Erro : " + e.getMessage());
  }
 }
 
 public Connection getConnection(String user, String pwd, String sid, String port, String host) throws SQLException
 {
  Connection conn=null;
  String connectionUrl="jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS=(PROTOCOL = TCP)(HOST = " + host + ")(PORT = " + port + "))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = " + sid + ")))";
  String dbUser = user;
  String dbPwd = pwd;
  //System.out.println(connectionUrl);
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
