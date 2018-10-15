package br.com.herculles.myproject.Usuario.persistencia;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioDao {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    public boolean setNome(String nome) {
        boolean isValido = true;
        try {
            if (currentUser != null) {
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nome)
                        .build();
                currentUser.updateProfile(profileChangeRequest);
            }
        }catch (DatabaseException e){
            isValido = false;
        }
        return isValido;
    }
}
