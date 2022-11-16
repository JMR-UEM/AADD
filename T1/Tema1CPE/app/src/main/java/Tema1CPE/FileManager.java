package Tema1CPE;

import Lienzo.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class FileManager {

    public FileManager() {

    }

    private boolean validaInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public Boolean Exists(String file) {
        File f = new File(file);
        if (f.isFile() && f.canRead()) {
            return true;
        } else {
            return false;
        }
    }

    public Escena importFromText(String file) {

       
       Escena escena=new Escena();
        FileReader fr=null;
        try {
            
            File f=new File(file);
            fr = new FileReader(f);
            BufferedReader br= new BufferedReader(fr);
            String linea="";
            int num_Lin=0;
            while((linea=br.readLine())!=null){
                linea=linea.toLowerCase(); // per evitar errores
                num_Lin++;
                // comentario. Lo pasamos
                if (linea.startsWith("#"))
                    continue;
                
                String elementos[]=linea.split("[ ]+");
                
                switch(elementos[0]){
                    case "dimensiones":
                        if (elementos.length!=3){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". El comando dimensiones debe tener 3 argumentos");
                            break;
                        }
                        else{
                            escena.dimensiones(Integer.parseInt(elementos[1]), Integer.parseInt(elementos[2]));
                        }
                        break;
                    case "circulo":
                        if (elementos.length!=5){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". El comando circulo debe tener 5 argumentos");
                            break;
                        }
                        
                        if (!validaInt(elementos[1])||
                            !validaInt(elementos[2]) ||
                            !validaInt(elementos[3])){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". Falla algun formato numérico");
                            break;
                        }
                        if (!HexColorValidator.validate(elementos[4])){
                            System.out.println(Colors.Red+ "El color RGB " + elementos[4] + " no es válido.");
                            break;
                        }
                            
                        Circulo c=new Circulo(Integer.parseInt(elementos[1]), Integer.parseInt(elementos[2]), 
                                            Integer.parseInt(elementos[3]), elementos[4]);
                        escena.add(c);
                        c.renderText();
                        break;
                    case "rectangulo":
                         if (elementos.length!=6){
                            System.out.println(Colors.Red+"Error en la lieia " + num_Lin +". El comando rectangulo debe tener 6 argumentos");
                            break;
                        }
                        
                        if (!validaInt(elementos[1])||
                            !validaInt(elementos[2]) ||
                            !validaInt(elementos[3]) ||
                            !validaInt(elementos[4])){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". Falla algun formato numérico");
                            break;
                        }
                        if (!HexColorValidator.validate(elementos[5])){
                            System.out.println(Colors.Red+ "El color RGB " + elementos[5] + " no es válido.");
                            break;
                        }
                            
                        Rectangulo r=new Rectangulo(Integer.parseInt(elementos[1]), Integer.parseInt(elementos[2]), 
                                            Integer.parseInt(elementos[3]),Integer.parseInt(elementos[4]), elementos[5]);
                        escena.add(r);
                        r.renderText();
                        break;
                    case "linea":
                        if (elementos.length!=6){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". El comando linea debe tener 6 argumentos");
                            break;
                        }
                        
                        if (!validaInt(elementos[1])||
                            !validaInt(elementos[2]) ||
                            !validaInt(elementos[3]) ||
                            !validaInt(elementos[4])){
                            System.out.println(Colors.Red+"Error en la linea " + num_Lin +". Falla algun formato numérico");
                            break;
                        }
                        if (!HexColorValidator.validate(elementos[5])){
                            System.out.println(Colors.Red+ "El color RGB " + elementos[5] + " no es valido.");
                            break;
                        }
                            
                        Linea l=new Linea(Integer.parseInt(elementos[1]), Integer.parseInt(elementos[2]), 
                                            Integer.parseInt(elementos[3]),Integer.parseInt(elementos[4]), elementos[5]);
                        escena.add(l);
                        l.renderText();
                        break;
                    default:
                        System.out.println(Colors.Red+"Error en el fitxer en la linia "+ num_Lin+Colors.Reset);
                }
                        
            }   
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return escena;

    }

    public Escena importFromObj(String file) {

        FileInputStream fi=null;
        Escena escena=null;
        try {
            fi = new FileInputStream(file);
            ObjectInputStream ois= new ObjectInputStream(fi);
            
            // Toda en bloque. Como la escena lo contiene todo
            escena=(Escena)(ois.readObject());
            
            ois.close();
            fi.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fi.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return escena;

    }

    public Boolean exportText(Escena escena, String file) {

        boolean out=false;
        FileWriter fw=null;
        try {
            File f=new File(file);
            fw = new FileWriter(f);
            PrintWriter pw=new PrintWriter(fw);
            
            if (escena.estaVacia())
                out= false;
            
            pw.println("dimensiones " + escena.getX() + " " + escena.getY());
            for (Figura fig : escena.getListaFiguras()) {
                pw.println(fig.toTXT().toLowerCase());
            }
            
            pw.flush();
            fw.close();
            out=true;
            
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return out;

    }

    public Boolean exportObj(Escena escena, String file) {

         FileOutputStream fos=null;
        boolean out=false;
        try {
            
            File f=new File(file);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            
            oos.writeObject(escena); // Se guarda todo
            
            oos.close();
            fos.close();
            
            out=true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return out;

    }

    public Boolean exportSVG(Escena escena, String file) {
        /*
            <?xmlversion="1.0"encoding="UTF-8"standalone="no"?> 2 <svgheight="500"width="500">
            <rect fill="#ccccee" height="480" width="480" x="10" y="10"/>
            <circle cx="250" cy="250" fill="#aaaaaa" r="100"/>
            <line stroke="#aaaaaa" stroke-width="3" x1="50" x2="450" y1="250" y2="250"/>
            <line stroke="#aaaaaa" stroke-width="3" x1="50" x2="50" y1="50" y2="
            450"/>
            <line stroke="#aaaaaa" stroke-width="3" x1="450" x2="450" y1="40" y2= "450"/>
            </svg>
         */

 boolean out=false;
        try {
            


            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
             document.setXmlVersion("1.0");
            //Document document = implementation.createDocument(null, "svg", null);  //nodo pdre
            
            Element root = document.createElement("svg");
           
            root.setAttribute("height", escena.getX()+"");
            root.setAttribute("width", escena.getY()+"");
            document.appendChild(root);
            
            for (Figura f : escena.getListaFiguras()) {
                f.toXML(document, root);
            }
            
            Source source = new DOMSource(document);
            Result result = new StreamResult(file);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            out=true;
           
        } catch (TransformerException ex) { 
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

         return out;
        
    }

    public Boolean exportJSON(Escena escena, String filename) {

          FileWriter file=null;
        boolean out=false;
        try {
            /************************************************
             * TO-DO: Mètode a implementar:                 *
             * exporta l'escena donada a un fitxer JSON.    *
             ************************************************/
            
            
            JSONObject objJSON = new JSONObject();
            
            JSONArray listaFiguras = new JSONArray();
            
            for (Figura f : escena.getListaFiguras()) {
                listaFiguras.put(f.toJSON());
            }
            
            
            objJSON.put("figuras", listaFiguras);
            objJSON.put("width", escena.getX());
            objJSON.put("height", escena.getY());
            
            JSONObject objRootJSON = new JSONObject();
            objRootJSON.put("escena", objJSON);
            
            file = new FileWriter(filename);
            file.write(objRootJSON.toString(4));
            file.flush();
            out=true;
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return out;

    }

}
