package model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class ReceitaDAO : SQLiteOpenHelper {

    companion object {
        const val DB_VERSION: Int = 1
        const val DB_NAME: String = "receitas"
        const val TABLE_TASKS = "receitas"
    }

    constructor(context: Context) : super(context, Companion.DB_NAME, null, Companion.DB_VERSION)


    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        val CREATE_USER = "CREATE TABLE `receitas` (" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " `descricao` varchar(50) NOT NULL, " +
                " `categoria_id` INTEGER NOT NULL, " +
                " `valor` FLOAT(13,2) NOT NULL );"
        if (sqLiteDatabase != null) {
            sqLiteDatabase.execSQL(CREATE_USER)
        }

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, i: Int, i1: Int) {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $" + TABLE_TASKS)
        }
        onCreate(sqLiteDatabase)
    }


    fun create(receita: Receita) {
        val db = this.writableDatabase

        val despesaTab = ContentValues()
        despesaTab.put("descricao", receita.descricao)
        despesaTab.put("categoria_id", receita.categoria)
        despesaTab.put("valor", receita.descricao)

        db.insert(DB_NAME, null, despesaTab)
        db.close()
    }

    fun update(receita: Receita) {
        val db = this.writableDatabase

        val despesaTab = ContentValues()
        despesaTab.put("descricao", receita.descricao)
        despesaTab.put("categoria_id", receita.categoria)
        despesaTab.put("valor", receita.descricao)


        db.update(DB_NAME, despesaTab, "id=?", arrayOf(receita.id.toString()))
        db.close()
    }

    fun delete(receita: Receita) {
        val db = this.writableDatabase

        db.delete(DB_NAME, "id=?", arrayOf(receita.id.toString()))
        db.close()
    }

    fun getAll(): ArrayList<Receita> {
        val receitaList = ArrayList<Receita>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var receita = Receita()


                receita.id = cursor.getInt(0)
                receita.descricao = cursor.getString(1)
                receita.categoria = cursor.getInt(2)
                receita.valor = cursor.getFloat(3)

                receitaList.add(receita)
            } while (cursor.moveToNext())
        }

        return receitaList
    }

    fun getDespesaById(id: Int): Receita {

        val db = this.readableDatabase

        val cursor = db.query(TABLE_TASKS, arrayOf("id", "descricao", "categoria_id", "valor"),
                "id = ?",
                arrayOf(id.toString()), null, null, null, null)
        var receita: Receita? = null
        if (cursor != null) {
            cursor.moveToFirst()
            receita = Receita()
            receita.id = cursor.getInt(0)
            receita.descricao = cursor.getString(1)
            receita.categoria = cursor.getInt(2)
            receita.valor = cursor.getFloat(3)
            return receita
        } else {
            throw RuntimeException("despesa nao existe!")
        }
    }


}