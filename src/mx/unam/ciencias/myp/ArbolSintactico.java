package mx.unam.ciencias.myp;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import java.util.NoSuchElementException;
import mx.unam.ciencias.myp.VerticeArbolBinario;

/**
 * <p>Clase que modela un árbol sintactico para poder evaluar la función.</p>
 * <p>El árbol -NO- analiza que la función cumpla con las condiciones de la gramática,
 * el encargado de realizar dicha tarea es la clase {@link Parser}, por lo tanto el árbol
 * supone que la lista de {@link TokenExpresion} que recibe, cumple con la gramática 
 * para notación prefija.</p>
 *
 * @author Miguel Mendoza.
 * @version Agosto 2014.
 * 
 */
public class ArbolSintactico {
	
	/*
     * Clase interna protegida para vértices.
     */
    private class Vertice implements VerticeArbolBinario {
        /* El elemento del vértice. */
        public TokenExpresion token;
        /* El padre del vértice. */
        public Vertice padre;
        /* El izquierdo del vértice. */
        public Vertice izquierdo;
        /* El derecho del vértice. */
        public Vertice derecho;

        /* Constructor único que recibe un elemento.*/
        public Vertice(TokenExpresion token) {
            this.token = token;
        }

        /* Regresa una representación en cadena del vértice.*/
        public String toString() {
            return token.getContenido();
        }

        /* Nos dice si el vértice tiene un padre.*/
        @Override
        public boolean hayPadre() {
            return padre != null;
        }

        /* Nos dice si el vértice tiene un izquierdo.*/
        @Override
        public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /* Nos dice si el vértice tiene un derecho.*/
        @Override
        public boolean hayDerecho() {
            return derecho != null;
        }

        /* Regresa el padre del vértice.*/
        @Override 
        public VerticeArbolBinario getPadre(){ 
            if(hayPadre())
                return padre;
                throw new NoSuchElementException();
        }

        /* Regresa el izquierdo del vértice.*/
        @Override
        public VerticeArbolBinario getIzquierdo() {
            if(hayIzquierdo())
                return izquierdo;
                throw new NoSuchElementException();
        }

        /* Regresa el derecho del vértice.*/
        @Override
        public VerticeArbolBinario getDerecho() {
            if(hayDerecho())
            return derecho;
            throw new NoSuchElementException();
        }

        /* Regresa el elemento al que apunta el vértice.*/
        @Override
        public TokenExpresion get() {
            return token;
        }
    }

    /* La raíz del árbol. */
    private Vertice raiz;
    /* El número de elementos */
    private int elementos;
    /* Lista de Tokens */
    private TokenExpresion [] tokens;
    /* Indice */
    private static int i;

    /**
     * Construye un árbol con cero elementos y una lista de {@link TokenExpresion}.
     * @param listaTokens , una lista con la cual se construirá el árbol.
     */
    public ArbolSintactico (Lista<TokenExpresion> listaTokens) {
        raiz = null;
        elementos = 0;
        i = 0;
        tokens = new TokenExpresion[listaTokens.getLongitud()];
        for(int i = 0; i < tokens.length;i++)
            tokens[i] = listaTokens.get(i);
    }

    /**
     * Regresa la profundidad del árbol. La profundidad de un árbol
     * es la longitud de la ruta más larga entre la raíz y una hoja.
     * @return la profundidad del árbol.
     */
    public int profundidad() {
        return profundidad(raiz) - 1;
    }

    /*Método auxiliar recursivo para obtener la profundidad*/
    private int profundidad(Vertice vertice){ 
        if (vertice == null)
            return 0;
        int li = profundidad(vertice.izquierdo);
        int ld = profundidad(vertice.derecho);
        if(li > ld)
            return 1 + li;
            return 1 + ld;
    }

    /**
     * Regresa el número de elementos en el árbol. El número de
     * elementos es el número de elementos que se han agregado al
     * árbol.
     * @return el número de elementos en el árbol.
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Agrega los tokens de la Lista de {@link TokenExpresion } al árbol.
     * Agrega según las reglas de la gramática definida para notación prefija.
     * @throws FuncionInvalida si se ingresa una función mal escrita.
     */
    public void agrega(){
    	if(tokens.length <3)
    		throw new NoSuchElementException("Función inválida.");
    	agrega(raiz,tokens[i]);
    }

