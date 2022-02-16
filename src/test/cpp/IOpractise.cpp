//
// Created by  Song Xu on 2022/1/17.
//
#include <iostream>
#include <cstdint>
#include "IOpractise.h"
#include <algorithm>

#include <cstring>
#include <memory>
#include <pthread.h>

using namespace std;

typedef struct
{
    uint64_t** N_site;
    ///uint8_t* read;
    char* name;

    uint8_t** read_sperate;
    uint64_t* read_length;
    uint64_t* read_size;
    uint8_t* trio_flag;

    ///seq start pos in uint8_t* read
    ///do not need it
    ///uint64_t* index;
    uint64_t index_size;

    ///name start pos in char* name
    uint64_t* name_index;
    uint64_t name_index_size;
    uint64_t total_reads;
    uint64_t total_reads_bases;
    uint64_t total_name_length;

//    Compressed_Cigar_record* cigars;
//    Compressed_Cigar_record* second_round_cigar;
//
//    ma_hit_t_alloc* paf;
//    ma_hit_t_alloc* reverse_paf;

    ///kvec_t_u64_warp* pb_regions;
} All_reads;

All_reads R_inf;

void * printaaa(int a){
    for (int i = 0; i < 100; ++i) {
        cout << "Thread" << a << " " << i << "times" << "\n";
    }
    return nullptr;
}

int load_All_reads(All_reads* r, char* read_file_name)
{
//    char* read_file_name = (char*)malloc(strlen(read_file_name)+15);
//    sprintf(read_file_name, "%s.bin", read_file_name);
    cout << read_file_name << endl;
    FILE* fp = fopen(read_file_name, "r");
    if (!fp) {
//        free(read_file_name);
        cout<<"out"<<endl;
        return 0;
    }
    int local_adapterLen;
    int f_flag;
    f_flag = fread(&local_adapterLen, sizeof(local_adapterLen), 1, fp);
    cout << "adaperlen:" << 0 << endl;
    cout << "adaperlen:" << local_adapterLen << endl;
    cout << "adaperlen:" << f_flag << endl;
    if(local_adapterLen != 0)
    {
        fprintf(stderr, "the adapterLen of index is: %d, but the adapterLen set by user is: %d\n",
                local_adapterLen, 0);
        exit(1);
    }
    f_flag += fread(&r->index_size, sizeof(r->index_size), 1, fp);
    cout << "index_size:" << r->index_size << endl;
    f_flag += fread(&r->name_index_size, sizeof(r->name_index_size), 1, fp);
    cout << "name_index_size:" << r->name_index_size << endl;
    f_flag += fread(&r->total_reads, sizeof(r->total_reads), 1, fp);
    cout << "total_reads:" << r->total_reads << endl;
    f_flag += fread(&r->total_reads_bases, sizeof(r->total_reads_bases), 1, fp);
    cout << "total_reads_bases:" << r->total_reads_bases << endl;
    f_flag += fread(&r->total_name_length, sizeof(r->total_name_length), 1, fp);
    cout << "total_name_length:" << r->total_name_length << endl;

    uint64_t i;
    uint64_t zero = 0;
    r->N_site = (uint64_t**)malloc(sizeof(uint64_t*)*r->total_reads);
    for (i = 0; i < r->total_reads; i++){
        f_flag += fread(&zero, sizeof(zero), 1, fp);

        if (zero){
            cout << zero << endl;
            r->N_site[i] = (uint64_t*)malloc(sizeof(uint64_t)*(zero + 1));
            r->N_site[i][0] = zero;
            if (r->N_site[i][0]){
                f_flag += fread(r->N_site[i]+1, sizeof(r->N_site[i][0]), r->N_site[i][0], fp);
            }
        }
        else{
            r->N_site[i] = NULL;
        }
    }

    r->read_length = (uint64_t*)malloc(sizeof(uint64_t)*r->total_reads);
    f_flag += fread(r->read_length, sizeof(uint64_t), r->total_reads, fp);

//    r->read_size = (uint64_t*)malloc(sizeof(uint64_t)*r->total_reads);
//    memcpy (r->read_size, r->read_length, sizeof(uint64_t)*r->total_reads);

    r->read_sperate = (uint8_t**)malloc(sizeof(uint8_t*)*r->total_reads);
    for (i = 0; i < r->total_reads; i++)
    {
        r->read_sperate[i] = (uint8_t*)malloc(sizeof(uint8_t)*(r->read_length[i]/4+1));
        f_flag += fread(r->read_sperate[i], sizeof(uint8_t), r->read_length[i]/4+1, fp);
    }


    r->name = (char*)malloc(sizeof(char)*r->total_name_length);
    f_flag += fread(r->name, sizeof(char), r->total_name_length, fp);

    r->name_index = (uint64_t*)malloc(sizeof(uint64_t)*r->name_index_size);
    f_flag += fread(r->name_index, sizeof(uint64_t), r->name_index_size, fp);

    /****************************may have bugs********************************/
    r->trio_flag = (uint8_t*)malloc(sizeof(uint8_t)*r->total_reads);
    f_flag += fread(r->trio_flag, sizeof(uint8_t), r->total_reads, fp);
//    f_flag += fread(&(0.hom_cov), sizeof(0.hom_cov), 1, fp);
//    f_flag += fread(&(0.het_cov), sizeof(0.het_cov), 1, fp);
    /****************************may have bugs********************************/

//    r->cigars = (Compressed_Cigar_record*)malloc(sizeof(Compressed_Cigar_record)*r->total_reads);
//    r->second_round_cigar = (Compressed_Cigar_record*)malloc(sizeof(Compressed_Cigar_record)*r->total_reads);
//    for (i = 0; i < r->total_reads; i++){
//        r->second_round_cigar[i].size = r->cigars[i].size = 0;
//        r->second_round_cigar[i].length = r->cigars[i].length = 0;
//        r->second_round_cigar[i].record = r->cigars[i].record = NULL;
//
//        r->second_round_cigar[i].lost_base_size = r->cigars[i].lost_base_size = 0;
//        r->second_round_cigar[i].lost_base_length = r->cigars[i].lost_base_length = 0;
//        r->second_round_cigar[i].lost_base = r->cigars[i].lost_base = NULL;
//    }
    ///r->pb_regions = NULL;

//    free(read_file_name);
    fclose(fp);
    fprintf(stderr, "Reads has been loaded.\n");

    return 1;
}

