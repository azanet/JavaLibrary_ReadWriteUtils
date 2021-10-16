David Freyre Muñoz <https://github.com/azanet>
<br /><br />
# JavaLibrary_ReadWriteUtils 
Class to work with "InputStream and OutputStream" Files and Sockets more easily

Contiene una clase para trabajar con "InputStream y OutputStream" Ficheros, Sockets
y transferencia de ficheros, más facilmente.
<br/>

## INSTRUCCIONES:

1. Agregar "ReadWriteUtils.jar" como "Libreria"
<br/>
2. Importar la libreria en las clases donde vaya a ser utilizada,
se debe importar de la siguiente forma:
*import readwriteutils.ReadWriteUtils;*
<br/>
3. Para hacer uso de sus métodos, debe crearse un objeto de "ReadWriteUtils":
*ReadWriteUtils rwUtils = new ReadWriteUtils();*
<br/>
4. Ya podemos hacer uso de sus métodos.

<br/><br/>
La DOCUMENTACION (Javadoc) se encuentra en la carpeta "javadoc", hay que
Visualizar(abrir) el archivo "index.html"

También podemos enlazar(desde el IDE, ya cada uno es a su manera) la CARPETA 
DEL javadoc a nuestra libreria cuando la importemos.
Así esta nos asistirá a la hora de programar, dándonos una descripción de cada método.



Los metodos disponibles en la clase son los siguientes:


                                       OPERACIONES CON FICHEROS                   
 
 * --createNewFILE
 * --deleteFILE
 * --resetFILE
 * --resetOrCreateFILE         
 * --copyFile                  =====> Realiza una copia binaria de un archivo (a otro)       
 * --addContentFROMFileIN_ToFileOUT =====> Añade el contenido del "fileIN" al "fileOUT")
 * --readFileAndPrint          =====> Lee un archivo y lo imprime por pantalla
 * --readFileToString          =====> Lee un archivo y lo retorna todo en una String
 * --readFileToArrayList       =====> Lee un archivo y Retorna un ArrayList con las lineas Leidas 
 * --readUniqueObjectFromFILE  =====> Lee archivo binario con OBJETO UNICO y retorna EL objeto)
 * --readSeveralObjectFromFILE =====> Lee archivo binario de objetos y retorna un ArrayList con todos los objetos)
 * --writeTextInFile           =====> Escribir TEXTO a un archivo
 * --writeUniqueObjectInFile   =====> Escribir OBJETO en un Archivo(que contendra UN SOLO OBJETO)
 * --writeSeveralObjectInFile  =====> Escribir VARIOS OBJETOS en un Archivo(que contendra VARIOS OBJETOS)

<br /><br /><br />

              -----PARA TRABAJAR CON Streams (SOCKETS y Sistema(Mas que nada))-----
            OBTENER STRINGS de InputStream - ENVIAR Bytes de STRINGS por OutputStream

 * --readOneLine               =====> Lee UNA Linea 
 * --readAllLines              =====> Lee TODAS las Lineas Hasta Recibir UNA Linea VACIA ("") 
 * --writeMessage              =====> Envia un Mensaje(STRING)
 * 
 *#############################################################################
 *    OBTENER OBJETOS desde el InputStream - ENVIAR OBJETOS por el OutputStream 
*##############################################################################
 * --readObject                =====> Lee un OBJETO 
 * --writeObject               =====> Envia un Objeto
 * 
 * 
 * 
*##############################################################################
 *                                  DESCARGA y ENVIO de FICHEROS
*##############################################################################
 * --requestAndDownloadFile    =====> Solicitar y descargar un archivo del Servidor
 * --sendRequestedFile         =====> Enviar Archivo Solicitado por el Cliente
 * 
