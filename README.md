<img width="274" height="558" alt="image" src="https://github.com/user-attachments/assets/e72dbce5-deed-49ab-a538-b41893a69a28" />
<img width="268" height="586" alt="image" src="https://github.com/user-attachments/assets/f3bc1143-c6d6-4fba-80ae-0c4bab417ea0" />
<img width="299" height="582" alt="image" src="https://github.com/user-attachments/assets/aad34b8e-7432-4042-b536-4f3b74ef3ff2" />
<img width="266" height="616" alt="image" src="https://github.com/user-attachments/assets/2c6e0ed5-0e88-4d3b-9d82-afef8323a8fc" />
<img width="265" height="287" alt="image" src="https://github.com/user-attachments/assets/09ab7b2a-7c82-437e-9fe6-a8aeb712b915" />

## Estructura de paquetes
```
com.tecsup.avisoscampus/
├── AvisosApp.kt
├── MainActivity.kt
├── model/Modelos.kt
├── data/AvisosRepository.kt
├── notificacion/Notificador.kt
├── receiver/ModoAvionReceiver.kt
├── viewmodel/
│   ├── AvisosViewModel.kt
│   └── AvisosViewModelFactory.kt
└── ui/
    ├── PantallaAvisos.kt
    ├── HistorialAvisos.kt
    └── theme/
```

1. ¿Por qué una notificación necesita un canal y un permiso? ¿Qué pasa si falta cada uno?

Porque el canal permite organizar las notificaciones y el permiso autoriza mostrarlas. Si falta alguno, la notificación no aparecerá correctamente.

2. ¿Por qué el ViewModel emite un evento y no muestra la notificación él mismo?

Porque el ViewModel maneja la lógica y la interfaz se encarga de mostrar la notificación. Así el código está más ordenado.

3. ¿Qué pasa si usas el mismo ID en dos notificaciones?

La nueva notificación reemplaza a la anterior, en lugar de aparecer como una diferente.

4. ¿Por qué onReceive debe ser corto y por qué hay que quitar el receptor al salir?

Para evitar que la aplicación se vuelva lenta. Se quita el receptor para evitar problemas de memoria y eventos innecesarios.

5. ¿En qué casos usarías una notificación local y en cuáles una push de Firebase?
- Local: para recordatorios, alarmas o tareas pendientes.
- Firebase: para recibir mensajes o avisos enviados desde un servidor.
