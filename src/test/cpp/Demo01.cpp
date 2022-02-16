//
// Created by Song Xu on 2021/12/15.
//

#include "Demo01.h"
#include <iostream>
#include <list>
#include <utility>
#include <vector>
#include "zlib.h"
#include <stdio.h>
#include <cstring>
#include <memory>
#include "DynamicProgram.h"
using namespace std;

void printVector(vector<int>& v) {
    for (int & it : v) {
        cout << it << " ";
    }
    cout << endl;
}

void test01(){
    vector<int>v1;
    for (int i = 0; i < 10; i++)
    {
        v1.push_back(i);
    }
    printVector(v1);

    vector<int>v2;
    for (int i = 10; i > 0; i--)
    {
        v2.push_back(i);
    }
    printVector(v2);

    cout << "swap then" << endl;
    v1.swap(v2);
    printVector(v1);
    printVector(v2);
}

int maxX(int a, int b, int c){
    int max = a;
    if(b>max) max=b;
    if(c>max) max=c;
    return max;
}

void test02()
{
    vector<int> v;
    for (int i = 0; i < 100000; i++) {
        v.push_back(i);
    }

    cout << "ca:" << v.capacity() << endl;
    cout << "si" << v.size() << endl;

    v.resize(3);

    cout << "ca:" << v.capacity() << endl;
    cout << "si:" << v.size() << endl;

//    v.shrink_to_fit();

    cout << "ca:" << v.capacity() << endl;
    cout << "si" << v.size() << endl;
}


/*
void test2(int a, int b){
    int arr[a][b];
//    for (int i = 0; i < a; ++i) {
//        for (int j = 0; j < b; ++j) {
//            arr[i][j] = 0;
//        }
//    }
    arr = {{0}};
}
*/


void test(char* a, char * b,int match_score=3, int gap_cost=2){
    int arr[strlen(a) + 1][strlen(b) + 1];
    for (int i = 0; i <strlen(a) + 1 ; ++i) {
        for (int j = 0; j < strlen(b) + 1; ++j) {
            arr[i][j] = 0;
        }
    }

//    arr = {{0}};

//    for (int i = 0; i < 3; ++i) {
//        arr[i][i] = 3;
//        cout << arr[i][i] << endl;
//    }
    for (int i = 1; i < strlen(a)+1; ++i) {
        for (int j = 1; j < strlen(b)+1; ++j) {
//            int temp_score = (a[i-1] == b[i-1]) ? match_score : -match_score
            int mat = (arr[i-1][j-1] + (a[i-1] == b[i-1])) ? match_score : -match_score;
            int del = arr[i-1][j] - gap_cost;
            int ins = arr[i][j-1] - gap_cost;
            arr[i][j] = maxX(mat,del,ins);
            cout << arr[i][j] << endl;
        }
    }
}

int main() {
//    test("abcdefg", "cdefg");
    char * a = "ATCG";
    char * b = "ATCG";
    if(a[1] == b[1]){
        cout << "y" << endl;

    }
    std::shared_ptr<int> p(new int(42));


//    test01();
//    test02();

//    char aa[10][10];
//    aa[0]='hello';
//    cout << aa[0] << endl;

//    cout << __cplusplus << endl;
//
//    gzFile dfp = gzopen("src/test.fq", "r");
//    if (dfp == 0)
//    {
//        fprintf(stderr, "[ERROR] Cannot find the input read file: \n");
//        exit(0);
//    }
//    gzclose(dfp);
//    return 0;
//char * s2 = "string123333";
//    cout << s2[2]<< endl;
}




