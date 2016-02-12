#youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 17 -a dn-utuv
#find -exec {}+
#ls --color=tty -alF "*.part"
#sudo shutdown -P -f now

#include <iostream>
#include <string>
#include <vector>
#include <thread>

void work(){
        std::string directory;
        std::string cmd("cd ");
        cmd += directory;
        system(cmd);
        while(ls *.part && <file> has content)
        {
                system("youtube-dl -a <file>");
        }
}

void dispatch(const std::vector<std::string>& files){

        std::vector<std::thread> vc_thrd(std::thread(),files.size());
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

int main()
{
        system("find -type f -i '*dwn*' -exec dispatcher {}+");

        std::vector<std::string> files;
        dispatch(files);
/*
  std::string name;
  std::cout << "What is your name? ";
  getline (std::cin, name);
  std::cout << "Hello, " << name << "!\n";
*/

//log
//c++11 date time
        
        system("shutdown");
}

