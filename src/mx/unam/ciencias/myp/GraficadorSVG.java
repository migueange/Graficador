package mx.unam.ciencias.myp;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import java.io.*;
import javax.swing.JOptionPane;
import java.util.Random;

/**
 * Clase que dibuja en un SVG.
 * @author Miguel Mendoza.
 * @version Agosto 2014.
 */
public class GraficadorSVG {	
	/* Un árbol sintáctico */
	ArbolSintactico arbol;
	/* Valores donde se va a evaluar */
	double x1,x2;
	/* El ancho y el alto de la imagen */
	int ancho,alto;

	/**
	 * Construye un evaluador.
	 * @param arbol , un árbol sintáctico.
	 * @param x1, el valor inicial de x.
	 * @param x2, el valor final de x.
	 * @param alto, el alto de la imagen.
	 * @param ancho, el ancho de la imagen.
	 */
	public GraficadorSVG(ArbolSintactico arbol,double x1,double x2,int alto, int ancho){
		this.arbol = arbol;
		this.x1 = x1;
		this.x2 = x2;
		this.alto = alto;
		this.ancho = ancho;
	}

	/**
	 * Genera el SVG con la gráfica dibujada.
	 * @throws NumeroInvalido si x1 = x2 o x2 es menor x1.
	 * @param y2 , el valor máximo de y
	 * @param y1 , el valor mínimo de y
	 * @return String con el código para generar la imagen en SVG.
	 */
	public String generaSVG(double y2, double y1){
		if(!(x1 < x2))
			throw new NumeroInvalido("No se puede evaluar desde " + x1 + "hasta " +x2);
		String grafica = "";
        Lista<String> lista = new Lista<String>();
        double a = ancho/(x2-x1), b = alto/(y2-y1),x=0,y=0;
		while(x1 <= x2){
			try{
				x = (a*x1) + (ancho/2);
				y = (b*(evaluaEn(x1)*(-1))) + (alto/2);
				lista.agregaFinal(String.format("%g,%g ",x,y));
				x1 += 0.05; 
			}catch(FuncionInvalida fi){
				x1+=0.05;
				continue;	
			}
		}
		Random random = new Random();
		int c = 1 + random.nextInt(6);
		String color="";
		switch(c){
			case 1:
				color = "blue";
			break;
			case 2:
				color = "red";
			break;
			case 3:
				color = "green";
			break;
			case 4: 
				color = "purple";
			break;
			case 5:
				color = "yellow";
			break;
			case 6:
				color = "orange";
			break;

		}		
		System.out.println(color);
		String puntos = "";
		for(String p : lista)
			puntos += p;
		return grafica + dibujaPlano(ancho/2,0,ancho/2,alto)+ dibujaPlano(0,alto/2,ancho,alto/2) + dibujaFuncion(puntos,color);
	}

	/* Evalua la función en un valor x */
	private double evaluaEn(double x){
		return arbol.evalua(x);	
	}

	/* Método que dibuja el eje x o y del plano cartesiano. */
    private String dibujaPlano(double x1 , double y1 , double x2 , double y2){
        return String.format("\n     <line x1='%g' y1='%g' x2='%g' y2='%g' style='stroke:rgb(224,224,224);stroke-width:1'/>",x1,y1,x2,y2);
    }


    /* Método que dibuja una función a partir de un conjunto de puntos. */
    private String dibujaFuncion(String puntos,String color){
    	return String.format("     \n<polyline points='%s' style='fill:none;stroke:%s;stroke-width:1' />",puntos,color);
    }

    /**
	 * Imprime el código SVG en un archivo formato "*.svg".
	 * @param svg, el String con el código SVG.
	 * @param archivo, el nombre del archivo donde se va a guardar.
	 */
    public void archivoSVG(String svg ,String archivo){
    	PrintWriter print = null; 
    	try{
    		print = new PrintWriter("img/"+archivo);
    		print.print(svg);
    		print.close();
    		JOptionPane.showMessageDialog(null,"Se ha generado exitosamente el archivo \""+archivo+"\".", "SVG", JOptionPane.INFORMATION_MESSAGE);
    	}catch(IOException ioe){
    		JOptionPane.showMessageDialog(null,"Error al generar SVG.", "Error", JOptionPane.ERROR_MESSAGE);
    	}finally{
    		print.close();
    	}
    }

}

