# Taller de Modularización con Virtualización e Introducción a Docker y a AWS
# Sebastián Villamarín Rodríguez
# AREP- LAB 5

## Descripción
En el Taller de Modularización con Virtualización e Introducción a Docker y a AWS se realizó primero el aprendizaje del manejo de contenedores en Docker, el cual se desplegó la primera instancia en Docker. Después, se realizó el respectivo aprendizaje de AWS para poder con el contenedor creado en Docker, desplegarlo en AWS en la máquina virtual, para así tener base para poder iniciar la tarea la cual consiste en una implementación de una arquitectura la cual consiste en un balanceador de carga en donde se usa el método de RoundRobin. Este balanceador de carga se encarga de realizar las respectivas peticiones a los LogService, los cuales se encargan de realizar la conexión con las bases de datos montada en MongoDB, que almacena todos los mensajes entrantes. En la arquitectura del programa se encuentran en total cinco contenedores, uno para el RoundRobin, tres para el LogService y uno para las bases de datos en Mongo.

## Prerrequisitos
Para la realización y ejecución tanto del programa como de las pruebas de este, se requieren ser instalados los siguientes programas:

  - Maven. Herramienta que se encarga de estandarizar la estructura física de los proyectos de software, maneja dependencias (librerías) automáticamente desde repositorios y           administra el flujo de vida de construcción de un software.
  - GIT. Sistema de control de versiones que almacena cambios sobre un archivo o un conjunto de archivos, permite recuperar versiones previas de esos archivos y permite otras         cosas como el manejo de ramas (branches).
  - Docker. Programa encargado de crear contenedores ligeros y portables para las aplicaciones software que puedan ejecutarse en cualquier máquina con Docker instalado,               independientemente del sistema operativo que la máquina tenga por debajo, facilitando así también los despliegues.
  
Para asegurar que el usuario cumple con todos los prerrequisitos para poder ejecutar el programa, es necesario disponer de un Shell o Símbolo del Sistema para ejecutar los siguientes comandos para comprobar que todos los programas están instalados correctamente, para así compilar y ejecutar tanto las pruebas como el programa correctamente.

  - mvn -version
  - git --version
  - java -version
  - docker version
  
# Instalación
Para descargar el proyecto de GitHub, primero debemos clonar este repositorio, ejecutando la siguiente línea de comando en GIT.

      https://github.com/svillamarin27/AREP-LAB5.git
      
# Ejecución
Para compilar el proyecto utilizando la herramienta Maven, nos dirigimos al directorio donde se encuentra alojado el proyecto, y dentro de este ejecutamos en un Shell o Símbolo del Sistema el siguiente comando:

      mvn package
      
# Localhost
Para probar ahora el correcto funcionamiento del Docker de manera local o localhost del programa RoundRobin, primero ejecutamos los siguientes comandos en orden.

      docker build --tag firstsparkjavarepo/roundrobin .
      docker images
      docker run -d -p 35000:6000 --name firstcontainerweb firstsparkjavarepo/roundrobin
      
Luego de ejecutarlos en exactamente ese mismo orden, tenemos el siguiente resultado en pantalla.

