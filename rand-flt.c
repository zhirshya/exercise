#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int main(int argc, char *argv[])
{
    srand((unsigned int)time(NULL));

    float a = 5.0;
    printf("RAND_MAX:[%f]\n",(float)RAND_MAX);

    for (int i=0;i<20;i++)
        printf("%f\n", ((float)rand()/(float)(RAND_MAX)) * a);
    return 0;
}
