package br.com.herculles.myproject.estabelecimento.negocio;

import br.com.herculles.myproject.estabelecimento.dominio.Estabelecimento;
import br.com.herculles.myproject.estabelecimento.persistencia.EstabelecimentoDao;

public class EstabelecimentoServices {

    private EstabelecimentoDao estabelecimentoDao = new EstabelecimentoDao();

    public boolean adicionaEstabelecimento(Estabelecimento estabelecimento){
        return estabelecimentoDao.adicionarEstabelecimento(estabelecimento);
    }
}
