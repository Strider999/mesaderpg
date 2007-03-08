package pedrociarlini.mesaderpg.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import pedrociarlini.mesaderpg.net.event.DataReceivedListener;

public class ConexaoJogador implements Conexao {
	
	private Set<DataReceivedListener> listeners = new HashSet<DataReceivedListener>();
	
	private ObjectInputStream ois = null;
	
	public ConexaoJogador(Socket socket) throws IOException {
		if (socket == null) {
			throw new IllegalArgumentException("Socket n�o pode ser nulo.");
		}
		ois = new ObjectInputStream(socket.getInputStream());
		
	}

	public void addDataReceivedListener(DataReceivedListener listener) {
		this.listeners.add(listener);
	}

	public void send(Object obj) throws IOException {

	}

	public void close() throws IOException {
		// TODO implementar todo o conjunto de opera��es para abrir e fechar conex�o
		
	}
	// TODO Implementar cria��o de socket etc. 

	public void open() throws IOException {
	}
}
