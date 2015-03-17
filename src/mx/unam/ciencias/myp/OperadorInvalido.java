package mx.unam.ciencias.myp;

/**
 * Clase para excepciones de operadores inválidos.
 */
public class OperadorInvalido extends RuntimeException {

	/**
	 * Constructor vacío.
	 */
	public OperadorInvalido(){
		super();
	}
	
	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra
     *                la excepción.
     */
    public OperadorInvalido(String mensaje) {
        super(mensaje);
    }
}