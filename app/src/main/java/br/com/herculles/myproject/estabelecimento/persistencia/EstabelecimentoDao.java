package br.com.herculles.myproject.estabelecimento.persistencia;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.herculles.myproject.estabelecimento.dominio.Estabelecimento;
import br.com.herculles.myproject.infraestrutura.persistencia.DBHelper;

public class EstabelecimentoDao {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public boolean adicionarEstabelecimento(Estabelecimento estabelecimento){
        boolean sucesso = true;
        try {
            databaseReference.child(DBHelper.REFERENCIA_ESTABELECIMENTO)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(estabelecimento);
        }catch(DatabaseException e){
            sucesso = false;
        }
        return sucesso;

    }
}
