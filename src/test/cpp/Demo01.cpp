//
// Created by Song on 2021/12/15.
//

#include "Demo01.h"
#include <iostream>
#include <list>
#include <utility>

using namespace std;

int test1(int i, int i1);
void bubbleSort(int *arr, int len);


void printArray(int * arr, int len){
    for (int i = 0; i < len; ++i) {
        cout << arr[i] << endl;
    }
}

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

class PhoneNumber{};


class ABEntry{
public:ABEntry(std::string  name, std::string  address, const std::list<PhoneNumber>& phones);

private:
    std::string theName;
    string theAddress;
    std::list<PhoneNumber> thePhones;
    int numTImesConsulted;

};

ABEntry::ABEntry(std::string  name, std::string address, const std::list<PhoneNumber> &phones)
    :theName(std::move(name)),
    theAddress(std::move(address)),
    thePhones(),
    numTImesConsulted(0){}

int main(){

    int arr[10] = {3,6,4,5,2,7,1,9,8,10};
    int len = sizeof(arr)/sizeof(arr[0]);
    bubbleSort(arr, len);
    printArray(arr,len);

//    std::cout << test1(10,8)<< endl;

}

int test1(int i, int i1) {
    int res;
    res = i+i1;
    return res;

}

void bubbleSort(int * arr, int len){
    for (int i = 0; i < len-1; ++i) {
        for (int j = 0; j < len-1-i; ++j) {
            if (arr[j] > arr[j+1]){
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}


