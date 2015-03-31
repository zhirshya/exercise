#include <stdio.h>
#include <stdlib.h>
#include <threads.h>
#include <limits.h>
#include <string.h>

//#define _POSIX_C_SOURCE
//#include <regex.h>

//#include <unistd.h>

//pthread_mutex_t count_mutex     = PTHREAD_MUTEX_INITIALIZER;
//pthread_cond_t  condition_var   = PTHREAD_COND_INITIALIZER;

thrd_start_t func_thrd();

int main(int argc, char *argv[])
{
   thrd_t thread1;

   thrd_create( &thread1, func_thrd, NULL);

   thrd_join( thread1, NULL);

   return EXIT_SUCCESS;
}

thrd_start_t func_thrd()
{
   char buf[LINE_MAX];
   fputs(buf,stdout); // echo
   
   FILE *fp;
   char cmdstr[PATH_MAX]; 
   char cmd_out_buf[LINE_MAX]; // cmd output line max?

//2 kinds of input: a. buffered with \n; b. single ESC or Ctrl-D for immediate exit.
//linux C read single and multiple characters at the same time
//   for(;;)
   while(fgets(buf, LINE_MAX-1, stdin))
   {
	printf("%s:[%c] %s:[%d]\n","*(buf+strlen(buf)-1)",*(buf+strlen(buf)-1),"ASCII code",*(buf+strlen(buf)-1));

	if('\n' == buf[strlen(buf)-1])
	{
		fputs("fgets got newline at the end!\n",stdout);
		*(buf+strlen(buf)-1)='\0'; // trim the newline at the end
	}

	*cmdstr='\0';
	strcat(cmdstr,"./fnd.sh ");
	strcat(cmdstr,buf);
	printf("%s:[%s]\n","command to execute",cmdstr);

	if(!(fp = popen(cmdstr,"r")))
	{
		fputs("fp = popen(cmdstr,\"r\") -> ERR!",stdout); //output detailed error message, how to?
		break;
	}

	while(NULL != fgets(cmd_out_buf,LINE_MAX,fp))
	 	fputs(cmd_out_buf,stdout);
	
	pclose(fp);

/*
	if( -1 == execl("/home/r/gthb/dmbexrcs/fnd.sh", buf))
	{
		fputs("err!\n", stdout);
		switch(errno)
		{
			case EACCES:
				fputs("err! EACCES:\n", stdout);
				break;
			case ENOEXEC:
				fputs("err! ENOEXEC:\n", stdout);
				break;
		}
	}
*/
    }
}
