#include <iostream>

//https://www.quora.com/What-is-your-favourite-short-C++-code-fragment-under-10-lines
//
using namespace std;

auto main() -> int
{
  auto f = [](auto&& s) { cout << s << "\n"; };
 
  f("Hello world(from f(\x22Hello world<reflect>\x22s))"s);
  f("Hello world(from f(\x22Hello world<reflect>\x22))");
  return 0;
}
