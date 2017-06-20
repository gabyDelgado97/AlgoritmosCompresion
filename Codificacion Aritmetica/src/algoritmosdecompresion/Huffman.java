/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosdecompresion;

/**
 *
 * @author kamila
 */

import java.io.*;
import java.util.*;

public class Huffman {

    public static String[] huffMan = new String[256];
    public static String[][] huffMan2 = new String[256][256];

    /* Función que recibe la cadena a codificar y el array que contiene
		 * los resultados de la codificación por huffman para la Fuente1.
     */
    public static String crearMensajeHuffman1(StringBuffer mensaje) {
        String mensajeHuffman = "";
        for (int i = 0; i <= mensaje.length() - 1; i++) {
            mensajeHuffman = mensajeHuffman + huffMan[mensaje.charAt(i)];
        }
        return (mensajeHuffman);
    }

    /* Función que recibe la cadena a codificar y el array que contiene
		 * los resultados de la codificación por huffman para la Fuente2.
     */
    public static String crearMensajeHuffman2(StringBuffer mensaje) {
        String mensajeHuffman2 = "";

        for (int i = 0; i <= mensaje.length() - 1; i += 2) {
            mensajeHuffman2 = mensajeHuffman2 + huffMan2[mensaje.charAt(i)][mensaje.charAt(i + 1)];
        }
        return (mensajeHuffman2);
    }

    /* Función que recibe la cadena a codificar y lo codifica en Ascii Extendido.*/
    public static String crearMensajeAscii(StringBuffer mensaje) {
        String mensajeAscii = "";
        for (int i = 0; i <= mensaje.length() - 1; ++i) {
            /* Calculamos la longitud del mensaje pasado a binario, ya que si contiene
            	 * menos de 8 dígitos binarios, tendremos que añadirle ceros.
             */
            int longBinario = Integer.toBinaryString(mensaje.charAt(i)).length();

            while (longBinario < 8) {
                mensajeAscii = mensajeAscii + "0";
                longBinario++;
            }
            mensajeAscii = mensajeAscii + Integer.toBinaryString(mensaje.charAt(i));
        }
        return (mensajeAscii);
    }

    /**
     * ProcessFile es la función encargada de crear el árbol de Nodos de Huffman
     * con Fuente1, recibe la cadena a codificar, y el array que almacena las
     * frecuencias de cada letra en el mensaje. Devuelve finalmente el array de
     * cadenas, que devuelve la tabla de caracteres con su valor en Huffman con
     * Fuente1.
     */
    public static String[] processFile(String fileContents, int[] frequency) {
        TreeSet<NodoHF1> arboles = new TreeSet<NodoHF1>();

        /* Hallo las frecuencias */
        for (int i = 0; i < fileContents.length(); i++) {
            char ch = fileContents.charAt(i);
            ++frequency[ch];
        }
        /* Creo el árbol inicial de las letras cuya frecuencia es mayor que 0
                 * es decir, se encuentra en el mensaje.
         */
        for (int i = 0; i < 255; i++) {
            if (frequency[i] > 0) {
                NodoHF1 n = new NodoHF1((char) (i), frequency[i]);
                arboles.add(n);
            }
        }
        /* Bucle while que crea el árbol de Huffman.*/
        while (arboles.size() > 1) {
            NodoHF1 tree1 = (NodoHF1) arboles.first();
            arboles.remove(tree1);
            NodoHF1 tree2 = (NodoHF1) arboles.first();
            arboles.remove(tree2);

            NodoHF1 merged = new NodoHF1(tree1, tree2);
            arboles.add(merged);
        }
        /* Aquí almacenamos el resultado del árbol en un array */
        if (arboles.size() > 0) {
            NodoHF1 theTree = (NodoHF1) arboles.first();
            NodoHF1.printTree(theTree, huffMan);
        } else {
            System.out.println("The file didn't contain useful characters.");
        }
        return (huffMan);
    }

    /**
     * ProcessFile2 es la función encargada de crear el árbol de Nodos de
     * Huffman con Fuente2, recibe la cadena a codificar, y el array que
     * almacena las frecuencias de cada letra en el mensaje. Devuelve finalmente
     * el array bidimensional de cadenas, que devuelve la tabla de caracteres
     * con su valor en Huffman con Fuente2.
     */
    public static String[][] processFile2(int[][] frecuencia, int[] fuente) {
        TreeSet<NodoHF2> arboles = new TreeSet<NodoHF2>();
        String letras;
        /* Hallo las frecuencias */
        for (int j = 0; j < 256; j++) {
            for (int k = 0; k < 256; k++) {
                frecuencia[j][k] = fuente[j] * fuente[k];
            }

        }
        /* Creo el árbol inicial de las parejas de letras cuya frecuencia es 
             * mayor que 0 es decir, se encuentra en el mensaje.
         */
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                if (frecuencia[i][j] > 0) {
                    letras = (char) i + "" + (char) j;
                    NodoHF2 n = new NodoHF2(letras, frecuencia[i][j]);
                    arboles.add(n);
                }
            }
        }

        /* Bucle while que crea el árbol de Huffman.*/
        while (arboles.size() > 1) {
            NodoHF2 tree1 = (NodoHF2) arboles.first();
            arboles.remove(tree1);
            NodoHF2 tree2 = (NodoHF2) arboles.first();
            arboles.remove(tree2);
            NodoHF2 merged = new NodoHF2(tree1, tree2);
            arboles.add(merged);
        }
        /* Aquí almacenamos el resultado del árbol en un array */
        if (arboles.size() > 0) {
            NodoHF2 theTree = (NodoHF2) arboles.first();
            NodoHF2.printTree(theTree, huffMan2);
        } else {
            System.out.println("The file didn't contain useful characters.");
        }
        return (huffMan2);
    }

}

