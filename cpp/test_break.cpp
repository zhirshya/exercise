#include <iostream>

int main()
{
	int i = 0, j = 0;
	
	for(; i < 5; ++i)
	{
		std::cout << i << ":" << "inside outer loop\n";
		for(; j < 5; ++j)
		{
			std::cout << j << ":" << "inside inner loop\n";
			if(2 == j)
			{
				std::cout << "continue fired from inside inner loop\n";
				continue;
			}
			else if(3 == j)
			{
				std::cout << "break from inner loop\n";
				break;
			}
		}
	}
	
	std::cout << "program exit\n";

	return 0;
}
