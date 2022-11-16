package Lienzo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Librerias para poder dibujar 
import javafx.scene.canvas.GraphicsContext;

public class Escena  implements Serializable{
    // tamaño de la escena
    private int tamX;
    private int tamY;

    //figuras que componen la escena
    List<Figura> listaFiguras;

    public Escena(){
        // Constructor. Por defecto escena de 800x600;
        this.tamX=800;
        this.tamY=600;

        // Iniciamos las figuras
        listaFiguras = new ArrayList<Figura>();
        
    }

    public Escena(int x, int y){

        // Constructor con un tamaño concreto

        this.tamX=x;
        this.tamY=y;


        listaFiguras = new ArrayList<Figura>();
    }

    public void  dimensiones(int x, int y){
        // Modificamos las dimensiones de la escena
        this.tamX=x;
        this.tamY=y;
    }

    // Getters del tamaño
    public int getX(){return this.tamX;}
    public int getY(){return this.tamY;}

    // Saber si la escena esta vacia
    public Boolean estaVacia(){
        return this.listaFiguras.isEmpty();
    }

    public List<Figura> getListaFiguras() {
        return listaFiguras;
    }

    
    // añadir una figura a la escena
    public void add(Figura figura){
        

        // La figura debe "caber" en la escena

        if(figura.posicion.getX()<this.tamX && figura.posicion.getY()<this.tamY){

            // Comprueba el color
            if (HexColorValidator.validate(figura.color)){
                this.listaFiguras.add(figura);
                System.out.println(Colors.Green+"OK"+Colors.Reset);
            } else {
                // En caso contrario, mostramos el error
                System.out.println(Colors.Red+"Color no válido."+Colors.Reset);
            }

        } else {
            // En caso contrario, mostramos l'error
            System.out.println(Colors.Red+"La imagen sale de la escena."+Colors.Reset);
        }
    }


    // muestra el texto del contenido de la escena
    public void renderText(){
        for (Figura f : this.listaFiguras) {
            f.renderText();
        }
    }
    
    //Dibuja la escena
    public void renderScene(GraphicsContext gc){
        /* Dibuja la  escena en un contexto de JavaFX.     */


        // Mas información del dibujo en
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html
        for (Figura f : this.listaFiguras) {
            f.render(gc);
        }
        
    };

    
}

