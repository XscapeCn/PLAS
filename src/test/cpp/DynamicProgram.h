//
// Created by Song Xu on 2021/12/17.
//
#include <string>
#ifndef ASB_DYNAMICPROGRAM_H
#define ASB_DYNAMICPROGRAM_H

using namespace std;

int maxX(int a, int b, int c);

typedef struct {
    int ** arr;
    int row;
    int col;
} mat;

typedef struct {
    string  b_;
    int i;
} res;

#endif //ASB_DYNAMICPROGRAM_H
