/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_jitsi_impl_neomedia_quicktime_CVPixelBuffer */

#ifndef _Included_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
#define _Included_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    getByteCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_getByteCount
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    getBytes
 * Signature: (J)[B
 */
JNIEXPORT jbyteArray JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_getBytes__J
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    getBytes
 * Signature: (JJI)I
 */
JNIEXPORT jint JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_getBytes__JJI
  (JNIEnv *, jclass, jlong, jlong, jint);

/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    getHeight
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_getHeight
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    getWidth
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_getWidth
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jitsi_impl_neomedia_quicktime_CVPixelBuffer
 * Method:    memcpy
 * Signature: ([BIIJ)V
 */
JNIEXPORT void JNICALL Java_org_jitsi_impl_neomedia_quicktime_CVPixelBuffer_memcpy
  (JNIEnv *, jclass, jbyteArray, jint, jint, jlong);

#ifdef __cplusplus
}
#endif
#endif
