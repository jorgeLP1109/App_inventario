# 🚀 Guía para Subir a GitHub

## Paso 1: Inicializar Git

Abre la terminal en la carpeta del proyecto y ejecuta:

```bash
cd d:\proyectos\app_inventario\android_app
git init
git add .
git commit -m "Initial commit: App de Inventario Android completa"
```

## Paso 2: Crear Repositorio en GitHub

1. Ve a https://github.com/new
2. Nombre del repositorio: `app-inventario-android`
3. Descripción: `Aplicación Android para gestión de inventario con Firebase`
4. Selecciona: **Público** o **Privado**
5. NO marques "Add a README" (ya lo tenemos)
6. Clic en **"Create repository"**

## Paso 3: Conectar y Subir

Copia los comandos que GitHub te muestra, o usa estos (reemplaza TU_USUARIO):

```bash
git remote add origin https://github.com/TU_USUARIO/app-inventario-android.git
git branch -M main
git push -u origin main
```

## Paso 4: Verificar

1. Refresca la página de GitHub
2. Deberías ver todos los archivos del proyecto
3. El README.md se mostrará automáticamente

## 📝 Notas Importantes

✅ **Archivos incluidos:**
- Todo el código fuente
- Layouts XML
- Documentación
- .gitignore
- README.md

❌ **Archivos excluidos (por .gitignore):**
- google-services.json (configuración de Firebase)
- build/ (archivos compilados)
- .gradle/ (caché de Gradle)
- .idea/ (configuración de Android Studio)
- *.apk (aplicaciones compiladas)

## 🔐 Seguridad

⚠️ **IMPORTANTE**: 
- `google-services.json` NO se sube al repositorio
- Cada usuario debe crear su propio proyecto Firebase
- Usa el template `google-services.json.template` como referencia

## 🔄 Actualizaciones Futuras

Para subir cambios:

```bash
git add .
git commit -m "Descripción de los cambios"
git push
```

## 🌿 Trabajar con Ramas

Para nuevas funcionalidades:

```bash
git checkout -b feature/nueva-funcionalidad
# Haz tus cambios
git add .
git commit -m "Agregar nueva funcionalidad"
git push origin feature/nueva-funcionalidad
```

Luego crea un Pull Request en GitHub.

## ✅ Checklist

Antes de subir, verifica:

- [ ] google-services.json está en .gitignore
- [ ] No hay credenciales en el código
- [ ] README.md está actualizado
- [ ] El proyecto compila sin errores
- [ ] Has probado la app

---

**¡Listo para compartir tu proyecto!** 🎉
