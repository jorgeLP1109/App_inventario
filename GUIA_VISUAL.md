# 🎬 GUÍA VISUAL PASO A PASO

## 📋 ANTES DE EMPEZAR

### Lo que necesitas:
- [ ] Android Studio instalado
- [ ] Cuenta de Google (para Firebase)
- [ ] 30 minutos de tiempo
- [ ] Conexión a internet

---

## 🔥 PARTE 1: CONFIGURAR FIREBASE (15 minutos)

### Paso 1.1: Crear Proyecto Firebase

1. Abre tu navegador
2. Ve a: **https://console.firebase.google.com/**
3. Clic en **"Agregar proyecto"** o **"Add project"**
4. Nombre del proyecto: **InventarioApp**
5. Clic en **"Continuar"**
6. Google Analytics: **Desactiva** (opcional, no es necesario)
7. Clic en **"Crear proyecto"**
8. Espera 30 segundos
9. Clic en **"Continuar"**

✅ **Resultado**: Estás en la consola de tu proyecto Firebase

---

### Paso 1.2: Agregar App Android

1. En la página principal de Firebase, busca el ícono de **Android** (robot verde)
2. Clic en el ícono de Android
3. Completa el formulario:
   ```
   Nombre del paquete de Android: com.inventario.app
   Apodo de la app: Inventario App
   Certificado de firma SHA-1: (déjalo vacío)
   ```
4. Clic en **"Registrar app"**
5. Clic en **"Descargar google-services.json"**
6. **GUARDA EL ARCHIVO** en tu carpeta de Descargas
7. Clic en **"Siguiente"** (ignora los pasos de código)
8. Clic en **"Siguiente"** otra vez
9. Clic en **"Continuar a la consola"**

✅ **Resultado**: Tienes el archivo google-services.json descargado

---

### Paso 1.3: Habilitar Authentication

1. En el menú lateral izquierdo, busca **"Authentication"**
2. Clic en **"Authentication"**
3. Clic en **"Comenzar"** o **"Get started"**
4. Verás una lista de proveedores
5. Busca **"Correo electrónico/contraseña"** o **"Email/Password"**
6. Clic en esa fila
7. Activa el switch **"Habilitar"** (debe ponerse azul)
8. Clic en **"Guardar"**

✅ **Resultado**: Authentication está habilitado

---

### Paso 1.4: Crear Realtime Database

1. En el menú lateral izquierdo, busca **"Realtime Database"**
2. Clic en **"Realtime Database"**
3. Clic en **"Crear base de datos"** o **"Create database"**
4. Ubicación: Selecciona **"United States (us-central1)"** o la más cercana
5. Clic en **"Siguiente"**
6. Reglas de seguridad: Selecciona **"Comenzar en modo de prueba"**
7. Clic en **"Habilitar"**
8. Espera 10 segundos

✅ **Resultado**: Database creada, verás una pantalla con datos vacíos

---

### Paso 1.5: Configurar Reglas de Seguridad

1. Estás en la pantalla de Realtime Database
2. Clic en la pestaña **"Reglas"** (arriba)
3. Verás un editor de texto con código JSON
4. **BORRA TODO** el contenido
5. **COPIA Y PEGA** este código:

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

6. Clic en **"Publicar"**
7. Confirma en el diálogo

✅ **Resultado**: Solo usuarios autenticados pueden leer/escribir

---

## 💻 PARTE 2: CONFIGURAR ANDROID STUDIO (10 minutos)

### Paso 2.1: Abrir el Proyecto

1. Abre **Android Studio**
2. Si ves proyectos recientes, clic en **"Open"**
3. Si no, en la pantalla de bienvenida, clic en **"Open"**
4. Navega a: **d:\proyectos\app_inventario\android_app**
5. Clic en **"OK"**
6. Espera a que cargue (verás una barra de progreso abajo)

✅ **Resultado**: Proyecto abierto, Gradle sincronizando

---

