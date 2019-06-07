package com.example.appctt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IncluirContato extends AppCompatActivity {

    EditText nome, eMail;
    Button btnIncluir, btnVoltar;
    SQLController dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_contato);

        nome = (EditText) findViewById(R.id.editNomeAtz);
        eMail = (EditText) findViewById(R.id.editMailAtz);
        btnIncluir = (Button) findViewById(R.id.btnAtualizar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        dbcon = new SQLController(this);
        dbcon.open();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNome = nome.getText().toString();
                String strEmail = eMail.getText().toString();
                dbcon.inserirRegistro(strNome, strEmail);
                Intent m = new Intent(IncluirContato.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mensagemExibir("Inclusão", "Contato adicionado com sucesso!");
                startActivity(m);
            }
        });
    }

    public void mensagemExibir(String titulo, String texto) {
        AlertDialog.Builder mensagem = new
                AlertDialog.Builder(IncluirContato.this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            // click listener on the alert box
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getApplicationContext(),
                        "Sucesso!", Toast.LENGTH_LONG).show();
            }
        });
        mensagem.show();
    }
}