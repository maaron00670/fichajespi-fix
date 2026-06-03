![logo](resources/fichajesPi.png  "logo")



FichajesPi es una aplicación ideada para permitir cumplir una necesidad de las empresas: __registrar las horas de trabajo de sus empleados__.
Se puede fichar tanto por pagina web como con tarjeta fisica

# Instalación

FichajesPi está pensado para ser instalado en una Raspberry Pi y poder usarse tambien por web
En esta rama se encuentra lo necesario para la parte de servidor, todo lo necesario para la raspberry esta en su rama correspondiente.

## Instalación en el servidor

La configuración y puesta en marcha del sistema debería ser llevada a cabo por el personal informático de la empresa o por una persona con conocimientos suficientes en informática para poder solventar posibles eventualidades surgidas en el proceso.

Para poder instalar la seccion de servidor ejecutaremos el siguiente comando
`curl -s https://raw.githubusercontent.com/maaron00670/fichajespi-fix/main/setup-server | sudo bash`

El script realizará las siguientes acciones:

- Intalara y perapara lo necesario para docker y docker compose
- Actualiza el sistema
- Clona el repositorio



__Antes de ejecutar este último paso podemos personalizar ciertos parámetros del sistema como son: usuario y contraseñas de base de datos, parámetros del servidor smtp y secret key del token JWT.__

Para modificar los parámetros por defecto debemos abrir el archivo ‘docker-compose.yml’ y fijarnos en los comentarios de las líneas que podemos modificar.

El docker cuenta ya con imagene precreadas pero se recomienda crear las suyas propias.

Una vez instalado todo solo se debe levantar el ‘docker-compose.yml’ con

`docker compose docker-compose.yml up`

usuario por defecto tras instalación:
user: fichajesPi000
pass: fichajesPi000
