    /* Método auxiliar recursivo para agregar */
    public void agrega(Vertice vertice,TokenExpresion token){
        if(raiz == null){
                if(esParentesisAbierto(token)){
                    agrega(vertice,tokens[++i]);
                    return;
                }
                if(esHoja(token)){
                    raiz = new Vertice(token);
                    elementos++;
                    return;
                }
                if(esFuncion(token)){
                    raiz = new Vertice(token);
                    elementos++;
                    raiz.izquierdo = new Vertice(null);
                    raiz.izquierdo.padre = raiz;
                    agrega(raiz.izquierdo,tokens[++i]);
                    return;
                }
                if(esOperador(token)){
                    raiz = new Vertice(token);
                    elementos++;
                    elementos++;
                    raiz.izquierdo = new Vertice(null);
                    raiz.izquierdo.padre = raiz;
                    raiz.derecho = new Vertice(null);
                    raiz.derecho.padre = raiz;
                    agrega(raiz.izquierdo,tokens[++i]);
                    agrega(raiz.derecho,tokens[++i]);
                    return;
                }
            }
        if(esParentesisCerrado(token)){
            agrega(vertice,tokens[++i]);
            return;
        }
        if(esParentesisAbierto(token)){
            agrega(vertice,tokens[++i]);
            return;
        }
        if(esHoja(token)){
            vertice.token = token;
            return;
        }
        if(esOperador(token)){
            vertice.token = token;
            vertice.izquierdo = new Vertice(null);
            vertice.izquierdo.padre = vertice;
            elementos++;
            vertice.derecho = new Vertice(null);
            vertice.derecho.padre = vertice;
            elementos++;
            agrega(vertice.izquierdo,tokens[++i]);
            agrega(vertice.derecho,tokens[++i]);
            return;
        }
        if(esFuncion(token)){
            vertice.token = token;
            vertice.izquierdo = new Vertice(null);
            vertice.izquierdo.padre = vertice;
            elementos++;
            agrega(vertice.izquierdo,tokens[++i]);
            return;
        }
    }

    /* Método para agregar al vertice izquierdo de un vértice.*/
    private void agregaIzquierdo(Vertice vertice, TokenExpresion token){
        vertice.izquierdo = new Vertice(token);
        vertice.izquierdo.padre = vertice;
        elementos++;
    }

    /* Método para agregar al vertice izquierdo de un vértice.*/
    private void agregaDerecho(Vertice vertice, TokenExpresion token){
        vertice.derecho = new Vertice(token);
        vertice.derecho.padre = vertice;
        elementos++;
    }

    /**
     * El árbol se evalua a si mismo en cierto valor para x.
     * @param x, el valor de x;
     * @return el valor de f(x).
     */
    public double evalua(double x){
 		double valor=0.0;
    	if(raiz.izquierdo == null && raiz.derecho == null){
    		switch(raiz.token.getExpresion()){
    			case VARIABLE:
    				if(raiz.token.getContenido().equals("-x"))
    					return x*(-1);
    					return x;
    			case NUMERO:
    				return Double.parseDouble(raiz.token.getContenido());
    		}
    	}
    	return evalua(x,buscaHojaIzquierda(raiz),0);
    }
    /* Auxiliar para evaluar una función no trivial */
    private double evalua(double x , Vertice vertice, double resultado){
    	double x1=0,x2=0;
    	switch(vertice.token.getExpresion()){
    		case OPERADOR:
    			switch(vertice.izquierdo.token.getExpresion()){
    				case NUMERO:
    					x1 = Double.parseDouble(vertice.izquierdo.token.getContenido());
    				break;
    				case VARIABLE:
    					if(vertice.izquierdo.token.getContenido().equals("x"))
    						x1 = x;
    					else
    						x1 = x*(-1);	
    				break;
    				case OPERADOR:
    					x1 = resultado;
    				break;
    				case FUNCION: 
    					x1 = resultado;
    				break;
    			}
    			switch(vertice.derecho.token.getExpresion()){
    				case NUMERO:
    					x2 = Double.parseDouble(vertice.derecho.token.getContenido());
    				break;
    				case VARIABLE:
    					if(vertice.derecho.token.getContenido().equals("x"))
    						x2 = x;
    					else
    						x2 = x*(-1);
    				break;	
    				case OPERADOR:
    					x2 = evalua(x,buscaHojaIzquierda(vertice.derecho),resultado);
    				break;
    				case FUNCION:
    					x2 = evalua(x,buscaHojaIzquierda(vertice.derecho),resultado);
    				break;
    			}
    			switch(vertice.token.getContenido()){
    				case "+":
    					resultado = x1 + x2;
    				break;
    				case "-":
    					resultado = x1 - x2;
    				break;
    				case "^":
    					resultado = Math.pow(x1,x2);
    				break;
    				case "*":
    					resultado = x1 * x2;
    				break;
    				case "/":
    					if(x2 == 0)
    						throw new FuncionInvalida("La función se indetermina");
    					resultado = x1/x2;
    				break;
    			}
    		if(vertice.padre == null)
    			return resultado;
    		if((vertice.padre.derecho == vertice) && esOperador(vertice.padre.token))
    			return resultado;
    			return evalua(x,vertice.padre,resultado);
    		case FUNCION:
    			switch(vertice.izquierdo.token.getExpresion()){
    				case NUMERO:
    					x1 = Double.parseDouble(vertice.izquierdo.token.getContenido());
    				break;
    				case VARIABLE:
    					if(vertice.izquierdo.token.getContenido().equals("x"))
    						x1 = x;
    					else
    						x1 = x*(-1);
    				break;
    				case OPERADOR:
    					x1 = resultado;
    				break;
    				case FUNCION:
    					x1 = resultado;
    				break;
    			}
    			switch(vertice.token.getContenido()){
    				case "sin":
    					resultado = Math.sin(x1);
    				break;
    				case "cos":
    					resultado = Math.cos(x1);
    				break;
    				case "tan":
    					resultado = Math.tan(x1);
    				break;
    				case "cot":
    					if(Math.tan(x1) == 0)
    						throw new FuncionInvalida("La función se indetermina.");
    					resultado = 1/Math.tan(x1);
    				break;
    				case "sec":    					
    					if(Math.cos(x1) == 0)
    						throw new FuncionInvalida("La función se indetermina.");
    					resultado = 1/Math.cos(x1);
    				break;
    				case "csc":
    				    if(Math.sin(x1) == 0)
    						throw new FuncionInvalida("La función se indetermina.");
    					resultado = 1/Math.sin(x1);
    				break;
    			}
    		if(vertice.padre == null)
    			return resultado;
    		if((vertice.padre.derecho == vertice) && esOperador(vertice.padre.token))
    			return resultado;
    		return evalua(x,vertice.padre,resultado);
    	}
    	return resultado;
    }

