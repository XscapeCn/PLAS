//
// Created by Song on 2021/12/15.
//

#include "Demo01.h"
#include <iostream>
using namespace std;

int test1(int i, int i1);

class Box{
public:
    int a;
    int b;

    void print();
    Box(int a, int b);
};

Box::Box(int aa, int bb) {
    a=aa;
    b=bb;
}

void Box::print() {
    cout << a <<"\n" << b << endl;
}

int main(){

    Box b(4,5);
    b.print();


//    std::cout << test1(10,8)<< endl;

}

int test1(int i, int i1) {
    int res;
    res = i+i1;
    return res;

}

