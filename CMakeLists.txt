cmake_minimum_required(VERSION 3.20)
project(asb)

set(CMAKE_CXX_STANDARD 14)

include_directories(src/main/cpp)
include_directories(src/test/cpp)

add_executable(asb
        src/test/cpp/Demo01.cpp
        src/test/cpp/Demo01.h
#        src/test/cpp/DynamicProgram.cpp
#        src/test/cpp/DynamicProgram.h
        )

add_executable(asb2
        src/test/cpp/DynamicProgram.cpp
        src/test/cpp/DynamicProgram.h
        )

