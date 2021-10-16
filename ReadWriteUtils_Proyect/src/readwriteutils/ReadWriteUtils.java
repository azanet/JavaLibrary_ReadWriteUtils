package readwriteutils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Freyre Muñoz &lt;https://github.com/azanet&gt;
 *
 * Clase para trabajar con "InputStream y OutputStream" Ficheros y Sockets más facilmente.
 *
 * Cuenta con los siguientes METODOS;
 * 
 *##############################################################################
 *                    OPERACIONES CON FICHEROS
 *##############################################################################
 * --createNewFILE
 * --deleteFILE
 * --resetFILE
 * --resetOrCreateFILE
 * --copyFile
 * --addContentFROMFileIN_ToFileOUT =====&gt; Realiza una copia binaria de un archivo (a otro)
 * --readFileAndPrint          =====&gt; Lee un archivo y lo imprime por pantalla
 * --readFileToString          =====&gt; Lee un archivo y lo retorna todo en una String
 * --readFileToArrayList       =====&gt; Lee un archivo y Retorna un ArrayList con las lineas Leidas 
 * --readUniqueObjectFromFILE  =====&gt; Lee archivo binario con OBJETO UNICO y retorna EL objeto)
 * --readSeveralObjectFromFILE =====&gt; Lee archivo binario de objetos y retorna un ArrayList con todos los objetos)
 * --writeTextInFile           =====&gt; Escribir TEXTO a un archivo
 * --writeUniqueObjectInFile   =====&gt; Escribir OBJETO en un Archivo(que contendra UN SOLO OBJETO)
 * --writeSeveralObjectInFile  =====&gt; Escribir VARIOS OBJETOS en un Archivo(que contendra VARIOS OBJETOS)
 * 
 * 
 *  -------PARA TRABAJAR CON Streams (SOCKETS y Sistema(Mas que nada))-----
*#############################################################################
 *   OBTENER STRINGS de InputStream - ENVIAR Bytes de STRINGS por OutputStream
*#############################################################################
 * --readOneLine               =====&gt; Lee UNA Linea 
 * --readAllLines              =====&gt; Lee TODAS las Lineas Hasta Recibir UNA Linea VACIA ("") 
 * --writeMessage              =====&gt; Envia un Mensaje(STRING)
 * 
 *#############################################################################
 *    OBTENER OBJETOS desde el InputStream - ENVIAR OBJETOS por el OutputStream 
*##############################################################################
 * --readObject                =====&gt; Lee un OBJETO 
 * --writeObject               =====&gt; Envia un Objeto
 * 
 * 
 * 
*##############################################################################
 *          DESCARGA y ENVIO de FICHEROS
*##############################################################################
 * --requestAndDownloadFile    =====&gt; Solicitar y descargar un archivo del Servidor
 * --sendRequestedFile         =====&gt; Enviar Archivo Solicitado por el Cliente
 * 
 * -----------------------------------------------------------------------------
 *   !!!--&gt; ESTAS SIGUIENTES CLASES,Son utilizadas en la transferencia
 *   (DESCARGA y ENVIO de FICHEROS), Meter en un mismo paquete en AMBAS CLASES
 * -----------------------------------------------------------------------------
 * --OBJFileSubmission         =====&gt; Clase usada para la transferencia 
 * --OBJFileRequest            =====&gt; Clase usada para la transferencia 
 *
 *
 */
public class ReadWriteUtils {

    /**
   * REALIZANDO la "CREACION" DEL FICHERO.
   * 
   * @param filePath {Nombre y Ruta de Archivo a CREAR}
   *  @return boolean - {TRUE(Si la ioperacion salio BIEN) || FALSE(Si la operacion salio mal)}
   */
    public boolean createNewFILE(String filePath) {
        return createNewFILE_p(filePath);
    }   
    private boolean createNewFILE_p(String filePath) {
        
        boolean state = false;
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
                state = true;
    //            System.out.println("Archivo " + filePath + ", CREADO");
            } else {
             String err = ("[!]ERROR. NO se HA CREADO EL FICHERO:" + filePath + "\n¡¡El FICHERO YA EXISTE!!");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }
   
   
   /**
    * REALIZANDO EL "Eliminado" DEL FICHERO.
    * 
    * @param filePath {Nombre y Ruta de Archivo a DELETE}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean deleteFILE(String filePath) {
       return deleteFILE_p(filePath);
    }
    private boolean deleteFILE_p(String filePath) {
        
        boolean state = false;       
        File file = new File(filePath);     
        if (file.exists()) {
            file.delete();
            System.gc();
            state = true;
    //         System.out.println("Archivo " + filePath + ", ELIMINADO");       
        } else {
            String err="[!]ERROR. EL FICHERO:" + filePath + "\n¡¡NO EXISTE!!";
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
        }

        return state;
    }
    
    
   /**
    * REALIZANDO EL "reseteo" DEL FICHERO.
    * 
    * @param filePath {Nombre y Ruta de Archivo a RESETEAR}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */ 
    
    public boolean resetFILE(String filePath) {
        return resetFILE_p(filePath);     
    }
    private boolean resetFILE_p(String filePath) {
        //
        boolean state = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                System.gc();
                file.createNewFile();
                state = true;
    //            System.out.println("Archivo " + filePath + ", REINICIADO");
            } else {
                String err= "[!]ERROR. EL FICHERO:" + filePath + "\n¡¡NO EXISTE, Proceda a CREARLO!!";
                Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return state;
    }

    
    