### Paso 2.2: Colocar google-services.json

1. En Android Studio, mira el panel izquierdo (Project)
2. Asegúrate de que esté en vista **"Android"** (dropdown arriba)
3. Busca la carpeta **"app"** (con ícono de Android)
4. Abre tu explorador de archivos de Windows
5. Ve a tu carpeta de **Descargas**
6. Busca el archivo **google-services.json**
7. **COPIA** el archivo (Ctrl+C)
8. En el explorador, ve a: **d:\proyectos\app_inventario\android_app\app**
9. **PEGA** el archivo aquí (Ctrl+V)
10. Vuelve a Android Studio
11. Clic derecho en la carpeta **"app"** → **"Synchronize 'app'"**

✅ **Resultado**: google-services.json está en su lugar

---

### Paso 2.3: Esperar Sincronización de Gradle

1. Mira la parte inferior de Android Studio
2. Verás: **"Gradle sync in progress..."**
3. **ESPERA** hasta que termine (2-5 minutos la primera vez)
4. Cuando termine, verás: **"Gradle sync finished"**
5. Si hay errores en rojo, revisa que google-services.json esté en app/

✅ **Resultado**: Proyecto sincronizado sin errores

---

### Paso 2.4: Crear Emulador

1. En la barra superior, busca el ícono de **teléfono** 📱
2. Clic en el ícono
3. Se abre **"Device Manager"**
4. Clic en **"Create Device"** (botón con +)
5. Categoría: **Phone**
6. Selecciona: **Pixel 5**
7. Clic en **"Next"**
8. System Image: Busca **"R"** (API Level 30, Android 11.0)
9. Si dice **"Download"**, clic en **"Download"**
   - Acepta la licencia
   - Espera la descarga (5-10 minutos)
10. Cuando esté descargado, selecciónalo
11. Clic en **"Next"**
12. Nombre: **Pixel 5 API 30** (o déjalo como está)
13. Clic en **"Finish"**

✅ **Resultado**: Emulador creado y listo

---

## 🚀 PARTE 3: EJECUTAR LA APP (5 minutos)

### Paso 3.1: Ejecutar

1. En la barra superior de Android Studio
2. Busca el dropdown de dispositivos (al lado del botón verde ▶️)
3. Selecciona: **Pixel 5 API 30**
4. Clic en el botón verde **"Run"** ▶️
5. O presiona: **Shift + F10**
6. Espera a que compile (2-5 minutos la primera vez)
7. El emulador se abrirá automáticamente
8. Espera a que Android inicie (1-2 minutos)
9. La app se instalará y abrirá automáticamente

✅ **Resultado**: App ejecutándose en el emulador

---

## 📱 PARTE 4: USAR LA APP (10 minutos)

### Paso 4.1: Registrar Usuario

1. Verás la pantalla de **Login**
2. Clic en **"¿No tienes cuenta? Regístrate"**
3. Completa el formulario:
   ```
   Nombre: Admin Principal
   Email: admin@inventario.com
   Contraseña: admin123
   Rol: Selecciona "Admin"
   ```
4. Clic en **"REGISTRARSE"**
5. Espera 2 segundos
6. Serás redirigido al **Dashboard**

✅ **Resultado**: Usuario registrado y en Dashboard

---

### Paso 4.2: Ver Dashboard

Verás:
- **Total de Productos**: 0
- **Stock Bajo**: 0
- **Ventas Hoy**: $0.00
- 5 tarjetas: Productos, Ventas, Reportes, Categorías, Logs

✅ **Resultado**: Dashboard funcionando

---

### Paso 4.3: Agregar Primer Producto

1. Clic en la tarjeta **"PRODUCTOS"**
2. Verás una lista vacía
3. Clic en el botón flotante **"+"** (esquina inferior derecha)
4. Completa el formulario:
   ```
   Nombre del Producto: Coca Cola 2L
   Código de Barras: (déjalo vacío o escribe: 123456789)
   Categoría: Selecciona "Gaseosas"
   Precio de Venta: 25.50
   Precio de Costo: 18.00
   Stock Inicial: 50
   Stock Mínimo: 10
   ```
