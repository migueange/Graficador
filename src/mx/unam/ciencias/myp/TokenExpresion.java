package mx.unam.ciencias.myp;	

/**
 * Interfaz para tokens de una función.
 * Un token nos puede dar:
 * OPERADOR,
 * NUMERO, 
 * FUNCION,
 * PARENTESIS_ABIERTO,
 * PARENTESIS_CERRADO o
 * VARIABLE.
 */
public interface TokenExpresion {
	
	/**
	 * Regresa el contenido del token.
	 * @return String contenido.
	 */
	public String getContenido();

	/**
	 * Regresa el tipo de expresión.
	 * @return Expresion una expresión de la gramática.
	 */
	public Expresion getExpresion();

}