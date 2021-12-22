cmake_minimum_required(VERSION 3.20)
project(PLAS)

set(CMAKE_CXX_STANDARD 14)

find_package(ZLIB REQUIRED)
find_package(Threads REQUIRED)
if (ZLIB_FOUND)
    include_directories(${ZLIB_INCLUDE_DIRS})
endif()

#set(ZLIB_TEST_SOURCES zpipe.c)

include_directories(src/main/cpp)
include_directories(src/test/cpp)
#include_directories(include)

set(SOURCE_FILES src/test/cpp/hifi/Assembly.cpp
        src/test/cpp/hifi/anchor.cpp
        src/test/cpp/hifi/CommandLines.cpp
        src/test/cpp/hifi/Correct.cpp
        src/test/cpp/hifi/extract.cpp
        src/test/cpp/hifi/Hash_Table.cpp
        src/test/cpp/hifi/hic.cpp
        src/test/cpp/hifi/hist.cpp
        src/test/cpp/hifi/horder.cpp
        src/test/cpp/hifi/htab.cpp
        src/test/cpp/hifi/inter.cpp
        src/test/cpp/hifi/ksw2_extz2_sse.c
        src/test/cpp/hifi/kthread.cpp
        src/test/cpp/hifi/Levenshtein_distance.cpp
        src/test/cpp/hifi/main.cpp
        src/test/cpp/hifi/Output.cpp
        src/test/cpp/hifi/Overlaps.cpp
        src/test/cpp/hifi/POA.cpp
        src/test/cpp/hifi/Process_Read.cpp
        src/test/cpp/hifi/Purge_Dups.cpp
        src/test/cpp/hifi/rcut.cpp
        src/test/cpp/hifi/sketch.cpp
        src/test/cpp/hifi/sys.cpp
        src/test/cpp/hifi/tovlp.cpp
        src/test/cpp/hifi/Trio.cpp)

add_executable(asb src/test/cpp/Demo01.cpp)

add_executable(asb2 src/test/cpp/DynamicProgram.cpp src/test/cpp/DynamicProgram.h)

#add_executable(hifi src/test/cpp/hifi/Assembly.cpp src/test/cpp/hifi/anchor.cpp src/test/cpp/hifi/CommandLines.cpp src/test/cpp/hifi/Correct.cpp src/test/cpp/hifi/extract.cpp src/test/cpp/hifi/Hash_Table.cpp src/test/cpp/hifi/hic.cpp src/test/cpp/hifi/hist.cpp src/test/cpp/hifi/horder.cpp src/test/cpp/hifi/htab.cpp src/test/cpp/hifi/inter.cpp src/test/cpp/hifi/ksw2_extz2_sse.c src/test/cpp/hifi/kthread.cpp src/test/cpp/hifi/Levenshtein_distance.cpp src/test/cpp/hifi/main.cpp src/test/cpp/hifi/Output.cpp src/test/cpp/hifi/Overlaps.cpp src/test/cpp/hifi/POA.cpp src/test/cpp/hifi/Process_Read.cpp src/test/cpp/hifi/Purge_Dups.cpp src/test/cpp/hifi/rcut.cpp src/test/cpp/hifi/sketch.cpp src/test/cpp/hifi/sys.cpp src/test/cpp/hifi/tovlp.cpp src/test/cpp/hifi/Trio.cpp)
add_executable(hifi ${SOURCE_FILES})

target_link_libraries(hifi ${ZLIB_LIBRARIES})
target_link_libraries(hifi Threads::Threads)
target_link_libraries(asb ${ZLIB_LIBRARIES})
