Option Explicit
dim path, para1, para2 , FunctionName, ServiceName, output, objArgs
Const WshRunning = 0
Const WshFinished = 1
Const WshFailed = 2

set objArgs = wscript.arguments

FunctionName = objArgs(0)
ServiceName = objArgs(1)

path = " ""C:\Program Files\NSClient++\scripts\acpItmcMonitoring.jar"" "
para1 = FunctionName 
para2 = ServiceName
'msgbox(path)
'msgbox(para1)
'msgbox(para2)

Dim shell : Set shell = CreateObject("WScript.Shell")
Dim exec : Set exec = shell.Exec( "java -jar " & path & " " &  para1 &  " " &  para2 )

While exec.Status = WshRunning
    WScript.Sleep 50
Wend

If exec.Status = WshFailed Then
    output = exec.StdErr.ReadAll
Else
    output = exec.StdOut.ReadAll
End If

     if InStr(1, output, "CRITICAL") > 0 then
		WScript.Echo output
		wscript.quit(2)
	end if
	
	if InStr(1, output, "WARNING") > 0 then
		WScript.Echo output
		wscript.quit(1)
	end if
		
	if Len(Trim(output)) = 0 then
		WScript.Echo para2 & " not found "
		wscript.quit(1)
	end if
	
    if InStr(1, output, "Installed but stopped") > 0 then
		WScript.Echo output
		wscript.quit(1)
	end if  

    	WScript.Echo output
		wscript.quit(0)

	