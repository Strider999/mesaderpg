package pedrociarlini.mesaderpg.net.trabalho;

import trabalho.IMessage;

/**
 * Representa uma mensagem que ser� enviada ou foi recebida.
 * @author Pedro Ciarlini
 *
 */
public class Message implements IMessage {
    
    /**
     * Bytes da mensagem.
     */
    private byte[] bytes;

    /**
     * Retorna o conte�do dos dados da mensagem.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Retorna a quantidade de bytes que foram salvos.
     */
    public int setBytes(byte[] b) {
        bytes = new byte[b.length];
        int i;
        for (i = 0; i < b.length; i++) {
            bytes[i] = b[i];
        }
        return i + 1;
    }
}