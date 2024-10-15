## Requisitos a cumplimentados:

Debe haber 2 versiones:

- TCP
- UDP

En este chat deben poder hablar simultáneamente varios usuarios identificados por su nickname. 

Se debe controlar que no haya nombres repetidos. 

## Form utilizados:

### 1. Login:

![image](https://github.com/user-attachments/assets/46e90c29-b96c-4e7a-8904-8650fc8ce6d2)


### 2. Chat:

![image](https://github.com/user-attachments/assets/7b7c261d-591c-4aa9-9563-ef07125d276f)

1. **Enviar mensajes:** Text area de la parte inferior.
2. **Enviar mensaje:** Botón con la fecha.
3. **Mostrar los mensajes:** Text area superior izquierda.
4. **Mostrar usuarios:** Text area de la derecha.

## Chat TCP:

### Estructura:

**Cliente**

- `ClienteChat:` Hilo del cliente que maneja sus principales funciones en cuanto a creación y recibo y envío de mensajes.
- `MainCliente:` Ejecutar el Cliente

**Data**

- `ComunHilos:` Datos comunes relativos al chat, usuarios conectados, mensajes enviados, etc.

**Servidor**

- `HiloServidorChat:` Funciones relativas a comprobar login, recibir mensajes y envió a los demás clientes.
- `MainServidor:` Ejecutar el Servidor

### Mejoras futuras:

Se puede hacer que el usuario que se conecte más tarde vea los mensajes anteriores a su conexión o no.

## Chat UDP:

- `ChatMulticast`: Funcionalidades relativas a la creación del grupo multicast y al envío y recibo de mensajes

### Mejoras futuras:

Que se muestre la totalidad de los usuarios conectados inclusive los anteriores.

Comprobar que el nombre escogido no este en uso.

Se puede hacer que el usuario que se conecte más tarde vea los mensajes anteriores a su conexión o no.
