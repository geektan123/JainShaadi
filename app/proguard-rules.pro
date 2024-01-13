# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
-keep class com.jainmaitri.app.DataClass** { *; }
-keep class com.jainmaitri.app.Hobby { *; }
-keep class com.jainmaitri.app.CardItem { *; }
-keep class com.jainmaitri.app.Messages { *; }
-keep class com.jainmaitri.app.MessagesAdapter { *; }
-keep class com.jainmaitri.app.Model { *; }
-keep class com.jainmaitri.app.SharedViewModel { *; }
#
# Copyright 2016 Google Inc. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# The FlexboxLayoutManager may be set from a layout xml, in that situation the RecyclerView
# tries to instantiate the layout manager using reflection.
# This is to prevent the layout manager from being obfuscated.

-keep class com.jainmaitri.app.ImagePagerAdapter { *; }
-keep class com.jainmaitri.app.CardAdapter { *; }
-keep class com.jainmaitri.app.MyCardAdapter { *; }
-keep class com.jainmaitri.app.ViewProfile { *; }
-keep class com.jainmaitri.app.MainActivity { *; }
-keep class com.jainmaitri.app.MyProfile { *; }

#
# Copyright 2016 Google Inc. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# The FlexboxLayoutManager may be set from a layout xml, in that situation the RecyclerView
# tries to instantiate the layout manager using reflection.
# This is to prevent the layout manager from being obfuscated.
-keepnames public class com.google.android.flexbox.FlexboxLayoutManager






