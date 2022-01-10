//
// Created by Song Xu on 2021/12/17.
//
#include "DynamicProgram.h"
#include <algorithm>
#include <iostream>
#include <cstring>
#include "stdio.h"
#include "stdlib.h"

using namespace std;

typedef struct {
    int ** arr;
    int row;
    int col;
} mat;

typedef struct {
    string  b_;
    int i;
} res;

void printLCS(dp* dp1){
    for (int i = 1; i < dp1->s1.size() / sizeof(dp1->s1[0]); ++i) {
        for (int j = 1; j < dp1->s2.size() / sizeof(dp1->s2[0]); ++j) {
            int tmp =0;
            if (dp1->s1[i-1] == dp1->s2[j-1]){
                tmp = 1;}
            dp1->arr[i][j] = maxX(dp1->arr[i - 1][j - 1] + tmp, dp1->arr[i - 1][j], dp1->arr[i][j - 1]);
        }
    }
}

int maxX(int a, int b, int c){
    int max = a;
    if(b>max) max=b;
    if(c>max) max=c;

    if (max > 0){
        return max;
    }else{
        return 0;
    }
}

void printMatrix(mat ma){
    for (int i = 0; i < ma.row; ++i) {
        for (int j = 0; j < ma.col; ++j) {
            cout << ma.arr[i][j] << ",";
        }
        cout << endl;
    }
}

void test(unsigned n){
    int arr[n][n];
    for (int i = 0; i < n; ++i) {
        arr[i][0] = 2;
        cout << arr[i][0] << endl;
    }

}

mat transfer_matrix(mat ma){
    int ** t_arr = new int * [ma.row];
    for (int i = 0; i < ma.row; ++i) {
        t_arr[i] = new int[ma.col];
        for (int j = 0; j < ma.col ; ++j) {
            t_arr[i][j] = ma.arr[ma.row - i - 1][ma.col - j - 1];
        }
    }
    mat res = {t_arr, ma.row, ma.col};
    return res;
}

mat subMatrix(mat ma, int idx, int idy){
    int ** ar = new int * [idx];
    for (int i = 0; i < (idx); i++){
        ar[i] = new int[idy];
        for (int j = 0; j < (idy); j++){
            ar[i][j] = ma.arr[ma.row -idx + i][ma.col - idy + j ];
        }
    }
    mat res = {ar, idx, idy};
    return res;

}


mat matrix(char* a, char* b, int match_score=3, int gap_cost=2){
/*
     int ** arr;
     arr = (int **) malloc((strlen(a) + 1)* sizeof(int*));
     for (int i = 0; i < (strlen(a) + 1); ++i) {
         arr[i]=(int *)malloc((strlen(b) + 1)*sizeof(int));
     }*/

// Method 3 to create 2D array
    int row = strlen(a) + 1;
    int col = strlen(b) + 1;

    int ** arr = new int * [row];
    for (int i = 0; i < (row); i++){
        arr[i] = new int[col];
        for (int j = 0; j < (col); j++){
            arr[i][j] = 0;
        }
    }
//    int i,j;
//    for (i = 0; i < strlen(a); ++i) {
//        arr[i] = new int[strlen(b)];
//        for (j = 0;  j< strlen(b); ++j) {
//            arr[i][j] = 0;
//        }
//    }

/* Method 1 to create 2D array
    int ** arr;
    arr = (int **) malloc((strlen(a) + 1) * sizeof(int*));
    memset(arr, 0, (strlen(a) + 1)*sizeof(int));
    for (int i = 0; i < (strlen(a) + 1); ++i) {
        arr[i]=(int *)malloc((strlen(b) + 1) * sizeof(int));
        memset(arr[i], 0, (strlen(b) + 1)*sizeof(int));
    }
*/

/* Method 2 to create 2D array
    int arr[strlen(a) + 1][strlen(b) + 1];
    memset(arr, 0, (strlen(a) + 1)*(strlen(b) + 1)*sizeof(int) );*/

    for (int i = 1; i < row; ++i) {
        for (int j = 1; j < col; ++j) {
            int mat = arr[i-1][j-1] + ((a[i-1] == b[j-1]) ? match_score : -match_score);
            int del = arr[i-1][j] - gap_cost;
            int ins = arr[i][j-1] - gap_cost;
            arr[i][j] = maxX(mat,del,ins);
        }
    }
    mat res = {arr, row, col};
    return res;
}

int* argMax(mat ma) {
    int max = ma.arr[0][0];
    int idx = 0; int idy = 0;
    for (int i = 0; i < ma.row; ++i) {
        for (int j = 0; j < ma.col; ++j) {
            if (ma.arr[i][j] > max){
                max = ma.arr[i][j];
                idx = i;
                idy = j;
            }
        }
    }
    int * res = new int[3]{idx, idy, max};
    return res;
}

res* traceback(mat ma, char * b, string b_ = "", int old_i = 0){

    int* tmp = argMax(ma);
    int i = ma.row - tmp[0] - 1;
    int j = ma.col - tmp[1] - 1;

    if(ma.arr[tmp[0]][tmp[1]] == 0){
        res re = {b_, j};
        res* p = &re;
//        cout << p << endl;
        cout << p->b_ << endl;
        return p;
    }
    if(old_i - i >1){
//        string tt = "-";
        b_ = b[j-1] + (string) "-" + b_;
    }else{
        b_ = b[j-1] + b_;
    }
//    cout << b_ << endl;
    mat ar = subMatrix(ma, i , j);
    return traceback(ar, b, b_, i);
}

res* smithWaterman(char * a, char * b){
    mat ma = matrix(a,b);
//    printMatrix(ma);
//    cout << ma.row << endl;
//    cout << ma.col << endl;
    mat bbc = transfer_matrix(ma);
//    printMatrix(bbc);

    return traceback(bbc,b);
}

int main(){
//    res * re = smithWaterman("ATACG", "ATCGA");
    res * re = smithWaterman("ATACGAAATTTCCCAAATTTTTTCCCAAATTT", "ATCGAAAATTTCCCAAATTTTTTCCCAAATTT");
    //AT-CGAAATTTCCCAAATTTTTTCCCAAATTT
}
