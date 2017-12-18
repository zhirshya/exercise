//c++17 CLI budget calculator
//regex replace [xX] -> *; put in HashMap (like Linux uniq); map: e.g. '96 [xX*] 3'|'96*3' -> (big decimal)96*3; reduce: + (sum all numeric elements of HashMap?); *1.08|1.1|1.12|1.15
//python to c++17 converter

//Rang:Minimal, Header only Modern c++ library for colors in your terminal

#include <iostream>
#include <algorithm>
#include <numeric>
#include <cmath>
#include <limits>
#include <iomanip>
#include <type_traits>
#include <vector>
#include <string>
#include <cstring>
#include <sstream> // for istringstream
#include <cstdlib> //std::strtof, std::strtod, std::strtold

//http://en.cppreference.com/w/cpp/numeric/math/round
#pragma STDC FENV_ACCESS ON

/*
std::wcstof, std::wcstod, std::wcstold
  C++  Strings library  Null-terminated wide strings 
Defined in header <cwchar>
float       wcstof( const wchar_t* str, wchar_t** str_end ); (since C++11)
double      wcstod( const wchar_t* str, wchar_t** str_end );
long double wcstold( const wchar_t* str, wchar_t** str_end ); (since C++11)
*/

//http://en.cppreference.com/w/cpp/types/numeric_limits/epsilon
template<class T>
typename std::enable_if<!std::numeric_limits<T>::is_integer, bool>::type
    almost_equal(T x, T y, int ulp)
{
    // the machine epsilon has to be scaled to the magnitude of the values used
    // and multiplied by the desired precision in ULPs (units in the last place)
    return std::abs(x-y) <= std::numeric_limits<T>::epsilon() * std::abs(x+y) * ulp
    // unless the result is subnormal
           || std::abs(x-y) < std::numeric_limits<T>::min();
}

using namespace std;

