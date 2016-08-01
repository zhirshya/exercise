#include <iostream>
#include <stdio.h>

namespace cds {
    char const nl = '\n';
}

enum minus {
	int minval=-5,
	maxval
}

int main()
{
	using namespace std;
	int x = 35;
	printf("%d,%d,%d\n", x == 35, x == 50, x > 40);
	cerr << "Unicode Character 'PLUS-MINUS SIGN' (U+00B1):" << "\u00B1" << cds::nl;
	cerr << "Unicode Character 'MINUS-OR-PLUS SIGN' (U+2213):" << "\u2213" << cds::nl;
	cout << "minus.maxval(uninit enum):" << minus.maxval << "\n";
}
