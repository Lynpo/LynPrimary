#include <jni.h>
#include <string>
#include "lynpo-const.h"

const std::string Lynpo_Const::LYNPO_NAME = "Lynpo";

extern "C"

//bool packageValid(JNIEnv *env, jobject ctx){
//    jclass android_content_Context =env->GetObjectClass(ctx);
//    jmethodID midGetPackageName = env->GetMethodID(android_content_Context,"getPackageName", "()Ljava/lang/String;");
//    jstring packageName= (jstring)env->CallObjectMethod(ctx, midGetPackageName);
//
//    jstring real_pkg_name = env->NewStringUTF("com.lynpo");
//
//    jclass cls = env->GetObjectClass(real_pkg_name);
//    jmethodID mID = env->GetMethodID(cls, "equals", "(Ljava/lang/Object;)Z");
//    jboolean equals = env->CallBooleanMethod(real_pkg_name, mID, packageName);
//
//    return equals;
//}

jstring
Java_com_lynpo_util_JniMethods_stringFromJNI(
        JNIEnv *env,
//        jclass,
        jobject/* obj*/) {
    std::string hello = "Hello from C++, my name is ";
    std::string name = Lynpo_Const::LYNPO_NAME;
    std::string sentence = hello.append(name);

//    if (packageValid(env, obj)) {
        return env->NewStringUTF(sentence.c_str());
//    } else {
//        return env->NewStringUTF("A u kidding me $_$");
//    }
}
