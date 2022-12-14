package Tema1CPE;

// Imports para la a entrada de dades
import Lienzo.*;
import java.util.Scanner;

// Imports para comoponentes gráficos
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.IOError;
// Imports per a gestió de llistes
import java.util.ArrayList;
import java.util.List;
 


    public class App extends Application{
    /* La clase App  es una
    subclase de Application, para tener el contexto gráfico
        que ofrece */

    // PrimaryStage es el contenedor de la escena en JavaFX
    static Stage primaryStage;

    // Escena es la clase que representará  nuestra escena
    static Escena AppEscena;
    
 
    public static void main(String[] args) {
        
        
        
        Scanner keyboard = new Scanner(System.in);

        // FileManager serà el gestor de almacenamiento
        FileManager fm;

        // Tamaño de la escena
        int width, height;

        try{
            // Si vienen en los argumentos
            width=Integer.parseInt(args[0]);
            height=Integer.parseInt(args[1]);;
        } catch (Exception e){
            // Sino, por defecto 800x600
            width=800;
            height=600;
        }

        // Inicio de la Escena a width x height
        AppEscena=new Escena(width, height);
        
        // bucle infinito hasta dibujar
        String figura;

        do {

            // Preguntamos la seguiente figura a guardar
            System.out.print(Colors.Blue+"# Figura: "+Colors.Reset);
            String ordre = keyboard.nextLine();

            // Dividimos la orden introducida
            String[] components=ordre.split(" ");
            
            figura=components[0];
            
            switch (figura){
                case "circulo":
                    try{
                        // sacamos las dimensiones
                        int x=Integer.parseInt((components[1]));
                        int y=Integer.parseInt((components[2]));
                        int radi=Integer.parseInt((components[3]));
                        String color=components[4];
                        
                        // lo creamos
                        Circulo nuevoCirculo=new Circulo(x, y, radi, color);
                        // Añadimos a la escena
                        AppEscena.add(nuevoCirculo);


                    } catch (Exception e){
                        System.out.println("\u001B[31m Error de sintaxis. La sintaxis correcta ss:\nccirculo x y radio color \u001B[0m");
                    };
                    break;
            
                case "rectangulo":
                    try{
                        // sacamos las dimensiones
                        int x=Integer.parseInt((components[1]));
                        int y=Integer.parseInt((components[2]));
                        int llarg=Integer.parseInt((components[3]));
                        int alt=Integer.parseInt((components[4]));
                        String color=components[5];
                        
                        // lo creamos
                        Rectangulo nuevoRectangulo=new Rectangulo(x, y, llarg, alt, color);
                        // lo añadimos
                        AppEscena.add(nuevoRectangulo);


                    } catch (Exception e){
                        System.out.println("\u001B[31m Error de sintaxis. La sintaxis correcta és:\nrectangulo x y largo ancho color \u001B[0m");
                    };
                    break;

                case "linea":
                    try{
                        // sacamos las dimensiones
                        int x=Integer.parseInt((components[1]));
                        int y=Integer.parseInt((components[2]));
                        int x2=Integer.parseInt((components[3]));
                        int y2=Integer.parseInt((components[4]));
                        String color=components[5];
                        
                        // la creamos
                        Linea liniaNova=new Linea(x, y, x2, y2, color);
                        // la añadimos
                        AppEscena.add(liniaNova);


                    } catch (Exception e){
                        System.out.println("\u001B[31m Error de sintaxis. La sintaxis correcta es:\nlinia x1 y1 x2 y2 color \u001B[0m");
                    };
                    break;

                case "dimensiones":
                    // Redimensiona el marco de la escena
                    try{
                        int x=Integer.parseInt((components[1]));
                        int y=Integer.parseInt((components[2]));
                        AppEscena.dimensiones(x, y);
                        System.out.println("\u001B[32m OK \u001B[0m");
                    }catch(Exception e){
                        System.out.println("\u001B[31m Error de sintaxis. La sintaxis correcta es:\ndimensiones x y \u001B[0m");
                    }

                    break;

                case "import":
                    // Creamos el gestor de almacenamiento
                    fm=new FileManager();

                    // leera el fichero indicado en el primer parámetro 
                    // i creará una nueva escena
                    if (!fm.Exists(components[1]))
                        System.out.println("El fichero no existe");
                    else {
                        System.out.println("Importando fichero...");
                        
                        String extensio=components[1].substring(components[1].length()-4, components[1].length());
                        
                        
                        switch (extensio){
                            case ".txt":
                                {Escena novaEscena=fm.importFromText(components[1]);
                                if (!novaEscena.estaVacia()) AppEscena=novaEscena;}
                            break;
                            case ".obj":
                                {Escena novaEscena=fm.importFromObj(components[1]);
                                if (!novaEscena.estaVacia()) AppEscena=novaEscena;}
                                
                            break;

                            default:
                                System.out.println("Formato no reconocido..");
                            break;

                        }
                        AppEscena.renderText();

                    }

                break;

                case "export":
                    try{
                        String extensio=components[1].substring(components[1].length()-4, components[1].length());
                        
                        fm=new FileManager();
                        if (fm.Exists(components[1])){
                            // Si el fichero existe error
                            System.out.println("\u001B[31m El fichero ya existe\u001B[0m");    
                            break;
                            
                        }
                        
                        switch (extensio){
                            case ".txt":
                                if (fm.exportText(AppEscena, components[1]))
                                    System.out.println(Colors.Bright_Cyan+ "Ok. Exportación Correcta a TXT"+Colors.Reset);
                                else
                                    System.out.println(Colors.Bright_Red+ "Error. Exportación Incorrecta"+Colors.Reset);
                            break;

                            case ".obj":
                                if (fm.exportObj(AppEscena, components[1]))
                                 System.out.println(Colors.Bright_Cyan+ "Ok. Exportación Correcta a OBJ"+Colors.Reset);
                                else
                                    System.out.println(Colors.Bright_Red+ "Error. Exportación Incorrecta"+Colors.Reset);
                            break;

                            case ".svg":
                               if (fm.exportSVG(AppEscena, components[1]))
                                 System.out.println(Colors.Bright_Cyan+ "Ok. Exportació Correctann a SVG"+Colors.Reset);
                                else
                                    System.out.println(Colors.Bright_Red+ "Error. Exportació Incorrecta"+Colors.Reset);
                            break;

                            case "json":
                                if (fm.exportJSON(AppEscena, components[1]))
                                 System.out.println(Colors.Bright_Cyan+ "Ok. Exportación Correcta a JSON"+Colors.Reset);
                                else
                                    System.out.println(Colors.Bright_Red+ "Error. Exportación Incorrecta"+Colors.Reset);
                            break;

                            default:
                                System.out.println("\u001B[31m Formato no soportado\u001B[0m");    
                            break;


                        }

                    } catch (Exception e){ 
                        System.out.println("\u001B[31m Error de exportación\u001B[0m");    
                    }

                    break;

                case "lista":
                        // Si la orden indicada es lista, se imprimen port pantalla
                        AppEscena.renderText();
                    break;

                case "draw":
                        // Si la orden es draw, cargamos la aplicación JavaFX
                        // implementaremos el método start (con @override), que es
                        // quien dibuja nuestra escena
                        launch(args);
                        System.exit(0);
                    
                    break;

                case "quit":
                    System.out.println(Colors.Magenta+"Acabando el programa."+Colors.Reset);
                    break;
                default:
                    
                    System.out.println(Colors.Bright_Red + "Figura no reconocida" + Colors.Reset);
            }

            
        } while(!figura.equals("quit"));
        System.exit(0);
    }


    @Override
    public void start(Stage primaryStage) {
        /*
        Dibujamos la escena
         */

        // Preparamos el contexto gráfico (GraphicsContent) 
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(App.AppEscena.getX(), App.AppEscena.getY());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Se lo pasamos al método renderScene de  nuestra escena, 
        // para que dibuje sobre el context los diferentes elementos.
        App.AppEscena.renderScene(gc);
        
        // Añadimos a la ventana el lienzo
        // establecemos algunas propiedades y lo dibujamos (Show)
        root.getChildren().add(canvas);
        primaryStage.setTitle("Escena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



 }