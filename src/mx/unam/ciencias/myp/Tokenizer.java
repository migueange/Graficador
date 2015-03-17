package mx.unam.ciencias.myp;	

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;

/**
 * Separa en tokens representados en una lista,
 * además analiza si los caracteres son válidos.
 * @author Miguel Mendoza.
 * @version Agosto 2014.
 */
public class Tokenizer {

	/* La función que se va a separar en tokens */
	private String funcion;
	
	/**
	 * Construye un analizador.
	 * @param funcion , la función que se va a evaluar en tokens.
	 */
	public Tokenizer(String funcion){
		this.funcion = funcion;
	}

	/* Clase privada que modela un token */
	private class Token implements TokenExpresion {
		
		/* Atributos */
		public String contenido;
		public Expresion expresion;

		/* Contructor por omisión.
		 * Por omisión no tiene ninguna expresión
		 * y ningún contenido
		 */
		public Token(){
			expresion = Expresion.NINGUNO;
			contenido = "";
		}

		/* Constructor que recibe el contenido y que tipo de expresión es.*/
		public Token(String contenido, Expresion expresion){
			this.contenido = contenido;
			this.expresion = expresion;
		}

		/*
		 * Obtiene el contenido.
		 */
		public String getContenido(){
			return contenido;
		}

		/* Obtiene el tipo de expresión. */
		public Expresion getExpresion(){
			return expresion;
		}

	}

	/**
	 * Analiza la función y agrega los tokens a una lista.
	 * @return una lista de expresiones.
	 * @throws FuncionInvalida si no se ingresó una función válida.
	 * @throws NumberFormatException si encuentra un número inválido.
	 * @throws OperadorInvalido si encuentra un operador inválido.
	 */
	public Lista<TokenExpresion> tokenizer(){
		if(funcion.length() == 0)
			throw new FuncionInvalida("No se ha ingresado ninguna función.");
		Lista<TokenExpresion> tokens = new Lista<TokenExpresion>();
		int i=0;
		tokenizer(tokens,i);
		return tokens;		
	}

	/* Analiza los simbolos más comúnes */
	private void tokenizer(Lista<TokenExpresion> tokens, int i){
		if(funcion.length() <= i)
			return;
		switch(funcion.charAt(i)){
			case ' ':
				tokenizer(tokens,++i);
			break;
			case '(':
				tokens.agregaFinal(new Token("(",Expresion.PARENTESIS_ABIERTO));
				tokenizer(tokens,++i);
			break;
			case ')':
				tokens.agregaFinal(new Token(")",Expresion.PARENTESIS_CERRADO));
				tokenizer(tokens,++i);
			break;
			case 'x':
				tokens.agregaFinal(new Token("x",Expresion.VARIABLE));
				tokenizer(tokens,++i);
			break;
			case '+':
				tokens.agregaFinal(new Token("+",Expresion.OPERADOR));
				tokenizer(tokens,++i);
			break;
			case '-':
				tokenizerNegativo(tokens,++i,"-");
			break;
			case '*':
				tokens.agregaFinal(new Token("*",Expresion.OPERADOR));
				tokenizer(tokens,++i);
			break;
			case '/':
				tokens.agregaFinal(new Token("/",Expresion.OPERADOR));
				tokenizer(tokens,++i);
			break;
			case '^':
				tokens.agregaFinal(new Token("^",Expresion.OPERADOR));
				tokenizer(tokens,++i);
			break;
			case '.':
				tokenizerNumero(tokens,++i,".",1);
			break;
			case 's':
				tokenizerFuncionS(tokens,++i,"s");
			break;
			case 'c':
				tokenizerFuncionC(tokens,++i,"c");
			break;
			case 't':
				tokenizerFuncionT(tokens,++i,"t");
			break;
			case '0':
				tokenizerNumero(tokens,++i,"0",0);
			break;
			case '1':
				tokenizerNumero(tokens,++i,"1",0);
			break;
			case '2':
				tokenizerNumero(tokens,++i,"2",0);
			break;
			case '3':
				tokenizerNumero(tokens,++i,"3",0);
			break;
			case '4':
				tokenizerNumero(tokens,++i,"4",0);
			break;
			case '5':
				tokenizerNumero(tokens,++i,"5",0);
			break;
			case '6':
				tokenizerNumero(tokens,++i,"6",0);
			break;
			case '7':
				tokenizerNumero(tokens,++i,"7",0);
			break;
			case '8':
				tokenizerNumero(tokens,++i,"8",0);
			break;
			case '9':
				tokenizerNumero(tokens,++i,"9",0);
			break;
			default:
				throw new FuncionInvalida("Símbolo no reconocido: \"" + funcion.charAt(i) + "\"");
		}
	}

	/* Analiza los numeros ya que pueden ser tan largas como la memoria soporte */
	private void tokenizerNumero(Lista<TokenExpresion> tokens, int i, String num,int puntos){
		if(funcion.length() == i){
			tokens.agregaFinal(new Token(num,Expresion.NUMERO));
			return;
		}
		int p;
		if(puntos == 0)
			p=0;
		else
			p=1;
		switch(funcion.charAt(i)){
			case ' ':
				tokens.agregaFinal(new Token(num,Expresion.NUMERO));
				tokenizer(tokens,++i);
			break;
			case ')':
				tokens.agregaFinal(new Token(num,Expresion.NUMERO));
				tokenizer(tokens,i);
			break;
			case '.':
				if(p != 0)
					throw new NumberFormatException("Se encontraron dos puntos decimales en un mismo número.");
				tokenizerNumero(tokens,++i,num+=".",1);
			break;
			case '0':
				tokenizerNumero(tokens,++i,num+="0",p);
			break;
			case '1':
				tokenizerNumero(tokens,++i,num+="1",p);
			break;
			case '2':
				tokenizerNumero(tokens,++i,num+="2",p);
			break;
			case '3':
				tokenizerNumero(tokens,++i,num+="3",p);
			break;
			case '4':
				tokenizerNumero(tokens,++i,num+="4",p);
			break;
			case '5':
				tokenizerNumero(tokens,++i,num+="5",p);
			break;
			case '6':
				tokenizerNumero(tokens,++i,num+="6",p);
			break;
			case '7':
				tokenizerNumero(tokens,++i,num+="7",p);
			break;
			case '8':
				tokenizerNumero(tokens,++i,num+="8",p);
			break;
			case '9':
				tokenizerNumero(tokens,++i,num+="9",p);
			break;
			default:
				throw new NumberFormatException("Número invalido: \""+ funcion.charAt(i)+ "\"");
		}
	}

