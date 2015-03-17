package mx.unam.ciencias.myp.test;

import mx.unam.ciencias.myp.*;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import mx.unam.ciencias.myp.*;
import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;

/**
 * Clase para la prueba unitaria del único método del {@link Parser}.
 */
public class TestParser {
	
	/* Atributos */
	private Tokenizer tokenizer;
	private Parser parser;
	private Random random;

	/**
	 * Crea un generador de número aleatorios.
	 */
	public TestParser(){
		random = new Random();
	}

	/**
	 * Prueba unitaria para {@link Parser#verifica}.
	 */
	@Test public void testVerifica(){
		String func = "(/ (+ x (- 29 -x)) (sin(^ x 2)))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
		}catch(Exception e){
			Assert.fail();
		}
		func = "(((((/ ((+ x ((- 29 -x)))) (sin(^ x 2)))))))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
		}catch(Exception e){
			Assert.fail();
		}
		func = "()";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
			Assert.fail();
		}catch(Exception e){}
		func = "(x)";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
		}catch(Exception e){
			Assert.fail();
		}
		func = "(7252.922)";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
		}catch(Exception e){
			Assert.fail();
		}
		//Parentesis desbalanceados
		func = "(/ (+ x (- 29 -x)) (sin(^ x 2))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
			Assert.fail();
		}catch(Exception e){}
	
		func = "(/ (+ x )- 29 -x)) (sin(^ x 2))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();
			Assert.fail();
		}catch(Exception e){}
		func ="(sin (tan ( cos (cot ( csc ( sec ( tan(x))))))))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();		
		}catch(Exception e){
			Assert.fail();
		}
		func ="(sin (tan ( cos (cot ( csc ( sec ( tan(*))))))))";
		try{
			tokenizer = new Tokenizer(func);
			parser = new Parser(tokenizer.tokenizer());
			parser.verifica();		
			Assert.fail();
		}catch(Exception e){}
	}
}