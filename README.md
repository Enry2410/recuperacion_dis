# Desarrollo e Integración de Software Práctica 2 Convocatoria Extraordinaria

Desarrollo y dockerización de una aplicación web (frontend y backend) que lea archivos json y los muestre en grids.


## Uso

Este repositorio existe meramente para poder visualizar el código fuente. El proyecto ha sido preparado para el uso exclusivamente con docker.


### Crear una red de docker:
```bash
docker network create {nombre de la red}
```
### Descargar las imágenes
```bash
docker pull enry24/dis-backend
```
```bash
docker pull enry24/dis-frontend
```

### Crear los contenedores
El contenedor del backend **debe** tener el argumento __--name backend__
```bash
docker run -d -p 2223:2223 --network dis --name backend enry24/dis-backend
```
```bash
docker run -d -p 2222:2222 --network dis --name frontend enry24/dis-frontend
```
