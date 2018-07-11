package com.vi.acp.autorun;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}
	
	public static String getCurrentTimeStampFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}
	
}
