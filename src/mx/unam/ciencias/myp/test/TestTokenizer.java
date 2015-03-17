package mx.unam.ciencias.myp.test;

import mx.unam.ciencias.myp.Tokenizer;
import mx.unam.ciencias.myp.TokenExpresion;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import mx.unam.ciencias.myp.*;
import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;

/**
 * Clase para la prueba unitaria del único método del {@link Tokenizer}.
 */
public class TestTokenizer {

	/* Atributos */
	private Tokenizer tokenizer;
	private Random random;

	/**
	 * Crea un generador de número aleatorios.
	 */
	public TestTokenizer(){
		random = new Random();
	}

	/**
	 * Prueba unitaria para {@link Tokenizer#tokenizer}.
	 */
	@Test public void testTokenizer(){
		tokenizer = new Tokenizer("");
		try{
			tokenizer.tokenizer();
			Assert.fail();
		}catch(FuncionInvalida fi){}
		String func = "";

		String [] funciones = {"sin","cos","tan","csc","cot","sec"};
		String [] parentesis = {"(",")"};
		String [] numeros = {"1","2","3","4","5","6","7","8","9"};
		String [] operadores = {"+","*","-","/","^"};
		String [] variables = {"x","-x"};

		
		int x = random.nextInt(2);
		func+= parentesis[x];
		x = random.nextInt(5);
		func += operadores[x] + ' ';
		func += generaNum(numeros);
		x = random.nextInt(2);
		func += variables[x]+' ';
		x = random.nextInt(6);
		func += funciones[x] + ' ';
		func += generaNum(numeros);
		x = random.nextInt(2);
		func+= parentesis[x];
		tokenizer = new Tokenizer(func);
		try{
			tokenizer.tokenizer();
		}catch(Exception e){
			Assert.fail();
		}
		Lista<TokenExpresion> lista = tokenizer.tokenizer();
		Assert.assertTrue(lista.getLongitud() == 7);
		String [] invalidas ={"a","e","i","o","u","2.2.2","-.x",".x","sen","?","@","#","=","y","--"};
		for(int i =0;i<invalidas.length; i++){
			try{	
				tokenizer=new Tokenizer(invalidas[i]);
				tokenizer.tokenizer();
				Assert.fail();
			}catch(Exception e){
				continue;
			}
		}
	}

	/* Genera una secuencia de números con dígitos aleatorios */
	private String generaNum(String [] numeros){
		String num="";
		int x = 0;
		int longitud = 5 + random.nextInt(10);
		while(longitud >= 0){
			x = random.nextInt(9);
			num += numeros[x];
			longitud--;
		}
		return num + ' ';
	}


}