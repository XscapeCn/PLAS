//
// Created by Song on 2021/12/17.
//

#include "DynamicProgram.h"
#include <algorithm>

#include <iostream>

using namespace std;



void printLCS(dp * dp1){
    for (int i = 1; i < dp1->s1.size() / sizeof(dp1->s1[0]); ++i) {
        for (int j = 1; j < dp1->s2.size() / sizeof(dp1->s2[0]); ++j) {
            int tmp =0;
            if (dp1->s1[i-1] == dp1->s2[j-1]){
                tmp = 1;}
            dp1->arr[i][j] = maxx(dp1->arr[i - 1][j - 1] + tmp, dp1->arr[i - 1][j], dp1->arr[i][j - 1]);
        }
    }
}

int maxx(int a, int b, int c){
    int max;
    max=a;
    if(b>max)
        max=b;
    if(c>max)
        max=c;
    return max;
}

int main(){
    dp dp1 = {"ATCG","ATCG"};
    std::cout << dp1.arr[0][0] << std::endl;
    printLCS(&dp1);
    int b = dp1.arr[2][2];
    std::cout << b << std::endl;

}

//__global__ void cuda_hello(){
//    printf("Hello World from GPU!\n");
//}
//
//int main() {
//    cuda_hello<<<1,1>>>();
//    return 0;
//}
