@echo off
rem	Xerox専用複合機ドライバインストール用バッチファイル
rem **************************************************************************************
rem  PRT_SETUP.BAT
rem 
rem  【機能】	Xerox専用複合機ドライバインストーラー呼び出し用バッチファイル
rem  【引数】	なし
rem  【戻り値】	なし
rem 
rem   Ver1.0	2011/06/08     					Phasein Nomura
rem   Copyright(c) Phasein Inc.
rem **************************************************************************************
Set DRIVERROOT=\\pise014\hbn\IS_82\printer
Set VENDOR=XEROX
Set MODEL=DC4C7780
Set COMPNAME=NST
Set CLASS=Multi
Set AREA=EAST
Set FLOANAME=EAST_8F
Set STRPRINTERNAME=NSTFP19
Set MODENAME=BIN0白黒片面
Set STRSETUP=setup.exe

cls
echo.
echo.
echo インストール準備中です。しばらくお待ちください。
echo.
echo （この画面は閉じないでください。）

IF NOT EXIST C:\TEMP MD C:\TEMP >nul
IF NOT EXIST C:\TEMP\%VENDOR% MD C:\TEMP\%VENDOR% >nul

rem	ドライバコアのコピー
rem XCOPY /e /i /r /y \\qise007\hbn\IS_82\printer\XEROX\DC4C4470\Driver_Core\*.* C:\TEMP\%VENDOR%\ >nul
XCOPY /e /i /r /y %DRIVERROOT%\%VENDOR%\%MODEL%\Driver_Core\*.* C:\TEMP\%VENDOR%\

rem	ドライバ差分のコピー
rem XCOPY /e /i /r /y \\qise007\hbn\IS_82\printer\XEROX\DC4C4470\DEV\BIN0白黒片面\*.* C:\TEMP\%VENDOR%\ >nul
XCOPY /e /i /r /y %DRIVERROOT%\%VENDOR%\%MODEL%\DEV\%MODENAME%\*.* C:\TEMP\%VENDOR%\

rem	固有ファイルのコピー
rem XCOPY /e /i /r /y \\qise007\hbn\IS_82\printer\AST\Multi\Suzukityou\ASTD27F\ASTLFP72\BIN0白黒片面\*.* C:\TEMP\%VENDOR%\ >nul
XCOPY /e /i /r /y %DRIVERROOT%\%COMPNAME%\%CLASS%\%AREA%\%FLOANAME%\%STRPRINTERNAME%\%MODENAME%\*.* C:\TEMP\%VENDOR%\

C:\TEMP\%VENDOR%\%STRSETUP% >nul

IF EXIST C:\TEMP\%VENDOR% DEL C:\TEMP\%VENDOR% /f /q /s >nul
IF EXIST C:\TEMP\%VENDOR% RD C:\TEMP\%VENDOR% /q /s >nul