int main(int argc, char** argv){
	cout << "argc:[" << argc << "]\n";
	if(1 == argc){
		cout << "Здравствуйте, nothing to do. До свидания!\n";
		return 0;
	}
/*
	vector<string> argvVectrErr{argv+1, argv+argc};
//https://www.codeproject.com/Questions/164306/convert-argv-to-something-and-back
	vector<string> argvVectr{argv, argv+argc};

//https://stackoverflow.com/questions/6361606/save-argv-to-vector-or-string
	vector<string> args;
	args.insert(args.end(), argv+1, argv+argc);
*/

//https://stackoverflow.com/questions/21807658/check-if-the-input-is-a-number-or-string-in-c
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
//	stringstream ss;
//https://rosettacode.org/wiki/Determine_if_a_string_is_numeric#C.2B.2B

	vector<float> parsedVct;
	float parsedArg=0;
	float subProduct=0;
	float sumArgv=0;
	//todo meaningful pointer initialization
	char* end/* = argv[1]*/; //std::strtof, std::strtod, std::strtold
	const char* p/* = argv[1]*/;
	float previousNum=0;
	unsigned char noNumeric=0;
	float f=0;

	cout << "(trace):const char* p; p==nullptr:[" << (p==nullptr) << "], char* end; end==nullptr:[" << (end==nullptr) << "]\n";
	/*
	 cpp/argv_sum.cpp:93:44: error: invalid operands of types ‘char’ and ‘std::nullptr_t’ to binary ‘operator==’
	 cout << "(trace):'\0'==nullptr:[" << ('\0'==nullptr) << "], 0==nullptr:[" << (0==nullptr) << "]\n";
	 */ 
	//cout << "(trace):'\0'==nullptr:[" << ('\0'==nullptr) << "], 0==nullptr:[" << (0==nullptr) << "]\n";
	cout << "(trace): 0==nullptr:[" << (0==nullptr) << "]\n";
	
	for(int i = 1; i < argc; ++i){
		parsedArg=strtof(argv[i],&end);
		cout << "(trace):parsedArg=strtof(argv[i],&end);{argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
		//http://en.cppreference.com/w/cpp/types/numeric_limits/epsilon
		//if(0 == parsedArg && 0 == strcmp(end,argv[i])){
		//todo: ./argvsumc++  90 81 83X2 280 158x2 54\*2
/*
http://en.cppreference.com/w/cpp/string/byte/strtof
Return value:
Floating point value corresponding to the contents of str on success. If the converted value falls out of range of corresponding return type, range error occurs and HUGE_VAL, HUGE_VALF or HUGE_VALL is returned. If no conversion can be performed, ​0​ is returned and *str_end is set to str.
*/
		if(almost_equal(0.0f,parsedArg,3) && 0 == strcmp(end,argv[i])){  //0 == string(end).compare(argv[i])
			cout << "(trace):almost_equal(0.0f,parsedArg,3) && 0 == strcmp(end,argv[i]);{argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
			if(1 == i)
				continue;
			if(argc -1 == i)
				break;
			if(0 == noNumeric || '\0' == noNumeric){
				noNumeric=*end;
			}
		}else{
			//if(0.0f == parsedArg)
			if(almost_equal(0.0f,parsedArg,3)){
				cout << "(trace):almost_equal(0.0f,parsedArg,3);{argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
				noNumeric=0;
				continue;
			}

			//substitute 96.3x3 37.9X6 63.9*3*7 with their product
			//./argvsumc++  90 81 83 X 2 2\*xXxx2\*\*\*\*xXxXx\*xXxXXxx7x2X5 79X2 x 2 54 \* 2 7\*137xxXXX\*\*
			cout << "(trace):&(argv[i]):[" << &(argv[i]) << "], &argv[i]:[" << &argv[i] << "], argv+i:[" << argv+i << "]\n";
			//todo: boolean literal
			cout << "(trace):&(argv[i])==&argv[i]:[" << (&(argv[i])==&argv[i]) << "], &argv[i]==argv+i:[" << (&argv[i]==argv+i) << "]\n";
			/*
			 error: invalid operands of types ‘char*’ and ‘char**’ to binary ‘operator-’
				if(end-&(argv[i]) < string(argv[i]).length()){
			*/
			//if(end-&(argv[i]) < string(argv[i]).length()){
			int arglen = string(argv[i]).length();
			if(end-argv[i] < arglen ){
				cout << "(trace):end-argv[i] < string(argv[i]).length():true; end-argv[i]:[" << end-argv[i] << "]\n";
				//for(p = argv[i]; p != end;){
				for(p = argv[i]; arglen > p-argv[i];){
					p = end;
					for(; 43 > *p || 47 == *p || 57 < *p; ++p); // + , - . (recognize signs, Decimal and Thousands Separators)
					//for(; 48 > *p || *p > 57; ++p);
					cout << "(trace):end-argv[i] < string(argv[i]).length():true; p and *p after *p non-numeric test for loop: p:[" << p << "], *p:[" << *p << "]\n";
					f=strtof(p,&end);
					cout << "(trace):f=strtof(p,&end); f:[" << f << "]\n";
					if(arglen >= end-argv[i]){ //63.9*3*7x or 63.9*3*7X or 63.9*3*7*
						if(0 != f){
						//cout << "(trace):f=strtof(p,&end); f:[" << f << "]\n";
							parsedArg*=f;
						}
					}else{
						break;
					}
				}
				noNumeric=0; //important!
				cout << "(trace):end-argv[i] < string(argv[i]).length():true; argv[i]:[" << argv[i] << "], parsedArg:[" << parsedArg << "]\n";
			}
			//if(0 != noNumeric && 0 != previousNum){
			//if(0 != noNumeric && !almost_equal(0.0f,previousNum,3)){
			if(0 != noNumeric && '\0' != noNumeric){
				//if(0 == subProduct){
				if(almost_equal(0.0f,subProduct,3)){
					if(!almost_equal(0.0f,previousNum,3)){
						subProduct=previousNum*parsedArg;
						cout << "(trace):subProduct=(previousNum*parsedArg);[" << subProduct << "]\n";
//						previousNum=0;
					}else{
						previousNum=parsedArg;
					}
				}else{
					subProduct*=parsedArg;
					cout << "(trace):subProduct*=parsedArg;[" << subProduct << "]\n";
//					previousNum=0;
				}

				if(argc-1 == i && !almost_equal(0.0f,subProduct,3)){
					parsedVct.push_back(subProduct);
					cout << "(trace):parsedVct.push_back(subProduct);[" << subProduct << "]\n";
//					subProduct=0;
				}
			}else{
				//if(0 != subProduct){
				if(!almost_equal(0.0f,subProduct,3)){
					parsedVct.push_back(subProduct);
					cout << "(trace):parsedVct.push_back(subProduct);[" << subProduct << "]\n";
					subProduct=0;
				}else if(!almost_equal(0.0f,previousNum,3)){
					parsedVct.push_back(previousNum);
					cout << "(trace):parsedVct.push_back(previousNum);[" << previousNum << "]\n";
				}

				if(argc-1 == i){
					parsedVct.push_back(parsedArg);
					cout << "(trace):parsedVct.push_back(parsedArg);[" << parsedArg << "]\n";
				}

				previousNum=parsedArg;
			}
		}
		noNumeric=*end;
	}
	
	cout << "before accumulating float vector, previousNum:[" << previousNum << "]\n";
	if(!almost_equal(0.0f,previousNum,3)){
		//sumArgv = accumulate(begin(parsedVct),end(parsedVct),0);
		sumArgv = accumulate(parsedVct.begin(),parsedVct.end(),0);
		float sumArgv8prcnt = sumArgv*1.08;
		float sumArgv10prcnt = sumArgv*1.1;
		float sumArgv12prcnt = sumArgv*1.12;
		float sumArgv15prcnt = sumArgv*1.15;
		float sumArgv18prcnt = sumArgv*1.18;
		float sumArgv20prcnt = sumArgv*1.2;
		float sumArgv25prcnt = sumArgv*1.25;
		cout << "sumArgv:[" << sumArgv << "]\n";
		cout << "sumArgv*1.08:[" << sumArgv8prcnt << "], round:[" << round(sumArgv8prcnt) << "], floor:[" << floor(sumArgv8prcnt) << "]\n";
		cout << "sumArgv*1.1:[" << sumArgv10prcnt << "], round:[" << round(sumArgv10prcnt) << "], floor:[" << floor(sumArgv10prcnt) << "]\n";
		cout << "sumArgv*1.12:[" << sumArgv12prcnt << "], round:[" << round(sumArgv12prcnt) << "], floor:[" << floor(sumArgv12prcnt) << "]\n";
		cout << "sumArgv*1.15:[" << sumArgv15prcnt << "], round:[" << round(sumArgv15prcnt) << "], floor:[" << floor(sumArgv15prcnt) << "]\n";
		cout << "sumArgv*1.18:[" << sumArgv18prcnt << "], round:[" << round(sumArgv18prcnt) << "], floor:[" << floor(sumArgv18prcnt) << "]\n";
		cout << "sumArgv*1.2:[" << sumArgv20prcnt << "], round:[" << round(sumArgv20prcnt) << "], floor:[" << floor(sumArgv20prcnt) << "]\n";
		cout << "sumArgv*1.25:[" << sumArgv25prcnt << "], round:[" << round(sumArgv25prcnt) << "], floor:[" << floor(sumArgv25prcnt) << "]\n";
	}else{
		cout << "Nothing to sum. Goodbye.\n";
	}
}
