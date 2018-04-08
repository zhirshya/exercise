#include <iostream>
#include <string>
//#include <cstring> //std::size_t http://en.cppreference.com/w/cpp/types/size_t

/*
 * Possible alternative is to use character sequence buffer to track index of delimiter, as "If idx is not a null pointer, the function also sets the value of idx to the position of the first character in str after the number." in http://www.cplusplus.com/reference/string/stoi
*/

//https://www.geeksforgeeks.org/converting-strings-numbers-cc/
//https://stackoverflow.com/questions/14265581/parse-split-a-string-in-c-using-string-delimiter-standard-c/14266139

//https://yvdinesh.quora.com/Rata-Die-Algorithm
int rdn(int y, int m, int d) { /* Rata Die day one is 0001-01-01 */
	std::cout << "entered rdn():passed in year:" << y << ",month:" << m << ",day:" << d << '\n';
	if (m < 3)
		y--, m += 12; // --y is better here?
	return 365*y + y/4 - y/100 + y/400 + (153*m - 457)/5 + d - 306;
}

int rdn2(int y, int m, int d) { /* Rata Die day one is 0001-01-01 */
	std::cout << "entered rdn2():passed in year:" << y << ",month:" << m << ",day:" << d << '\n';
	if (m < 3)
		--y, m += 12;
	return 365*y + y/4 - y/100 + y/400 + (153*m - 457)/5 + d - 306;
}

int main(int argc, char** argv){
//todo: input sanitization and validation: 2017-2-29, 2018-13-1, 2018-4-31
	if(3 > argc){
		std::cout << "Error: No start day(YYYY-mm-dd) and end day(YYYY-mm-dd) specified. Try again having them specified.\n";
		return -1;
	}

	std::string sDateStr(argv[1]);
	std::string eDateStr(argv[2]);
/*
	std::cout << "Type start day(YYYY-mm-dd), space and end day(YYYY-mm-dd), then Enter:";
	std::string sDateStr;
	std::string eDateStr;
	std::cin >> sDateStr >> eDateStr;
*/	

/*
	int sYear = 0;
	int sMonth = 0;
	int sDay = 0;
	int eYear = 0;
	int eMonth = 0;
	int eDay = 0;
*/	
	int sYear = 0, sMonth = 0, sDay = 0, eYear = 0, eMonth = 0, eDay = 0;
	std::string delimiter = "-";
	//std::size_t idxPrev = 0, idxCurr = 0;
	std::string::size_type idxStart = 0, idxDlmtr = sDateStr.find(delimiter,idxStart);
	
	//http://en.cppreference.com/w/cpp/string/basic_string/substr http://en.cppreference.com/w/cpp/string/basic_string/stol
	sYear = stoi(sDateStr.substr(idxStart,idxDlmtr-idxStart));
	idxStart = idxDlmtr+delimiter.length();
	idxDlmtr = sDateStr.find(delimiter,idxStart);
	sMonth = stoi(sDateStr.substr(idxStart,idxDlmtr-idxStart));
	idxStart = idxDlmtr+delimiter.length();
	sDay = stoi(sDateStr.substr(idxStart));

	idxStart = 0;
	idxDlmtr = eDateStr.find(delimiter,idxStart);
	eYear = stoi(eDateStr.substr(idxStart,idxDlmtr-idxStart));
	idxStart = idxDlmtr+delimiter.length();
	idxDlmtr = eDateStr.find(delimiter,idxStart);
	eMonth = stoi(eDateStr.substr(idxStart,idxDlmtr-idxStart));
	idxStart = idxDlmtr+delimiter.length();
	eDay = stoi(eDateStr.substr(idxStart));

	//int days = rdn(2018, 4, 5) - rdn(2018, 2, 5);
	int days = rdn(eYear, eMonth, eDay) - rdn(sYear, sMonth, sDay);
	int days2 = rdn2(eYear, eMonth, eDay) - rdn2(sYear, sMonth, sDay);
	std::cout << "rdn(i)Number of days between " << sDateStr << " and " << eDateStr << " is:[" << days << "]\n";
	std::cout << "rdn2(i)Number of days between " << sDateStr << " and " << eDateStr << " is:[" << days2 << "]\n";
}

