@echo off
REM Este archivo lo creé para ejecutar el pipeline de HTR y TTS

REM tenía diferentes versiones de python y no me funcionaba, así que usé anaconda para ejecutar las dos IA's
set PYTHON_EXE=D:\Anaconda\python.exe

REM Check if Python exists
if not exist "%PYTHON_EXE%" (
    echo Error: Could not find Python at %PYTHON_EXE%
    echo Please check your Anaconda installation path.
    pause
    exit /b 1
)

echo Ejecutando Script...
"%PYTHON_EXE%" main.py --image data/test_image.jpg --output data/output.wav

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Success! Audio saved to data/output.wav
) else (
    echo.
    echo Pipeline failed with error code %ERRORLEVEL%
)

pause
