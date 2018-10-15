package br.com.herculles.myproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import br.com.herculles.myproject.Usuario.negocio.UsuarioServices;
import br.com.herculles.myproject.estabelecimento.dominio.Estabelecimento;
import br.com.herculles.myproject.estabelecimento.negocio.EstabelecimentoServices;

public class CadastroActvity extends AppCompatActivity {
    private  EditText edtEmail;
    private EditText edtSenha;
    private EditText edtNick;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_actvity);
        setupViews();


    }



    public void setupViews(){
        edtEmail = findViewById(R.id.edtEmailCadastro);
        edtSenha = findViewById(R.id.edtSenhaCadastro);
        edtNick = findViewById(R.id.edtNickCadastro);
        progressDialog = new ProgressDialog(CadastroActvity.this);
        progressDialog.setTitle("Registrando...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void confirmaCadastro(View view){
        cadastrar(edtEmail.getText().toString(),edtSenha.getText().toString());
    }

    public void cadastrar(String email, String senha){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,senha).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(isCombinacaoValida(task)){
                            if(adicionaEstabelecimento(criarEstabelecimento())){
                                setNomeUsuario();
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Resgistrado",Toast.LENGTH_LONG).show();
                                irTelaLogin();
                            }else{
                                progressDialog.dismiss();
                                progressDialog.setCanceledOnTouchOutside(true);
                                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void setNomeUsuario(){
        UsuarioServices usuarioServices = new UsuarioServices();
        usuarioServices.setNome(edtNick.getText().toString());
    }

    private boolean isCombinacaoValida(@NonNull Task<AuthResult> task) {
            boolean verificador = true;

            try{
            if (task.isSuccessful()) {
            return true;
            }else {
            verificador = false;
            progressDialog.dismiss();
            progressDialog.setCanceledOnTouchOutside(false);
            throw task.getException();
            }
            }catch (FirebaseAuthInvalidCredentialsException e){
            edtEmail.setError("Informe um email válido");
            }catch (FirebaseAuthUserCollisionException e){
            edtEmail.setError("Uma conta com esse email já existe");
            }catch (Exception e){
            Toast.makeText(this,"erro no banco",Toast.LENGTH_LONG).show();
            }
            return verificador; }




    public Estabelecimento criarEstabelecimento(){
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNick(edtNick.getText().toString());
        return  estabelecimento;
    }
    public boolean adicionaEstabelecimento(Estabelecimento estabelecimento){
        EstabelecimentoServices estabelecimentoServices = new EstabelecimentoServices();
        return estabelecimentoServices.adicionaEstabelecimento(estabelecimento);

    }

    public void irTelaLogin(){
        Intent intent = new Intent(CadastroActvity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
