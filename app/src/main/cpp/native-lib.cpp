#include <jni.h>
#include <string>
#include "lynpo-const.h"

const std::string Lynpo_Const::LYNPO_NAME = "Lynpo";

extern "C"
jstring
Java_com_lynpo_HomeActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++, my name is ";
    std::string name = Lynpo_Const::LYNPO_NAME;
    std::string sentence = hello.append(name);

    return env->NewStringUTF(sentence.c_str());
}
