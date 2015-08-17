#include <iostream>
#include <cmath>

int dosum(int param)
{
	int sum = 0;
	param = std::abs(param);
	do{
		sum += param%10;
		std::cout << "param:[" << param << "],sum:[" << sum << "]" << std::endl;
	} while(param/10 > 0 && (param/=10));
		
	return sum;
}

int main()
{
	//unsigned tmp = dosum(-237961);
	std::cout << "sum of all individual figures of " << -237961 << " is:[" << dosum(-237961) << "]" << std::endl;
	return 0;
}
