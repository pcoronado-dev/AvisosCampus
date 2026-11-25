PROMPT 1: Analiza todo el proyecto Android antes de realizar cualquier modificación. Identifica la clase encargada de gestionar las notificaciones (`Notificador`), el `ViewModel` correspondiente y los componentes que intervienen en el envío de notificaciones.

**OBJETIVO: IMPLEMENTAR DOS CANALES DE NOTIFICACIÓN INDEPENDIENTES EN ANDROID (API 26+).**

### 1. Crear y configurar los canales

Modifica la clase `Notificador` para crear y registrar automáticamente los siguientes canales mediante `NotificationManager`:

**Canal 1: Urgentes**
- ID: `canal_urgentes`.
- Nombre visible: `Urgentes`.
- Descripción: Notificaciones importantes, como recordatorios.
- Importancia: `NotificationManager.IMPORTANCE_HIGH`.
- Debe permitir que las notificaciones aparezcan como emergentes (Heads-up), siempre que el dispositivo y la configuración del usuario lo permitan.

**Canal 2: Avisos**
- ID: `canal_avisos`.
- Nombre visible: `Avisos`.
- Descripción: Notificaciones generales de la aplicación.
- Importancia: `NotificationManager.IMPORTANCE_DEFAULT`.

Los dos canales deben crearse y registrarse correctamente al iniciar la aplicación. Evita crear canales duplicados y utiliza los mismos identificadores en toda la aplicación.

### 2. Actualizar la lógica de envío

Modifica los métodos de la clase `Notificador` para que cada notificación utilice el canal correspondiente:

- **Recordatorios:** deben utilizar exclusivamente el canal `canal_urgentes`.
- **Notificaciones generales:** deben utilizar exclusivamente el canal `canal_avisos`.

Asegúrate de que cada notificación se construya utilizando `NotificationCompat.Builder` con el ID del canal correcto.

### 3. Actualizar el ViewModel

Localiza el `ViewModel` encargado de gestionar las notificaciones y adapta su lógica para trabajar con los dos canales.

Requisitos:
- Mantener la separación entre la lógica de presentación y la lógica de notificaciones.
- El `ViewModel` debe invocar los métodos correspondientes de `Notificador`.
- Los eventos de recordatorio deben enviar notificaciones urgentes.
- Los eventos generales deben enviar notificaciones estándar.
- Evitar duplicar código y conservar el flujo actual de eventos y estados (`UiState`, `SharedFlow` o el mecanismo que ya utilice el proyecto).

### 4. Compatibilidad y permisos

- Mantener compatibilidad con Android API 26 o superior.
- Comprobar que los canales se creen antes de enviar notificaciones.
- Verificar el permiso `POST_NOTIFICATIONS` en Android 13 (API 33+) y superiores.
- No solicitar permisos innecesariamente si ya están concedidos.
- No intentar enviar notificaciones si el permiso requerido no está disponible.
- Conservar las configuraciones de los canales, respetando las modificaciones que realice el usuario desde los ajustes de Android.

### 5. Restricciones importantes

- No modificar innecesariamente otras clases o funcionalidades.
- No cambiar la arquitectura actual del proyecto.
- No eliminar funcionalidades existentes.
- No crear clases duplicadas si ya existen componentes que cumplen esa función.
- Respetar los nombres de paquetes, convenciones y estructura de carpetas.
- Mantener el código limpio, organizado y compatible con las dependencias actuales.
- Reutilizar las implementaciones existentes siempre que sea posible.

### 6. Verificación final

Después de implementar los cambios:

1. Comprueba que ambos canales se registren correctamente.
2. Verifica que los recordatorios utilicen `canal_urgentes`.
3. Verifica que las notificaciones generales utilicen `canal_avisos`.
4. Comprueba que no existan errores de compilación.
5. Revisa que los permisos de Android 13+ estén correctamente gestionados.
6. Verifica que la arquitectura y las funcionalidades anteriores sigan funcionando.

### 7. Entrega del resultado

Al terminar, proporciona:

- Las clases y archivos que modificaste.
- La ruta exacta de cada archivo.
- El código completo actualizado de cada archivo modificado.
- Una explicación breve de cómo funciona cada canal.
- Una explicación de cómo se comunica el ViewModel con `Notificador`.
- Los pasos para probar ambos tipos de notificaciones en un emulador o dispositivo Android.

**IMPORTANTE:** Primero analiza el código existente y comprende cómo funciona el sistema actual de notificaciones. Después realiza las modificaciones necesarias directamente sobre el proyecto. No reemplaces la arquitectura existente por una implementación nueva.

PROMPT 2: Analiza el proyecto Android y localiza la clase `Notificador`, responsable de construir y mostrar las notificaciones mediante `NotificationCompat.Builder`.

