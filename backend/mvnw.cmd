@echo off
setlocal

set "MAVEN_VERSION=3.9.9"
set "BASE_DIR=%~dp0"
set "MVN_HOME=%BASE_DIR%.mvn\apache-maven-%MAVEN_VERSION%"
set "MVN_EXE=%MVN_HOME%\bin\mvn.cmd"

if exist "%MVN_EXE%" goto run

echo Downloading Maven %MAVEN_VERSION% for local wrapper...
if not exist "%BASE_DIR%.mvn" mkdir "%BASE_DIR%.mvn"

set "ZIP_PATH=%BASE_DIR%.mvn\apache-maven-%MAVEN_VERSION%-bin.zip"
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$url='https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip';" ^
  "Invoke-WebRequest -Uri $url -OutFile '%ZIP_PATH%';" ^
  "Expand-Archive -Path '%ZIP_PATH%' -DestinationPath '%BASE_DIR%.mvn' -Force"

if errorlevel 1 (
  echo Failed to download Maven wrapper dependencies.
  exit /b 1
)

:run
call "%MVN_EXE%" %*
exit /b %errorlevel%
