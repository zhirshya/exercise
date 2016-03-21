#include <iostream>
#include <stdio.h>

int main()
{
	int x = 35;
	printf("%d,%d,%d\n", x == 35, x == 50, x > 40);
	std::cout << "Unicode Character 'PLUS-MINUS SIGN' (U+00B1):" << "\u00B1" << std::endl;
	std::cout << "Unicode Character 'MINUS-OR-PLUS SIGN' (U+2213):" << "\u2213" << std::endl;
}
