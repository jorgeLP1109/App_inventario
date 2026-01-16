# GUÍA RÁPIDA DE CONFIGURACIÓN

## ⚠️ IMPORTANTE: Antes de ejecutar la app

### 1. Configurar Firebase (OBLIGATORIO)

#### Paso 1: Crear proyecto Firebase
1. Ve a: https://console.firebase.google.com/
2. Clic en "Agregar proyecto"
3. Nombre: "InventarioApp" (o el que prefieras)
4. Continúa con los pasos (Google Analytics es opcional)

#### Paso 2: Agregar app Android
1. En el proyecto Firebase, clic en el ícono de Android
2. Nombre del paquete Android: `com.inventario.app`
3. Apodo de la app: "Inventario App"
4. Certificado SHA-1: (opcional por ahora)
5. Clic en "Registrar app"

#### Paso 3: Descargar google-services.json
1. Descarga el archivo `google-services.json`
2. Colócalo en: `android_app/app/google-services.json`
   (Al mismo nivel que el archivo build.gradle del módulo app)

#### Paso 4: Habilitar Authentication
1. En Firebase Console → Authentication
2. Clic en "Comenzar"
3. Pestaña "Sign-in method"
4. Habilita "Correo electrónico/contraseña"
5. Guarda

#### Paso 5: Crear Realtime Database
1. En Firebase Console → Realtime Database
2. Clic en "Crear base de datos"
3. Ubicación: Elige la más cercana (ej: us-central1)
4. Reglas de seguridad: "Comenzar en modo de prueba"
5. Habilitar

#### Paso 6: Configurar reglas de seguridad
1. En Realtime Database → Reglas
2. Pega este código:

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

3. Publicar

### 2. Abrir en Android Studio

1. Abre Android Studio
2. File → Open
3. Selecciona la carpeta: `android_app`
4. Espera a que Gradle sincronice (puede tardar unos minutos)

### 3. Verificar google-services.json

⚠️ CRÍTICO: Asegúrate de que el archivo esté en:
```
android_app/
└── app/
    ├── build.gradle
    └── google-services.json  ← AQUÍ
```

### 4. Configurar Emulador

1. Tools → Device Manager
2. Create Device
3. Selecciona: Pixel 5 o similar
4. System Image: API 30 o superior (Android 11+)
5. Finish

### 5. Ejecutar la App

1. Selecciona el emulador en la barra superior
2. Clic en el botón Run (▶️) o Shift+F10
3. Espera a que compile y se instale

### 6. Primer Uso

1. La app abrirá en la pantalla de Login
2. Clic en "¿No tienes cuenta? Regístrate"
3. Completa el formulario:
   - Nombre: Tu nombre
   - Email: tu@email.com
   - Contraseña: mínimo 6 caracteres
   - Rol: Selecciona "Admin"
4. Clic en "Registrarse"
5. Inicia sesión con las credenciales

## Verificación Rápida

### ✅ Checklist antes de ejecutar:
- [ ] Proyecto Firebase creado
- [ ] App Android agregada en Firebase
- [ ] google-services.json descargado y colocado en app/
- [ ] Authentication habilitado (Email/Password)
- [ ] Realtime Database creado
- [ ] Reglas de seguridad configuradas
- [ ] Proyecto abierto en Android Studio
- [ ] Gradle sincronizado sin errores
- [ ] Emulador configurado

## Problemas Comunes

### "google-services.json is missing"
- Descarga el archivo desde Firebase Console
- Colócalo en `android_app/app/` (NO en src/)
- Sync Project with Gradle Files

### "Failed to resolve: com.google.firebase"
- Verifica conexión a internet
- File → Invalidate Caches / Restart
- Sync Project with Gradle Files

### "FirebaseApp initialization unsuccessful"
- Verifica que google-services.json esté en la ubicación correcta
- Verifica que el package name sea: com.inventario.app
- Rebuild Project

### La app se cierra al abrir
- Revisa Logcat en Android Studio
- Verifica que Firebase esté configurado correctamente
- Asegúrate de que las reglas de Database permitan lectura/escritura

## Estructura de Firebase Database

Después de usar la app, tu database tendrá esta estructura:

```
inventario-app/
├── users/
│   └── {userId}/
│       ├── uid
│       ├── email
│       ├── name
│       ├── role
│       └── createdAt
├── products/
│   └── {productId}/
│       ├── name
│       ├── barcode
│       ├── categoryId
│       ├── price
│       ├── costPrice
│       ├── stock
│       ├── minStock
│       └── ...
├── categories/
│   └── {categoryId}/
│       ├── name
│       ├── color
│       └── createdAt
├── sales/
│   └── {saleId}/
│       ├── items[]
│       ├── total
│       ├── profit
│       ├── soldBy
│       └── timestamp
└── logs/
    └── {logId}/
        ├── userId
        ├── userName
        ├── action
        ├── details
        └── timestamp
```

## Contacto y Soporte

Si tienes problemas:
1. Revisa el README.md completo
2. Verifica los logs en Logcat
3. Consulta la documentación de Firebase: https://firebase.google.com/docs

¡Listo! Tu app de inventario está configurada y lista para usar. 🚀
