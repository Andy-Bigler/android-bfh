cmake_minimum_required(VERSION 3.22.1)

project("jni")

add_library(
        jni
        SHARED
        native-lib.cpp)

find_library(
        log-lib
        log)

# Path to the pre-built OpenSSL library.
set(openssl_DIR ${CMAKE_SOURCE_DIR}/../jniLibs/libssl)

# Add and link the OpenSSL ssl library.
add_library(
        libssl
        STATIC
        IMPORTED)

set_target_properties(
        libssl
        PROPERTIES IMPORTED_LOCATION
        ${openssl_DIR}/lib/${ANDROID_ABI}/libssl.a)

add_library(
        libcrypto
        STATIC
        IMPORTED)

set_target_properties(
        libcrypto
        PROPERTIES IMPORTED_LOCATION
        ${openssl_DIR}/lib/${ANDROID_ABI}/libcrypto.a)

# Include the OpenSSL headers.
include_directories(${openssl_DIR}/include)

target_link_libraries(
        jni
        ${log-lib}
        libssl
        libcrypto)