int main(){

//    load_All_reads(&R_inf, "/mnt/e/WSL/task/hifiasmTest/aa.ec.bin");

    pthread_t * n;
    n = (pthread_t *) calloc(10, sizeof(pthread_t));

    for (int i = 0; i < 10; ++i) {
        pthread_create(&n[i], NULL, reinterpret_cast<void *(*)(void *)>(printaaa), reinterpret_cast<void *>(i));
    }
    for (int i = 0; i < 10; ++i) {
        pthread_join(n[i], 0);
    }
    //

//    cout << R_inf.N_site[2];



//    auto ** p  = new unsigned char*[2];
//    for (int i = 0; i < 2; ++i) {
//        p[i] = new unsigned char[10];
//        for (int j = 0; j < 10; ++j) {
//            p[i][j] = 0;
//        }
//    }
//    p[0]= (unsigned char *) "ALIBABA";
//    p[1]= (unsigned char *) "BUBBLE";
//
//
////
//    FILE* fp = fopen("/mnt/e/task/IPractice.txt", "w");
//    for (int i = 0; i < 2; i++){
//        if (p[i] != nullptr){
//            ///number of Ns
//            fwrite(p[i], sizeof(p[i][0]), 1, fp);
//            cout << p[i][0] ;
//            if (p[i][0]){
//                auto a = fwrite(p[i]+1, sizeof(p[i][0]), p[i][0], fp);
//                cout << "\n" << a << "\n";
//
//            }
//        }
//    }

//    for (int i = 0; i < 10; ++i){
//        delete p[i];
//    }
//    delete[] p;
}
