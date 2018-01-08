#include <stdio.h>
int main(int argc, char* argv[]) {
	char c[] = "?!&.#0aZ";
	char RFC_3986_section_ 2.2_Reserved_Characters[]="!#$&'()*+,/:;=?@[]";
	int cnt = sizeof(c)/sizeof(char);
	for(int i = 0; i < cnt; ++i)
		printf("ascii code of %c:%x\n",c[i],c[i]);

	int cnt_rfc = sizeof(RFC_3986_section_ 2.2_Reserved_Characters)/sizeof(char);
	for(int i = 0; i < cnt_rfc; ++i)
		printf("ascii code of %c:%x\n",RFC_3986_section_ 2.2_Reserved_Characters[i],RFC_3986_section_ 2.2_Reserved_Characters[i]);

	return 0;
}