### OBJETIVO
Implementar soporte para mensajes largos en las notificaciones utilizando `NotificationCompat.BigTextStyle`, de manera que el usuario pueda expandir una notificación y visualizar el contenido completo del mensaje.

### 1. Modificar NotificationCompat.Builder

Localiza todos los métodos de la clase `Notificador` que construyen notificaciones mediante `NotificationCompat.Builder`.

Agrega la siguiente configuración al constructor de cada notificación que muestre mensajes:

```kotlin
.setStyle(
    NotificationCompat.BigTextStyle()
        .bigText(mensaje)
)
```

Donde `mensaje` debe ser la variable que contiene el texto completo de la notificación.

**IMPORTANTE:** No utilices una versión resumida, truncada o recortada del mensaje. Debes pasar el contenido completo para que se visualice al expandir la notificación.

### 2. Conservar la configuración existente

Mantén intactos los siguientes elementos de cada notificación:

- Icono mediante `.setSmallIcon()`.
- Título mediante `.setContentTitle()`.
- Contenido mediante `.setContentText()`.
- Prioridad configurada mediante `.setPriority()`.
- Canal correspondiente mediante el ID utilizado en `NotificationCompat.Builder`.
- Identificador de notificación.
- PendingIntent y acciones existentes.
- Configuraciones adicionales que ya tenga el proyecto.

No elimines ni reemplaces ninguna configuración existente. Únicamente incorpora `BigTextStyle`.

### 3. Compatibilidad

- Utiliza `NotificationCompat.BigTextStyle()` de AndroidX.
- Mantén la compatibilidad con la implementación actual de Android.
- Asegúrate de que funcione tanto con las notificaciones generales como con los recordatorios urgentes.
- No modifiques los canales de notificación ni sus niveles de importancia.

### 4. Verificación

Después de realizar los cambios:

1. Comprueba que el mensaje completo se envíe a `.bigText()`.
2. Verifica que las notificaciones puedan expandirse para mostrar el texto completo.
3. Comprueba que el título y el texto resumido sigan apareciendo correctamente.
4. Verifica que los iconos, prioridades, canales y acciones sigan funcionando.
5. Comprueba que el proyecto compile sin errores.

### 5. Entrega

Al finalizar, indica:

- Archivo modificado y su ruta exacta.
- Método donde se implementó `BigTextStyle`.
- Código completo actualizado de la clase `Notificador`.
- Breve explicación de cómo funciona `BigTextStyle`.
- Pasos para probar una notificación con un mensaje largo.

**RESTRICCIÓN:** Modifica únicamente lo necesario dentro de `Notificador`. Conserva la arquitectura, los nombres de métodos y toda la funcionalidad existente del proyecto. No crees clases nuevas ni elimines código.

PROMPT 3: Analiza el proyecto Android CafeCampusFINAL antes de realizar cualquier modificación. Revisa la clase `Notificador`, el ViewModel encargado de los recordatorios, el `AndroidManifest.xml` y la lógica actual de envío de notificaciones.

## OBJETIVO: IMPLEMENTAR LA ACCIÓN INTERACTIVA «POSPONER 10 S»

Implementa una acción interactiva en las notificaciones que permita al usuario posponer un recordatorio durante exactamente 10 segundos. Debes conservar la arquitectura actual del proyecto y utilizar los componentes `Notificador + BroadcastReceiver + ViewModel`.

### 1. Crear un BroadcastReceiver propio

Crea una clase llamada `PosponerReceiver.kt` que herede de `BroadcastReceiver`.

Responsabilidades:
- Recibir la acción cuando el usuario pulse el botón «Posponer 10 s».
- Recuperar el identificador y los datos necesarios de la notificación actual.
- Cancelar la notificación que se está posponiendo mediante `NotificationManager`.
- Programar nuevamente el recordatorio para que se muestre exactamente 10 segundos después.
- Evitar que se generen notificaciones duplicadas.
- Mantener el contenido original del recordatorio.

El Receiver no debe acceder directamente a una instancia de ViewModel, ya que su ciclo de vida está vinculado a la interfaz.

### 2. Registrar el BroadcastReceiver

Modifica `AndroidManifest.xml` y registra correctamente el nuevo Receiver dentro de la etiqueta `<application>`.

Requisitos:
- Utilizar el nombre completo de la clase o su ruta relativa correcta.
- Configurar `android:exported="false"` porque será un componente interno de la aplicación.
- No modificar ni eliminar las declaraciones existentes.
- Verificar que Android pueda resolver correctamente el componente.

### 3. Modificar la clase Notificador

Localiza la clase `Notificador` y modifica el `NotificationCompat.Builder` para incorporar un botón de acción utilizando `.addAction()`.

El botón debe mostrar exactamente el siguiente texto:

**Posponer 10 s**

Utiliza un `PendingIntent` de tipo `getBroadcast()` que apunte a `PosponerReceiver`.

