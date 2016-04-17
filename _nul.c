#include <stdio.h>

int main ()
{ 
      FILE* outfile = fopen ("/dev/null", "w");
      if (outfile == NULL){
      	fputs ("could not open '/dev/null'", stderr);
      }
      outfile = fopen ("nul", "w");
      if (outfile == NULL){
      	fputs ("could not open 'nul'", stderr);
      }
      return 0;
}