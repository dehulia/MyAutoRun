package com.vanderlande.op5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class dbOracleLogic {
	
	private final static int TableSpacePersentLimit = 80; 
	
    static Connection connection = null;
    ResultSet rs = null;
    
	public static String fetchforversion(String user, String pwd, String sid, String port, String host) 
			throws SQLException 
			{
				String version = ""; 
		        ResultSet resultset=null;
				Statement statement=null;        
				try
				{
					connection = ConnectionFactory.getInstance().getConnection(user,pwd,sid,port,host);
					version = "Schema:" + user;
					//below check is applicable for WMCDB only
				  if (user.equals("WMCDB")){
						statement = connection.createStatement();
						
						String Query = "select * from productversion order by CREATED desc"; 
						resultset = statement.executeQuery(Query);
	
					    while(resultset.next())
						{
							version = version + ", PRODUCTNAME: '" + resultset.getString("PRODUCTNAME");
							version = version + "', RELEASENO: '" + resultset.getString("RELEASENO");
							version = version + "', UPDATENO: '" + resultset.getString("UPDATENO");
							version = version + "', CREATED: " + resultset.getDate("CREATED");
						}
				  }else if(user.equals("ASRHB")){
					statement = connection.createStatement();
					
					String Query = "select count(1) as Count from tsuretrieval where " +
								   " status not in ('Finished','Cancelled')and tsudbid = 0 ";
					resultset = statement.executeQuery(Query);
					resultset.next();
					version = version + " Logged in sucessfully, ASRHB QueryCount: " + resultset.getInt("Count");
				
				  }else if(user.equals("ASRHDS")){
					statement = connection.createStatement();
					
					String Query = "select count(1) as Count from " +
									" (select tsudbid, min(created), count (*) as StorageCount from storageorder" +
									" where status not in ('Finished','Cancelled')" +
									" group by tsudbid)"  + 
									" where StorageCount > 1";
					resultset = statement.executeQuery(Query);
					resultset.next();
					version = version + " Logged in sucessfully, ASRHDS Query Count: " + resultset.getInt("Count");
					}else{
						  version = version + ", Logged in sucessfully ";
					  }
				}
				catch(Exception error)
				{
					System.out.print("");//do not print userid password error or TNS error.
				}
			  finally
			  {
					if (connection !=null)  
						try{connection.close();}
					catch(SQLException ignore){System.out.print("");};
			  }
				return version;
			}
	
	
	public static String fetchforTablespace(String user, String pwd, String sid, String port, String host) 
			throws SQLException 
			{
				String version = "";
				String CRITICAL = " ! CRITICAL ! ";
				boolean critical_usage = false;
		        ResultSet resultset=null;
				Statement statement=null;        
				try
				{
					connection = ConnectionFactory.getInstance().getConnection(user,pwd,sid,port,host);
					//below check is applicable for WMCDB only
                     statement = connection.createStatement();
					
					String Query = "select * from dba_tablespace_usage_metrics"; 
					resultset = statement.executeQuery(Query);

				    while(resultset.next())
					{
						//version = version + "', USED_SPACE: '" + resultset.getFloat("USED_SPACE");
						//version = version + "', TABLESPACE_SIZE: '" + resultset.getFloat("TABLESPACE_SIZE");
						version = version + ", " + resultset.getString("TABLESPACE_NAME") + " "+ Math.round(resultset.getFloat("USED_PERCENT")) + "% Used";
						if(Math.round(resultset.getFloat("USED_PERCENT"))> TableSpacePersentLimit ){
							critical_usage = true;
							CRITICAL = CRITICAL + ", "+ resultset.getString("TABLESPACE_NAME") + " "+ Math.round(resultset.getFloat("USED_PERCENT")) + "% Used";
						}
					}
				}
				catch(Exception error)
				{
					System.out.print("Error user not able to login " + error.getMessage());//do not print userid password error or TNS error.
				}
			  finally
			  {
					if (connection !=null)  
						try{connection.close();}
					catch(SQLException ignore){System.out.print("");};
			  }
				if (critical_usage)
				   return CRITICAL;
				else
				   return version;
			}
	
	
	public static String  DbLocic(String user, String pwd, String sid, String port, String host  ) throws SQLException{
		
		
		
		//System.out.print("Dblogic  Calling with ServiceName : " + ServiceName + ", DBinfor : " + DBinfor);
		return fetchforversion( user,  pwd,  sid,  port,  host );

	}
	
	
	public static String  TableSpace(String user, String pwd, String sid, String port, String host  ) throws SQLException{
		
		//System.out.print("Dblogic  Calling with ServiceName : " + ServiceName + ", DBinfor : " + DBinfor);
		return fetchforTablespace( user,  pwd,  sid,  port,  host );

	}
}
