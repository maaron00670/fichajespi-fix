## Requisitos previos

Los clientes interesados en montar un sistema de control de presencia mediante la implementación de FichajesPi en sus empresas deben contar con unos requisitos de hardware y software que pasaremos a enumerar:

- __Ordenador Raspberry Pi 4 Model B.__ Raspberry Pi es un mini ordenador de placa reducida y de bajo costo que ejecuta sistemas operativos basados en linux. Su uso está muy extendido para la realización de proyectos personales ligados al IoT, Robótica, etc gracias a sus entradas y salidas digitales y analógicas que permiten que se comunique con dispositivos externos.
También es comúnmente empleada como servidor debido a su bajo consumo y la poca demanda de recursos que suelen requerir los servidores basados en sistemas linux.
En esta ocasión se ha seleccionado el modelo 4B ya que cuenta con un buen equilibrio entre prestaciones y precio, pudiéndose adquirir por unos 50€ el modelo de 2GB de RAM.

- __Pantalla táctil LCD de 3,5”__. Con esta pantalla los empleados pueden verificar que se ha realizado correctamente el fichaje con tarjeta NFC. Es sencillo encontrar pantallas de este tipo en comercios especializados en venta de material para Raspberry 
Pi.

- __Lector de tarjetas NFC__. Para que los empleados puedan fichar sus entradas y salidas de forma ágil, se conectará al micro-ordenador un lector USB. El modelo escogido es el ACR122U, que es fácilmente conseguible en multitud de tiendas online.
Este lector soporta los protocolos: ISO 14443 Type A and B cards, MIFARE, FeliCa, y los 4 tipos de NFC tags (ISO/IEC 18092).

- __Tarjetas NFC compatibles.__ Se entregará una a cada empleado. Han de ser de alguno de los tipos compatibles indicados en el apartado anterior.

- __Cargador__ USB tipo C de 12W.

- __Tarjeta micro SD.__ En la que grabaremos la imagen con el sistema operativo y que servirá de unidad de almacenamiento principal al sistema. Dado que la aplicación contará con una base de datos en la que se insertarán registros habitualmente es preferible que la tarjeta micro SD sea de gran capacidad y velocidad, al menos 64GB Clase 10, para que el rendimiento sea óptimo.

- __Disipadores de calor__ para los microprocesadores de Raspberry. De esta manera aumentaremos la vida útil de la máquina.

- __Carcasa__ para proteger el equipo.

- __Monitor, teclado y ratón__ para la configuración inicial del sistema. Opcional. - si no se desea o no se puede hacer por conexión SSH -

- __Servidor de correo electrónico.__ Para que la aplicación pueda enviar emails de notificación a los usuarios se debe contar con un servidor de correo electrónico SMTP para poder indicar a la aplicación parámetros como: host, puerto, username y password.

- __Conexión a internet.__ La instalación se lleva a cabo descargando software desde internet por lo que para instalar Fichajes Pi se debe contar con conexión a internet

##Instalación de Raspberry Pi OS en la tarjeta micro SD:

Este sistema operativo basado en Debian es el escogido para servir de host a la aplicación, ya que, cuenta con una amplio soporte y es muy sencillo encontrar documentación que nos ayude en caso de aparecer errores o querer personalizar configuraciones.

Proceso de instalación desde linux:

- Instalar Raspberry Pi Imager desde https://www.raspberrypi.com/software/

- Ejecutar el programa y seleccionar ‘RASPBERRY PI OS (32-BIT)’ instalarlo en la microSD y seguir las instrucciones del programa. - si se tiene epleado usar conexion por ssh selecionar la opcion en la instalación -

- Pulsar ‘WRITE’ y esperar a que se complete el proceso.

- Fijar una IP estática. Editamos con permisos de super usuario el archivo `/etc/dhcpcd.conf` de la tarjeta microSD.

Un ejemplo de configuración (en este caso por cable ethernet):

```
interface eth0
static ip_address=192.168.0.10/24
static routers=192.168.0.1
static domain_name_servers=1.1.1.1 8.8.8.8
```

- Introducimos la microSD en la RaspberryPi y la encendemos.

- Escribimos en la terminal `ssh $USER@192.168.0.10` para conectarnos remotamente a la RaspberryPi que debe estar conectada a la red. La ip la debemos sustituir por la que hayamos indicado

- Ejecutamos `sudo raspi-config` para cambiar la configuración del sistema,por ejemplo cambiar la contrasela, si la pantalla no se detecta se puede arreglar desde aqui...

- Tras seguir estos pasos ya estaríamos listos para seguir las instrucciones de instalación de FichajesPi.

## Instalación del prorgama

FichajesPi está pensado para ser instalado en una Raspberry Pi, este hecho estandariza su despliegue ya que permite construir un script que automatice la instalación de dependencias y empaquetado de la aplicación.

La configuración y puesta en marcha del sistema debería ser llevada a cabo por el personal informático de la empresa o por una persona con conocimientos suficientes en informática para poder solventar posibles eventualidades surgidas en el proceso.

Para poder instalar la aplicación debemos partir de una Raspberry Pi con Raspberry OS instalado y con el servicio de ssh activado para poder acceder al sistema de forma remota. También se debe haber instalado una pantalla táctil de 3.5”.

Los pasos para la instalación del SO vienen descritos en el apartado anterior.

Primero debemos conectarnos vía ssh a la Raspberry con el siguiente comando:
`ssh $USER@192.168.1.99`

Donde 192.168.1.99 debe ser sustituido por la ip estática que hayamos elegido en el proceso de configuración.

A continuación comenzamos con la instalación mediante el siguiente comando:

`curl -s https://raw.githubusercontent.com/maaron00670/fichajespi-fix/RPI-Files/setup-rpi | sudo bash`

El script realizará las siguientes acciones:

- Instalara SmartCard Reader
- Drivers necesarios
- Vnc
- Compilara la aplicacion
- Configurara los servicios pcscd

Tras la ejecución de este primer lote de acciones se reiniciará el dispositivo.

AL iniciar el sistema se devera abrir al poco tiempo el programa.  - 10 o 15 segundo como mucho - 















