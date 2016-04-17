cd /d C:\DevTools\workspace\wz11-wz1139\bin
start /wait /b mvn_clean.bat
cd /d C:\DevTools\workspace\wz11-wz1139\bin
start /wait /b mvn_build_development.bat
echo finish build of wz11-wz1139

cd /d C:\DevTools\workspace\wz11-wz1140\bin
start /wait /b mvn_clean.bat
cd /d C:\DevTools\workspace\wz11-wz1140\bin
start /wait /b mvn_build_development.bat
echo finish build of wz11-wz1140
@pause