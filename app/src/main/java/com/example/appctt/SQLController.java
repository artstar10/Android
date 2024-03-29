package com.example.appctt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {ourcontext = c;}

    public SQLController open() throws SQLException {
        dbhelper = new DBHelper(this.ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public void inserirRegistro(String nome, String eMail) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.CONTATO_NOME, nome);
        cv.put(DBHelper.CONTATO_EMAIL, eMail);

        database.insert(DBHelper.TABELA, null, cv);
    }

    public Cursor carregarDados() {
        String[] allColumns = new String[] {
                DBHelper.CONTATO_ID,
                DBHelper.CONTATO_NOME,
                DBHelper.CONTATO_EMAIL};

        Cursor c = database.query(DBHelper.TABELA, allColumns, null, null,
                null, null, DBHelper.CONTATO_NOME);

        if (c != null) {
            c.moveToFirst();
        }
        return  c;
    }

    public int atualizarRegistro(long id, String nome, String eMail) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.CONTATO_NOME, nome);
        cvUpdate.put(DBHelper.CONTATO_EMAIL, eMail);

        int i = database.update(DBHelper.TABELA, cvUpdate,
                DBHelper.CONTATO_ID + " = " + id, null);

        return i;
    }

    public void excluirRegistro(long id) {
        database.delete(DBHelper.TABELA, DBHelper.CONTATO_ID + "="
                + id, null);
    }
}