![image](https://user-images.githubusercontent.com/37603257/111008829-f3c77900-835f-11eb-8eac-d391c782089c.png)

Ahora, para correr los tres logs en puertos diferentes, se ejecutan los siguientes comandos en orden.

       docker run -d -p 34000:6000 --name firstdockercontainer firstsparkjavarepo/logservice
       docker run -d -p 34001:6000 --name seconddockercontainer firstsparkjavarepo/logservice
       docker run -d -p 34002:6000 --name thirddockercontainer firstsparkjavarepo/logservice
       
Ahora, para verificar que en la aplicación Docker se hayan desplegado con éxito los contenedores LogService y RoundRobin en sus respectivos puertos, se abre la aplicación de Docker de escritorio y se hace la verificación que todos los contenedores estén corriendo en sus respectivos puertos. Como se ve en la siguiente imagen, todos los contenedores están corriendo satisfactoriamente.

![image](https://user-images.githubusercontent.com/37603257/111009048-8a943580-8360-11eb-8c23-e518f86d6339.png)

Para comprobar que la página web ha sido desplegada con éxito, se ingresa en el navegador la siguiente URL: localhost:34999/Datos . Luego de ingresar la URL en el navegador, se obtiene el siguiente resultado.

![image](https://user-images.githubusercontent.com/37603257/111411319-52fef380-86a8-11eb-8427-f502182e5947.png)

# AWS
Antes de iniciar a utilizar AWS, primero se debe subir cada uno de los contenedores creados a un repositorio. Para realizar esto, primero se creó el primer repositorio en Docker Hub llamado firstsparkjavarepo, como se ve a continuación.

![image](https://user-images.githubusercontent.com/37603257/111009578-00e56780-8362-11eb-93b1-27af049f72bd.png)

Luego, se ejecutaron los siguientes comandos en orden para poder subir los contenedores.

      docker tag firstsparkjavacontainer/roundrobin svillamarin27/firstsparkjavacontainer
      docker push svillamarin27/firstsparkjavacontainer:latest
      
Para iniciar a desplegar el contenedor en una máquina virtual alojada en AWS, primero se selecciona el tipo de máquina virtual que se utilizará, en este caso, se utilizará Amazon Linux 2 AMI (HVM), SSD Volume Type. Para utilizarla, se realiza clic en el botón Seleccionar.

![image](https://user-images.githubusercontent.com/37603257/111010042-566e4400-8363-11eb-8fa3-0b7b4316534c.png)

Ahora se selecciona el tipo de instancia. Para esta máquina virtual, se selecciona t2.micro, la cual es apta para la capa gratuita. luego de seleccionarla, se realiza clic en Revisar y lanzar.

![image](https://user-images.githubusercontent.com/37603257/111010126-8fa6b400-8363-11eb-8f49-d94ea1644b83.png)

A continuación se muestra la instancia para verificar la máquina virtual que está a punto de ser lanzada. Para lanzarla, se realiza clic en el botón Lanzar.

![image](https://user-images.githubusercontent.com/37603257/111010190-bd8bf880-8363-11eb-92ee-e6a596400ecf.png)

Luego, se procede a crear un nuevo par de llaves para poder acceder a la máquina virtual desde el computador en cuestión. Para esto se selecciona la opción Crear un nuevo par de llaves y se escribe el nombre del par de claves. Para descargar la llave, se realiza clic en el botón Descargar par de llaves.

![image](https://user-images.githubusercontent.com/37603257/111010265-fcba4980-8363-11eb-8035-386d05b642a1.png)

Después de descargar el par de llaves, ahora se procede a realizar clic en el botón Lanzar instancias.

![image](https://user-images.githubusercontent.com/37603257/111010342-34c18c80-8364-11eb-9699-f5a30fe5f558.png)

Ahora, se muestra que la instancia ha sido lanzada con éxito. Para verificar que esta ha sido lanzada, se realiza clic en el botón Ver instancias.

![image](https://user-images.githubusercontent.com/37603257/111010396-663a5800-8364-11eb-9f90-cbebe3a52fcc.png)

Para conectarse a la instancia, se realiza clic en el botón Acciones, para posteriormente realizar clic en el botón Conectar.

![image](https://user-images.githubusercontent.com/37603257/111010457-9a157d80-8364-11eb-80bd-ff9456551092.png)

Para realizar la respectiva conexión con la instancia, se realiza clic en el botón Cliente SSH, que es el medio en el cual se realizará la conexión con la instancia.

![image](https://user-images.githubusercontent.com/37603257/111010534-cfba6680-8364-11eb-9cf4-f7bfb741ce18.png)

Ahora, se ejecuta el SSH desde el computador con el cual se desea realizar la conexión con la instancia, y se ejecuta el siguiente comando.

        ssh -i "awsthird.pem" ec2-user@ec2-54-163-34-86.compute-1.amazonaws.com
        
Como se puede observar, el contenedor ha sido desplegado satisfactoriamente desde la máquina virtual montada en AWS.

![image](https://user-images.githubusercontent.com/37603257/111411509-b12bd680-86a8-11eb-9c45-5fc895e8101f.png)
 
 # Autor
 Sebastián Villamarín Rodríguez
 
 # Licencia

Este proyecto esta licenciado bajo la licencia General Public License v3.0, revise el archivo [LICENSE](LICENSE) para más información.
