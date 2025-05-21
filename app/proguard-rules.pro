# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Mantener modelos de datos
-keep class com.tuapp.model.** { *; }

# Mantener entidades y DAOs de Room
-keep class androidx.room.** { *; }
-keep class com.tuapp.data.db.** { *; }

# Mantener clases de Dagger/Hilt
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.tuapp.di.** { *; }

# Si tu proyecto usa WebView con JS interface, descomenta y reemplaza la clase:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Mantener información de línea para stacktraces
-keepattributes SourceFile,LineNumberTable

# Renombrar el atributo del archivo fuente
-renamesourcefileattribute SourceFile