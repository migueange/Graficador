package mx.unam.ciencias.myp;

import java.util.NoSuchElementException;

/**
 * Clase para excepciones de funcion inválida.
 */
public class FuncionInvalida extends NoSuchElementException {

	/**
	 * Constructor vacío.
	 */
	public FuncionInvalida(){
		super();
	}
	
	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra
     *                la excepción.
     */
    public FuncionInvalida(String mensaje) {
        super(mensaje);
    }
}