# ⚡ INICIO RÁPIDO - LEE ESTO PRIMERO

## 🎯 ¿QUÉ TENGO?

Una **app Android completa de inventario** con:
- ✅ Login y registro de usuarios
- ✅ Gestión de productos (manual + código de barras)
- ✅ Categorías personalizables
- ✅ Registro de ventas
- ✅ Reportes y estadísticas con gráficos
- ✅ Sistema de logs para auditoría
- ✅ Sincronización multiusuario en tiempo real

## 📚 ¿QUÉ DOCUMENTOS LEER?

### Para empezar AHORA (30 minutos):
👉 **GUIA_VISUAL.md** - Tutorial paso a paso con instrucciones detalladas

### Para configurar Firebase:
👉 **SETUP.md** - Guía de configuración de Firebase

### Para entender el proyecto:
👉 **RESUMEN_EJECUTIVO.md** - Resumen completo del proyecto
👉 **PROYECTO.md** - Detalles técnicos

### Para ejecutar en emulador:
👉 **EMULADOR.md** - Guía específica del emulador

### Para desarrolladores:
👉 **README.md** - Documentación técnica completa

## 🚀 PASOS RÁPIDOS (30 MIN)

### 1. Configurar Firebase (15 min)
```
1. Ve a: https://console.firebase.google.com/
2. Crea proyecto "InventarioApp"
3. Agrega app Android: com.inventario.app
4. Descarga google-services.json
5. Habilita Authentication (Email/Password)
6. Crea Realtime Database (modo prueba)
```

### 2. Configurar Android Studio (10 min)
```
1. Abre Android Studio
2. File → Open → android_app
3. Copia google-services.json a: android_app/app/
4. Espera sincronización de Gradle
5. Crea emulador (Pixel 5, API 30)
```

### 3. Ejecutar (5 min)
```
1. Selecciona emulador
2. Clic en Run ▶️
3. Espera compilación
4. Registra usuario en la app
5. ¡Usa la app!
```

## ⚠️ IMPORTANTE

### ANTES de ejecutar la app:
1. ✅ Configura Firebase (OBLIGATORIO)
2. ✅ Descarga google-services.json
3. ✅ Colócalo en: android_app/app/
4. ✅ Habilita Authentication
5. ✅ Crea Realtime Database

### SIN estos pasos, la app NO funcionará.

## 📁 ESTRUCTURA DE ARCHIVOS

```
android_app/
├── 📄 GUIA_VISUAL.md          ← EMPIEZA AQUÍ
├── 📄 SETUP.md                ← Configuración Firebase
├── 📄 EMULADOR.md             ← Guía del emulador
├── 📄 RESUMEN_EJECUTIVO.md    ← Resumen del proyecto
├── 📄 PROYECTO.md             ← Detalles técnicos
├── 📄 README.md               ← Documentación técnica
├── 📄 INICIO_RAPIDO.md        ← Este archivo
├── app/
│   ├── google-services.json   ← DEBES COLOCARLO AQUÍ
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── java/          ← Código Java
│           ├── res/           ← Recursos (layouts, etc)
│           └── AndroidManifest.xml
├── build.gradle
└── settings.gradle
```

## 🎯 ORDEN RECOMENDADO

### Si quieres ejecutar YA:
1. Lee **GUIA_VISUAL.md** (10 min lectura)
2. Sigue los pasos (20 min ejecución)
3. ¡Listo!

### Si quieres entender primero:
1. Lee **RESUMEN_EJECUTIVO.md** (5 min)
2. Lee **PROYECTO.md** (10 min)
3. Lee **GUIA_VISUAL.md** (10 min)
4. Ejecuta (20 min)

### Si eres desarrollador:
1. Lee **README.md** (15 min)
2. Lee **SETUP.md** (5 min)
3. Revisa el código fuente
4. Ejecuta y prueba

## 🆘 PROBLEMAS COMUNES

### "google-services.json is missing"
**Solución**: Descárgalo de Firebase y colócalo en app/

### "FirebaseApp initialization unsuccessful"
**Solución**: Verifica que Firebase esté configurado correctamente

### La app se cierra al abrir
**Solución**: Revisa Logcat en Android Studio para ver el error

### El emulador es muy lento
**Solución**: Aumenta RAM del emulador a 2048 MB

## 📞 ¿NECESITAS AYUDA?

1. Revisa **GUIA_VISUAL.md** - Tiene soluciones paso a paso
2. Revisa **SETUP.md** - Para problemas de Firebase
3. Revisa **EMULADOR.md** - Para problemas del emulador
4. Revisa Logcat en Android Studio - Para errores de código

## ✅ CHECKLIST ANTES DE EMPEZAR

- [ ] Tengo Android Studio instalado
- [ ] Tengo cuenta de Google (para Firebase)
- [ ] Tengo 30 minutos disponibles
- [ ] Tengo conexión a internet
- [ ] He leído este archivo completo

## 🎉 ¿LISTO?

### Abre **GUIA_VISUAL.md** y comienza

O si prefieres ir directo:

1. **Configura Firebase**: https://console.firebase.google.com/
2. **Abre Android Studio**: File → Open → android_app
3. **Coloca google-services.json**: en app/
4. **Ejecuta**: Shift + F10

---

**¡Éxito con tu app de inventario!** 🚀

Cualquier duda, revisa los archivos de documentación.
