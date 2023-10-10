@echo off
SETLOCAL ENABLEEXTENSIONS
setlocal enableDelayedExpansion

set JAVADIR="C:\Program Files\Java\jdk-11.0.8\bin\java.exe"
set /a c=1

"C:\Program Files\Java\jdk-11.0.8\bin\java.exe" -jar "B:\Program Files\OpenOSRS\ExternalPlugins\release\Obfuscators\ZKM\ZKM.jar" "B:\Program Files\OpenOSRS\ExternalPlugins\release\Obfuscators\ZKM\configAllZKMBinscure.txt"

for %%v in (./Obfuscated/Important/*.jar) do (
    echo "Binscure Obfuscating Plugin: [!c!] %%v"
		
		echo %%v|findstr /i /L "API">nul
		if errorlevel 1 (
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'input:.*', 'input: ./Obfuscated/Important/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'output:.*', 'output: ./Obfuscated/Blaur/Binscured/Injector/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'classPrefix:.*', 'classPrefix: \"!c!\"' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			%JAVADIR% -jar Obfuscators\Binsecure\binscure.jar Obfuscators\Binsecure\binscureConfigAPIOld.yml 
		) else (
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'input:.*', 'input: ./Obfuscated/Important/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'output:.*', 'output: ./Obfuscated/Blaur/Binscured/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			powershell -Command "(gc Obfuscators\Binsecure\binscureConfigAPIOld.yml) -replace 'classPrefix:.*', 'classPrefix: \"!c!\"' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigAPIOld.yml"
			%JAVADIR% -jar Obfuscators\Binsecure\binscure.jar Obfuscators\Binsecure\binscureConfigAPIOld.yml 
		)
	
	set /a c=c+1
)

TIMEOUT 2

for %%v in (./Obfuscated/*.jar) do (
    
    echo %%v|findstr /i /L "DZ">nul
    if errorlevel 1 (
        Rem file name does not contain DZ
    ) else (
        Rem file name contains DZ
		echo "Binscure Obfuscating Plugin: [!c!] %%v"
    
        powershell -Command "(gc Obfuscators\Binsecure\binscureConfigPluginOld.yml) -replace 'input:.*', 'input: ./Obfuscated/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigPluginOld.yml"
        powershell -Command "(gc Obfuscators\Binsecure\binscureConfigPluginOld.yml) -replace 'output:.*', 'output: ./Obfuscated/Blaur/Binscured/%%v' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigPluginOld.yml"
        powershell -Command "(gc Obfuscators\Binsecure\binscureConfigPluginOld.yml) -replace 'classPrefix:.*', 'classPrefix: \"!c!\"' | Out-File -encoding ASCII Obfuscators\Binsecure\binscureConfigPluginOld.yml"
        %JAVADIR% -jar Obfuscators\Binsecure\binscure.jar Obfuscators\Binsecure\binscureConfigPluginOld.yml

    )
	
	set /a c=c+1
)

pause
endlocal