   /**
    * REALIZANDO EL "reseteo o Creacion" DEL FICHERO.
    * 
    * @param filePath {Nombre y Ruta de Archivo a CREAR o RESETEAR}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */    
    public boolean resetOrCreateFILE(String filePath) {
        return resetOrCreateFILE_p(filePath);
    }
    private boolean resetOrCreateFILE_p(String filePath) {
        
        boolean state = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                System.gc();
                file.createNewFile();
                state = true;
    //            System.out.println("Archivo " + filePath + ", REINICIADO");
            } else {
                file.createNewFile();
                state = true;
    //            System.out.println("Archivo " + filePath + ", Se ha CREADO");
            }

        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return state;
    }

    
    
    /**
    * Hacer una copia exacta de "Fichero de Entrada" a "Fichero de salida".
    * 
    * @param pathFileIN {Fichero del que LEEMOS el contenido que queremos COPIAR}
    * @param pathFileOUT {Fichero al que se COPIARA el CONTENIDO Leido}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean copyFile (String pathFileIN, String pathFileOUT) {
        return copyFile_p(pathFileIN,pathFileOUT);    
    }
    private boolean copyFile_p(String pathFileIN, String pathFileOUT) {
        
        boolean state = false;
        RandomAccessFile FileOriginal = null;
        RandomAccessFile FileNew = null;
        File in = new File(pathFileIN);
        File out = new File(pathFileOUT);
        
        //Reiniciamos o Creamos NUEVO el archivo de SALIDA
        if (resetOrCreateFILE(pathFileOUT)){
        
                      
        try {
            FileOriginal = new RandomAccessFile(in, "r");
            FileNew = new RandomAccessFile(out, "rw");

            byte[] buffer = new byte[1024 * 1024]; // 1 mb
            FileNew.seek(FileNew.length());
            FileOriginal.seek(0);

            while (true) {
                //Cargando Buffer a partir de la lactura del archivo Original
                int count = FileOriginal.read(buffer);
                if (count == -1) {
                    break;
                }

                //Escribiendo el Buffer al Archivo Nuevo
                FileNew.write(buffer, 0, count);
            }

            FileOriginal.close();
            FileNew.close();

            out.setLastModified(in.lastModified());
            state = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }else{
            Logger.getLogger(ReadWriteUtils.class.getName()).severe("No se pudo crear el FICHERO");
        }

        return state;
    }
    
    
    /**
    * Agregar contenido de "Fichero de Entrada" a "Fichero de salida".
    * 
    * @param pathFileIN {Fichero del que LEEMOS el contenido que queremos Agregar}
    * @param pathFileOUT {Fichero al que se AGREGA el CONTENIDO Leido}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean addContentFROMFileIN_ToFileOUT(String pathFileIN, String pathFileOUT) {
        return addContentFROMFileIN_ToFileOUT_p(pathFileIN, pathFileOUT);     
    }
    private boolean addContentFROMFileIN_ToFileOUT_p(String pathFileIN, String pathFileOUT) {
        
        boolean state = false;
        RandomAccessFile FileOriginal = null;
        RandomAccessFile FileNew = null;
        File in = new File(pathFileIN);
        File out = new File(pathFileOUT);

        try {
            FileOriginal = new RandomAccessFile(in, "r");
            FileNew = new RandomAccessFile(out, "rw");

            byte[] buffer = new byte[1024 * 1024]; // 1 mb
            FileNew.seek(FileNew.length());
            FileOriginal.seek(0);

            while (true) {
                //Cargando Buffer a partir de la lactura del archivo Original
                int count = FileOriginal.read(buffer);
                if (count == -1) {
                    break;
                }

                //Escribiendo el Buffer al Archivo Nuevo
                FileNew.write(buffer, 0, count);
            }

            FileOriginal.close();
            FileNew.close();

            out.setLastModified(in.lastModified());
            state = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return state;
    } 

    
    /**
     * LEE ARCHIVO Y LO IMPRIME POR PANTALLA.
     * 
     * @param filePath {Archivo a LEER}
     * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
     */
    public boolean readFileAndPrint(String filePath) {
        return readFileAndPrint_p(filePath);      
    }
    private boolean readFileAndPrint_p(String filePath) {
        
        boolean state = false;
        RandomAccessFile file =null;
//        FileLock fileLock = null;
        BufferedReader reader = null;
        byte[] buffer = null;
        String data = "";
        try {

            //Abrimos el archivo
            file = new RandomAccessFile(filePath, "r");

            //Bloqueamos archivo por seguridad
//            fileLock = file.getChannel().lock();

            // Nos posicionamos al principio del fichero
            file.seek(0L);

            // Creamos un BUFFER, del tamaño del ARCHIVO a LEER
            buffer = new byte[(int) file.length()];

            //Leemos en fichero completo(indicamos los Bytes a LEER. que en este caso va a valer BUFFER)
            file.readFully(buffer);

            //Leemos un ByteArray del tamaño de BUFFER(Todo el fichero vamos), y lo enviamos al BufferesReader
            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

            //Almacenando el ARCHIVO Leido en una STRING 
            data = reader.lines().collect(Collectors.joining("\n"));
            
            //Imprimimos los DATOS por consola.
            System.out.println(data);
            state = true;
            
        } catch (IOException e) {
            
         //   System.out.println("IO Exception\n"+e.getMessage());
        
        }catch (Exception e) {
            String err=("No se puede leer el archivo" + e.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
         }finally{
            
             buffer = null;
             
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }         
//            try {
//                //cerramos el lector
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }         
            try {          
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.gc(); 
            
        }//Fin del Finally
        
        return state;
        
    }//FIN de ReadFileAndPrint
    
    
    
    
    
    /**
    * LEE ARCHIVO Y LO RETORNA EN UNA STRING.
    * 
    * @param filePath {Archivo a LEER}
    * @return String - {Retorna una STRING con todo el contenido del archivo}
    */
    public String readFileToString(String filePath) {
        return readFileToString_p(filePath);       
    }
    private String readFileToString_p(String filePath) {
               
        RandomAccessFile file =null;
//        FileLock fileLock = null;
        BufferedReader reader = null;
        byte[] buffer = null;
        String data = "";
        
         try {

            //Abrimos el archivo
            file = new RandomAccessFile(filePath, "r");
//            fileLock = file.getChannel().lock();
            // Nos posicionamos al principio del fichero
            file.seek(0L);

            // Creamos un BUFFER, del tamaño del ARCHIVO a LEER
            buffer = new byte[(int) file.length()];

            //Leemos en fichero completo(indicamos los Bytes a LEER. que en este caso va a valer BUFFER)
            file.readFully(buffer);

            //Leemos un ByteArray del tamaño de BUFFER, y lo enviamos al BufferesReader
            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

            //Almacenando el ARCHIVO Leido en una STRING y procedemos a retornarla
            data = reader.lines().collect(Collectors.joining("\n"));        
                                
        } catch (Exception e) {
        //    System.out.println("No se puede leer el archivo\n"+ e.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            //Cerrando BuffRead
            buffer = null;
            
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }           
//            try {
//                //cerramos el lector
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }         
            try {          
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            System.gc(); 
        }
        //Retornando String con todo el contenido del fichero
        return data;

    }
    
 

    
     /**
     * LEE ARCHIVO Y LO RETORNA EN UN ArrayList&lt;String&gt; de STRINGS.
     * 
     * Cada linea se almacenara en una posicion.
     * 
    * @param filePath {Archivo a LEER}
    * @return ArrayList&lt;String&gt; - {Retorna un ARRAYLIST de STRING con todo el contenido del archivo}
     */
    public ArrayList<String> readFileToArrayList(String filePath) {
        return readFileToArrayList_p(filePath);
    }
    private ArrayList<String> readFileToArrayList_p(String filePath) {
        
        RandomAccessFile file =null;
//        FileLock fileLock = null;
        BufferedReader reader = null;
        byte[] buffer = null;
        String data = "";
        ArrayList<String> arrayList = null;
         try {

            //Abrimos el archivo
            file = new RandomAccessFile(filePath, "r");

            //Bloqueamos archivo por seguridad
//            fileLock = file.getChannel().lock();

            // Nos posicionamos al principio del fichero
            file.seek(0L);

            // Creamos un BUFFER, del tamaño del ARCHIVO a LEER
            buffer = new byte[(int) file.length()];

            //Leemos en fichero completo(indicamos los Bytes a LEER. que en este caso va a valer BUFFER)
            file.readFully(buffer);

            //Leemos un ByteArray del tamaño de BUFFER, y lo enviamos al BufferesReader
            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

            //Almacenando el ARCHIVO Leido en una STRING 
            data = reader.lines().collect(Collectors.joining("\n"));
            //Pasamos el Archivo a un ArrayList, Contiene una linea en cada Elemento de la Lista.
            arrayList = new ArrayList<>(Arrays.asList(data.split("\n"))); 

        } catch (Exception e) {
            String err=("No se puede leer el archivo\n"+ e.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
        }finally{
            
            buffer = null;
            
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }           
//            try {
//                //Quitamos bloqueo 
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }        
            try {          
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.gc(); 
        }

        //Retornando ArrayList con contenido del fichero
        return arrayList;

    }//Fin de readFileToArrayList
    

      
    
    /**
    * Lee un objeto GENERICO de un archivoBinario y lo retorna.             
    * 
    * (DEBERA CASTEARSE EN SU SALIDA al Objeto que deseemos).
    * 
    * @param filePath {Archivo de Lectura}
    * @return Object - {Retorna un OBJETO Generico}
    */
    public Object readUniqueObjectFromFILE(String filePath) {
        return readUniqueObjectFromFILE_p(filePath);
    }
    private Object readUniqueObjectFromFILE_p(String filePath) {

        Object object = null;
        ObjectInputStream readerObject = null;
        RandomAccessFile file = null;
//        FileLock fileLock = null;

        try {
            // abre el fichero de acceso aleatorio  
            file = new RandomAccessFile(filePath, "r");
//            fileLock = file.getChannel().lock();
            // pone el puntero al principio  
            file.seek(0L);
            
            //Creamos un Array de la dimension del tamaño del fichero
            byte[] buffer = new byte[(int) file.length()];
            //Lee todo el fichero y lo almacena en el Buffer
            file.readFully(buffer);

            //Deserializa el array de bytes para proceder a su lectura
            readerObject = new ObjectInputStream(new ByteArrayInputStream(buffer));
            
            //Leemos el Objeto
            try {
                object = (Object) readerObject.readObject();
            } catch (EOFException eof) {
                //End Of File
            }
          
        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
           
        } finally {
            try {
                readerObject.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
//            try {
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//Fin del Finally

        return object;

    }
 
    
    
    

   /**
    * LEE VARIOS OBJETOS de un Archivo(Binario) y 
    * los Retorna en un ArrayList&lt;Object&gt; de OBJETOS GENERICOS.
    * 
    * (DEBERA CASTEARSE EN SU SALIDA al Objeto que deseemos).
    * 
    * @param filePath {Archivo a LEER}
    * @return ArrayList&lt;Object&gt; - {Array de Objetos contenidos en el archivo}
    */
    public ArrayList<Object> readSeveralObjectFromFILE(String filePath) {
        return readSeveralObjectFromFILE_p(filePath);
    }
    private ArrayList<Object> readSeveralObjectFromFILE_p(String filePath) {

        ArrayList<Object> objectList = new ArrayList<>();//Array que contendrá todos los OBJETOS LEIDOS
        Object object = null;
        ObjectInputStream readerObject = null;
        RandomAccessFile file = null;
//        FileLock fileLock = null;
        
        try {
            //abre el fichero de acceso aleatorio  
            file = new RandomAccessFile(filePath, "r");
            //Bloqueamos el Fichero
//            fileLock= file.getChannel().lock();
            
            //pone el puntero al principio  
            file.seek(0L);
            //Lee un array de bytes del fichero
            byte[] bytes = new byte[(int) file.length()];
            file.readFully(bytes);

            //Deserializa el array de bytes
            readerObject = new ObjectInputStream(new ByteArrayInputStream(bytes));

            try {
                while ((object = (Object) readerObject.readObject()) != null) {
                    objectList.add(object);
                }
            } catch (EOFException eof) {
                //End Of File
            }

            readerObject.close();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            object = null;
            try {
                readerObject.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
//             try {
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            if (file != null) {
                try {
                    file.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.gc();
        }//Fin del Finally
        
        return objectList;
    }

    


    
    /**
     * Escribir STRING en un ARCHIVO.
     * 
    * @param filePath {Archivo a AGREGAR Texto}
    * @param message {Texto que se desea Agregar Al Archivo}
    *  @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean writeTextInFile(String filePath, String message){
        return writeTextInFile_p(filePath, message);
    }
    private boolean writeTextInFile_p(String filePath, String message){
 
        boolean state = false;
        RandomAccessFile file = null;
//        FileLock fileLock = null;
        byte[] buffer = null;
        
        try {
            //Accedemos al Archivo como LECTURA-escritura
            file = new RandomAccessFile(filePath, "rw");
            //Bloqueamos el canal del archivo
//            fileLock = file.getChannel().lock();

            //Colocamos puntero en posición FINAL del archivo para proceder a escribir
            file.seek(file.length());

            //Agregando salto de linea a mensaje
            //    message +="\n";
            
            //Obteniendo Bytes de Mensaje
            buffer = message.getBytes("UTF-8");

            //Escribiendo bytes en el fichero
            file.write(buffer);
   
            state = true;
            
        } catch (NullPointerException | IOException ioe) {
            // System.out.println("IO EXCEPTION producida\n"+ioe.getMessage());
        } catch (Exception e) {
            //System.out.println("EXCEPTION Error de escritura\n" + e.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            
            buffer = null;
//            try {
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.gc();
        }//Fin del Try-Catch
        
        return state;
    }

    
    
   /**
    * Escribe UN UNICO OBJETO en el ARCHIVO.
    * (Borra el Archivo Si existe, y crea uno nuevo).
    * 
    * El archivo solo contendrá dicho objeto.
    * 
    * @param filePath {Archivo a ESCRIBIR el Objedto}
    * @param OBJ {Objeto a ESCRIBIR en el Archivo}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean writeUniqueObjectInFile(String filePath, Object OBJ) {
        return writeUniqueObjectInFile_p(filePath, OBJ);  
    }
    private boolean writeUniqueObjectInFile_p(String filePath, Object OBJ) {
        
        boolean state = false;
        ByteArrayOutputStream objectBytes = null;
        ObjectOutputStream writerObject = null;
        RandomAccessFile file = null;
//        FileLock fileLock = null;
        
        //REALIZANDO EL "reseteo" DEL FICHERO
        resetOrCreateFILE(filePath);

        try {
            //abre el fichero de acceso aleatorio
            file = new RandomAccessFile(filePath, "rw");

            //Bloqueamos el Fichero
//            fileLock= file.getChannel().lock();
            
            //pone el puntero al principio
            file.seek(0L);
            
            //serializa el Objeto convirtiéndolo a una secuencia de bytes
            objectBytes = new ByteArrayOutputStream();
           
            //Creamos el Escritor de objetos
            writerObject = new ObjectOutputStream(objectBytes);

            //Escribimos el objeto, y quedará almacenado en el "objectBytes"
            writerObject.writeObject(OBJ);
            writerObject.close();//Cerramos escritor de objetos

            //almacenamos los bytes del objeto(objectBytes) en el buffer 
            byte[] buffer = objectBytes.toByteArray();
            //escribe el "buffer" en el fichero
            file.write(buffer);
            
            state = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writerObject.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                objectBytes.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
//            try {
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.gc();
        }
        
        return state;
    }

 
   /**
    * -Escribe Agrega OBJETOS en el ARCHIVO Existente (El archivo contiene VARIOS("N") OBJETOS)-
    * 
    * @param filePath {Archivo a ESCRIBIR el Objedto}
    * @param OBJ {Objeto a ESCRIBIR en el Archivo}
    * @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
    */
    public boolean writeSeveralObjectInFile(String filePath, Object OBJ) {
        return writeSeveralObjectInFile_p(filePath, OBJ);    
    }
    private boolean writeSeveralObjectInFile_p(String filePath, Object OBJ) {
  
        boolean state = false;
        ByteArrayOutputStream objectBytes = null;
        ObjectOutputStream writerObject = null;
        RandomAccessFile file = null;
//        FileLock fileLock = null;
          
        try {
            //abre el fichero de acceso aleatorio
            file = new RandomAccessFile(filePath, "rw");

            //Bloqueamos el Fichero
//            fileLock= file.getChannel().lock();
            
            //pone el puntero al Final
            file.seek(file.length());
            
            //serializa el OBJETO convirtiéndolo a una secuencia de bytes
            objectBytes = new ByteArrayOutputStream();
            
            if (file.length() == 0) {
                //Si NO tiene registros, creamos "objeto" escritor con la Clase NORMAL. para escribir la cabecera del objeto. 
                writerObject = new ObjectOutputStream(objectBytes);
            } else {
                //Si SI tiene Registros, UTIlizaremos creamos "objeto" escritor con la clase "SOBREESCRITA" para escribir lo9s objetos sin cabeceras.                            
                writerObject = new MiObjectOutputStream(objectBytes);
            }
            
            //Cerramos el escritor de objetos
            writerObject.writeObject(OBJ);
            writerObject.close();
            
            //obtiene los bytes del Objeto serializado por "writerObject"
            byte[] buffer = objectBytes.toByteArray();
            
            //escribe los bytes en el fichero
            file.write(buffer);
            state = true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            try {
                writerObject.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                objectBytes.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
//            try {
//                fileLock.release();
//            } catch (IOException ex) {
//                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.gc();
        }//Fin del Finally
        
        return state;
    }
    
    
    
    

//#####################################################################
//#####################################################################
//               ENVIAR - LEER Mensajes y Objetos 
//          por los Stream  (De los Sockets) o (E/S del sistema)[no se si hay mas]
//#####################################################################
//#####################################################################
    
    /**
     * Lee Flujo de BYTES y devuelve STRING Codificada en UTF8.
     * 
     * Este método RECIBE un InputStream, Leerá la entrada de BYTES
     * proporcionada por el InputStream y DEVOLVERÁ una STRING codificada en
     * UTF8
     *
     * LEERA UNA SOLA LINEA.
     *
     * @param inputStream {InputStream del Socket o Consola}
     * @return String - {RETORNA una string con TODO el MENSAJE recibido}
     */
    public String readOneLine(InputStream inputStream) {
        return readOneLine_p(inputStream);      
    }
    private String readOneLine_p(InputStream inputStream) {

        String message = "";

        BufferedReader readerMessage = null;

        try {
            //Leyendo BYTES y Devolviendo STRING
            readerMessage = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

            //Leyendo UNA sola Linea
            message = readerMessage.readLine();
//            if ((message = readerMessage.readLine()) != null) {
//                readerMessage = null;
//                System.gc();
//                return message;
//            }

        } catch (NullPointerException npe) {
            String err=("\n[!]ERROR Null Pointer Exception\n--InputStream NULL");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            
        } catch (UnsupportedEncodingException use) {

            String err=("\n[!]Error en la CODIFICACIÓN:\n--" + use.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } catch (IOException ex) {
            
            String err=("\n[!]ERROR en la Entrada de Datos:\n--" + ex.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } finally {
            //Cerrando Lector (No se puede cerrar porque mataría el SOCKET)
            //  readerMessage.close();
            readerMessage = null;
            System.gc();
        }

        //RETORNANDO MENSAJE RECIBIDO
        return message;
    }//Fin readONELine
    
    
 
    /**
     * Lee Flujo de BYTES(lineas) hasta recibir Linea VACIA
     * y RETORNA TODO en una STRING Codificado en UTF8.
     * 
     * Este método RECIBE un InputStream, Leerá la entrada de BYTES
     * proporcionada por el InputStream y DEVOLVERÁ una STRING con TODO el
     * MENSAJE codificada en UTF8.
     *
     * NECESITA RECIBIR UNA LINEA VACÍA("") PARA DETERMINAR QUE ES EL FINAL. 
     * Si no la recibe, se quedará esperando El mensaje para siempre, sin retornar.
     *
     * @param inputStream {InputStream del Socket o Consola}
     * @return String - {RETORNA una string con TODO el MENSAJE recibido}
     */
     public String readAllLines(InputStream inputStream) {
      // String END_OF_MESSAGE = "";
         return readAllLines_p(inputStream, "");
     }
    
     /**
     * Lee Flujo de BYTES(lineas) hasta recibir Linea VACIA
     * y RETORNA TODO en una STRING Codificado en UTF8.
     * 
     * Este método RECIBE un InputStream, Leerá la entrada de BYTES
     * proporcionada por el InputStream y DEVOLVERÁ una STRING con TODO el
     * MENSAJE codificada en UTF8.
     *
     * NECESITA RECIBIR UNA LINEA VACÍA("") PARA DETERMINAR QUE ES EL FINAL. 
     * Si no la recibe, se quedará esperando El mensaje para siempre, sin retornar.
     *
     * INDICAR el "END_OF_MESSAGE" para determinar 
     * "QUE QUEREMOS QUE ENTIENDA" como EL FIN DEL MENSAJE.
     *
     * @param inputStream {InputStream del Socket o Consola}
     * @param END_OF_MESSAGE {Establecer un "Final de mensaje" Personalizado}
     * @return String - {RETORNA una string con TODO el MENSAJE recibido}
     */
    public String readAllLines(InputStream inputStream, String END_OF_MESSAGE) {
        return readAllLines_p(inputStream, END_OF_MESSAGE);
    }  
    private String readAllLines_p(InputStream inputStream, String END_OF_MESSAGE) {

        
        String message = ""; //Mensaje a Retornar
        BufferedReader readerMessage = null; 

        try {
            //Leyendo BYTES y Devolviendo STRING
            readerMessage = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

            //Leyendo MENSAJE que exista en la "Entada de datos"  
            //Parará de leer cuando reciba una linea VACIA "";    
            String aux = readerMessage.readLine();

            
            while (!(aux.equals(END_OF_MESSAGE))) {

                message += aux;

                //vOLVEMOS A Leer, Y SI Existe Otra línea, AGREGAMOS UN SALTO DE LINEA.
                if (!(aux = readerMessage.readLine()).equals(END_OF_MESSAGE)) {

                    message += "\n";
                }               
            }//Fin del While
          
       } catch (NullPointerException npe) {
            String err=("\n[!]ERROR Null Pointer Exception\n--InputStream NULL");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            
        } catch (UnsupportedEncodingException use) {

            String err=("\n[!]Error en la CODIFICACIÓN:\n--" + use.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } catch (IOException ex) {
            
            String err=("\n[!]ERROR en la Entrada de Datos:\n--" + ex.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } finally {
            //Cerrando Lector (No se puede cerrar porque mataría el SOCKET)
            //  readerMessage.close();
            readerMessage = null;
            System.gc();
        }

        //RETORNANDO MENSAJE RECIBIDO
        return message;
    }//Fin readAllLines

    
    
    /**
     * Leer OBJETOS del InputStream.
     * 
     * Este método recibe un InputStream, Lee la entrada el Flujo proporcionada
     * por el InputStream y lo convierte a un OBJETO tipo "Object", el cual
     * RETORNA, Listo para ser CASTEADO en el objeto DEBIDO.
     *
     * @param inputStream {Recibe un InputStream el cual leerá}
     * @return Object - {Retorna un OBJETO Generico}
     */
    public Object readObject(InputStream inputStream) {
        return readObject_p(inputStream);       
    }
    private Object readObject_p(InputStream inputStream) {

        Object obj = null;
        ObjectInputStream readObject = null;

        try {

            readObject = new ObjectInputStream(inputStream);

            //LEYENDO OBJETO
            obj = (Object) readObject.readObject();

        } catch (NullPointerException npe) {
            String err=("\n[!]ERROR Null Pointer Exception\n--InputStream NULL");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            
        } catch (IOException ex) {
            
            String err=("\n[!]ERROR en la Entrada de Datos:\n--" + ex.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } catch (ClassNotFoundException ex) {
           Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
       
        } finally {
            //Cerrando lector (No se puede cerrar porque mataría el SOCKET)
            //  readObj.close();
           readObject = null;
           System.gc();
        }

        return obj;

    }

    
    
    /**
     * Escribir/Enviar STRING en BYTES.
     * 
     * Este método recibe un OutputStream, Y una STRING(mensaje);
     *
     * Recibe una STRING(la codifica en utf8) y la envia
     * por el OutputStream una cadena de BYTES.
     *
     * @param outputStream {OutputStream del Socket o Consola}
     * @param message - {Mensaje que se necesita ENVIAR}
     * @return boolean - {TRUE(Si la ioperacion salio BIEN) || FALSE(Si la operacion salio mal)}
     */
    public boolean writeMessage(OutputStream outputStream, String message) {
        return writeMessage_p(outputStream, message);       
    }
    private boolean writeMessage_p(OutputStream outputStream, String message) {

        boolean state = false;
        BufferedWriter writerMessage=null;

        try {

            writerMessage = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF8"));

            if (message.length() > 0) {
                writerMessage.write(message);
            }

            writerMessage.newLine(); //O Tambien se puede enviar un salto de linea  "writeMessage.write("\n")"   
            
            state=true;
            
        } catch (NullPointerException npe) {
            String err=("\n[!]ERROR Null Pointer Exception\n--InputStream NULL");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            
        } catch (UnsupportedEncodingException use) {

            String err=("\n[!]Error en la CODIFICACIÓN:\n--" + use.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } catch (IOException ex) {
            
            String err=("\n[!]ERROR en la Entrada de Datos:\n--" + ex.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } finally {
            try {          
   
                writerMessage.flush();
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Cerrando Escritor (No se puede cerrar porque mataría el SOCKET)
            //  readFromServer.close();
            writerMessage = null;
            System.gc();
        }
        
        return state;
    }


    
    
    /**
     * Enviar OBJETOS por el OutputStream.
     * 
     * Este método recibe un OutputStream, y un OBJETO(el que sea) y Enviará el
     * OBJETO que haya recibido a través del OutputStream.
     * 
     * @param outputStream {OutputStream del Socket o Consola}
     * @param object - {Objeto que se quiere ENVIAR}
     * @return boolean - {TRUE(Si la ioperacion salio BIEN) || FALSE(Si la operacion salio mal)}
     */
    public boolean writeObject(OutputStream outputStream, Object object) {
        return writeObject_p(outputStream, object);      
    }
    private boolean writeObject_p(OutputStream outputStream, Object object) {

        boolean state = false;
        ObjectOutputStream writeObject;

        try {

            writeObject = new ObjectOutputStream(outputStream);

            writeObject.writeObject(object);
            
            state = true;
            writeObject.flush();
            
        } catch (NullPointerException npe) {
            String err=("\n[!]ERROR Null Pointer Exception\n--InputStream NULL");
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            
        } catch (IOException ex) {
            
            String err=("\n[!]ERROR en la Entrada de Datos:\n--" + ex.getMessage());
            Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);

        } finally {
            //Cerrando Escritor (No se puede cerrar porque mataría el SOCKET)
            //   writeObject.close();

            writeObject = null;
            System.gc();
        }
        
        return state;        
    }


 
    
//////////////////////////////////////////////////////////////////////


//#####################################################################
//                  ENVIAR - PEDIR/Descargar FICHEROS (Por Sockets)
//#####################################################################

/**
* Envia un OBJETO con el "nombre del archivo" Que Solicita,
* Se queda a la espera de recibir y descargar el Archivo Solicitado.
* 
* Crea un OBJETO, el cual SOLO contiene el Nombre(con la ruta) del Archivo 
* que quiere DESCARGAR, y se lo envia al SERVIDOR.
* Posteriormente se queda a la espera de que el servidor comienze a mandar el 
* Fichero en su totalidad.
*  
* @param outputStream {Flujo por donde enviará(por el OutputStream del Socket) el OBJETO para SOLICITAR EL ARCHIVO}
* @param pathFileRequest {Ruta y Nombre del archivo que se SOLICITA AL SERVIDOR}
* @param inputStream {Por el que se recibe(el InputStream del Socket) la Descarga del ARCHIVO Solicitado}
* @param pathFileDownloaded {Ruta y nombre para almacenar archivo descargado.}
* @return boolean - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
**/
    public boolean requestAndDownloadFile(OutputStream outputStream, String pathFileRequest, InputStream inputStream, String pathFileDownloaded){
        return requestAndDownloadFile_p(outputStream, pathFileRequest, inputStream, pathFileDownloaded);
    }
    private boolean requestAndDownloadFile_p(OutputStream outputStream, String pathFileRequest, InputStream inputStream, String pathFileDownloaded){
        
        boolean state= false;
        try {
            // Proparamos el Flujo para leer Objetos
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            //Creamos nuestro objeto Pasandole el Nombre del fichero a Solicitar
            OBJFileRequest requestFile = new OBJFileRequest();
            requestFile.nombreFichero= pathFileRequest;//Enviamos Objeto de Solicitud del fichero          
            oos.writeObject(requestFile);

            // Se abre un fichero para empezar a copiar lo que se reciba.
         //   FileOutputStream fos = new FileOutputStream(requestFile.nombreFichero + "_RECEIVED");
            FileOutputStream fos = new FileOutputStream(pathFileDownloaded);

            // Se crea un ObjectInputStream del socket para leer los mensajes
            // que contienen el fichero.
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            OBJFileSubmission mensajeRecibido;
            Object mensajeAux;
            
            do {
                // Se lee el mensaje en una variabla auxiliar
                mensajeAux = ois.readObject();
                
                // Si es del tipo esperado, se trata
               // if (mensajeAux instanceof OBJFileSubmission){
                if (Class.forName("readwriteutils.OBJFileSubmission").isInstance(mensajeAux)){
                    
                    mensajeRecibido = (OBJFileSubmission) mensajeAux;
                    // Se escribe en pantalla y en el fichero
              //    System.out.print(new String(mensajeRecibido.contenidoFichero, 0, mensajeRecibido.bytesValidos));
                    fos.write(mensajeRecibido.contenidoFichero, 0, mensajeRecibido.bytesValidos);
                    
                } else {
                    // Si no es del tipo esperado, se marca error y se termina
                    // el bucle
                    String err=("Mensaje no esperado "+ mensajeAux.getClass().getName());
                    Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
                    
                    break;
                }
               
            } while (!mensajeRecibido.ultimoMensaje);
            
            state = true;
          // Se cierra socket y fichero
       //     fos.close();
       //     ois.close();

        } catch (Exception e)
        {
            e.printStackTrace();
         }finally{
            System.gc(); 
        }
        
        return state;
    }
    
//******************************************************************************
    /**
     * Envia el fichero indicado a traves del ObjectOutputStream indicado.
     * 
     * RECIBE del CLIENTE un OBJETO, el cual SOLO contiene el Nombre(con la ruta) del Archivo 
     * que quiere DESCARGAR.
     * Procesa el objeto y extrae la Ruta y Nombre del fichero.
     * Posteriormente comienza a transferir el Fichero.
     * 
     * @param inputStream {"Canal" Por donde se RECIBE el OBJETO del CLIENTE}
     * @param outputStream {"Canal" que enviara el FICHERO solicitado al CLIENTE}
     *
     * @return state - {TRUE(Si la ioperacion salio BIEN); FALSE(Si la operacion salio mal)}
     */
    public boolean sendRequestedFile(InputStream inputStream, OutputStream outputStream) {
        return sendRequestedFile_p(inputStream, outputStream);     
    }
    private boolean sendRequestedFile_p(InputStream inputStream, OutputStream outputStream) {
        
        boolean state = false;
        try {
            // Se lee el OBJETO de Peticion de Fichero del cliente.
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object mensajeRead = (Object) ois.readObject();
            String requestedFile = ""; //Nombre del fichero Solicitado

            // Si el mensaje es de peticion de fichero
        //   if (mensajeRead instanceof OBJFileRequest){
            if (Class.forName("readwriteutils.OBJFileRequest").isInstance(mensajeRead)) {

                //Se muestra en pantalla el fichero pedido y se envia
                System.out.println("Me piden: " + ((OBJFileRequest) mensajeRead).nombreFichero);

                //Almacenando nombre del fichero Solicitado
                requestedFile = ((OBJFileRequest) mensajeRead).nombreFichero;

                //Creamos flujo de salida para Enviar el Fichero
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);

             //enviaFicheros********************************
                boolean enviadoUltimo = false;
                // Se abre el fichero.
                FileInputStream fis = new FileInputStream(requestedFile);

                // Se instancia y rellena un mensaje de envio de fichero
                OBJFileSubmission receivedData = new OBJFileSubmission();
                receivedData.nombreFichero = requestedFile;

                // Se leen los primeros bytes del fichero en un campo del mensaje
                int leidos = fis.read(receivedData.contenidoFichero);

                // Bucle mientras se vayan leyendo datos del fichero
                while (leidos > -1) {

                    // Se rellena el numero de bytes leidos
                    receivedData.bytesValidos = leidos;

                    // Si no se han leido el maximo de bytes, es porque el fichero
                    // se ha acabado y este es el ultimo mensaje
                    if (leidos < OBJFileSubmission.LONGITUD_MAXIMA) {
                        receivedData.ultimoMensaje = true;
                        enviadoUltimo = true;
                    } else {
                        receivedData.ultimoMensaje = false;
                    }

                    // Se envia por el socket
                    oos.writeObject(receivedData);

                    // Si es el ultimo mensaje, salimos del bucle.
                    if (receivedData.ultimoMensaje) {
                        break;
                    }

                    // Se crea un nuevo mensaje
                    receivedData = new OBJFileSubmission();
                    receivedData.nombreFichero = requestedFile;

                    // y se leen sus bytes.
                    leidos = fis.read(receivedData.contenidoFichero);
                }

                if (enviadoUltimo == false) {
                    receivedData.ultimoMensaje = true;
                    receivedData.bytesValidos = 0;
                    oos.writeObject(receivedData);
                }
                
                state = true;
                // Se cierra el ObjectOutputStream
              //  oos.close();
               
            } else {
                // Si no es el mensaje esperado, se avisa y se sale todo.
                String err=("[!]ERROR, Mensaje no esperado:\n" + mensajeRead.getClass().getName());
                Logger.getLogger(ReadWriteUtils.class.getName()).severe(err);
            }

        } catch (Exception e) {
            e.printStackTrace();
         }finally{
            System.gc(); 
        }
        
        return state;
    }
 
}//Fin de la clase principal




//#############################################################################
//#############################################################################
//#   CLASES NECESARIAS PARA EL CORRECTO FUNCIONAMIENTO DE ALGUNOS METODOS    #
//#############################################################################
//#############################################################################

/**
 * Clase, que realiza una Sobrecarga de Metodos de ObjectOutputStream 
 * para permitir escribir Varios objetos en un archivo Binario 
 * (Extirpando la cabecera)
 * y que puedan ser leidos posteriormente.
 */
 class MiObjectOutputStream extends ObjectOutputStream {

        /**
         * Constructor que recibe OutputStream
         * @param out
         * @throws java.io.IOException
         */
        public MiObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        /**
         * Constructor sin parametros
         * @throws java.io.IOException
         */
        protected MiObjectOutputStream() throws IOException, SecurityException {
            super();
        }

        /**
         * Redefinición del metodo de escribir la cabecera para que no haga
         * nada.
         * @throws java.io.IOException
         */
        @Override
        protected void writeStreamHeader() throws IOException {
        }      
    }// Fin de MiObjectOutputStream




 /** ******************************************************************************
  * CLASES A AGREGAR EN UN MISMO PAQUETE 
  * Ejemplo paquete "Utils" EN LOS RESPECTIVOS PROYECTOS, 
  * PARA PODER REALIZAR CORRECTAMENTE 
  *   LA TRANSFERENCIA DE ARCHIVOS.
  * (SI NO, DARÁ ERROR indicando que los Objetos, no se encuentran)
  * ******************************************************************************
  */

// CLASE "OBJFileRequest" ==> Se utiliza para SOLICITAR el FICHERO 
//    (la ruta del fichero es lo unico que contiene ete objeto)
//-----------------------------------------------------------
//
// package Utils;
// public class OBJFileRequest implements Serializable{
//     /** path completo del fichero que se pide */
//     public String nombreFichero;
//
// }


//CLASE "OBJFileSubmission" ==> Se encarga del ENVIO del FICHERO
//-----------------------------------------------------------------
//package Utils;
//public class OBJFileSubmission implements Serializable{
//          /**
//         * Nombre del fichero que se transmite. Por defecto ""
//         */
//        public String nombreFichero = "";
//
//        /**
//         * Si este es el ultimo mensaje del fichero en cuestion o hay mas
//         * despues
//         */
//        public boolean ultimoMensaje = true;
//
//        /**
//         * Cuantos bytes son validos en el array de bytes
//         */
//        public int bytesValidos = 0;
//
//        /**
//         * Array con bytes leidos(o por leer) del fichero
//         */
//        public byte[] contenidoFichero = new byte[LONGITUD_MAXIMA];
//
//        /**
//         * Numero maximo de bytes que se envian en cada mensaje
//         */
//        public final static int LONGITUD_MAXIMA = 1024;
//}

