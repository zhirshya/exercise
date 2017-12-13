#c++17 CLI budget calculator
#regex replace [xX] -> *; put in HashMap (like Linux uniq); map: e.g. '96 [xX*] 3'|'96*3' -> (big decimal)96*3; reduce: + (sum all numeric elements of HashMap?); *1.08|1.1|1.12|1.15
#python to c++17 converter

#Rang:Minimal, Header only Modern c++ library for colors in your terminal

#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <sstream> // for istringstream
#include <cstdlib> #std::strtof, std::strtod, std::strtold

/*
std::wcstof, std::wcstod, std::wcstold
  C++  Strings library  Null-terminated wide strings 
Defined in header <cwchar>
float       wcstof( const wchar_t* str, wchar_t** str_end ); (since C++11)
double      wcstod( const wchar_t* str, wchar_t** str_end );
long double wcstold( const wchar_t* str, wchar_t** str_end ); (since C++11)
*/

using namespace std;

int main(int argc, char** argv) {
	vector<string> argvVectrErr{argv+1, argv+argc};
#https://www.codeproject.com/Questions/164306/convert-argv-to-something-and-back
	vector<string> argvVectr{argv, argv+argc};

#https://stackoverflow.com/questions/6361606/save-argv-to-vector-or-string
	vector<string> args;
	args.insert(args.end(), argv+1, argv+argc);

#https://stackoverflow.com/questions/21807658/check-if-the-input-is-a-number-or-string-in-c
/*
#include <sstream>

template<typename T>
bool isNumber(T x){
   std::string s;
   std::stringstream ss;
   ss << x;
   ss >>s;
   if(s.empty() || std::isspace(s[0]) || std::isalpha(s[0])) return false ;
   char * p ;
   strtod(s.c_str(), &p) ;
   return (*p == 0) ;
}
*/

	stringstream ss;

#https://rosettacode.org/wiki/Determine_if_a_string_is_numeric#C.2B.2B

}
