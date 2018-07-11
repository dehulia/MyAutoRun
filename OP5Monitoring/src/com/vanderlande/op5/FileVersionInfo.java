package com.vanderlande.op5;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javaxt.io.Jar;


public class FileVersionInfo
{
    interface Version extends Library {

        Version INSTANCE = (Version) Native.loadLibrary("Version", Version.class, W32APIOptions.UNICODE_OPTIONS);

        public int GetFileVersionInfoSizeW(String lptstrFilename, int dwDummy);

        public boolean GetFileVersionInfoW(String lptstrFilename, int dwHandle,
            int dwLen, Pointer lpData);

        public int VerQueryValueW(Pointer pBlock, String lpSubBlock,
            PointerByReference lplpBuffer, IntByReference puLen);

    }

    public static class VS_FIXEDFILEINFO extends com.sun.jna.Structure {
        public int dwSignature;
        public int dwStrucVersion;
        public int dwFileVersionMS;
        public int dwFileVersionLS;
        public int dwProductVersionMS;
        public int dwProductVersionLS;
        public int dwFileFlagsMask;
        public int dwFileFlags;
        public int dwFileOS;
        public int dwFileType;
        public int dwFileSubtype;
        public int dwFileDateMS;
        public int dwFileDateLS;

           public VS_FIXEDFILEINFO(com.sun.jna.Pointer p){
                super(p);
           }
    }

    public static String getJarFileVersionJAR(String filepath)
    {
    
    Map<String, String> jarsWithVersionFound   = new LinkedHashMap<String, String>();
    List<String> jarsWithNoManifest     = new LinkedList<String>();
    List<String> jarsWithNoVersionFound = new LinkedList<String>();

    File file = new File(filepath);

        String fileName = file.getName();
        try
        {
            String jarVersion = new Jar(file).getVersion();

            if(jarVersion == null)
                jarsWithNoVersionFound.add(fileName);
            else
                jarsWithVersionFound.put(fileName, jarVersion);

        }
        catch(Exception ex)
        {
            jarsWithNoManifest.add(fileName);
        }

    for(Entry<String, String> jarName : jarsWithVersionFound.entrySet())
        return filepath + " : " + jarName.getValue();

    for(String jarName : jarsWithNoVersionFound)
    	return filepath + " Version not found ";

    for(String jarName : jarsWithNoManifest)
    	return filepath + " Version not found ";
	
    return fileName + " has error ";
    
}
    static String  getFileVersionEXE(String filepath) {

    	try{
    		int dwDummy = 0;
            int versionlength = Version.INSTANCE.GetFileVersionInfoSizeW( filepath, dwDummy);

            byte[] bufferarray = new byte[versionlength];
            Pointer lpData = new Memory(bufferarray.length);    

            PointerByReference lplpBuffer = new PointerByReference();
            IntByReference puLen = new IntByReference();
            boolean FileInfoResult = Version.INSTANCE.GetFileVersionInfoW(filepath,0, versionlength, lpData);
           
            int verQueryVal = Version.INSTANCE.VerQueryValueW(lpData,"\\", lplpBuffer, puLen);
    		
            VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(lplpBuffer.getValue());
            
            lplpBufStructure.read();

	        lplpBufStructure.read();
	        short[] rtnData = new short[4];
	        rtnData[0] = (short) (lplpBufStructure.dwFileVersionMS >> 16);
	        rtnData[1] = (short) (lplpBufStructure.dwFileVersionMS & 0xffff);
	        rtnData[2] = (short) (lplpBufStructure.dwFileVersionLS >> 16);
	        rtnData[3] = (short) (lplpBufStructure.dwFileVersionLS & 0xffff);
	       
	        return filepath + " : " +  rtnData[0] + "." + rtnData[1] + "." + rtnData[2] + "." + rtnData[3];
	    	}
	        catch (Exception e) {return "Version not found";}
    	
    }

        
    public static String getJarFileVersionDLL(String filepath)
    {
       String cmd = "wmic datafile where name=\"" + filepath.replace("\\", "\\\\") + "\" get version";
       String version="";
    	 try {
    	        final Process process = Runtime.getRuntime().exec(cmd );
    	         final InputStream in = process.getInputStream();
    	         int ch;
    	         
    	         while((ch = in.read()) != -1 ) {
    	        	 version = version + (char)ch;
    	         }
    	         if (version.trim().equals("")){
    	        	 version = filepath + " : " + "Not found";}
    	         else{
    	        	 version = filepath + " : " + version.toString().replaceAll("[\\t\\n\\r]+"," ").replace("Version", "").trim();
    	         }
    	        } catch (IOException e) {
    	        	{return "Version not found";}
    	          }
    	return version;
    }
    

}
