#include <iostream>
#include <cmath>
#include <chrono>

using namespace std;

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
	cout << "system_clock" << endl;
	cout << chrono::system_clock::period::num << endl;
	cout << chrono::system_clock::period::den << endl;
	cout << "steady = " << boolalpha << chrono::system_clock::is_steady << endl << endl;
 
	cout << "high_resolution_clock" << endl;
	cout << chrono::high_resolution_clock::period::num << endl;
	cout << chrono::high_resolution_clock::period::den << endl;
	cout << "steady = " << boolalpha << chrono::high_resolution_clock::is_steady << endl << endl;
 
	cout << "steady_clock" << endl;
	cout << chrono::steady_clock::period::num << endl;
	cout << chrono::steady_clock::period::den << endl;
	cout << "steady = " << boolalpha << chrono::steady_clock::is_steady << endl << endl;

	auto start = chrono::steady_clock::now();
	//unsigned tmp = dosum(-237961);
	std::cout << "sum of all individual figures of " << -237961 << " is:[" << dosum(-237961) << "]" << std::endl;

	auto end = chrono::steady_clock::now();
	auto diff = end - start;
	cout << chrono::duration <double, nano> (diff).count() << " ns" << endl;
	cout << chrono::duration <double, micro> (diff).count() << " us" << endl;
	cout << chrono::duration <double, milli> (diff).count() << " ms" << endl;

	return 0;
}
