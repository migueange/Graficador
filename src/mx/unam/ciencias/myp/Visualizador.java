package mx.unam.ciencias.myp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;	
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import java.io.*;

/**
 * Visualizador
 * Contiene los elementos y comportamientos de la interfaz gráfica.
 * @author Miguel Mendoza.
 * @version Agosto 2014.	
 */
public class Visualizador extends JFrame implements ActionListener,ChangeListener,FocusListener {
	
	/* Atributos */
	private JLabel funcion,lAncho,lAlto,lx1,lx2,ly1,ly2,developer;
	private JPanel panel;
	private JTextField campoTexto;
	private JSpinner spAncho,spAlto,spX1,spY1,spX2,spY2;
	private JButton clean,graficar,svg;
	private String fx,svgCad;
	private int ancho,alto;
	private double x1,y1,x2,y2;
	private Tokenizer tokenizer;
	private Parser parser;
	private GraficadorSVG graficador;
	private GraficadorPanel gp;
	private int verificaSVG,numAr;
	private Lista<ArbolSintactico> l;

	/**
	 * Constructor por omisión.
	 */
	public Visualizador(){
		l = new Lista<ArbolSintactico>();
		gp = new GraficadorPanel();
		/* Se inician las variables */
		fx = "";
		svgCad =  "<?xml version='1.0' encoding='UTF-8' ?>\n";
		ancho = 450;
		alto = 300;
		x1 = y1 = -10;
		x2 = y2 = 10;
		numAr=verificaSVG = 0;
		/* Creación de los JPanel */
		panel = new JPanel();
		panel.setBounds(20,515,760,120);
		panel.setLayout(null);
		//panel.setBackground(Color.DARK_GRAY);
		/* Creación JTexField para la función */
		campoTexto = new JTextField("(/ 1 x)");
		campoTexto.setBounds(new Rectangle(40,8,708,21));
		campoTexto.addFocusListener(this);
		campoTexto.requestFocus();
		/* Creación de los JLabel */
		funcion = new JLabel("f(x) =");
		funcion.setBounds(1,8,50,20);
		lAncho = new JLabel("Ancho:");
		lAncho.setBounds(1,40,50,20);
		lAlto = new JLabel("Alto:");
		lAlto.setBounds(110,40,50,20);
		lx1 = new JLabel("x1:");
		lx1.setBounds(210,40,50,20);
		lx2 = new JLabel("x2:");
		lx2.setBounds(295,21,50,60);
		ly1 = new JLabel("y1:");
		ly1.setBounds(380,40,50,20);
		ly2 = new JLabel("y2:");
		ly2.setBounds(465,40,50,20);
		developer = new JLabel("**Developed by Miguel Mendoza-UNAM Facultad de Ciencias 2014**");
		developer.setBounds(210,140,600,20);
		developer.setFont(new Font("Serif",Font.ITALIC,8));
		/* Creación de los JSpinner */
		spAncho = new JSpinner(new SpinnerNumberModel(ancho,50,1000,1));
		spAncho.setBounds(50,40,50,20);
		spAncho.addChangeListener(this);
		spAlto = new JSpinner(new SpinnerNumberModel(alto,50,1000,1));
		spAlto.setBounds(150,40,50,20);
		spAlto.addChangeListener(this);
		spX1 = new JSpinner(new SpinnerNumberModel(x1,-100.0,100.0,.1));
		spX1.setBounds(235,40,50,20);
		spX1.addChangeListener(this);
		spX2 = new JSpinner(new SpinnerNumberModel(x2,-100.0,100.0,.1));
		spX2.setBounds(320,40,50,20);
		spX2.addChangeListener(this);
		spY1 = new JSpinner(new SpinnerNumberModel(y1,-100.0,100.0,.1));
		spY1.setBounds(405,40,50,20);
		spY1.addChangeListener(this);
		spY2 = new JSpinner(new SpinnerNumberModel(y2,-100.0,100.0,.1));
		spY2.setBounds(490,40,50,20);
		spY2.addChangeListener(this);
		/* Creación de los JButton */
		clean = new JButton("Limpiar");
		clean.setBounds(1,90,100,30);
		clean.addActionListener(this);
		graficar = new JButton("Graficar");
		graficar.setBounds(650,90,100,30);
		graficar.addActionListener(this); 	
		svg = new JButton("S V G");
		svg.setBounds(320,90,100,30);
		svg.addActionListener(this);
		svg.setVisible(false);
		/* Se agregan todos los objetos al panel */
		panel.add(funcion);
		panel.add(campoTexto);
		panel.add(spAncho);
		panel.add(spAlto);
		panel.add(spX1);
		panel.add(spX2);
		panel.add(spY1);
		panel.add(spY2);
		panel.add(lAncho);
		panel.add(lAlto);
		panel.add(lx1);
		panel.add(lx2);
		panel.add(ly1);
		panel.add(ly2);
		panel.add(developer);
		panel.add(clean);
		panel.add(graficar);
		panel.add(svg);
		/* Propiedades del frame */
		add(panel);
		add(gp);
		setLayout(null);
		setTitle("Graficador");
		setSize(800,650);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Se invoca cuando una acción ocurre, es decir, cuando se presiona un botón.
	 * @param e , the ActionEvent.
	 */
	 @Override 
	 public void actionPerformed(ActionEvent e) {
   		if(e.getSource() == clean){
   			campoTexto.setText("");
   			spAncho.setValue(450);
   			spAlto.setValue(300);
   			spX1.setValue(-10.0);
   			spX2.setValue(10.0);
   			spY1.setValue(-10.0);
   			spX2.setValue(10.0);
   			l = new Lista<ArbolSintactico>();
   			gp.dibujarNueva(l,x1,x2,y1,y2);
  		  	this.add(gp);
  			this.repaint();
   			svgCad =  "<?xml version='1.0' encoding='UTF-8' ?>\n";
   			verificaSVG = 0;
   			l = new Lista<ArbolSintactico>();
   		}
   		if(e.getSource() == graficar){
   			try{
   				tokenizer = new Tokenizer(fx);
   				Lista<TokenExpresion> tokens = tokenizer.tokenizer();
    			parser = new Parser(tokens);
    			ArbolSintactico arbol = parser.verifica();
  		  		graficador = new GraficadorSVG(arbol,x1,x2,alto,ancho);
  		  		if(verificaSVG == 0){
  		  			svgCad += String.format("<svg width='%d' height='%d' >\n    <rect width='%d' height='%d' style='fill:rgb(255,255,255);stroke-width:3;stroke:rgb(0,0,0)' />",ancho,alto,ancho,alto)+ graficador.generaSVG(y2,y1);
  		  			verificaSVG++;
  		  			numAr++;
  		  		}else
  					svgCad += graficador.generaSVG(y2,y1);
  					
  					svg.setVisible(true);
  					l.agregaFinal(arbol);
  					gp.dibujarNueva(l,x1,x2,y1,y2);
  					this.add(gp);
  					this.repaint();
  	    	}catch(Exception fi){
    			JOptionPane.showMessageDialog(null,fi.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  			}
    	}
    	if(e.getSource() == svg){
    			graficador.archivoSVG(svgCad+="\n</svg>",String.format("grafica%d.svg",numAr));
  		  		svgCad =  "<?xml version='1.0' encoding='UTF-8' ?>\n";
  		  		verificaSVG = 0;
  		  		svg.setVisible(false);
  		  		panel.repaint();
  		  		l = new Lista<ArbolSintactico>();
  		  		gp.dibujarNueva(l,x1,x2,y1,y2);
  		  		this.add(gp);
  				this.repaint();
    	}
    }

    /**
     * Se invoca cuando se modifica el valor de un JSpinner.
     * @param e, the ChangeEvent.
     */
    @Override
    public void stateChanged(ChangeEvent e) { 
    	if(e.getSource() == spAncho){
    		ancho = (Integer) spAncho.getValue();
    	}
    	if(e.getSource() == spAlto){
    		alto = (Integer) spAlto.getValue();
    	}
    	if(e.getSource() == spX1){
    		x1 = (double) spX1.getValue();
    	}
    	if(e.getSource() == spX2){
    		x2 = (double) spX2.getValue();
    	}
    	if(e.getSource() == spY1){
    		y1 = (double) spY1.getValue();
    	}
    	if(e.getSource() == spY2){
    		y2 = (double) spY2.getValue();
    	}			
    }

    /**
     * Se invoca cuando se pierde el foco en el JTextField de la función.
     * @param e the FocusEvent.
     */
    @Override
    public void focusLost(FocusEvent e){
    	if( e.getSource() == campoTexto){
    		fx = ((JTextField)e.getSource()).getText();
    	}
    }

    /**
     * Se invoca cuando se enfoca el JTextField de la función.
     * En este caso el método no realiza ninguna acción.
     * @param e.
     */
    @Override
    public void focusGained(FocusEvent e){
    	/* No se hace nada */
    }

	public static void main(String [] args){
		Visualizador visualizador = new Visualizador();
	}
    /*FIN*/
}