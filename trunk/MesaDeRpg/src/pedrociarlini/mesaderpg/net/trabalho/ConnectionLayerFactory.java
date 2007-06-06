package pedrociarlini.mesaderpg.net.trabalho;

import java.util.HashMap;
import java.util.Map;

import trabalho.IConnLayer;

/**
 * Factory de factories de conex�o.
 * @author pedro
 *
 */
public class ConnectionLayerFactory {

	private static Map<String, Class<IConnLayer>> conns = new HashMap<String, Class<IConnLayer>>();

	/**
	 * Registra uma classe que pode servir como conex�o pela aplica��o.
	 * 
	 * @param connName
	 * @param classe
	 */
	@SuppressWarnings("unchecked")
	public static void registerConnection(String connName,
			Class classe) {
		conns.put(connName, (Class<IConnLayer>) classe);
	}

	/**
	 * Cria uma nova inst�ncia de uma Factory de conex�o.
	 * 
	 * @param connName
	 *            Nome da classe, registrada previamente.
	 * @return nova inst�ncia de uma conex�o.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static IConnLayer createConnLayerInstance(String connName) {
		IConnLayer result = null;
		try {
			if (conns.containsKey(connName)) {
				result = conns.get(connName).newInstance();
			}
			else {
				throw new Exception("A conex�o cujo nome de registro � \"" + connName + "\" n�o existe.");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
