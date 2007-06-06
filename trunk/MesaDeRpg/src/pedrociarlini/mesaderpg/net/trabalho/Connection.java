package pedrociarlini.mesaderpg.net.trabalho;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import trabalho.IConnection;
import trabalho.IMessage;

/**
 * O protocolo de transporte usado � o TCP
 * @author pedro
 */
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
        this(new Socket(ip, porta));
    }
    
    public Connection(Socket client) throws Exception {
        socket = client;
        connectionCounter++;
        receiver = new ObjectInputStream(socket.getInputStream());
        sender = new ObjectOutputStream(socket.getOutputStream());
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
     * Altera os bytes da mensagem passada por par�metro, colocando os
     * bytes que foram enviados pelo host remoto.
     * 
     * Esse m�todo bloqueia a execu��o at� que uma nova mensagem esteja dispon�vel.
     * 
     * @param m
     *            Mensagem a ser alterada.
     * @return Tamanho da mensagem que chegou.
     */
    public int receive(IMessage m) {
        IMessage incomingMessage = null;
        try {
        	if (socket.isConnected()) {
        		incomingMessage = (IMessage) receiver.readObject();
        	}
        	else {
        		throw new IOException("Conex�o j� estava fechada.");
        	}
        } catch (EOFException e) {
        	throw new RuntimeException("Conex�o deve ter sido fechada: " + e.getMessage());
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

    /**
     * Cria uma nova mensagem, funcionando como f�brica de mensagens.
     */
	public IMessage createBlankMessage() {
		return new Message();
	}
}