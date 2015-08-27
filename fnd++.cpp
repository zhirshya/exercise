#include <iostream>
#include <sstream>
#include <string>
#include <stdlib.h> //system()

//https://www.youtube.com/watch?v=Q_0_1mKTlnY
//uuid may contain -, so check the length of resulting substring!
//call shell command find, as "find . -iname '*Q_0_1mKTlnY*'"
//echo https://www.youtube.com/watch?v=Q_0_1mKTlnY | gawk -F '=' '{print $2}'

//1. c++ 11 c++ 14 array to vector
int main(int argc, char* argv[])
{
    using namespace std;

    bool argv_argc_null = (nullptr == argv[argc]);
    cout << std::boolalpha << "nullptr == argv[argc] : [" << argv_argc_null << "]" << endl;
    
    std::ostringstream oss;
    oss << std::boolalpha << "1 as [" << bool(1) << "], 0 as [" << bool(0) << "]";
//    oss << "1 as [" << bool(1) << "], 0 as [" << bool(0) << "]"; //1 as [1], 0 as [0]
    cout << oss.str() << endl; 

/*    for(const auto& v : argv)
    {
	cout << v << endl;
    }
*/
    if(2 <= argc)
    {
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
//    return 0; //omittable in c++11
}
