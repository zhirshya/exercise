//c++17 CLI budget calculator
//regex replace [xX] -> *; put in HashMap (like Linux uniq); map: e.g. '96 [xX*] 3'|'96*3' -> (big decimal)96*3; reduce: + (sum all numeric elements of HashMap?); *1.08|1.1|1.12|1.15

#include <iostream>
#include <algorithm>
#include <numeric>
#include <cmath>
#include <limits>
#include <iomanip>
#include <type_traits>
#include <vector>
#include <string>
//#include <string.h>
#include <cstring> //std::strlen
#include <sstream> //istringstream
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

//https://en.wikipedia.org/wiki/Unit_in_the_last_place
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

int main(int argc, char** argv){
	std::cout << "argc:[" << argc << "], argv[0]:[" << argv[0] << "]\n";
	if(1 == argc){
		std::cout << "Здравствуйте, no numbers given, nothing to do. До свидания!\n";
		return 0;
	}
/*
	std::vector<std::string> argvVectrErr{argv+1, argv+argc};
//https://www.codeproject.com/Questions/164306/convert-argv-to-something-and-back
	std::vector<std::string> argvVectr{argv, argv+argc};

//https://stackoverflow.com/questions/6361606/save-argv-to-vector-or-string
	std::vector<std::string> args;
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
//	std::stringstream ss;
//https://rosettacode.org/wiki/Determine_if_a_string_is_numeric#C.2B.2B

	std::vector<double> parsedVct;
	std::cout << "std::vector<double> parsedVct; parsedVct.empty():[" << parsedVct.empty() << "]\n";
	double parsedArg=0.0;
	double subProduct=0.0;
	double sumArgv=0.0;
	//todo meaningful pointer initialization
	char* end/* = argv[1]*/; //std::strtof, std::strtod, std::strtold
	//const char* p/* = argv[1]*/;
	char* p/* = argv[1]*/;
	//todo: c c++ maximum length of command line argument
	char numStr[256];
	char* pNumStrArray = numStr;
	char* pNumPart;
	double previousNum=0.0;
	unsigned char noNumeric=0;
	double dbl=0.0;

	//http://en.cppreference.com/w/cpp/io/manip/setprecision
	//https://stackoverflow.com/questions/554063/how-do-i-print-a-double-value-with-full-precision-using-std::cout
	//todo: doesn't reflect double's precision!
	std::cout.precision(std::numeric_limits<double>::digits10 + 2);
/*
	max_digits10 is the number of digits that are necessary to uniquely represent all distinct double values. max_digits10 represents the number of digits before and after the decimal point.

	set_precision() with fixed specifier sets the number of digits only after the decimal point.
*/
//	std::cout << std::setprecision(std::numeric_limits<long double>::digits10 + 2);

	std::cout << "const char* p; p==nullptr:[" << (p==nullptr) << "], char* end; end==nullptr:[" << (end==nullptr) << "]\n";
	/*
	 cpp/argv_sum.cpp:93:44: error: invalid operands of types ‘char’ and ‘std::nullptr_t’ to binary ‘operator==’
	 std::cout << "'\0'==nullptr:[" << ('\0'==nullptr) << "], 0==nullptr:[" << (0==nullptr) << "]\n";
	 */ 
	//std::cout << "'\0'==nullptr:[" << ('\0'==nullptr) << "], 0==nullptr:[" << (0==nullptr) << "]\n";
	std::cout << " 0==nullptr:[" << (0==nullptr) << "]\n";
	//todo: C null in C++
	std::cout << " '\\0'==0:[" << ('\0'==0) << "]\n";
	
	for(int i = 1; i < argc; ++i){
		parsedArg=strtod(argv[i],&end);
		std::cout << "parsedArg=strtod(argv[i],&end); {argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
/*
http://en.cppreference.com/w/cpp/string/byte/strtof
Return value:
Floating point value corresponding to the contents of str on success. If the converted value falls out of range of corresponding return type, range error occurs and HUGE_VAL, HUGE_VALF or HUGE_VALL is returned. If no conversion can be performed, 0 is returned and *str_end is set to str.
*/
		if(almost_equal(0.0,parsedArg,1) && 0 == strcmp(end,argv[i])){
//		if(almost_equal(0.0,parsedArg,1) && 0 == std::string(end).compare(argv[i])){
			std::cout << "almost_equal(0.0,parsedArg,1) && 0 == std::string(end).compare(argv[i]); {argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
			if(1 == i)
				continue;
			if(argc -1 == i){
				if(!almost_equal(0.0,previousNum,1)){
					parsedVct.push_back(previousNum);
					std::cout << "parsedVct.push_back(previousNum);[" << previousNum << "]\n";
				}
				break;
			}
			if(0 == noNumeric || '\0' == noNumeric){
				noNumeric=*end;
			}
		}else{
			//skip 0, 0.0f, 0.0, etc.
			if(almost_equal(0.0,parsedArg,1)){
				std::cout << "almost_equal(0.0,parsedArg,1);{argv[i]:[" << argv[i] << "], *end:[" << *end << "], parsedArg:[" << parsedArg << "]}\n";
				noNumeric=0;
				continue;
			}

			//substitute 96.3x3 37.9X6 63.9*3x7X9.9 with multiplication result
			//./argvsumc++ 90  0  81 0  75.5 \*\*\* 5  0  83 X 2  0  2\*xXxx2\*\*\*\*xXxXx\*xXxXXxx7x2X5  0  xxxx\*XXXXX\*\*xxxxxxxXX  0  79X2 x 2  0  54 \* 2  0  124.5 XXX 5  0  7\*137xxXXX\*\*  xxxx\*XXXXX  \*xxxXXXX\*  0  X\*x
			std::cout << "&(argv[i]):[" << &(argv[i]) << "], &argv[i]:[" << &argv[i] << "], argv+i:[" << argv+i << "]\n";
			//todo: boolean literal
			std::cout << "&(argv[i])==&argv[i]:[" << (&(argv[i])==&argv[i]) << "], &argv[i]==argv+i:[" << (&argv[i]==argv+i) << "]\n";
			/*
			 error: invalid operands of types ‘char*’ and ‘char**’ to binary ‘operator-’
				if(end-&(argv[i]) < std::string(argv[i]).length()){
			*/
			//int arglen = std::string(argv[i]).length();
			int arglen = strlen(argv[i]);
			if(end-argv[i] < arglen ){
				pNumPart=argv[i];
				std::cout << "end-argv[i] < strlen(argv[i]):true; end-argv[i]:[" << end-argv[i] << "]\n";
				for(p = end; arglen > p-argv[i]; p = end){
					if(42 > *p || 47 == *p || (57 < *p && 88 != *p && 120 != *p)){
						std::cout << "argument \x027" << argv[i] << "\x027 is not a valid number,/* though partially convertible,*/ better try again than continue with faulty (partial) conversion.\n";
						continue;
					}else if(42 == *p || 88 == *p || 120 == *p){
						std::cout << "multiplication sign:'" << *p << "' found. ※multiply inplace!\n";
						do ++p; while(48 > *p || 57 < *p);
						dbl=strtod(p,&end);
						std::cout << "dbl=strtod(p,&end); dbl:[" << dbl << "]\n";
						if(arglen >= end-argv[i]){  //63.9*3*7x or 63.9*3*7X or 63.9*3*7*
							if(!almost_equal(0.0,dbl,1) && !almost_equal(0.0,parsedArg,1)){
								parsedArg*=dbl;
							}
						}else{
							break;
						}
					//}else if(44 == *p){
					}else{
					/*
					http://defindit.com/ascii.html
					 	 *   \x2a \052  42
						 +   \x2b \053  43
						 ,   \x2c \054  44
						 -   \x2d \055  45
						 .   \x2e \056  46
						 X   \x58 \130  88
						 x   \x78 \170 120
					 */ 
/* todo
./argvsumc++ 9,,,876,,,543,,,219,,, XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxxxxxxxxxxxxxx\*\*\*xX\* 3
./argvsumc++ 9,,,876,,,543,,,219    XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxxxxxxxxxxxxxx\*\*\*xX\* 3
*/
						std::cout << "44==',':" << (44==',') << "\n";
						std::cout << "\x22while(44 == *p++);\x22 increment p?! (before while) p==end:" << (p==end) << "\n";
						while(44 == *p++); //Decimal and Thousands Separators
						std::cout << "\x22while(44 == *p++);\x22 increment p?! (after while) p==end:" << (p==end) << "\n";
						/*
						void *memcpy(void *s1, const void *s2, size_t n);
						Copies n bytes from the object pointed to by s2 into the object
						pointed to by s1 . A pointer to the resulting object is returned.
						 */ 
						//memcpy(numStr,p,end-p);
						memcpy(pNumStrArray,pNumPart,end-pNumPart);
						std::cout << "memcpy(numStr,pNumPart,end-pNumPart); numStr:[" << numStr << "]\n";
						/*
						error: incompatible types in assignment of ‘long int’ to ‘char [256]’
						numStr+=end-pNumPart;

						error: incompatible types in assignment of ‘int’ to ‘char [256]’
						numStr+=(int)(end-pNumPart);

						error: invalid operands of types ‘char [256]’ and ‘char*’ to binary ‘operator+’
						numStr+=(char*)(end-pNumPart);
						 */
						//http://www.cs.bu.edu/teaching/cpp/string/array-vs-ptr/
						pNumStrArray+=end-pNumPart;  // error: incompatible types in assignment of ‘long int’ to ‘char [256]’
						pNumPart=p;

						dbl=strtod(p,&end);
						std::cout << "dbl=strtod(p,&end); dbl:[" << dbl << "]\n";
						if(arglen >= end-argv[i]){  //63.9*3*7x or 63.9*3*7X or 63.9*3*7*
							if(!almost_equal(0.0,dbl,1) && !almost_equal(0.0,parsedArg,1)){
								parsedArg*=dbl;
							}
						}else{
							break;
						}
					}
					std::cout << "end-argv[i] < strlen(argv[i]):true; p and *p inside 'non-numeric(*p) test' for loop before 'strtod(p,&end)': p:[" << p << "], *p:[" << *p << "]\n";
					/* 63.9*3*7x or 63.9*3*7X or 63.9*3*7*
					dbl=strtod(p,&end);
					std::cout << "dbl=strtod(p,&end); dbl:[" << dbl << "]\n";
					if(arglen >= end-argv[i]){  //63.9*3*7x or 63.9*3*7X or 63.9*3*7*
						if(!almost_equal(0.0,dbl,1) && !almost_equal(0.0,parsedArg,1)){
							parsedArg*=dbl;
						}
					}else{
						break;
					}
					*/
				}

				memset(numStr,0,sizeof(numStr));
//				memset(pNumStrArray,0,sizeof(pNumStrArray));

				noNumeric=0; //important!
				std::cout << "end-argv[i] < strlen(argv[i]):true; argv[i]:[" << argv[i] << "], parsedArg:[" << parsedArg << "]\n";
			}
			//if(0 != noNumeric && !almost_equal(0.0,previousNum,1)){
			if(0 != noNumeric && '\0' != noNumeric){
				if(almost_equal(0.0,subProduct,1)){
					if(!almost_equal(0.0,previousNum,1)){
						subProduct=previousNum*parsedArg;
						std::cout << "subProduct=(previousNum*parsedArg);[" << subProduct << "]\n";
					}else{
						previousNum=parsedArg;
					}
				}else{
					subProduct*=parsedArg;
					std::cout << "subProduct*=parsedArg;[" << subProduct << "]\n";

					if(argc-1 == i){
						parsedVct.push_back(subProduct);
						std::cout << "parsedVct.push_back(subProduct);[" << subProduct << "]\n";
					}
				}
			}else{
				if(!almost_equal(0.0,subProduct,1)){
					parsedVct.push_back(subProduct);
					std::cout << "parsedVct.push_back(subProduct);[" << subProduct << "]\n";
					subProduct=0.0;
				}else if(!almost_equal(0.0,previousNum,1)){
					parsedVct.push_back(previousNum);
					std::cout << "parsedVct.push_back(previousNum);[" << previousNum << "]\n";
				}

				if(argc-1 == i && !almost_equal(0.0,parsedArg,1)){
					parsedVct.push_back(parsedArg);
					std::cout << "parsedVct.push_back(parsedArg);[" << parsedArg << "]\n";
				}

				previousNum=parsedArg;
			}
		}
		noNumeric=*end; //unnecessary?
	}
	
	//std::cout << "before accumulating parsed arguments' std::vector, previousNum:[" << previousNum << "]\n";
	if(!parsedVct.empty()){
		//https://stackoverflow.com/questions/22448747/using-stdaccumulate-to-add-floats-with-best-precision
		sumArgv = std::accumulate(std::begin(parsedVct),std::end(parsedVct),0.0);
		double sumArgv8prcnt = sumArgv*1.08;
		double sumArgv10prcnt = sumArgv*1.1;
		double sumArgv12prcnt = sumArgv*1.12;
		double sumArgv15prcnt = sumArgv*1.15;
		double sumArgv18prcnt = sumArgv*1.18;
		double sumArgv20prcnt = sumArgv*1.2;
		double sumArgv25prcnt = sumArgv*1.25;
		std::cout << "sumArgv:[" << sumArgv << "]\n";
		std::cout << "sumArgv*1.08:[" << sumArgv8prcnt << "], round:[" << round(sumArgv8prcnt) << "], floor:[" << floor(sumArgv8prcnt) << "]\n";
		std::cout << "sumArgv*1.1:[" << sumArgv10prcnt << "], round:[" << round(sumArgv10prcnt) << "], floor:[" << floor(sumArgv10prcnt) << "]\n";
		std::cout << "sumArgv*1.12:[" << sumArgv12prcnt << "], round:[" << round(sumArgv12prcnt) << "], floor:[" << floor(sumArgv12prcnt) << "]\n";
		std::cout << "sumArgv*1.15:[" << sumArgv15prcnt << "], round:[" << round(sumArgv15prcnt) << "], floor:[" << floor(sumArgv15prcnt) << "]\n";
		std::cout << "sumArgv*1.18:[" << sumArgv18prcnt << "], round:[" << round(sumArgv18prcnt) << "], floor:[" << floor(sumArgv18prcnt) << "]\n";
		std::cout << "sumArgv*1.2:[" << sumArgv20prcnt << "], round:[" << round(sumArgv20prcnt) << "], floor:[" << floor(sumArgv20prcnt) << "]\n";
		std::cout << "sumArgv*1.25:[" << sumArgv25prcnt << "], round:[" << round(sumArgv25prcnt) << "], floor:[" << floor(sumArgv25prcnt) << "]\n";
	}else{
		std::cout << "Found vector empty, nothing to sum. До свидания.\n";
	}
}
