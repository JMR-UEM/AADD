package Lienzo;


import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Circulo extends Figura  implements Serializable{

    private Integer radio;

    // Constructors
    public Circulo() {
        super(); 
        this.radio = 0;
    }

    public Circulo(int x, int y, int radio, String color){
    super(x - radio, y - radio,  color); // Invoquem al constructor padre
        this.radio = radio; // Indiquem el valor del rado
        // Nota: La posición del círculo será su centro
        // por eso le restamos el radio
    }

    public void renderText() {
        System.out.println("Círculo en (" + this.posicion.getX() + "," + this.posicion.getY() + ") de radi " + this.radio + " i color " + this.color);
    }
    

    

    public void render(GraphicsContext gc) {
    
        Color c = Color.web(this.color);
        gc.setFill(c);
        gc.fillOval(this.posicion.getX(), this.posicion.getY(), this.radio * 2, this.radio * 2);
    }

       @Override
    public String toTXT() {
        return "circulo " + this.posicion.getX() + " " + this.posicion.getY() + " " + this.radio + " " + this.color;
    }

    @Override
    public void toXML(Document document, Element root) {

        Element e = document.createElement("circle");
        if (root == null) {
            document.appendChild(e);
        } else {
            root.appendChild(e);
        }

        e.setAttribute("cx", String.valueOf(this.posicion.getX() + this.radio));
        e.setAttribute("cy", String.valueOf(this.posicion.getY() + this.radio));
        e.setAttribute("fill", this.color);
        e.setAttribute("r", this.radio + "");

    }

    @Override
    public JSONObject toJSON() {
        JSONObject objJSON = new JSONObject();

        objJSON.put("r", this.radio + "");
        objJSON.put("cx", String.valueOf(this.posicion.getX() + this.radio));
        objJSON.put("cy", String.valueOf(this.posicion.getY() + this.radio));
        objJSON.put("fill", this.color);
        return  new JSONObject().put("circulo",objJSON);
    }
}
