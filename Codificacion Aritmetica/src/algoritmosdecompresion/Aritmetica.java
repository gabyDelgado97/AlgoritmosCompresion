package algoritmosdecompresion;

public class Aritmetica {

    public Aritmetica() { }

    public static String crearMensajeAritmetico(StringBuffer mensaje, float[] prim, float[] seg) {
        boolean bandera = false;
        String mensajeAritmetico = "";
        String mensLow = "";
        String mensHigh = "";
        
        float low = (float) 0;
        float high = (float) 0;
        float lowAntes = (float) 0;
        /**
         * Con este bucle for realizo el algoritmo normal de codificación
         * aritmética Hn = (H-L) * Li + L; Jn = (H-L) * Hn + L;
         */
        for (int i = 0; i <= mensaje.length() - 1; i++) {
            if (i == 0) {
                low = prim[(int) mensaje.charAt(i)];
                high = seg[(int) mensaje.charAt(i)];
            } else {
                lowAntes = low;
                low = (high - lowAntes) * prim[(int) mensaje.charAt(i)] + lowAntes;
                high = (high - lowAntes) * seg[(int) mensaje.charAt(i)] + lowAntes;

            }
        }
        /**
         * Con este bucle while simplemente paso los números reales del
         * intervalo [L,H) Cuando la L y la H coinciden en 0 o 1, pero la H
         * acaba antes que la L, entonces el mensaje a escoger es L, de lo
         * contrario, se escoge la H.
         */
        while (low != 1) {
            low = low * 2;
            if (low > 1) {
                mensLow = mensLow + "1";
                low = low - 1;
            } else if (low == 1) {
                mensLow = mensLow + "1";
            } else {
                mensLow = mensLow + "0";
            }
            if ((!bandera) && (low != 1)) {
                high = high * 2;
                if (high > 1) {
                    mensHigh = mensHigh + "1";
                    high = high - 1;
                } else if (high == 1) {
                    bandera = true;
                    mensHigh = mensHigh + "1";
                } else {
                    mensHigh = mensHigh + "0";
                }
            }
        }
        if (!bandera) {
            mensajeAritmetico = mensHigh;
        } else {
            mensajeAritmetico = mensLow;
        }
        return (mensajeAritmetico);
    }
}
