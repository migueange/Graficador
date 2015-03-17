package mx.unam.ciencias.myp;

/**
 * Clase para excepciones de números inválidos.
 */
public class NumeroInvalido extends NumberFormatException {

	/**
	 * Constructor vacío.
	 */
	public NumeroInvalido(){
		super();
	}
	
	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra
     *                la excepción.
     */
    public NumeroInvalido(String mensaje) {
        super(mensaje);
    }
}