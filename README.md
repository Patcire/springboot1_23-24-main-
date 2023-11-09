# API usuario-producto 
*Por Patricio*

#### Preguntas
* Comprobar si un producto existe antes de crearlo. Si existe devolver un error de 
producto existente. Esto impide tener un mismo producto con varios ID. ¿Sería deseable tener  
un producto repetido? Si es así, ¿como lo solucionarías?
> No, no es deseable porque es aumentar el coste de almacenamiento sin ningún motivo.
> Para solucionarlo implemento que reconozca si el nombre de ese producto ya existe
> y si es así devolver error. Lo que sí se podría mirar es si  la clase Producto implementará un
> atributo *Cantidad* en el que contabilizar el nº de unidades.


* DTO. Estudiar si sería deseable usar DTO en el caso de tener que añadir Mappings 
para la relación entre usuario y producto. Razones para usar y para no usar DTO.

> Sí, añadir DTO sería una buena práctica, ya que por una parte nos evita
> la recursividad de las respuestas en la consulta. Pero especialmente es recomendable por
> una cuestión de seguridad, ya que sin DTO el usuario podría ver como se relacionan los datos
> en nuestra BBDD y por tanto podrían explotar vulnerabilidades o acceder a información no deseada