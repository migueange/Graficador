package mx.unam.ciencias.myp;

import mx.unam.ciencias.edd.Lista; 
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.Pila;

/**
 * <p>Analizador sintáctico que verifica que una lista de {@link TokenExpresion }
 * que recibe cumpla con las condiciones de la gramática para notación prefija.<p>
 *
 * @author Miguel Mendoza.
 * @version Agosto 2014.
 *
 */
public class Parser {

	/* Lista de Tokens */
	private Lista<TokenExpresion> tokens;

	/**
	 * Construye un parser con una lista de {@link TokenExpresion } que se analizará.
	 * @param listaTokens , una lista de {@link TokenExpresion}.
	 */
	public Parser(Lista<TokenExpresion> listaTokens){
		tokens = listaTokens;
	}	

	/**
	 * Analiza que la función cumpla con la gramática.
	 * @return ArbolDerivacion un árbol de derivación para evaluar la función, 
	 * lo regresa si cumple con las condiciones de la gramática para notación
	 * prefija.
	 * @throws ParentesisInvalidos si los parentesis no están balanceados.
	 * @throws FuncionInvalida si la funcion no cumple con la gramática.
	 */
	public ArbolSintactico verifica(){
		int expresion = 1 ,pC = 2,analiza;
		Pila<Integer> pila = new Pila<Integer>();
		pila.mete(expresion);
		for(TokenExpresion token : tokens){
			if(pila.esVacia())
				throw new ParentesisInvalidos("Los parentesis no están balanceados.");
			analiza = pila.saca();
			if(analiza == expresion){
				if(esParentesisCerrado(token))
					throw new FuncionInvalida("Los parentesis están mal balanceados o falta algún operador, número o variable.");
				if(esParentesisAbierto(token)){
					pila.mete(pC);
					pila.mete(expresion);
					continue;
				}
				if(esOperador(token)){
					pila.mete(expresion);
					pila.mete(expresion);
					continue;
				}
				if(esFuncion(token)){
					pila.mete(expresion);
					continue;
				}
				if(esNumero(token))
					continue;
				if(esVariable(token))
					continue;
			}
			if(analiza == pC){
				if(!esParentesisCerrado(token))
					throw new FuncionInvalida("Los parentesis están mal balanceados o falta algún operador,número o variable.");
				continue;
			}
		}
		if(!pila.esVacia())
			throw new FuncionInvalida("Los parentesis están mal balanceados o falta algún operador, número o variable.");
		return crearArbol();

	}

	/* Construye un árbol de derivación con la lista de Tokens. */
	private ArbolSintactico crearArbol(){
		ArbolSintactico arbol = new ArbolSintactico(tokens);
		arbol.agrega();
		return arbol;
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

}