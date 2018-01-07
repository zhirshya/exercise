/*
sudo find / -xdev -type f -iname "stdc++*.h"
/usr/include/c++/7/x86_64-redhat-linux/32/bits/stdc++.h
/usr/include/c++/7/x86_64-redhat-linux/bits/stdc++.h
*/
#include <bits/stdc++.h>

/*
#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
*/

//better with
//http://thispointer.com/find-and-replace-all-occurrences-of-a-sub-string-in-c/

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

//todo: unicode support, CJK full-width : + - _ if found at first or last char position of args, unnecessary to output seperators!
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
	string seperator("_");
	for(int i=1; i < argc; ++i){
		string arg(argv[i]);
/*		string argNew;
		argNew=replace(arg.begin(),arg.end(),"&","\&");
		argNew=replace(arg.begin(),arg.end(),"!","\!");
		argNew=replace(arg.begin(),arg.end(),"?","\?");
		argNew=replace(arg.begin(),arg.end(),"*","\*");
		cout << argNew << '_';
*/
//todo: "a + b + c + - _ - + + - - : d" -> "a+b+c:d"
//https://stackoverflow.com/questions/8899069/how-to-find-if-a-given-string-conforms-to-hex-notation-eg-0x34ff-without-regex
//https://stackoverflow.com/questions/9438209/for-every-character-in-string
		unsigned arglen = arg.length();
		//todo: elegant way to look ahead first char of next arg, if is one of : + _ unnecessary to use seperators!
		if(arg[arglen-1]==':'){
			if(0 != seperator.compare(":"))
				seperator=':';
		}else if(arg[arglen-1]=='+'){
			if(0 != seperator.compare("+"))
				seperator='+';
		}else{
			if(0 != seperator.compare("_"))
				seperator='_';
		}

		bool isWord = false;
/*
		for(unsigned j=0; j < arglen; ++j){
			unsigned asciiVal=(unsigned int)arg[j];
			if((47 < asciiVal && 58 > asciiVal) || (64 < asciiVal && 91 > asciiVal) || (96 < asciiVal && 123 > asciiVal)){
*/
//		find_if(begin(arg),end(arg),[&isWord](const char& c){
		find_if(begin(arg),end(arg),[&](const char& c){
			//todo: not strictly meaningful word, e.g. +a-9: :1+-_Z
			if(('0' <= c && '9' >= c) || ('a' <= c && 'z' >= c) || ('A' <= c && 'Z' >= c)){
				isWord = true;
				return true;
			}
		});
//		}

		if(!isWord)
			continue;

		/*
		 ./strrpl Learn Node.js by building Udemy: Stripe + MailChimp + Wistia
		Learn Node.js by building Udemy: Stripe + MailChimp + Wistia
		Learn_Node.js_by_building:Udemy:_Stripe_MailChimp_Wistia
		*/ 

		if(1 < i)
			cout << seperator;
		
		find_and_replace(arg,"&","\\&");
		find_and_replace(arg,"!","\\!");
		find_and_replace(arg,"?","\\?");
		find_and_replace(arg,"*","\\*");
		cout << arg;
		/*
		if(argc-1 > i)
			cout << seperator;
		*/
	}
	cout << '\n';
}
