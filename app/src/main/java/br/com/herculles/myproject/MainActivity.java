package br.com.herculles.myproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.herculles.myproject.estabelecimento.gui.TesteActvity;
import br.com.herculles.myproject.infraestrutura.persistencia.DBHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnLogar;
    private Button btnCadastrar;
    private EditText edtEmail, edtSenha;
    private ProgressDialog mProgressDialog;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private boolean validarCampos(){
        boolean validacao = true;

        if (edtEmail.getText().toString().trim().length() == 0){
            edtEmail.setError("O campo está vázio");
            validacao = false;
        }

        if (edtSenha.getText().toString().trim().length() == 0){
            edtSenha.setError("O campo está vázio");
            validacao = false;
        }
        return validacao;
    }
    private void login(String email, String senha) {
        if (!validarCampos()){
            return;
        }
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            verificarTipoConta(currentUser);
                        }else {
                            mProgressDialog.dismiss();
                            mProgressDialog.setCanceledOnTouchOutside(true);
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logar(View view){
        login(edtEmail.getText().toString(),edtSenha.getText().toString());
    }

    private void verificarTipoConta(FirebaseUser cUser){
        firebaseReference.child(DBHelper.REFERENCIA_ESTABELECIMENTO)
                .child(cUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            irTelaEstabelecimento();
                            finish();
                        }else{
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void irTelaEstabelecimento(){
        Intent intentAbrirTelaEstabelecimento = new Intent(MainActivity.this, TesteActvity.class);
        startActivity(intentAbrirTelaEstabelecimento);
        finish();
    }
    private void cleanViews(){
        edtEmail.setText(null);
        edtSenha.setText(null);
    }
    public void setupViews(){
        btnLogar = findViewById(R.id.btnLogar);
        btnCadastrar = findViewById(R.id.btnTelaCadastro);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("Entrando...");
    }


    public void irTelaCadastro(){
        Intent intent = new Intent(this,CadastroActvity.class);
        startActivity(intent);
        finish();
    }
    public void clickCadastro(View view){
        irTelaCadastro();
    }
}
