#include <iostream>
#include <string>
#include <stdlib.h>

//https://www.youtube.com/watch?v=Q_0_1mKTlnY
//uuid may contain -, so check the length of resulting substring!
//call shell command find, as "find . -iname '*Q_0_1mKTlnY*'"
//echo https://www.youtube.com/watch?v=Q_0_1mKTlnY | gawk -F '=' '{print $2}'

//1. c++ 11 c++ 14 array to vector
//2. c++ 11 c++ 14 string concatenation
//3. c++ 11 c++ 14 string to char*
int main(int argc, char* argv[])
{
	using namespace std;
	cout << argc << endl;
	string arg{argv[1]};
	cout << arg << endl;

	arg = arg.substr(arg.find('=')+1);
	cout << arg << endl;
	string syscmd{"find /home/r/tekvid -iname '*"};
	syscmd.append(arg).append("*'");
	cout << syscmd << endl;

	system(syscmd.c_str());
	return 0;
}
