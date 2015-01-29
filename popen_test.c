#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <limits.h>
#include <string.h>

void *func_thrd();

int main(int argc, char *argv[])
{
   pthread_t thread1;

   pthread_create( &thread1, NULL, &func_thrd, NULL);

   pthread_join( thread1, NULL);

   return EXIT_SUCCESS;
}

void *func_thrd()
{
   char buf[LINE_MAX], cmdstr[PATH_MAX], cmd_out_buf[LINE_MAX];
   FILE *fp;

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

    }

	return NULL;
}
