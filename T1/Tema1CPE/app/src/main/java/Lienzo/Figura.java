package Lienzo;

// Librerias para poder dibujas
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Figura  implements Serializable {


    
    protected Punto posicion;
    protected String color;

    // Constructores:
    // Se inicia la posición y el color
    
    // La posición es de tipo punto, clase definida en la aplicación
    // El color es un string en formato hexadecimal: #ff0000=rojo, #00ff00=verde, #0000ff=azul

    public Figura(){
        // Constructor por defecto
        this.posicion=new Punto();
        this.color="#000000";
    }
    
    public Figura(int x, int y){
        // Constructor con la posición
        this.posicion=new Punto(x,y);
        this.color="#000000";
    };

    Figura(int x, int y, String color){
        //  Constructor con posición y color
        this.posicion=new Punto(x,y);
        this.color=color;
    }

    // Métodes abastractos que deben de implementar las clases herederas
     public abstract void renderText(); // Per mostrar una descripción de la figura geométrica en texto
     public abstract void render(GraphicsContext gc); // Para dibujar en un contexto gráfico

     public abstract String toTXT();
    
    public abstract void toXML(Document d,Element root);
    
    public abstract JSONObject toJSON();
 
}

