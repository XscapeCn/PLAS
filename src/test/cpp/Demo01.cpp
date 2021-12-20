//
// Created by Song on 2021/12/15.
//

#include "Demo01.h"
#include <iostream>
#include <list>
#include <utility>
#include <vector>
using namespace std;

void printVector(vector<int>& v) {

    for (vector<int>::iterator it = v.begin(); it != v.end(); it++) {
        cout << *it << " ";
    }
    cout << endl;
}

void test01()
{
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

    //互换容器
    cout << "swap then" << endl;
    v1.swap(v2);
    printVector(v1);
    printVector(v2);
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

    //收缩内存
    v.shrink_to_fit(); //匿名对象

    cout << "ca:" << v.capacity() << endl;
    cout << "si" << v.size() << endl;
}

int main() {

    test01();

    test02();

//    system("pause");

    return 0;
}




