#include <iostream>
#include <string>
#include <stdlib.h> //system()

//https://www.youtube.com/watch?v=Q_0_1mKTlnY
//uuid may contain -, so check the length of resulting substring!
//call shell command find, as "find . -iname '*Q_0_1mKTlnY*'"
//echo https://www.youtube.com/watch?v=Q_0_1mKTlnY | gawk -F '=' '{print $2}'

//1. c++ 11 c++ 14 array to vector
int main(int argc, char* argv[])
{
    if(2 <= argc)
    {
	using namespace std;
	cout << "argc: " << argc << endl;
	string arg{argv[1]};
	cout << "1st arg: [" << arg << "]" << endl;

	arg = arg.substr(arg.find('=')+1);
	cout << "substr to send to system(): [" << arg << "]" << endl;
	string syscmd{"find /home/r/tekvid -iname '*"};
	syscmd.append(arg).append("*'");
	cout << "invoke shell command: [" << syscmd << "]" << endl;

	system(syscmd.c_str());
    }
    return 0;
}
