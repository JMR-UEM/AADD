package Lienzo;


import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Rectangulo extends Figura   implements Serializable{


    private Integer largo;
    private Integer alto;

    // Constructor por defecto
    public Rectangulo() {
        super(); 
        this.largo = 0;
        this.largo = 0;
    }

    // constructor general
    public Rectangulo(int x, int y, int largo, int alto, String color) {
        super(x, y, color); 
        this.largo = largo; 
        this.alto = alto; 
    }

    @Override
    public void renderText() {
        System.out.println("Rectangulo en (" + this.posicion.getX() + "," + this.posicion.getY() + ") de llarg " + this.largo + ", altura " + this.alto + " i color " + this.color);
    }

    
    @Override
    public void render(GraphicsContext gc) {
        // Dibujamos el rectángulo
        Color c = Color.web(this.color);
        gc.setFill(c);
        gc.fillRect(this.posicion.getX(), this.posicion.getY(), this.largo, this.alto);
        
    }
 @Override
    public String toTXT() {
        return "rectangulo " + this.posicion.getX() + " " + this.posicion.getY() + " "
                + this.largo + " " + this.alto + " " + this.color;
    }

    @Override
    public void toXML(Document document, Element root) {

        Element e = document.createElement("rect");
        if (root == null) {
            document.appendChild(e);
        } else {
            root.appendChild(e);
        }

        e.setAttribute("fill", this.color);
        e.setAttribute("height", this.alto + "");
        e.setAttribute("width", this.largo + "");
        e.setAttribute("x", this.posicion.getX() + "");
        e.setAttribute("y", this.posicion.getY() + "");

    }
    
        @Override
    public JSONObject toJSON() {

        JSONObject objJSON = new JSONObject();
        objJSON.put("x", this.posicion.getX() + "");
        objJSON.put("y", this.posicion.getY() + "");
        objJSON.put("width", this.largo + "");
        objJSON.put("height", this.alto + "");
        objJSON.put("fill", this.color);

        return new JSONObject().put("rectangulo",objJSON);
    }
    
}
