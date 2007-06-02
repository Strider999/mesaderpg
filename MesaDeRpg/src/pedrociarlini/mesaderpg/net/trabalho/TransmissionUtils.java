package pedrociarlini.mesaderpg.net.trabalho;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Agrupa m�todos utilit�rios para transmiss�o pela API especificada pelo professor.
 * @author Pedro Ciarlini
 */
public class TransmissionUtils {

    /**
     * Impede a cria��o de uma inst�ncia dessa classe.
     */
    private TransmissionUtils(){
    }
    
    /**
     * Retorna os bytes que representam a serializa��o do objeto.
     * @param obj
     * @return
     */
    public static byte[] getObjectBytes(Object obj) {
        MemoryOutputStream mos = new MemoryOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(mos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return mos.getBytes();
    }

    /**
     * Inner class usada para capturar os bytes gerados pelo
     * {@link ObjectOutputStream} e retornar o array de bytes.
     * @author Pedro Ciarlini
     */
    private static class MemoryOutputStream extends OutputStream{

        double FILL_FACTOR = 0.7;
        
        private byte[] target = new byte[1024];
        
        private int actualIndex = 0;
        
        /**
         * Escreve o byte em em�ria.
         */
        @Override
        public void write(int b) throws IOException {
            checkTargetGrowth();
            target[actualIndex++] = (byte)b;
        }
        
        /**
         * Verifica se o array que est� em mem�ria j� est� cheio, para
         * incrementar seu tamanho.
         */
        private void checkTargetGrowth() {
            if(actualIndex >= target.length) {
                byte[] aux = new byte[(int)(target.length * FILL_FACTOR)];
                System.arraycopy(target, 0, aux, 0, target.length);
                target = aux;
            }
        }
        
        /**
         * Retorna apenas os bytes que foram preenchidos durante a captura.
         * @return array de bytes que fora preenchidos
         */
        public byte[] getBytes() {
            byte[] result = new byte[actualIndex];
            System.arraycopy(target, 0, result, 0, actualIndex-1);
            return result;
        }
    }
}