@echo off
cd /d "%~dp0"

echo Compiling forge + common...
call "%~dp0gradlew.bat" :forge:classes :common:classes
if errorlevel 1 (echo Compilation failed! && exit /b 1)

echo Merging resources into classes directories...
xcopy /E /Y /I "forge\build\resources\main" "forge\build\classes\java\main" >nul
xcopy /E /Y /I "common\build\resources\main" "common\build\classes\java\main" >nul
echo Resources merged.

echo Creating forge\bin\main junction...
if not exist "forge\bin" mkdir "forge\bin"
if exist "forge\bin\main" (
    echo forge\bin\main already exists.
) else (
    mklink /J "forge\bin\main" "forge\build\classes\java\main"
    echo Junction created: forge\bin\main
)

echo Creating common\bin\main junction...
if not exist "common\bin" mkdir "common\bin"
if exist "common\bin\main" (
    echo common\bin\main already exists.
) else (
    mklink /J "common\bin\main" "common\build\classes\java\main"
    echo Junction created: common\bin\main
)

echo Done! You can now run "forge - Client" from VSCode (Ctrl+F5).
pause