5. Clic en **"GUARDAR PRODUCTO"**
6. Verás el producto en la lista

✅ **Resultado**: Primer producto creado

---

### Paso 4.4: Agregar Más Productos

Repite el paso anterior con estos productos:

**Producto 2:**
```
Nombre: Sabritas Original
Categoría: Snacks
Precio: 15.00
Costo: 10.00
Stock: 100
Stock Mínimo: 20
```

**Producto 3:**
```
Nombre: Agua Ciel 1L
Categoría: Bebidas
Precio: 12.00
Costo: 8.00
Stock: 80
Stock Mínimo: 15
```

✅ **Resultado**: 3 productos en inventario

---

### Paso 4.5: Registrar Primera Venta

1. Botón **"Atrás"** (flecha en la barra superior)
2. Vuelves al Dashboard
3. Clic en la tarjeta **"VENTAS"**
4. Clic en el botón flotante **"+"**
5. Clic en **"AGREGAR PRODUCTO"**
6. Selecciona **"Coca Cola 2L - $25.50"**
7. Verás el producto agregado con cantidad 1
8. Clic en el botón **"+"** junto al producto para aumentar a 3
9. Clic en **"AGREGAR PRODUCTO"** otra vez
10. Selecciona **"Sabritas Original - $15.00"**
11. Aumenta cantidad a 5
12. Verás: **Total: $151.50**
13. Clic en **"COMPLETAR VENTA"**
14. Verás un mensaje: "Venta registrada"

✅ **Resultado**: Primera venta registrada

---

### Paso 4.6: Ver Reportes

1. Vuelve al Dashboard (botón atrás)
2. Clic en la tarjeta **"REPORTES"**
3. Verás:
   ```
   Ventas Totales: $151.50
   Ganancias: $47.50
   Promedio: $151.50
   ```
4. Desplázate hacia abajo
5. Verás gráficos de:
   - Productos más vendidos (gráfico circular)
   - Ganancias por producto (gráfico de barras)

✅ **Resultado**: Reportes funcionando

---

### Paso 4.7: Ver Categorías

1. Vuelve al Dashboard
2. Clic en la tarjeta **"CATEGORÍAS"**
3. Verás las categorías predefinidas:
   - Comida
   - Snacks
   - Gaseosas
   - Bebidas
   - Limpieza
4. Clic en el botón **"+"**
5. Escribe: **Dulces**
6. Clic en **"Guardar"**
7. Verás la nueva categoría en la lista

✅ **Resultado**: Categorías funcionando

---

### Paso 4.8: Ver Logs de Auditoría

1. Vuelve al Dashboard
2. Clic en la tarjeta **"LOGS"**
3. Verás el historial de acciones:
   ```
   Admin Principal
   LOGIN
   Usuario inició sesión
   [fecha y hora]

   Admin Principal
   PRODUCTO_CREADO
   Producto: Coca Cola 2L
   [fecha y hora]

   Admin Principal
   VENTA_REGISTRADA
   Total: $151.50
   [fecha y hora]
   ```

✅ **Resultado**: Sistema de logs funcionando

---

### Paso 4.9: Probar Búsqueda

1. Vuelve al Dashboard
2. Clic en **"PRODUCTOS"**
3. En la barra de búsqueda arriba, escribe: **coca**
4. Verás solo "Coca Cola 2L"
5. Borra el texto
6. Verás todos los productos otra vez

✅ **Resultado**: Búsqueda funcionando

---

### Paso 4.10: Verificar Stock Actualizado

1. En la lista de productos, busca **"Coca Cola 2L"**
2. Verás: **Stock: 47** (era 50, vendiste 3)
3. Busca **"Sabritas Original"**
4. Verás: **Stock: 95** (era 100, vendiste 5)

