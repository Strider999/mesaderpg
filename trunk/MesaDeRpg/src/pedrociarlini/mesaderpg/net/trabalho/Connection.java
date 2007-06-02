package pedrociarlini.mesaderpg.net.trabalho;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import trabalho.IConnection;
import trabalho.IMessage;

public class Connection implements IConnection {

    Socket socket;

    ObjectInputStream receiver;

    ObjectOutputStream sender;
    
    private static int connectionCounter = 0;

    /**
     * Cria uma <code>Connection</code> baseada no endere�o e porta passados
     * por par�metro. O protocolo de transporte usado � o TCP.
     * 
     * @param ip
     *            Endere�o a ser conectado.
     * @param porta
     *            Porta que estar� com a aplica��o esperando a conex�o.
     * @throws Exception
     *             Para qualquer erro que ocorrer.
     */
    Connection(InetAddress ip, int porta) throws Exception {
        socket = new Socket(ip, porta);
        connectionCounter++;
        receiver = new ObjectInputStream(socket.getInputStream());
        sender = new ObjectOutputStream(socket.getOutputStream());
        /*
        receiver = new Thread("Recebedora-" + connectionCounter) {
            @Override
            public void run() {
                
            }
        };
        sender = new Thread("Enviadora-" + connectionCounter) {
            @Override
            public void run() {
                
            }
        };
        */
    }

    /**
     * Fecha a conex�o com a m�quina remota.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("N�o foi poss�vel fechar a conex�o.");
        }
    }

    /**
     * Altera os bytes da mensagem passada por par�metro.
     * 
     * @param m
     *            Mensagem a ser alterada.
     * @return Tamanho da mensagem que chegou.
     */
    public int receive(IMessage m) {
        IMessage incomingMessage = null;
        try {
            incomingMessage = (IMessage) receiver.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Erro de I/O: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Classe \"IMessage\" n�o encontrada. (" + e.getMessage() + ")");
        }
        m.setBytes(incomingMessage.getBytes());
        return incomingMessage.getBytes().length;
    }

    /**
     * Envia a mensagem passada por par�metro.
     */
    public int send(IMessage m) {
        try {
            sender.writeObject(m);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar mensagem: " + e.getMessage());
        }
        return 0;
    }
}