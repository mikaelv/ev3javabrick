# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-dontwarn org.usb4java.**
-keep,includedescriptorclasses class org.usb4java.LibUsb { public *; }

#-dontwarn com.google.json.internal.UnsafeAllocator
-dontnote com.google.json.internal.UnsafeAllocator 
-dontnote com.google.common.cache.Striped64*
-dontnote com.google.gson.internal.UnsafeAllocator
-dontnote sun.misc.Unsafe

-dontwarn com.google.common.cache.Striped64*

-dontwarn com.google.common.primitives.UnsignedBytes*
-dontwarn com.google.common.reflect.Invokable*
-dontnote com.google.common.reflect.Invokable*
-dontwarn org.apache.xmlrpc.webserver.*Servlet*
-dontwarn org.apache.xmlrpc.jaxb.**
-dontnote org.apache.xmlrpc.metadata.Util

-dontnote javax.usb.UsbHostManager
-dontnote org.ros.internal.message.definition.MessageDefinitionReflectionProvider
-dontwarn org.ros.internal.system.Process
-dontwarn org.xbill.DNS.spi.**

-dontnote org.jboss.netty.**
# http://stackoverflow.com/questions/9730097/proguard-and-netty-on-android
# Get rid of warnings about unreachable but unused classes referred to by Netty
-dontwarn org.jboss.netty.**

# Needed by commons logging
#-keep class org.apache.commons.logging.* {*;}


#Some Factory that seemed to be pruned
#-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdater {*;}
#-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdaterImpl{*;}

#Some important internal fields that where removed     
-keep class org.jboss.netty.channel.DefaultChannelPipeline{volatile <fields>;}

#A Factory which has a static factory implementation selector which is pruned
-keep class org.jboss.netty.util.internal.QueueFactory{static <fields>;}

#Some fields whose names need to be maintained because they are accessed using inflection
-keepclassmembernames class org.jboss.netty.util.internal.**{*;}
