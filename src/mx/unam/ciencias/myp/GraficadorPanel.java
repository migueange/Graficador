package mx.unam.ciencias.myp;

import javax.swing.*;
import java.awt.*;
//import java.io.*;
import java.util.Random;
import mx.unam.ciencias.edd.Lista; 
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;

/** 
 * Dibuja una funci&oacute;n en el panel.
 * @author Miguel Mendoza.
 * @version Agosto 2014
 */
public class GraficadorPanel extends JPanel{

	/* Valores donde se va a evaluar */
	private double x1,x2,y1,y2;
	private int alto,ancho;
	Lista<ArbolSintactico> lista;

	/**
	 * Construye un Graficador con un ancho y alto predeterminado.
	 */
	public GraficadorPanel(){
		alto = 510;
		ancho = 760;	
		setBounds(20,10,760,500);
		setBackground(Color.GRAY);
		lista = new Lista<ArbolSintactico>();
	}


	/**
	 * Pinta una funci&oacute;n
	 * @param g , Graphics para dibujar.
	 */
	@Override
	public void paintComponent(Graphics g){
   		super.paintComponent(g);
   		dibujarFuncion(g);
 	}

 	/** 
 	 * Dibuja una nueva funci&oacute;.
 	 * @param listaArbol una lista de {@link ArbolSintactico}.
 	 * @param x1 valor inicial de x.
 	 * @param x2 valor inicial de x.
 	 * @param y1 valor inicial de y.
 	 * @param y2 valor inicial de y.
 	 */
 	public void dibujarNueva(Lista<ArbolSintactico> listaArbol, double x1, double x2,double y1,double y2){
 		this.x1 = x1;
 		this.x2 = x2;
 		this.y1 = y1;
 		this.y2 = y2;
 		lista = listaArbol;
 		this.repaint();
 	}

 	/**
 	 * Dibuja un funci&oacute;n.
 	 * @param q Graphics para dibujar.
 	 */
 	private void dibujarFuncion(Graphics q){
 		Graphics2D g = (Graphics2D)q;
 		g.setStroke(new BasicStroke(1));
 		g.setColor(Color.white);
 		g.drawLine(ancho/2,0,ancho/2,alto);
 		g.drawLine(0,alto/2,ancho,alto/2);
 		if(lista.getLongitud() != 0){
 			for(ArbolSintactico arbol: lista ){
 				double x11 = x1;
 				if(!(x1 < x2))
					throw new NumeroInvalido("No se puede evaluar desde " + x1 + "hasta " +x2);
 				double a = ancho/(x2-x1), b = alto/(y2-y1),x=0,y=0;
 				int size = (int)(x2-x1),i=0;
 				int [] arrayX = new int[(size+1)*10];
 				int [] arrayY = new int[(size+1)*10];
	 			Graphics2D g1 = (Graphics2D)q;
 				g1.setStroke(new BasicStroke(2));
 				Random random = new Random();
 				int c = 1 + random.nextInt(6);
				switch(c){
					case 1:
						g1.setColor(Color.DARK_GRAY);
					break;
					case 2:
						g1.setColor(Color.GREEN);
					break;
					case 3:
						g1.setColor(Color.ORANGE);
					break;
					case 4: 
						g1.setColor(Color.BLUE);
					break;
					case 5:
						g1.setColor(Color.YELLOW);
					break;
					case 6:
						g1.setColor(Color.MAGENTA);
					break;
				}		
	 			while(x11 <= x2){
 					try{
 						x = (x11*a) + (ancho/2);
						y = (b*(evaluaEn(x11,arbol)*(-1))) + (alto/2);
						arrayX[i] = (int)x;
						arrayY[i] = (int)y;
						x11+= .1;
						i++;
 					}catch(FuncionInvalida fi){
 						x11+= .1;
 						i++;
	 					continue;	
 					}
 				}
 				g1.drawPolyline(arrayX,arrayY,200);
 			}
 		}
 	}

	/* Evalua la función en un valor x */
	private double evaluaEn(double x, ArbolSintactico arbol){
		return arbol.evalua(x);	
	}


}