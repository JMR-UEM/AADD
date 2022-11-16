/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tema1;



import org.json.JSONObject;

/**
 *
 * @author joange
 */
class AlumnoTel {

    private Long idAlumno;
    private String nombre;
    private int edad;
    private boolean repetidor;
    private String[] telefonos;

    public AlumnoTel(Long idAlumno, String nombre, int edad, boolean repetidor, String[] telefonos) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.edad = edad;
        this.repetidor = repetidor;
        this.telefonos = telefonos;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdat() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    public String[] getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String[] telefonos) {
        this.telefonos = telefonos;
    }

  
    public JSONObject asJSON() {
       
        //DO-DO 
        
        return null;
    }

}

public class A5_CP_TO_DO {

    public static void main(String[] args) {
        
        AlumnoTel a1 = new AlumnoTel(1000L, "Jose Andres Perez", 22, false, new String[]{"222333444", "555666777", "888999000"});
        AlumnoTel a2 = new AlumnoTel(1001L, "Ana Sanchis Cabanilles", 21, false, new String[]{"222333444", "555666777", "888999000", "333421039"});
        AlumnoTel a3 = new AlumnoTel(1002L, "Aina Gomes Pons", 23, true, new String[]{});
        AlumnoTel a4 = new AlumnoTel(1003L, "Ainara Gabaldón Estruch", 24, false, new String[]{"222111214", "515626677"});

        
    }
}
