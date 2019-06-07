package com.example.appctt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModificarContato extends AppCompatActivity {

    // Atributos (variáveis) para interagir com activity:
    EditText tNome, tEmail;
    TextView tID;
    Button btAtualizar, btExcluir, btVoltar;

    long id;

    // Inicializa o controller do banco de dados
    SQLController dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_contato);
        try {
            dbcon = new SQLController(this);

            // Abre a conexão com o bando de dados
            dbcon.open();

            // Recebe os valores da View
            tNome = (EditText) findViewById(R.id.editNomeAtz);
            tEmail = (EditText) findViewById(R.id.editMailAtz);
            tID = (TextView) findViewById(R.id.tvID);

            btAtualizar = (Button) findViewById(R.id.btnAtualizar);
            btExcluir = (Button) findViewById(R.id.btnExcluir);
            btVoltar = (Button) findViewById(R.id.btnVoltar);

            // Cria uma Intent paa manipular os dados
            Intent i = getIntent();

            String memberID = i.getStringExtra("memberID");
            String memberName = i.getStringExtra("memberName");
            String memberEmail = i.getStringExtra("memberEmail");

            id = Long.parseLong(memberID);

            // Apresenta os valores na View
            tID.setText(memberID);
            tNome.setText(memberName);
            tEmail.setText(memberEmail);

            btExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Exclui um registro selecionado
                    dbcon.excluirRegistro(id);
                    voltarPrincipal();
                }
            });
            btAtualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Efetua a atualização de um registro
                    String memName_upd = tNome.getText().toString();
                    String memEmail_upd = tEmail.getText().toString();
                    dbcon.atualizarRegistro(id, memName_upd, memEmail_upd);
                    voltarPrincipal();
                }
            });
            btVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e("ModificarContato", e.toString());
        }
    }

    public void voltarPrincipal() {
        // Retorna para a View contendo todos os registros cadastrados
        Intent pi = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pi);
    }
}