/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tema1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author joange
 */
public class A5_PP_SOL {

    public static String name;
    public static String date;
    public static ArrayList<TrackPoint> traks;

    public static void main(String[] args) {
        cargarTraksPoints("trail.json");
        System.out.println(name + " - " + date + " traks=" + traks.size());

        maxMin();

        mostrarLaps();

        guardarXML("trail.xml");

    }

    public static void cargarTraksPoints(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            String myJson = "";

            int i;
            while ((i = file.read()) != -1) {
                myJson = myJson + ((char) i);
            }
            file.close();

            JSONObject trak = new JSONObject(myJson);

            name = trak.getString("name");
            date = trak.getString("date");

            JSONArray losTrackPoint = trak.getJSONArray("trackPoints");
            traks = new ArrayList<>();

            for (int j = 0; j < losTrackPoint.length(); j++) {
                TrackPoint trk = new TrackPoint(losTrackPoint.getJSONObject(j));
                traks.add(trk);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void maxMin() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (TrackPoint trak : traks) {
            if (trak.getEle() > max) {
                max = trak.getEle();
            }
            if (trak.getEle() < min) {
                min = trak.getEle();
            }
        }

        System.out.println("La elevación mínima es " + min + " metros");
        System.out.println("La elevación máxima es " + max + " metros");
    }

    public static long segundosEntre(Date ini, Date fin) {
        long segundos = 0;

        segundos = (fin.getTime() - ini.getTime());

        return segundos / 1000;
    }

    public static void mostrarLaps() {
        ArrayList<Integer> tempsTramsKM = new ArrayList<>();

        double distanciaTotal = 0.0;
        double distKM = 0.0;

        int tiempoParcial = 0;

        for (int i = 0; i < traks.size() - 1; i++) {

            long t = segundosEntre(traks.get(i).getTime(), traks.get(i + 1).getTime());
            double s = traks.get(i).distanciaTo(traks.get(i + 1));

            // acumulamos distancias
            distanciaTotal += s;
            distKM += s;

            //acumulamos temps
            tiempoParcial += t;

            if (distKM > 1000) {
                // guardamos el timepo de la vuelta
                tempsTramsKM.add(tiempoParcial);
                tiempoParcial = 0;
                distKM = (distKM - 1000);
            }

        }
        //Añadimos el tiempo de la última vuelta, seguramente incompleta
        tempsTramsKM.add(tiempoParcial);

        System.out.println("Longitud total de la ruta: " + distanciaTotal);
        System.out.println("Tiempo de cada km");
        for (int i = 0; i < tempsTramsKM.size(); i++) {
            int minuts = tempsTramsKM.get(i) / 60;
            int segons = tempsTramsKM.get(i) % 60;
            if (i < tempsTramsKM.size() - 1) {
                System.out.printf("Lap %2d, 1000 m efectuada en %02d:%02d\n", (i + 1), minuts, segons);
            } else {// última volta, no son 1000m
                int dist = (int) distanciaTotal - ((tempsTramsKM.size() - 1) * 1000);
                System.out.printf("Lap %2d, %04d m efectuada en %2d:%02d\n", (i + 1), dist, minuts, segons);
            }
        }
    }

    public static void guardarXML(String fileName) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            document.setXmlVersion("1.0");
            
            Element raiz = document.createElement("trk");
            document.appendChild(raiz);
            
            Element nameElement = document.createElement("name");
            nameElement.setTextContent(name);
            raiz.appendChild(nameElement);
            
            Element dateElement = document.createElement("date");
            dateElement.setTextContent(date);
            raiz.appendChild(dateElement);
            
            Element trkElement = document.createElement("trkPoints");
            raiz.appendChild(trkElement);
            
            for (TrackPoint trak : traks) {
                trkElement.appendChild(trak.toXML(document));
            }
            
            // Preparamos el transformer para la conversión
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            
            // Origen: el DOM del documento creado
            DOMSource source = new DOMSource(document);
            
            //Destino: un fichero XML
            StreamResult result = new StreamResult(new FileOutputStream(fileName));
            
            trans.transform(source, result);
            
        } catch (ParserConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
