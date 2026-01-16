# CÓMO VER LA APP EN EL EMULADOR DE ANDROID STUDIO

## Pasos Detallados

### 1. Instalar Android Studio

Si aún no lo tienes:
1. Descarga desde: https://developer.android.com/studio
2. Instala siguiendo el asistente
3. En la primera ejecución, descarga los componentes necesarios

### 2. Configurar Firebase (CRÍTICO)

⚠️ **SIN ESTE PASO LA APP NO FUNCIONARÁ**

Sigue la guía completa en `SETUP.md`, pero en resumen:

1. Crea proyecto en Firebase Console
2. Agrega app Android con package: `com.inventario.app`
3. Descarga `google-services.json`
4. Colócalo en: `android_app/app/google-services.json`
5. Habilita Authentication (Email/Password)
6. Crea Realtime Database con reglas de prueba

### 3. Abrir el Proyecto

1. Abre Android Studio
2. En la pantalla de bienvenida, clic en "Open"
3. Navega a: `d:\proyectos\app_inventario\android_app`
4. Clic en "OK"

### 4. Esperar Sincronización de Gradle

- Android Studio comenzará a sincronizar automáticamente
- Verás una barra de progreso en la parte inferior
- Puede tardar 2-5 minutos la primera vez
- Si hay errores, verifica que `google-services.json` esté en su lugar

### 5. Crear y Configurar Emulador

#### Opción A: Usar Device Manager (Recomendado)

1. En Android Studio, clic en el ícono de teléfono en la barra superior
   O ve a: Tools → Device Manager

2. Clic en "Create Device"

3. Selecciona un dispositivo:
   - **Recomendado**: Pixel 5
   - Categoría: Phone
   - Clic en "Next"

4. Selecciona System Image:
   - **Recomendado**: API Level 30 (Android 11.0)
   - Si no está descargado, clic en "Download" junto al nombre
   - Espera la descarga (puede tardar varios minutos)
   - Clic en "Next"

5. Configuración AVD:
   - Nombre: Pixel 5 API 30 (o el que prefieras)
   - Startup orientation: Portrait
   - Clic en "Finish"

#### Opción B: Usar Dispositivo Físico

1. Habilita "Opciones de desarrollador" en tu Android:
   - Configuración → Acerca del teléfono
   - Toca 7 veces en "Número de compilación"

2. Habilita "Depuración USB":
   - Configuración → Opciones de desarrollador
   - Activa "Depuración USB"

3. Conecta el dispositivo por USB
4. Acepta el mensaje de autorización en el teléfono

### 6. Ejecutar la Aplicación

1. En la barra superior de Android Studio:
   - Selecciona el dispositivo/emulador en el dropdown
   - Verás algo como "Pixel 5 API 30" o tu dispositivo físico

2. Clic en el botón verde "Run" (▶️)
   O presiona: Shift + F10

3. Espera a que compile:
   - Primera compilación: 2-5 minutos
   - Compilaciones posteriores: 30 segundos - 1 minuto

4. El emulador se abrirá automáticamente (si no estaba abierto)

5. La app se instalará y abrirá automáticamente

### 7. Usar la Aplicación

#### Primera Vez - Registro

1. La app abrirá en una pantalla de splash (2 segundos)
2. Luego verás la pantalla de Login
3. Clic en "¿No tienes cuenta? Regístrate"
4. Completa el formulario:
   ```
   Nombre: Admin Principal
   Email: admin@inventario.com
   Contraseña: admin123
   Rol: ● Admin
   ```
5. Clic en "REGISTRARSE"
6. Serás redirigido al Dashboard

#### Explorar el Dashboard

El Dashboard muestra:
- **Total de Productos**: 0 (inicialmente)
- **Stock Bajo**: 0
- **Ventas Hoy**: $0.00
- Botones para: Productos, Ventas, Reportes, Categorías, Logs

#### Agregar Primer Producto

1. Clic en "PRODUCTOS"
2. Clic en el botón flotante "+" (esquina inferior derecha)
3. Completa:
   ```
   Nombre: Coca Cola 2L
   Código de Barras: (déjalo vacío o escribe: 7501234567890)
   Categoría: Gaseosas
   Precio de Venta: 25.50
   Precio de Costo: 18.00
   Stock Inicial: 50
   Stock Mínimo: 10
   ```
4. Clic en "GUARDAR PRODUCTO"
5. Verás el producto en la lista

#### Registrar Primera Venta

