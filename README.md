> [!IMPORTANT]
> Usar Java 17 o superior

Creación de una aplicación que exponga un API REST para almacenar estados de un pedido
durante su transporte. El único endpoint será un POST al recurso “/order/tracking/” y podrá
recibir ficheros con las actualizaciones de seguimiento de los pedidos en formato xml y/o
json.

Nota: Solo será necesario recibir los ficheros en uno de los dos formatos (opcionalmente en
los dos).
Los estados de seguimiento son los siguientes:
1. RECOGIDO EN ALMACÉN
2. EN REPARTO
3. INCIDENCIA EN ENTREGA
4. ENTREGADO

Para cada nueva actualización del estado de un seguimiento de un pedido se comprobará si
cumple las reglas de la máquina de estados, en caso de que sea una transición válida, se
deberá almacenar la actualización.

Se debe mantener un registro de los diferentes estados por los que ha pasado un pedido,
almacenando para cada estado la fecha hora del cambio de estado y la fecha hora de
inserción de ese nuevo estado.

Las reglas de la máquina de estados son las siguientes:

- El estado "RECOGIDO EN ALMACEN" es un estado inicial, no se puede transitar a este
estado desde el resto de estados.
- El estado "ENTREGADO" es un estado final, una vez un pedido alcance ese estado se
descartarán el resto de actualizaciones de seguimiento que recibamos.
- El resto de estados son transitables entre sí y no tienen ninguna limitación.

EJEMPLO DE JSON:
```
{
  "orderTrackings": [
    {
      "orderId": 230688716,
      "trackingStatusId": 1,
      "changeStatusDate": "2019-01-27T07:18:23.108+00:00"
    },
    {
      "orderId": 230688717,
      "trackingStatusId": 2,
      "changeStatusDate": "2019-01-27T07:18:23.108+00:00"
    },
    {
      "orderId": 230688718,
      "trackingStatusId": 3,
      "changeStatusDate": "2019-01-27T07:18:23.108+00:00"
    }]
}
  
```
