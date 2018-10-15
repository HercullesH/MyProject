package br.com.herculles.myproject.Usuario.negocio;

import br.com.herculles.myproject.Usuario.persistencia.UsuarioDao;

public class UsuarioServices {
    private UsuarioDao usuarioDao = new UsuarioDao();
    public boolean setNome(String novoNome){

        return usuarioDao.setNome(novoNome);
    }
}