Ejemplo de referencia:

```kotlin
val intent = Intent(context, PosponerReceiver::class.java).apply {
    action = "com.tecsup.cafecampus.POSPONER_RECORDATORIO"
    putExtra("NOTIFICATION_ID", notificationId)
    putExtra("TITULO", titulo)
    putExtra("MENSAJE", mensaje)
}

val pendingIntent = PendingIntent.getBroadcast(
    context,
    notificationId,
    intent,
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
)

builder.addAction(
    R.drawable.ic_notification,
    "Posponer 10 s",
    pendingIntent
)
```

Adapta este ejemplo a los nombres, iconos y estructuras reales del proyecto. No crees recursos duplicados si ya existe un icono apropiado.

### 4. Programar nuevamente el recordatorio

Utiliza `AlarmManager` para programar el recordatorio 10 segundos después de pulsar el botón.

Requisitos:
- Utilizar `SystemClock.elapsedRealtime()` como referencia temporal.
- Programar la alarma con `SystemClock.elapsedRealtime() + 10_000L`.
- Utilizar un `PendingIntent` que permita volver a ejecutar la lógica de notificación.
- Cuando se active la alarma, debe mostrarse nuevamente el recordatorio con su título y mensaje originales.
- Conservar el canal de notificación que corresponda.
- Evitar programar múltiples alarmas para el mismo recordatorio.
- Utilizar identificadores consistentes para poder cancelar o reemplazar alarmas anteriores.

Para este intervalo corto, utiliza `AlarmManager` en lugar de `WorkManager`, ya que WorkManager no garantiza ejecuciones con intervalos de 10 segundos.

### 5. Integración con el ViewModel

Revisa el ViewModel que actualmente gestiona los recordatorios.

Adapta su implementación para mantener la separación de responsabilidades:

- El ViewModel continúa gestionando los eventos y estados de la interfaz.
- `Notificador` continúa construyendo y mostrando las notificaciones.
- `PosponerReceiver` recibe la acción del usuario y coordina la reprogramación.
- La alarma vuelve a ejecutar el envío del recordatorio mediante el componente correspondiente.

No intentes obtener directamente una instancia del ViewModel desde el BroadcastReceiver.

Si necesitas reutilizar la lógica del ViewModel, extrae únicamente la operación necesaria a un componente reutilizable, respetando la arquitectura existente.

### 6. Mantener los canales de notificación

Conserva los dos canales implementados anteriormente:

- `canal_urgentes`: importancia `NotificationManager.IMPORTANCE_HIGH`.
- `canal_avisos`: importancia `NotificationManager.IMPORTANCE_DEFAULT`.

Los recordatorios pospuestos deben volver a mostrarse utilizando el canal `canal_urgentes`.

No modifiques los niveles de importancia ni los identificadores de los canales.

### 7. Restricciones del proyecto

- Mantener la arquitectura MVVM existente.
- No eliminar funcionalidades anteriores.
- No duplicar clases o métodos.
- No modificar pantallas que no estén relacionadas con esta funcionalidad.
- Conservar los iconos, títulos, mensajes, prioridades y canales actuales.
- Mantener la compatibilidad con la configuración de Android del proyecto.
- Utilizar Kotlin y las dependencias existentes siempre que sea posible.
- Evitar errores de compilación y referencias incorrectas.

### 8. Pruebas de funcionamiento

Comprueba los siguientes escenarios:

1. Se muestra un recordatorio normalmente.
2. La notificación presenta el botón «Posponer 10 s».
3. Al pulsar el botón, desaparece la notificación actual.
4. Diez segundos después vuelve a aparecer el mismo recordatorio.
5. El recordatorio conserva su título y mensaje originales.
6. La notificación vuelve a utilizar el canal `canal_urgentes`.
7. Las notificaciones generales siguen funcionando con el canal `canal_avisos`.
8. Pulsar varias veces el botón no genera alarmas duplicadas.
9. La aplicación sigue funcionando si está en segundo plano.

### 9. Entrega final

Al terminar, proporciona:

- Lista de archivos creados y modificados.
- Ruta exacta de cada archivo.
- Código completo de `PosponerReceiver.kt`.
- Código completo actualizado de `Notificador.kt`.
- Código completo actualizado del ViewModel, si fue necesario modificarlo.
- Fragmento actualizado del `AndroidManifest.xml`.
- Explicación del recorrido desde que se pulsa el botón hasta que reaparece la notificación.
- Resultado de la compilación y las pruebas realizadas.

**INSTRUCCIÓN FINAL:** Primero analiza la implementación real de CafeCampusFINAL. Después realiza los cambios directamente sobre el proyecto. No inventes clases, métodos, paquetes ni dependencias sin comprobar si ya existen. Conserva la arquitectura y el funcionamiento actual de la aplicación.
