#include <jni.h>
#include <string>
#include <android/log.h>
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, "SensorManager", __VA_ARGS__)

// needed for openssl
#include <openssl/bio.h> /* BasicInput/Output streams */
#include <openssl/err.h> /* errors */
#include <openssl/ssl.h> /* core library */

extern "C" JNIEXPORT jstring JNICALL
Java_ch_bfh_opensslsample_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_jni_MainActivity_encodeBase64(JNIEnv *env, jobject thiz, jstring data) {
    BIO *bio, *b64;
    BUF_MEM *bufferPtr;
    b64 = BIO_new(BIO_f_base64());

    //No line break
    BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
    bio = BIO_new(BIO_s_mem());
    bio = BIO_push(b64, bio);

    const char *nativeString = env->GetStringUTFChars(data, 0);
    LOGI("Encoding string '%s'", nativeString);

    //encode
    BIO_write(bio, nativeString, (int) strlen(nativeString));
    BIO_flush(bio);
    BIO_get_mem_ptr(bio, &bufferPtr);
    //The second parameter here is very important and must be assigned

    std::string result(bufferPtr->data, bufferPtr->length);
    env->ReleaseStringUTFChars(data, nativeString);
    LOGI("Encoded String is '%s'", result.c_str());
    BIO_free_all(bio);
    return env->NewStringUTF(result.c_str());
}
