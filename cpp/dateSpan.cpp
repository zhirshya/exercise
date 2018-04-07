#include <iostream>
#include <string>
#include <cstring>

int rdn(int y, int m, int d) { /* Rata Die day one is 0001-01-01 */
	if (m < 3)
		y--, m += 12;
	return 365*y + y/4 - y/100 + y/400 + (153*m - 457)/5 + d - 306;
}

int main(int argc, char** argv){
	std::cout << "Type start day(YYYY-mm-dd), space and end day(YYYY-mm-dd), then Enter:";
	std::string sDateStr;
	std::string eDateStr;
	std::cin >> sDateStr >> eDateStr;

	int sYear = sMonth = sDay = 0;
	int eYear = eMonth = eDay = 0;
	
	#int days = rdn(2018, 4, 5) - rdn(2018, 2, 5);
	int days = rdn(eYear, eMonth, eDay) - rdn(sYear, sMonth, sDay);
	std::cout << "Number of days between " << sDateStr << " and " << eDateStr << " is:[" << days << "]\n";
}

