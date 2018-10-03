
package com.vi.acp.autorun;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class JobClass {
	
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
    static Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  
    String filepath; 
    String logfilename = "LOG_" +DateTime.getCurrentTimeStampFile() + ".log" ;
	JobClass(WebDriver driver)  {
		driver.manage().window().maximize();
		System.out.println(DateTime.getCurrentTimeStamp() +ANSI_YELLOW + " : Info : -> Trying to load Jenkins page" + ANSI_RESET);
		driver.get("http://dedorawb1/login?from=%2F");		
		try{
			filepath = System.getProperty("user.dir") + "\\" + logfilename;
			fh = new FileHandler(filepath);  
	        logger.addHandler(fh);
	        logger.setUseParentHandlers(false);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);  
			System.out.println(DateTime.getCurrentTimeStamp()  +ANSI_YELLOW + " : Info : -> " + filepath + ANSI_RESET);
	}catch(IOException e){
		System.out.println(e.getMessage());
	}
	}

	boolean login(WebDriver driver){
	
		try{
			WebElement clickLogin = driver.findElement(By.xpath("//*[@id='header']/div[2]/a/b") );
			clickLogin.click();
			
			//*[@id="j_username"]
			
		WebElement user = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
		 logger.info(" : Info : User id placed : " + XMLParcing.username);
		user.sendKeys(XMLParcing.username);
		 Thread.sleep(200);
		 logger.info(" : Info : Placing Password : " + XMLParcing.password);
		WebElement pwd = driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div/form/table/tbody/tr[2]/td[2]/input"));
		pwd.sendKeys(XMLParcing.password);
     	
		Thread.sleep(200);
		 logger.info(" : Info : Click Submit");
		WebElement click = driver.findElement(By.xpath("//*[@id='yui-gen1-button']") );
		click.click();
		
		Thread.sleep(200);
		System.out.println(DateTime.getCurrentTimeStamp()  +ANSI_YELLOW + " : Info : -> Sucessfully logged into Jenkins" + ANSI_RESET );
		logger.info(" : Info : main-panel");
		 driver.findElement(By.xpath("//*[@id='main-panel']/div[2]/div[1]/div[7]/a")).click();
		System.out.println(DateTime.getCurrentTimeStamp() +ANSI_YELLOW + " : Info : -> Project " + ANSI_RESET );
		 Thread.sleep(200);
		 logger.info(" : Info : DevOps");
		 //<a href="/view/Projects/view/DevOps/">DevOps</a>
		driver.findElement(By.linkText("DevOps")).click();
		System.out.println(DateTime.getCurrentTimeStamp()  +ANSI_YELLOW + " : Info : -> Project/DevOps "  + ANSI_RESET);
		 Thread.sleep(200);  
		 logger.info(" : Info : Project/DevOps");
        driver.findElement(By.xpath("//*[@id='job_ACP-Deployment']/td[3]/a")).click();
		 logger.info(" : Info : job_ACP-Deployment ");
        System.out.println(DateTime.getCurrentTimeStamp()  +ANSI_YELLOW + " : Info : -> Project/DevOps/ACP-Deployment "  + ANSI_RESET);
        logger.info(" : Info : Project/DevOps/ACP-Deployment ");
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[@id='job_Components']/td[3]/a")).click();
        logger.info(" : Info : job_Components ");
        
		}catch(Exception e){
			 System.out.println(DateTime.getCurrentTimeStamp() + " : Warning : Error while login into Jenkins ");
			 System.out.println(DateTime.getCurrentTimeStamp() + " : Error   : Please refer to log file for details...");
			 logger.info(" : Warning : Error while login into Jenkins ");
			 logger.info(" : Error   : Please refer to log file for details...");
			 logger.info( " Error in com.vi.acp.autorun.JobClass.login  : " + e.getMessage()); 
			return (false);
		}
        return (true);
	}
	
	
	boolean runJob( WebDriver driver, Node nNode) throws InterruptedException{
		// TODO Auto-generated method stub
		            boolean job_result = false;
		            boolean skype_job_check = false;
				    String fullResultText = "";
				    String job_number = " ";
	    			Element eElement = (Element) nNode;
	    			boolean component_or_workstation = false; //workstation => true component => false 
	    			try{
	    			System.out.println(DateTime.getCurrentTimeStamp() +  " : Info : Include in Run : " + eElement.getElementsByTagName("includeinrun").item(0).getTextContent()+ " - " + eElement.getElementsByTagName("sr_no").item(0).getTextContent() + ". "+ eElement.getElementsByTagName("component").item(0).getTextContent() );
	    			}catch(Exception e){
	    				
	    				System.out.println(e.getMessage());
	    			}
    				String str_component = eElement.getElementsByTagName("component").item(0).getTextContent();
	    			String str_substr_ws = "-WS";
	    			//String str_substr_svs = "SVS-";
    				int pos_ws = str_component.indexOf(str_substr_ws);
	    			//int pos_svs = str_component.indexOf(str_substr_svs);
    				
	    			
	    				if (!(pos_ws > 0)){
                                // Component job
	    					component_or_workstation = false;
	    					
			    		    //System.out.println(DateTime.getCurrentTimeStamp() + " : Info : Start Executing : " + eElement.getElementsByTagName("sr_no").item(0).getTextContent() + ". " + eElement.getElementsByTagName("component").item(0).getTextContent());
				            //System.out.println(DateTime.getCurrentTimeStamp() +" : Info : Providing values for Jenkins job");
			                
				            driver.findElement(By.xpath(eElement.getElementsByTagName("component_xpath").item(0).getTextContent())).click();
				            
				            Thread.sleep(600);
				            //click on Build with Parameters
				            driver.findElement(By.xpath("//*[@id='tasks']/div[5]/a[2]")).click();
				            Thread.sleep(1000);
				            
				            //IP address 
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[1]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[1]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ip_address").item(0).getTextContent());
				            Thread.sleep(200);
				            
				            //UserName
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[2]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[2]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("username").item(0).getTextContent());
				            Thread.sleep(200);
				            
				            //Password 
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[3]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[3]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("password").item(0).getTextContent());
				            Thread.sleep(200);
				            
                            //RELEASE_TAG
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("release_tag").item(0).getTextContent());
				            Thread.sleep(200);
				            
			            	if (str_component.equals("PrintJobManager")){
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);

				            }else if (str_component.equals("APP-PNS-TOP")){
				            	
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("APP-PNS-LFL")){
				            	
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
				            	
				            	//drive name
					            //*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("APP-INBD-RCV")){
				            	
				            	//PROJECT_NAME
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//DRIVE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
					            //SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            } else if (str_component.equals("APP-INBD-TEA") || str_component.equals("APP-INBD-TEA-1745")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());

				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            } else if (str_component.equals("APP-INBD-YM")){
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
				            	
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            } else if (str_component.equals("APP-PTS-ASR-HB")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
				            	
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("APP-PTS-ATR-PAL")){
			            	
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
				            	
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	
				            }else if (str_component.equals("APP-REPL-DEP-AUTO")){

				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	
					            //drop down
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);

					            //Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
				            	
					            //ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("APP-REPL-DEP-MAN")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);

				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("APP-TTS-ASR-HDS")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
				            	
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            }else if (str_component.equals("APP-TTS-ASR-MS")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("APP-TTS-ATR-TRAY")){
				            	
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("APP-PICK-PAL-AUTO")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
				            	
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("APP-PICK-PAL-MAN")){
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

					            //drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	//Oracle_service
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//ORACLE_SERVICE_PORT
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service_port").item(0).getTextContent());
					            Thread.sleep(200);

				            	//SERVICE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[10]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				            
				            } else  if (str_component.equals("SVS-ASR-HB")){
				            	
				            	
				            	//DRIVE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
				            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//SVS_NAME
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
					            Thread.sleep(200);
				            }else if (str_component.equals("SVS-ASR-HDS")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
						            Thread.sleep(200);
				             }else if (str_component.equals("SVS-ASR-MS")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
						            Thread.sleep(200);
				            	}else if (str_component.equals("SVS-DEP-AUTO")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
						            Thread.sleep(200);
						            
				            	}else if (str_component.equals("SVS-DEP-MAN")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
						            Thread.sleep(200);
						            
				            	}else if (str_component.equals("SVS-General-AH")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
				            	}else if (str_component.equals("SVS-General-WOW")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
				            	}else if (str_component.equals("SVS-TEA")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
				            	}else if (str_component.equals("SVS-TOP")){
					            	
					            	//DRIVE_NAME
					            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
						            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
						            Thread.sleep(200);
					            	
					            	//SVS_NAME
					            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
						            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
				            }else if (str_component.equals("SVS-PAL-AUTO")){
				            	
			            	
				            	//DRIVE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
				            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//SVS_NAME
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
				         }else if (str_component.equals("SVS-LFL")){
				            	
				            	//DRIVE_NAME
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
				            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//SVS_NAME
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("svs_name").item(0).getTextContent());
					            
				         }else if (str_component.equals("WMC-MBA")){
				            	
				            	//FM_RELEASE_TAG 
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("fm_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
			            	
				            	//YM_RELEASE_TAG
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ym_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//drop down project_name
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            
				            	//DRIVE_NAME
				            	driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[10]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drop down TIME_ZONE 
					            Select dropdownTimeZone = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[11]/tr[1]/td[3]/div/select")));
					            dropdownTimeZone.selectByVisibleText(eElement.getElementsByTagName("time_zone").item(0).getTextContent());
					            Thread.sleep(200);

				         }else if (str_component.equals("MSG-MBE")){
				            					            	
				            	//drive name
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//SERVICE_NAME
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
				         }else if (str_component.equals("MSG-MBA-3PP")){
											        
				        	    //FM_RELEASE_TAG 
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("fm_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	
					            //drop down project_name
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
								//drive name
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
								Thread.sleep(200);
								
								//drop down Environment
					            Select dropdownEnv = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/select")));
					            dropdownEnv.selectByVisibleText(eElement.getElementsByTagName("service_name").item(0).getTextContent());
								Thread.sleep(200);
								
								
						}else if (str_component.equals("MSG-GTW")){
				        	 
				            	//drop down
				            	Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//Oracle_service
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("oracle_service").item(0).getTextContent());
					            Thread.sleep(200);
					            		            
				            	//SERVICE_NAME
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath(" //*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("service_name").item(0).getTextContent());
					            Thread.sleep(200);
	
	    				}else
				        { //this is the case when component Match not found then do not run the job
   				        	System.out.print(DateTime.getCurrentTimeStamp() +  ANSI_RED +  " : Warning : Component " + str_component + " match not found, XLS configuration error Component name in xls is not correct" + ANSI_RESET );
				        	  logger.info(DateTime.getCurrentTimeStamp() + " : Warning : Component " + str_component + " Match not found ! Show this to DevOps team XLS configuration error " );
				        	return false;
				        	
				        }

				   /*Above FMC, SVSs, Queues handled */         
				   /*Below are the Workstations Logic this is based on Componenet should contains -WS */
				            
				            
	    			}else if ((pos_ws > 0))
		    			{
			    			//This is a workstation
	    					component_or_workstation = true;

	    					System.out.println(DateTime.getCurrentTimeStamp() + " : Info : Start Executing WorkSorkStation Job : " + eElement.getElementsByTagName("component").item(0).getTextContent());
    						
	    					Thread.sleep(1000);
    						driver.findElement(By.xpath(eElement.getElementsByTagName("component_xpath").item(0).getTextContent())).click();
    						
    						Thread.sleep(1000);
    						driver.findElement(By.xpath(eElement.getElementsByTagName("ws_xpath").item(0).getTextContent())).click();

    						//click on Build with Parameters
    						Thread.sleep(1000);
    						driver.findElement(By.xpath("//*[@id='tasks']/div[5]/a[2]")).click();
    						Thread.sleep(200);
 				            //IP address 

    						driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[1]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[1]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ip_address").item(0).getTextContent());
				            Thread.sleep(200);
				            
				            //UserName
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[2]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[2]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("username").item(0).getTextContent());
				            Thread.sleep(200);
				            
				            //Password 
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[3]/tr[1]/td[3]/div/input[2]")).clear();
				            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[3]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("password").item(0).getTextContent());
				            Thread.sleep(200);
				            
				            if (str_component.equals("CLR-PAL-TEA-WS")){
				            	
				            	//CLR release tag
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("clr_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//TEA release tag
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//TEA project Release tag
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }  else if (str_component.equals("CLR-PAL-TEA-WS-1745")){
				            	
				            	//CLR release tag
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("clr_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//TEA release tag
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//TEA project Release tag
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            }else if (str_component.equals("CLR-PAL-WS")){
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            }else if (str_component.equals("CLR-TRAY-TEA-WS")){
				            	
				            	//CLR release tag
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("clr_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//TEA release tag
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//TEA project Release tag
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);

				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);

				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            } else if (str_component.equals("CLR-TRAY-TEA-WS-1745")){
				            	
				            	//CLR release tag
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("clr_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//TEA release tag
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//TEA project Release tag
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("tea_project_release_tag").item(0).getTextContent());
					            Thread.sleep(200);

				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);

				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);

				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            }else if (str_component.equals("DEP-AUTO-WS") || str_component.equals("IC-DEP-AUTO-WS")){   //ok
				            	
				            	//DEFOIL_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("defoil_release_tag").item(0).getTextContent());
								Thread.sleep(200);
								
				            	//OPS_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ops_release_tag").item(0).getTextContent());
								Thread.sleep(200);
								
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            }else if (str_component.equals("DEP-MAN-WS") || str_component.equals("IC-DEP-MAN-WS")){//ok
				            	
				            	//DEFOIL_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("defoil_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//OPS_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ops_release_tag").item(0).getTextContent());
								Thread.sleep(200);
								
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[9]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[9]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("PAL-AUTO-WS")){  //ok
				            	
				            	//OPS_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ops_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("PAL-MAN-WS")){  //ok
				            	
				            	//OPS_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("ops_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("RCV-WS") || str_component.equals("IC-TLS-WS") ){ //ok
				            	
				             	//RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//DEPLOY_PATH
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);	
				            	
				            }else if (str_component.equals("TEA-WS")){ //ok
				            	
				            	//RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//project_name
					            //*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
 					            //DEPLOY_PATH
					            //*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);	
					            
				            }else if (str_component.equals("TEA-WS-1745")){ //ok
				            	
				            	//RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[4]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[4]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("release_tag").item(0).getTextContent());
					            Thread.sleep(200);
				            	
				            	//PROJECT_RELEASE_TAG
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/input[2]
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).clear();
								driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("project_release_tag").item(0).getTextContent());
								Thread.sleep(200);
				            	
				            	//drive name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//project_name
					            //*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
 					            //DEPLOY_PATH
					            //*[@id="main-panel"]/form/table/tbody[8]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[8]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);	
					            
				            }							else if (str_component.equals("CLR-TRAY-WS")){
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }else if (str_component.equals("CLR-CARTON-WS")){
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
								
							}else if (str_component.equals("GTP-WS")){
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
								
							}else if (str_component.equals("CLR-OB-PAL6-WS")){
				            	
				            	//project_name
				            	//*[@id="main-panel"]/form/table/tbody[5]/tr[1]/td[3]/div/select
					            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[5]/tr[1]/td[3]/div/select")));
					            dropdown.selectByVisibleText(eElement.getElementsByTagName("project_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//drive_name
				            	//*[@id="main-panel"]/form/table/tbody[6]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[6]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("drive_name").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            	//deploy_path
				            	//*[@id="main-panel"]/form/table/tbody[7]/tr[1]/td[3]/div/input[2]
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).clear();
					            driver.findElement(By.xpath("//*[@id='main-panel']/form/table/tbody[7]/tr[1]/td[3]/div/input[2]")).sendKeys(eElement.getElementsByTagName("deploy_path").item(0).getTextContent());
					            Thread.sleep(200);
					            
				            }
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            
				            else{
				            	//this is the case when component Match not found then do not run the job
	   				        	System.out.print(DateTime.getCurrentTimeStamp() +  ANSI_RED +  " : Warning : Workstation " + str_component + " match not found, XLS configuration error, Workstation name in xls is not correct" + ANSI_RESET );
					        	  logger.info(DateTime.getCurrentTimeStamp() + " : Warning : Component " + str_component + " Match not found ! Show this to DevOps team XLS configuration error " );
					        	return false;
				            }
				        }
	    				
	    		   Thread.sleep(1000);

    				//Common for both 'Workstation' and 'Non Workstation'
		            
				    System.out.print(DateTime.getCurrentTimeStamp() + " : Info : Executing job.");
				    
				    driver.findElement(By.xpath(" //*[@id='yui-gen1-button']")).click();
		            
		            try {
		            	Thread.sleep(1000);
		            driver.findElement(By.xpath("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[2]/table/tbody/tr/td[2]")).click();
		          
		            }catch (Exception e)
		            {   
		            	System.out.println("");
		            	System.out.println(DateTime.getCurrentTimeStamp() +  ANSI_RED + ANSI_WHITE_BACKGROUND + " : Warning : Build " + str_component + " is in pending state, for details please check Junkins build history LATER !!! " + ANSI_RESET);
		            	skype_job_check = true; //skip execution check of this job
		            }
		            
		           // new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='out']/pre")));
		            boolean sucess = false;
		            boolean failure = false;
		            boolean aborted = false; 
		            boolean loop_condition = true;
		            if (!skype_job_check){
		            do{
		                try{
		                	System.out.print(ANSI_BLUE + "." + ANSI_RESET );
		                	Thread.sleep(12000);
		                	
		                	driver.navigate().refresh();

		                	fullResultText = driver.findElement(By.xpath("//*[@id='main-panel']/pre")).getText();

		                    boolean result = fullResultText.contains("Finished: ");
		                    
		                    loop_condition = true;
		                    
		                    if(result)
		                    	
		                    	sucess = fullResultText.contains("Finished: SUCCESS");
		                    	failure = fullResultText.contains("Finished: FAILURE");
		                    	aborted = fullResultText.contains("Finished: ABORTED");
		                    	
		                       {
				                    if(sucess)
				                    { 
				                    	System.out.println("");
				                    	System.out.println(DateTime.getCurrentTimeStamp()  + " : Info :"+ ANSI_GREEN+" ! Finished: SUCCESS ! - " + str_component + ANSI_RESET );
				                	    loop_condition = false;
				                	    job_result = true;
				                    }
				                    else if(failure)
				                    {   System.out.println("");
				                    	System.out.println(  DateTime.getCurrentTimeStamp() + " : Info :"+ ANSI_RED +" * Finished: FAILURE * - " + str_component + ANSI_RESET );
				                	    loop_condition = false;
				                	    job_result = false;
				                    }
				                    else if (aborted)
				                    {
				                    	System.out.println("");
				                    	System.out.println(  DateTime.getCurrentTimeStamp() + " : Info : " + ANSI_RED +"#### Finished: ABORTED #### -" + str_component + ANSI_RESET );
				                	    loop_condition = false;
				                	    job_result = false;
				                    }
		                        }
		                   }catch (org.openqa.selenium.TimeoutException e2){ 
			                 System.out.println(DateTime.getCurrentTimeStamp() + " : Warning : " + e2.toString());  
			               }catch (org.openqa.selenium.NoSuchElementException e1){
		                     System.out.println(DateTime.getCurrentTimeStamp() + " : Warning : " + e1.toString());  
		                   } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }while(loop_condition);

		            if(component_or_workstation){
		            	//workstation
		            	job_number = driver.findElement(By.xpath("//*[@id='breadcrumbs']/li[15]/a")).getText();
		            	Thread.sleep(100);
		            }else{
		            	//component
		            	job_number = driver.findElement(By.xpath("//*[@id='breadcrumbs']/li[13]/a")).getText();
		            	Thread.sleep(100);
	    			}
		            
		            logger.info(DateTime.getCurrentTimeStamp() + "Executing component : " + str_component);
		            logger.info(DateTime.getCurrentTimeStamp() + "========================= * Jenkins Console Output * =========================");
		            logger.info(DateTime.getCurrentTimeStamp() + "Jenkins Job Number : " + job_number);
		            logger.info(DateTime.getCurrentTimeStamp() + fullResultText);
		            logger.info(DateTime.getCurrentTimeStamp() +" : Info : Return to Component page");
		            
		            System.out.println(DateTime.getCurrentTimeStamp() + " : Info : " + str_component + " Jenkins Job Number " + job_number );
		            
		            }//End skip job check
		            
		            
		            driver.findElement(By.xpath("//*[@id='breadcrumbs']/li[9]/a")).click();

		           return (job_result);
    				
	}
		
}
