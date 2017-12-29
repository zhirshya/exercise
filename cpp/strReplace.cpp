#include <iostream>
#include <algorithm>
#include <vector>
#include <string>

/*
	argv -> vector
	filter and transform: escape special characters, e.g. & ! ? * ->  \& \! \? \*
	concat
*/

//https://www.fluentcpp.com/2017/02/13/transform-central-algorithm/
//v | filter(predicate) | transform(f)

using namespace std;

//http://bits.mdminhazulhaque.io/cpp/find-and-replace-all-occurrences-in-cpp-string.html
void find_and_replace(string& source, string const& find, string const& replace)
{
    for(string::size_type i = 0; (i = source.find(find, i)) != string::npos;)
    {
        source.replace(i, find.length(), replace);
        i += replace.length();
    }
}

/*
//https://stackoverflow.com/questions/37710597/c-how-do-i-save-argv-to-vector-as-strings
typedef basic_string<TCHAR> tstring;

int _tmain(int argc, _TCHAR* argv[]){
*/
int main(int argc, char** argv){
/*
	vector<tstring> argsVktr(argv+1,argv+argc);
	for (const tstring& arg: argsVktr)
		cout << arg << ' ';
	cout << '\n';
*/
	vector<string> argsVktr(argv+1,argv+argc);
	for (const string& arg: argsVktr)
		cout << arg << ' ';
	cout << '\n';
	
/*
	int idx=0;
	for (tstring& arg: argv){
		if(string::npos != (idx=arg.find('&'))
				arg.replace( , , );
*/
	for(int i=1; i < argc; ++i){
		string arg(argv[i]);
/*		string argNew;
		argNew=replace(arg.begin(),arg.end(),"&","\&");
		argNew=replace(arg.begin(),arg.end(),"!","\!");
		argNew=replace(arg.begin(),arg.end(),"?","\?");
		argNew=replace(arg.begin(),arg.end(),"*","\*");
		cout << argNew << '_';
*/
		find_and_replace(arg,"&","\\&");
		find_and_replace(arg,"!","\\!");
		find_and_replace(arg,"?","\\?");
		find_and_replace(arg,"*","\\*");
		cout << arg;
		if(argc-1 > i)
			cout << '_';
	}
	cout << '\n';

}
