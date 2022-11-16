package Lienzo;


import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Linea extends Figura  implements Serializable{

    //El fin de la linea, la posición de figura es el primero
    private Punto vector;

    
   public  Linea() {
        super(); 
        this.vector = new Punto(0, 0);
    }

    public Linea(int x, int y, int x2, int y2, String color) {
        // Con parámetros:
        super(x, y, color); 
        this.vector = new Punto(x2, y2); 
    }

    @Override
    public void renderText() {
        System.out.println("Linea de (" + this.posicion.getX() + "," + this.posicion.getY() + ") hasta  (" + this.vector.getX() + ", " + this.vector.getY() + ") y color " + this.color);
    }

    

    @Override
    public void render(GraphicsContext gc) {
        Color c = Color.web(this.color);
        gc.setLineWidth(3); // Grosor por defecto de la línea: 3
        gc.setStroke(c);
        gc.strokeLine(this.posicion.getX(), this.posicion.getY(), this.vector.getX(), this.vector.getY());

    }
    
        @Override
    public String toTXT() {
        return "linea " + this.posicion.getX() + " " + this.posicion.getY() + " "
                + this.vector.getX() + " " + this.vector.getY() + " " + this.color;
    }

    @Override
    public void toXML(Document document, Element root) {

        Element e = document.createElement("line");
        if (root == null) {
            document.appendChild(e);
        } else {
            root.appendChild(e);
        }

        e.setAttribute("stroke", this.color);
        e.setAttribute("stroke-width", "3");
        e.setAttribute("x1", this.posicion.getX() + "");
        e.setAttribute("y1", this.posicion.getY() + "");
        e.setAttribute("x2", this.vector.getX() + "");
        e.setAttribute("y2", this.vector.getY() + "");

    }

    @Override
    public JSONObject toJSON() {

        JSONObject objJSON = new JSONObject();
        objJSON.put("x1", this.posicion.getX() + "");
        objJSON.put("y1", this.posicion.getY() + "");
        objJSON.put("x2", this.vector.getX() + "");
        objJSON.put("y2", this.vector.getY() + "");
        objJSON.put("stroke", this.color);
        objJSON.put("stroke-width", "3");

         return new JSONObject().put("linea",objJSON);
    }

}
