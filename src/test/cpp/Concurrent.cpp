//
// Created by  Song Xu on 2022/2/15.
//

#include "Concurrent.h"
#include <pthread.h>

void * printaaa(int a){
    for (int i = 0; i < 5; ++i) {
        cout << "Thread" << a << " " << i << "times" << "\n";
    }
    return nullptr;
}

int main(){
    pthread_t n;
    n = (pthread_t*)calloc(10, sizeof(pthread_t));

    for (int i = 0; i < 10; ++i) {
        thread_create(&n[i], NULL, printaaa, i);
    }
    for (int i = 0; i < 10; ++i) {
        thread_join(n[i], 0);
    }
    free(n);

}