#include <stdio.h>
//#include <stdlib.h>
//#include <ctype.h>

struct char_only_struct {
	char c;
};

struct charptr_only_struct {
	char *c;
};

int main(int argc, char *argv[]) {
	struct char_only_struct strukt = {.c = 'a'};
	struct charptr_only_struct strukt_ptr = {.c = "a"};
	unsigned x = sizeof(strukt);
	unsigned y = sizeof(char);
	unsigned z = sizeof(strukt_ptr);
	unsigned w = sizeof(char*);
	char prntf_str1[] = "printf(\"%s:%2d,%-2d.\\n\",prntf_str1,x,x)";
	char prntf_str2[] = "printf(\"%s:%2d,%-2d.\\n\",prntf_str2,y,y)";
	char prntf_str3[] = "printf(\"%s:%2d,%-2d.\\n\",prntf_str3,z,z)";
	char prntf_str4[] = "printf(\"%s:%2d,%-2d.\\n\",prntf_str4,w,w)";
	printf("%s:%2d,%-2d.\n",prntf_str1,x,x);
	printf("%s:%2d,%-2d.\n",prntf_str2,y,y);
	printf("%s:%2d,%-2d.\n",prntf_str3,z,z);
	printf("%s:%2d,%-2d.\n",prntf_str4,w,w);
//	printf("%s:%-2d.\n","sizeof(byte)",sizeof(byte));
	return 0;
}