	/* Analiza las variables o numeros negativos */
	private void tokenizerNegativo(Lista<TokenExpresion> tokens, int i, String num){
		if(funcion.length() == i)
			return;
		switch(funcion.charAt(i)){
			case ' ':
				tokens.agregaFinal(new Token("-",Expresion.OPERADOR));
				tokenizer(tokens,++i);
			break;
			case 'x':
				tokens.agregaFinal(new Token(num+="x",Expresion.VARIABLE));
				tokenizer(tokens,++i);
			break;
			case '.':
				tokenizerNumero(tokens,++i,num+=".",1);
			break;
			case '0':
				tokenizerNumero(tokens,++i,num+="0",0);
			break;
			case '1':
				tokenizerNumero(tokens,++i,num+="1",0);
			break;
			case '2':
				tokenizerNumero(tokens,++i,num+="2",0);
			break;
			case '3':
				tokenizerNumero(tokens,++i,num+="3",0);
			break;
			case '4':
				tokenizerNumero(tokens,++i,num+="4",0);
			break;
			case '5':
				tokenizerNumero(tokens,++i,num+="5",0);
			break;
			case '6':
				tokenizerNumero(tokens,++i,num+="6",0);
			break;
			case '7':
				tokenizerNumero(tokens,++i,num+="7",0);
			break;
			case '8':
				tokenizerNumero(tokens,++i,num+="8",0);
			break;
			case '9':
				tokenizerNumero(tokens,++i,num+="9",0);
			break;
			default: 
				throw new OperadorInvalido("Símbolo no reconocido: \"-"+ funcion.charAt(i) + "\"");
		}
	}

	/* Analiza las funciones que empienzan con 's' */
	private void tokenizerFuncionS(Lista<TokenExpresion> tokens,int i,String func){
		if(funcion.length() <= i)
				return;
			switch(funcion.charAt(i)){
				case 'i':
					tokenizerFuncionS(tokens,++i,func+="i");
				break;
				case 'e':
					tokenizerFuncionS(tokens,++i,func+="e");
				break;
				case 'n':
					//++i;
					if(!func.equals("si")){
						func+="n";
						throw new FuncionInvalida("Función no reconocida: \""+ func+ "\"");
					}
					tokens.agregaFinal(new Token(func+="n",Expresion.FUNCION));
					tokenizer(tokens,++i);
				break;
				case 'c':
					
					if(!func.equals("se")){
						func+="c";
						throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
					}
					tokens.agregaFinal(new Token(func+="c",Expresion.FUNCION));
					tokenizer(tokens,++i);				
				break;
				default:
					func += ""+funcion.charAt(i);
					throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
			}
	}

	/* Analiza las funciones que empiezan con 't' */
	private void tokenizerFuncionT(Lista<TokenExpresion> tokens,int i, String func){
		if(funcion.length() <= i)
			return;
		switch(funcion.charAt(i)){
			case 'a':
				tokenizerFuncionT(tokens,++i,func += "a");
			break;
			case 'n':
				if(!func.equals("ta")){
					func+="n";
					throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
				}
				tokens.agregaFinal(new Token(func+="n",Expresion.FUNCION));
				tokenizer(tokens,++i);
			break;
			default:
				func += ""+funcion.charAt(i);
				throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
		}
	}

	/* Analizan las funciones que empiezan con 'c' */
	private void tokenizerFuncionC(Lista<TokenExpresion> tokens,int i, String func){
		if(funcion.length() <= i)
			return;
		switch(funcion.charAt(i)){
			case 'o':
				tokenizerFuncionC(tokens,++i,func +="o");
			break;
			case 's':
				if(func.equals("c")){
					tokenizerFuncionC(tokens,++i,func+="s");
				}else{ if(!func.equals("co")){
							 func+="s";
							throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
						}
					tokens.agregaFinal(new Token(func+="s",Expresion.FUNCION));
					tokenizer(tokens,++i);
				}
			break;
			case 't':
				if(!func.equals("co")){
					func+="t";
					throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
				}
				tokens.agregaFinal(new Token(func+="t",Expresion.FUNCION));
				tokenizer(tokens,++i);
			break;
			case 'c':
				if(!func.equals("cs")){
					func+="c";
					throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
				}
				tokens.agregaFinal(new Token(func+="c",Expresion.FUNCION));
				tokenizer(tokens,++i);
			break;
			default:
				func += "" + funcion.charAt(i);
				throw new FuncionInvalida("Función no reconocida: \""+ func +"\"");
		}
	}


	/**
	 * Imprime la funcion recibida a partir de la lista de tokens.
	 * @param tokens, una lista de {@link TokenExpresion}.
	 */
	public void imprimeFuncion(Lista<TokenExpresion> tokens){
		for(TokenExpresion t: tokens)
			System.out.print(t.getContenido() + " ");
	}

}