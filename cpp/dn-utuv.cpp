#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <thread>
#include <cstdlib> //system()
//#include <unistd.h>
#include <cunistd> //execv(), wait()

using namespace std;

static vector<string> partial_file_vec;
static vector<string> partial_file_dir_vec;
static vector<string> partial_file_id_vec;

void find_partial_download(){
/*
find *.part files and download separately by extracting ID:
find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
extract the substring of 11 characters between last(or the one next to last) hyphen(-) and the first period(.) after that
*/
	string partial_download_cmd("find /mnt/0 -type f -iname '*.part' -fprint /tmp/partial_download_file ");
	ifstream infile("/tmp/partial_download_file");
	string line;
	if(infile){
		while(getline(infile, line)){
			partial_file_vec.push_back(line);
		}
	}

	string partial_file_dir;
	string partial_file_id;
	for(string&& file : partial_file_vec){
		partial_file_dir_vec.push_back(file.substr(0,file.rfind('/')));
		partial_file_dir(file.substr(0,file.rfind('/')));
		partial_file_id_vec.push_back(file.substr(file.find(file.rfind('-'),'.')-11,11));
		partial_file_id(file.substr(file.find(file.rfind('-'),'.')-11,11));
        	string cmd("cd ");
		cmd += partial_file_dir;
		cmd += " && ";
		cmd += "youtube-dl --youtube-skip-dash-manifest ";
		cmd += partial_file_id;
        	system(cmd.c_str());
	}
	
}

void work(){
        string directory;
        string cmd("cd ");
        cmd += directory;
        system(cmd);
	string dn_cmd("youtube-dl --youtube-skip-dash-manifest ");
//	string dn_cmd("youtube-dl -a <file>");
        while(ls *.part && <file> has content)
        {
                system(dn_cmd.c_str());
        }
}

void dispatch(const vector<string>& files){

        vector<thread> vc_thrd(thread(),files.size());
        for(auto thrd : vc_thrd)
        {
                thrd(work);
                thrd.start();
        }
        
        for(auto thrd : vc_thrd)
        {
                thrd.join();
        }
}

int main(){
	find_partial_download();

	string urlfile_download_cmd("find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +");
        system(urlfile_download_cmd.c_str());

	system("wait");

        vector<string> files;
        dispatch(files);
/*
  string name;
  cout << "What is your name? ";
  getline (cin, name);
  cout << "Hello, " << name << "!\n";
*/

//log
//c++11 date time
        string poweroff_cmd("sudo shutdown -P -f +3");
        system(poweroff_cmd.c_str());
}
