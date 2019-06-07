package com.example.appctt;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnAdicionar, btnSair;
    ListView lv;
    SQLController dbcon;
    TextView memID_tv, memName_tv, memEmail_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cria a instancia para a conexão com o banco:
        dbcon = new SQLController(this);

        // Abre o banco leitura e gravação
        dbcon.open();

        // Criar referências aos componentes da Activity:
        lv = (ListView) findViewById(R.id.lvContatos);
        btnAdicionar = (Button) findViewById(R.id.btnAddContato);
        btnSair = (Button) findViewById(R.id.btnSair);

        // Criar evento click:
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, IncluirContato.class);
                startActivity(it);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizar();
            }
        });

        // Cria relação entre a lista gerada pelo banco de dados e a ListView:
        Cursor cursor = dbcon.carregarDados();
        String[] from = new String[]{
                DBHelper.CONTATO_ID,
                DBHelper.CONTATO_NOME,
                DBHelper.CONTATO_EMAIL
        };

        int[] to = new int[]{R.id.idID, R.id.idNome, R.id.idEMail};

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.activity_visualizar_contato,
                cursor, from, to);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        // Detectar evento de seleção de um item da ListView:
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {

                    // Recuperar valores do item selecionado:
                    memID_tv = (TextView) view.findViewById(R.id.idID);
                    memName_tv = (TextView) view.findViewById(R.id.idNome);
                    memEmail_tv = (TextView) view.findViewById(R.id.idEMail);

                    // Faz a conversão das variáveis para modo String
                    String memberID_val = memID_tv.getText().toString();
                    String memberName_val = memName_tv.getText().toString();
                    String memberEmail_val = memEmail_tv.getText().toString();

                    // Prepara Intent para envio dos dados para activity de modificação:
                    Intent modif = new Intent(MainActivity.this, ModificarContato.class);
                    //Intent modif = new Intent(getApplicationContext(),ModificarContato.class);
                    modif.putExtra("memberID", memberID_val);
                    modif.putExtra("memberName", memberName_val);
                    modif.putExtra("memberEmail", memberEmail_val);

                    // Aciona activity de modificações:
                    startActivity(modif);
                } catch (Exception e) {
                    Log.e("Principal", e.toString());
                }
            }
        });
    }

    private void finalizar() {
        this.finish();
    }

}