/* Esta clase es la que crea las ramas del árbol de Huffman con fuente 1 */
class NodoHF1
        implements Comparable {

    private int valor; // Almacena el valor de la rama
    private char contenido; // Almacena el carácter 
    private NodoHF1 left; // Almacena la rama izquierda
    private NodoHF1 right; // Almacena la rama derecha

    /* Constructor que crea el nodo al principio */
    public NodoHF1(char contenido, int valor) {
        this.contenido = contenido;
        this.valor = valor;
    }

    /* Constructor que crea el nodo pasándole las ramas izquierda y derecha */
    public NodoHF1(NodoHF1 left, NodoHF1 right) {
        /* Si la letra de la izquierda, es alfabéticamente anterior a la de la derecha
		         * nos quedamos con la izquierda, y sino, con la derecha. Es decir, almacenamos 
		         * en la siguiente rama, el carácter posicionado más anteriormente.
         */
        this.contenido = (left.contenido < right.contenido) ? left.contenido : right.contenido;
        // El valor ahora es la suma de los 2 valores.
        this.valor = left.valor + right.valor;
        this.left = left;
        this.right = right;
    }

    /* Función que compara 2 nodos */
    public int compareTo(Object arg) {
        NodoHF1 other = (NodoHF1) arg;

        if (this.valor == other.valor) {
            return this.contenido - other.contenido;
        } else {
            return this.valor - other.valor;
        }
    }


    /* Función que crea el array de cadenas que contiene la codificación
		 * de Huffman que se ha creado.
     */
    private void printNodoHF1(String path, String[] hf) {
        if ((left == null) && (right == null)) {
            hf[(int) contenido] = path;
        }

        if (left != null) {
            left.printNodoHF1(path + '0', hf);
        }
        if (right != null) {
            right.printNodoHF1(path + '1', hf);
        }
    }

    public static void printTree(NodoHF1 tree, String[] hf) {
        tree.printNodoHF1("", hf);
    }
}

/* Esta clase es la que crea las ramas del árbol de Huffman con fuente 2 */
class NodoHF2
        implements Comparable {

    private int valor;
    private String contenido;
    private NodoHF2 left;
    private NodoHF2 right;

    /* Constructor que crea el nodo al principio */
    public NodoHF2(String contenido, int valor) {
        this.contenido = contenido;
        this.valor = valor;

    }
    /* Constructor que crea el nodo pasándole las ramas izquierda y derecha */
    public NodoHF2(NodoHF2 left, NodoHF2 right) {
        /* Si la letra de la izquierda, es alfabéticamente anterior a la de la derecha
         * nos quedamos con la izquierda, y sino, con la derecha. Es decir, almacenamos 
         * en la siguiente rama, el carácter posicionado más anteriormente.
         */
        if (left.contenido.charAt(0) < right.contenido.charAt(0)) {
            this.contenido = left.contenido;
        } else if (left.contenido.charAt(0) == right.contenido.charAt(0)) {
            if (left.contenido.charAt(1) < right.contenido.charAt(1)) {
                this.contenido = left.contenido;
            } else {
                this.contenido = right.contenido;
            }
        } else if (left.contenido.charAt(0) > right.contenido.charAt(0)) {
            this.contenido = right.contenido;
        }
        this.valor = left.valor + right.valor;
        this.left = left;
        this.right = right;
    }
    /* Función que compara 2 nodos */
    public int compareTo(Object arg) {
        NodoHF2 other = (NodoHF2) arg;

        if (this.valor == other.valor) {
            if (this.contenido.charAt(0) == other.contenido.charAt(0)) {
                return this.contenido.charAt(1) - other.contenido.charAt(1);
            } else {
                return this.contenido.charAt(0) - other.contenido.charAt(0);
            }
        } else {
            return this.valor - other.valor;
        }
    }
    /* Función que crea el array de cadenas que contiene la codificación
	 * de Huffman que se ha creado.
     */
    private void printNodoHF2(String path, String[][] hf) {
        if ((left == null) && (right == null)) {
            hf[(int) contenido.charAt(0)][(int) contenido.charAt(1)] = path;
        }
        if (left != null) {
            left.printNodoHF2(path + '0', hf);
        }
        if (right != null) {
            right.printNodoHF2(path + '1', hf);
        }
    }
    public static void printTree(NodoHF2 tree, String[][] hf) {
        tree.printNodoHF2("", hf);
    }
}
