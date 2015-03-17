package mx.unam.ciencias.myp;

/**
 * Interfaz para v&eacute;rtices de {@link ArbolSintactico}.
 */
public interface VerticeArbolBinario {

    /**
     * Nos dice si el v&eacute;rtice tiene v&eacute;rtice padre.
     * @return <tt>true</tt> si el v&eacute;rtice tiene v&eacute;rtice padre,
     *         <tt>false</tt> en otro caso.
     */
    public boolean hayPadre();

    /**
     * Nos dice si el v&eacute;rtice tiene v&eacute;rtice izquierdo.
     * @return <tt>true</tt> si el v&eacute;rtice tiene v&eacute;rtice izquierdo,
     *         <tt>false</tt> en otro caso.
     */
    public boolean hayIzquierdo();

    /**
     * Nos dice si el v&eacute;rtice tiene v&eacute;rtice derecho.
     * @return <tt>true</tt> si el v&eacute;rtice tiene v&eacute;rtice derecho,
     *         <tt>false</tt> en otro caso.
     */
    public boolean hayDerecho();

    /**
     * Regresa el v&eacute;rtice padre del v&eacute;rtice.
     * @return el v&eacute;rtice padre del v&eacute;rtice.
     */
    public VerticeArbolBinario getPadre();

    /**
     * Regresa el v&eacute;rtice izquierdo del v&eacute;rtice.
     * @return el v&eacute;rtice izquierdo del v&eacute;rtice.
     */
    public VerticeArbolBinario getIzquierdo();

    /**
     * Regresa el v&eacute;rtice derecho del v&eacute;rtice.
     * @return el v&eacute;rtice derecho del v&eacute;rtice.
     */
    public VerticeArbolBinario getDerecho();

    /**
     * Regresa el elemento que contiene el v&eacute;rtice.
     * @return el elemento que contiene el v&eacute;rtice.
     */
    public TokenExpresion get();

}
