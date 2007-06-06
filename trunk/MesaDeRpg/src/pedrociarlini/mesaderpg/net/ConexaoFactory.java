package pedrociarlini.mesaderpg.net;


public class ConexaoFactory {
    
    /**
     * Usado para criar uma inst�ncia de <code>Conexao</code>.
     * @return Uma inst�ncia de um tipo de conex�o que ser� usada em toda 
     * a aplca��o.
     */
    public static Conexao createConexaoInstance() {
        // Conex�o do trabalho do Nabor.
        return new ConexaoTrabalho();
    }
}
