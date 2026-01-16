# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.firebase.database.PropertyName <fields>;
}
-keep class com.inventario.app.models.** { *; }
