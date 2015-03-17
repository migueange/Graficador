package mx.unam.ciencias.myp;

/**
 * Clase para excepciones de parentesis inválidos.
 */
public class ParentesisInvalidos extends ArrayIndexOutOfBoundsException {

	/**
	 * COnstructor vacío.
	 */
	public ParentesisInvalidos(){
		super();
	}
	
	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra
     *                la excepción.
     */
    public ParentesisInvalidos(String mensaje) {
        super(mensaje);
    }
}

