# App de Inventario - Android

## Características Principales

✅ **Autenticación de Usuarios**
- Login y registro con Firebase Authentication
- Roles: Admin y Vendedor
- Sesión persistente

✅ **Gestión de Productos**
- Agregar productos manualmente o con código de barras
- Búsqueda y filtrado
- Alertas de stock bajo
- Categorías personalizables

✅ **Gestión de Ventas**
- Registro de ventas con múltiples productos
- Actualización automática de inventario
- Historial completo de ventas

✅ **Reportes y Estadísticas**
- Ventas totales y ganancias
- Productos más vendidos
- Productos con mayor ganancia
- Gráficos visuales (Pie Chart y Bar Chart)

✅ **Sistema de Logs**
- Auditoría completa de acciones
- Registro de usuario, acción y timestamp
- Supervisión de cambios

✅ **Categorías**
- Categorías predefinidas: Comida, Snacks, Gaseosas, Bebidas, Limpieza
- Crear categorías personalizadas
- Código de colores

✅ **Multiusuario**
- Sincronización en tiempo real con Firebase
- Múltiples dispositivos con misma información
- Modo offline con persistencia local

## Configuración de Firebase

### Paso 1: Crear Proyecto en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto llamado "InventarioApp"
3. Habilita Google Analytics (opcional)

### Paso 2: Agregar App Android

1. En la consola de Firebase, haz clic en "Agregar app" → Android
2. Nombre del paquete: `com.inventario.app`
3. Descarga el archivo `google-services.json`
4. Coloca `google-services.json` en: `android_app/app/`

### Paso 3: Habilitar Servicios

#### Firebase Authentication
1. Ve a Authentication → Sign-in method
2. Habilita "Email/Password"

#### Firebase Realtime Database
1. Ve a Realtime Database → Crear base de datos
2. Selecciona ubicación (ej: us-central1)
3. Modo: "Comenzar en modo de prueba"
4. Reglas de seguridad (para desarrollo):

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

**IMPORTANTE**: Para producción, usa reglas más restrictivas:

```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    },
    "products": {
      ".read": "auth != null",
      ".write": "auth != null"
    },
    "categories": {
      ".read": "auth != null",
      ".write": "auth != null"
    },
    "sales": {
      ".read": "auth != null",
      ".write": "auth != null"
    },
    "logs": {
      ".read": "auth != null",
      ".write": "auth != null"
    }
  }
}
```

## Instalación y Ejecución

### Requisitos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 8 o superior
- SDK de Android 24 o superior
- Dispositivo Android o Emulador

### Pasos

1. **Abrir el proyecto en Android Studio**
   - File → Open → Selecciona la carpeta `android_app`

2. **Colocar google-services.json**
   - Descarga desde Firebase Console
   - Coloca en `app/` (al mismo nivel que build.gradle)

3. **Sincronizar Gradle**
   - Android Studio sincronizará automáticamente
   - O manualmente: File → Sync Project with Gradle Files

4. **Configurar Emulador**
   - Tools → Device Manager
   - Create Device → Selecciona un dispositivo (ej: Pixel 5)
   - API Level 24 o superior

5. **Ejecutar la App**
   - Haz clic en el botón "Run" (▶️)
   - Selecciona el emulador o dispositivo físico

## Estructura del Proyecto

```
android_app/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/inventario/app/
│   │       │   ├── activities/          # Pantallas de la app
│   │       │   ├── adapters/            # Adaptadores RecyclerView
│   │       │   ├── models/              # Modelos de datos
│   │       │   ├── utils/               # Utilidades (Firebase)
│   │       │   └── MainActivity.java    # Splash screen
│   │       ├── res/
│   │       │   ├── layout/              # Layouts XML
│   │       │   ├── values/              # Strings, colors
│   │       │   └── menu/                # Menús
│   │       └── AndroidManifest.xml
│   ├── build.gradle                     # Dependencias del módulo
│   └── google-services.json             # ⚠️ DEBES AGREGARLO
├── build.gradle                         # Configuración del proyecto
└── settings.gradle
```

