#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_beachball_recipebuilder_BaseNavActivity_getNativeKey(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "YOUR_KEY_HERE");
}