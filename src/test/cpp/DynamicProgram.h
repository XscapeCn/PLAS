//
// Created by Song Xu on 2021/12/17.
//
#include <string>
#ifndef ASB_DYNAMICPROGRAM_H
#define ASB_DYNAMICPROGRAM_H

int maxX(int a, int b, int c);
typedef struct {
    std::string s1;
    std::string s2;
    int arr[sizeof(s1) / sizeof(s1[0]) + 1][sizeof(s2) / sizeof(s2[0]) + 1];
} dp;

#endif //ASB_DYNAMICPROGRAM_H