## Uso de la Aplicación

### Primer Uso

1. **Registro**
   - Abre la app
   - Haz clic en "¿No tienes cuenta? Regístrate"
   - Completa: Nombre, Email, Contraseña
   - Selecciona rol: Admin o Vendedor
   - Haz clic en "Registrarse"

2. **Login**
   - Ingresa email y contraseña
   - Haz clic en "Iniciar Sesión"

### Dashboard

El dashboard muestra:
- Total de productos
- Productos con stock bajo
- Ventas del día
- Accesos rápidos a todas las secciones

### Gestión de Productos

1. **Agregar Producto**
   - Productos → Botón "+"
   - Completa información:
     - Nombre (obligatorio)
     - Código de barras (opcional, usa "Escanear")
     - Categoría
     - Precio de venta (obligatorio)
     - Precio de costo (opcional, para estadísticas)
     - Stock inicial (obligatorio)
     - Stock mínimo (opcional, default: 5)
   - Guardar

2. **Buscar Productos**
   - Usa la barra de búsqueda
   - Busca por nombre o código de barras

### Registrar Ventas

1. Ventas → Botón "+"
2. "Agregar Producto" → Selecciona producto
3. Ajusta cantidad con +/-
4. Agrega más productos si necesitas
5. "Completar Venta"
6. El stock se actualiza automáticamente

### Ver Reportes

- Reportes → Ver estadísticas:
  - Ventas totales
  - Ganancias totales
  - Promedio por venta
  - Gráfico de productos más vendidos
  - Gráfico de ganancias por producto

### Gestionar Categorías

- Categorías → Ver categorías predefinidas
- Botón "+" → Agregar nueva categoría

### Ver Logs de Auditoría

- Logs → Ver historial completo de acciones
- Muestra: Usuario, Acción, Detalles, Fecha/Hora

## Características Técnicas

### Arquitectura
- Patrón MVC (Model-View-Controller)
- Firebase Realtime Database para sincronización
- Persistencia local automática (offline)

### Librerías Utilizadas
- Firebase Auth (autenticación)
- Firebase Realtime Database (base de datos)
- ML Kit Barcode Scanning (escaneo de códigos)
- CameraX (cámara)
- MPAndroidChart (gráficos)
- Material Design Components

### Permisos
- INTERNET (Firebase)
- CAMERA (escaneo de códigos)
- WRITE_EXTERNAL_STORAGE (Android ≤ 28)
- READ_EXTERNAL_STORAGE (Android ≤ 32)

## Solución de Problemas

### Error: google-services.json no encontrado
- Descarga el archivo desde Firebase Console
- Colócalo en `app/` (no en `app/src/`)

### Error de sincronización Gradle
- File → Invalidate Caches / Restart
- Verifica conexión a internet
- Actualiza Android Studio

### La cámara no funciona
- Verifica permisos en configuración del dispositivo
- Usa un dispositivo físico (emulador puede tener problemas)

### No se sincronizan los datos
- Verifica conexión a internet
- Revisa reglas de Firebase Database
- Verifica que el usuario esté autenticado

## Plan Gratuito de Firebase

Firebase ofrece un plan gratuito (Spark) que incluye:
- **Authentication**: Ilimitado
- **Realtime Database**: 
  - 1 GB almacenamiento
  - 10 GB/mes descarga
  - 100 conexiones simultáneas

Esto es suficiente para 3 dispositivos y uso moderado.

## Próximas Mejoras Sugeridas

- [ ] Exportar reportes a PDF/Excel
- [ ] Notificaciones push para stock bajo
- [ ] Modo oscuro
- [ ] Backup automático
- [ ] Gestión de proveedores
- [ ] Múltiples sucursales
- [ ] Impresión de tickets de venta
- [ ] Dashboard con más métricas

## Soporte

Para problemas o preguntas:
1. Revisa la documentación de Firebase
2. Verifica los logs en Android Studio (Logcat)
3. Asegúrate de tener la última versión de Android Studio

## Licencia

Este proyecto es de código abierto para uso educativo y comercial.