✅ **Resultado**: Stock se actualiza automáticamente

---

## 🔍 VERIFICAR EN FIREBASE

### Paso 5.1: Ver Datos en Firebase Console

1. Vuelve a tu navegador
2. Ve a Firebase Console
3. Clic en **"Realtime Database"**
4. Verás la estructura de datos:
   ```
   inventario-app
   ├── categories
   │   ├── -Nxxx...
   │   │   ├── name: "Comida"
   │   │   ├── color: "#FF5722"
   │   │   └── createdAt: 1234567890
   │   └── ...
   ├── products
   │   ├── -Nxxx...
   │   │   ├── name: "Coca Cola 2L"
   │   │   ├── price: 25.5
   │   │   ├── stock: 47
   │   │   └── ...
   │   └── ...
   ├── sales
   │   └── -Nxxx...
   │       ├── total: 151.5
   │       ├── profit: 47.5
   │       └── ...
   ├── logs
   │   └── ...
   └── users
       └── ...
   ```

✅ **Resultado**: Datos sincronizados en Firebase

---

## 🎉 ¡FELICIDADES!

Has completado exitosamente:
- ✅ Configuración de Firebase
- ✅ Configuración de Android Studio
- ✅ Ejecución de la app
- ✅ Registro de usuario
- ✅ Gestión de productos
- ✅ Registro de ventas
- ✅ Visualización de reportes
- ✅ Sistema de logs
- ✅ Sincronización en tiempo real

---

## 📸 CAPTURAS DE PANTALLA

Para documentar tu app, toma capturas:

1. **En el emulador**, clic derecho → **"Take Screenshot"**
2. Guarda capturas de:
   - Pantalla de Login
   - Dashboard
   - Lista de productos
   - Formulario de nuevo producto
   - Registro de venta
   - Reportes con gráficos
   - Categorías
   - Logs

---

## 🔄 PROBAR MULTIUSUARIO

### En el mismo emulador:

1. En la app, clic en los 3 puntos (⋮) arriba a la derecha
2. Clic en **"Cerrar Sesión"**
3. En Login, clic en **"Regístrate"**
4. Crea otro usuario:
   ```
   Nombre: Vendedor 1
   Email: vendedor@inventario.com
   Contraseña: vendedor123
   Rol: Vendedor
   ```
5. Inicia sesión
6. Verás los mismos productos y ventas

### En múltiples emuladores:

1. Crea otro emulador (Paso 2.4)
2. Ejecuta la app en ambos
3. Los cambios en uno se verán en el otro instantáneamente

---

## 🆘 PROBLEMAS COMUNES

### "google-services.json is missing"
**Solución:**
1. Verifica que el archivo esté en: `android_app/app/`
2. File → Sync Project with Gradle Files

### La app se cierra al abrir
**Solución:**
1. Mira la pestaña "Logcat" en Android Studio
2. Busca errores en rojo
3. Verifica que Firebase esté configurado correctamente

### El emulador es muy lento
**Solución:**
1. Cierra otras aplicaciones
2. En Device Manager, edita el AVD
3. Advanced Settings → RAM: 2048 MB

### No se sincronizan los datos
**Solución:**
1. Verifica conexión a internet
2. Revisa las reglas de Firebase Database
3. Asegúrate de estar autenticado

---

## 📚 DOCUMENTACIÓN ADICIONAL

- **README.md**: Documentación técnica completa
- **SETUP.md**: Guía detallada de configuración
- **EMULADOR.md**: Guía específica del emulador
- **PROYECTO.md**: Resumen del proyecto

---

## 🎯 PRÓXIMOS PASOS

1. Experimenta con todas las funcionalidades
2. Agrega más productos de diferentes categorías
3. Registra múltiples ventas
4. Revisa los reportes y estadísticas
5. Prueba con múltiples usuarios
6. Personaliza la app según tus necesidades

---

**¡Disfruta tu app de inventario completamente funcional! 🚀**
