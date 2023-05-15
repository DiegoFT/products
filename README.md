# Disponibilidad de productos en una tienda

### Estructuras de datos utilizadas en el algoritmo

* Uso `Set` para gestionar los elementos de los csv, eliminando duplicados y siendo más rápida la búsqueda.

* En el `Controller` uso `List` para mantener el orden.

_Dependiendo de los requerimientos de negocio se puede valorar gestionar duplicados (¿acumular unidades de stock?)
y usar List en lugar de Set_

### Complejidad temporal del algoritmo

Teniendo en cuenta que podemos cargar "n" productos y "m" tallas con sus correspondientes unidades,
la complejidad temporal es de O(n*m).
Uso métodos como **filter**, **anyMatch** y listas para reducir la complejidad en algunos casos.

### Versiones 🔧

* Java: 17
* Spring Boot: 3.0.6
* OpenAPI: 2.1.0
* Commons CSV: 1.10.0

### Dependencias 📋

* **OpenAPI**

  Para documentar API con Swagger

  [Local Swagger](http://localhost:8080/swagger-ui/index.html)


* **Commons CSV**

  Para leer los archivos CSV


* **Lombok**

  Para ahorrar escribir código repetitivo.

  _Lo uso solo en capa de infra_

***
**Author:** Diego Forero
***