1. Vuelve al Dashboard (botón atrás)
2. Clic en "VENTAS"
3. Clic en el botón flotante "+"
4. Clic en "AGREGAR PRODUCTO"
5. Selecciona "Coca Cola 2L - $25.50"
6. Usa los botones + y - para ajustar cantidad (ej: 3)
7. Clic en "COMPLETAR VENTA"
8. El stock se actualizará automáticamente (50 → 47)

#### Ver Reportes

1. Vuelve al Dashboard
2. Clic en "REPORTES"
3. Verás:
   - Ventas Totales: $76.50
   - Ganancias: $22.50
   - Promedio: $76.50
   - Gráficos de productos más vendidos

#### Ver Logs de Auditoría

1. Vuelve al Dashboard
2. Clic en "LOGS"
3. Verás todas las acciones realizadas:
   - LOGIN
   - PRODUCTO_CREADO
   - VENTA_REGISTRADA

### 8. Probar Multiusuario

#### En el mismo emulador:

1. Menú (⋮) → Cerrar Sesión
2. Registra otro usuario:
   ```
   Nombre: Vendedor 1
   Email: vendedor@inventario.com
   Contraseña: vendedor123
   Rol: ● Vendedor
   ```
3. Inicia sesión
4. Verás los mismos productos y ventas (sincronización en tiempo real)

#### En múltiples dispositivos:

1. Crea otro emulador (repite paso 5)
2. Ejecuta la app en ambos emuladores
3. Registra/inicia sesión con diferentes usuarios
4. Los cambios en uno se reflejarán en el otro instantáneamente

### 9. Probar Escaneo de Código de Barras

⚠️ **Nota**: El escaneo funciona mejor en dispositivos físicos

En emulador:
1. Productos → + → "ESCANEAR"
2. El emulador puede no tener cámara funcional
3. Usa la opción de ingresar código manualmente

En dispositivo físico:
1. Productos → + → "ESCANEAR"
2. Apunta a un código de barras real
3. Se detectará automáticamente

### 10. Atajos de Teclado Útiles

- **Shift + F10**: Ejecutar app
- **Ctrl + F9**: Compilar proyecto
- **Alt + Shift + F10**: Seleccionar configuración y ejecutar
- **Shift + F9**: Ejecutar en modo debug

### 11. Ver Logs en Tiempo Real

1. En Android Studio, pestaña "Logcat" (parte inferior)
2. Filtra por: "com.inventario.app"
3. Verás todos los eventos de la app en tiempo real

### 12. Verificar Datos en Firebase

1. Ve a Firebase Console
2. Realtime Database
3. Verás la estructura de datos en tiempo real:
   ```
   inventario-app/
   ├── users/
   ├── products/
   ├── categories/
   ├── sales/
   └── logs/
   ```

## Solución de Problemas en Emulador

### El emulador es muy lento
- Asegúrate de tener HAXM instalado (Intel)
- O AMD Hypervisor (AMD)
- Aumenta RAM del emulador: Edit AVD → Advanced → RAM: 2048 MB

### El emulador no inicia
- Tools → SDK Manager → SDK Tools
- Verifica que "Android Emulator" esté instalado
- Reinicia Android Studio

### La app se cierra inmediatamente
- Revisa Logcat para ver el error
- Verifica que google-services.json esté configurado
- Verifica que Firebase esté habilitado

### "Unable to resolve dependency"
- File → Invalidate Caches / Restart
- Verifica conexión a internet
- Sync Project with Gradle Files

### La cámara no funciona en emulador
- Es normal, usa dispositivo físico para escaneo
- O ingresa códigos manualmente

## Capturas de Pantalla Recomendadas

Para documentar tu app, toma capturas de:
1. Pantalla de Login
2. Dashboard con datos
3. Lista de productos
4. Formulario de nuevo producto
5. Registro de venta
6. Reportes con gráficos
7. Lista de categorías
8. Logs de auditoría

En emulador: Clic derecho en la ventana → "Take Screenshot"

## Rendimiento Óptimo

Para mejor experiencia:
- **RAM del PC**: Mínimo 8 GB (recomendado 16 GB)
- **Espacio en disco**: 10 GB libres
- **Procesador**: Intel i5/AMD Ryzen 5 o superior
- **Emulador**: API 30, RAM 2 GB, Resolución 1080x1920

## Próximos Pasos

Una vez que la app funcione:
1. Prueba todas las funcionalidades
2. Agrega más productos de diferentes categorías
3. Registra múltiples ventas
4. Revisa los reportes y estadísticas
5. Verifica los logs de auditoría
6. Prueba con múltiples usuarios

¡Disfruta tu app de inventario! 🎉
