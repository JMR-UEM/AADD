/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tema1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joange
 */
public class A2_PP_SOL {

    private static final String nombreFicheroIN = "alumnos.csv";
    private static final String nombreFicheroOUT = "alumnosAprobados.csv";

    public static void main(String[] args) {
        ArrayList<Alumno> alumnos;

        alumnos = importar(nombreFicheroIN);
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
        Alumno elMejor = buscarMejorAlumno(alumnos);
        System.out.println("El mejor alumno es " + elMejor);
        exportarAprobados(nombreFicheroOUT, alumnos);
    }

    public static ArrayList<Alumno> importar(String nombre) {
        FileReader fr = null;
        ArrayList<Alumno> alumnos = null;
        try {
            alumnos = new ArrayList<>();
            File f = new File(nombre);
            fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String linea = "";
            while ((linea = bfr.readLine()) != null) {
                String[] partes = linea.split(",");
                Alumno a = new Alumno(partes[0],
                        Integer.parseInt(partes[1]),
                        partes[2],
                        Double.parseDouble(partes[3]));
                alumnos.add(a);
            }
            bfr.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return alumnos;
    }

    public static Alumno buscarMejorAlumno(ArrayList<Alumno> alumnos) {
        if (alumnos == null) {
            return null;
        }
        Alumno a = alumnos.get(0);
        for (int i = 1; i < alumnos.size(); i++) {
            if (alumnos.get(i).getNota() > a.getNota()) {
                a = alumnos.get(i);
            }
        }
        return a;
    }

    public static void exportarAprobados(String nom, ArrayList<Alumno> alumnos) {
        FileWriter fw = null;
        try {
            File f = new File(nom);
            fw = new FileWriter(f);
            BufferedWriter bfw = new BufferedWriter(fw);
            for (Alumno alumno : alumnos) {
                if (alumno.aprobado()) {
                    bfw.write(alumno.toCSV());
                    bfw.newLine();
                }
            }
            bfw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