    /* Busca la hoja izquierda más profunda y regresa su padre,
     * es decir, su operador.
     */
    private Vertice buscaHojaIzquierda(Vertice v){
    	Vertice vertice = v;
    	while(vertice != null){
    		if(esHoja(vertice.token))
    			break;
    		vertice = vertice.izquierdo;
    	}
    	return vertice.padre;
    }

    /* Nos dice si el token es un paréntesis abierto. */
    private boolean esParentesisAbierto(TokenExpresion token){
    	return token.getExpresion() == Expresion.PARENTESIS_ABIERTO;
    }

    /* Nos dice si el token es un paréntesis cerrado. */
    private boolean esParentesisCerrado(TokenExpresion token){
    	return token.getExpresion() == Expresion.PARENTESIS_CERRADO;
    }

    /* Nos dice si el token es un número.*/
    private boolean esNumero(TokenExpresion token){
    	return token.getExpresion() == Expresion.NUMERO;
    }

    /* Nos dice si el token es una variable. */
    private boolean esVariable(TokenExpresion token){
    	return token.getExpresion() == Expresion.VARIABLE;
    }

    /* Nos dice si el token es una función. */
    private boolean esFuncion(TokenExpresion token){
    	return token.getExpresion() == Expresion.FUNCION;
    }

    /* Nos dice si el token es un operador. */
    private boolean esOperador(TokenExpresion token){
    	return token.getExpresion() == Expresion.OPERADOR;
    }

    /*
     * Nos dice si el token es una hoja, es decir,
     * si es un número o una variable...
     */
    private boolean esHoja(TokenExpresion token){
    	if(token.getExpresion() == Expresion.NUMERO)
    		return true;
    	if(token.getExpresion() == Expresion.VARIABLE)
    		return true;
    	return false;
    }

    /*************************************************************************************/

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        /* Necesitamos la profundidad para saber cuántas ramas puede
           haber. */
        if (elementos == 0)
            return "";
        int p = profundidad() + 1;
        /* true == dibuja rama, false == dibuja espacio. */
        boolean[] rama = new boolean[p];
        for (int i = 0; i < p; i++)
            /* Al inicio, no dibujamos ninguna rama. */
            rama[i] = false;
        String s = aCadena(raiz, 0, rama);
        return s.substring(0, s.length()-1);
    }

    /* Método auxiliar recursivo que hace todo el trabajo. */
    private String aCadena(Vertice vertice, int nivel, boolean[] rama) {
        /* Primero que nada agregamos el vertice a la cadena. */
        String s = vertice + "\n";
        /* A partir de aquí, dibujamos rama en este nivel. */
        rama[nivel] = true;
        if (vertice.izquierdo != null && vertice.derecho != null) {
            /* Si hay vertice izquierdo Y derecho, dibujamos ramas o
             * espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo izquierdo. */
            s += "├─›";
            /* Recursivamente dibujamos el hijo izquierdo y sus
               descendientes. */
            s += aCadena(vertice.izquierdo, nivel+1, rama);
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo derecho. */
            s += "└─»";
            /* Como ya dibujamos el último hijo, ya no hay rama en
               este nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo derecho y sus
               descendientes. */
            s += aCadena(vertice.derecho, nivel+1, rama);
        } else if (vertice.izquierdo != null) {
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo izquierdo. */
            s += "└─›";
            /* Como ya dibujamos el último hijo, ya no hay rama en
               este nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo izquierdo y sus
               descendientes. */
            s += aCadena(vertice.izquierdo, nivel+1, rama);
        } else if (vertice.derecho != null) {
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo derecho. */
            s += "└─»";
            /* Como ya dibujamos el último hijo, ya no hay rama en
               este nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo derecho y sus
               descendientes. */
            s += aCadena(vertice.derecho, nivel+1, rama);
        }
        return s;
    }

    /* Dibuja los espacios (incluidas las ramas, de ser necesarias)
       que van antes de un vértice. */
    private String espacios(int n, boolean[] rama) {
        String s = "";
        for (int i = 0; i < n; i++)
            if (rama[i])
                /* Rama: dibújala. */
                s += "│  ";
            else
                /* No rama: dibuja espacio. */
                s += "   ";
        return s;
    }

}//FIN