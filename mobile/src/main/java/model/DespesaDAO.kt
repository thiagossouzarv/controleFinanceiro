package model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DespesaDAO : SQLiteOpenHelper {

    companion object {
        const val DB_VERSION: Int = 1
        const val DB_NAME: String = "despesas"
        const val TABLE_TASKS = "despesas"
    }

    constructor(context: Context) : super(context, Companion.DB_NAME, null, Companion.DB_VERSION)


    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        val CREATE_USER = "CREATE TABLE `despesas` (" +
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


    fun create(despesa: Despesa) {
        val db = this.writableDatabase

        val despesaTab = ContentValues()
        despesaTab.put("descricao", despesa.descricao)
        despesaTab.put("categoria_id", despesa.categoria)
        despesaTab.put("valor", despesa.valor)

        db.insert(DB_NAME, null, despesaTab)
        db.close()
    }

    fun update(despesa: Despesa) {
        val db = this.writableDatabase

        val despesaTab = ContentValues()
        despesaTab.put("descricao", despesa.descricao)
        despesaTab.put("categoria_id", despesa.categoria)
        despesaTab.put("valor", despesa.valor)


        db.update(DB_NAME, despesaTab, "id=?", arrayOf(despesa.id.toString()))
        db.close()
    }

    fun delete(despesa: Despesa) {
        val db = this.writableDatabase

        db.delete(DB_NAME, "id=?", arrayOf(despesa.id.toString()))
        db.close()
    }

    fun getAll(): ArrayList<Despesa> {
        val despesasList = ArrayList<Despesa>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var despesas = Despesa()


                despesas.id = cursor.getInt(0)
                despesas.descricao = cursor.getString(1)
                despesas.categoria = cursor.getInt(2)
                despesas.valor = cursor.getFloat(3)

                despesasList.add(despesas)
            } while (cursor.moveToNext())
        }

        return despesasList
    }

    fun getDespesaById(id: Int): Despesa {

        val db = this.readableDatabase

        val cursor = db.query(TABLE_TASKS, arrayOf("id", "descricao", "categoria_id", "valor"),
                "id = ?",
                arrayOf(id.toString()), null, null, null, null)
        var despesa: Despesa? = null
        if (cursor != null) {
            cursor.moveToFirst()
            despesa = Despesa()
            despesa.id = cursor.getInt(0)
            despesa.descricao = cursor.getString(1)
            despesa.categoria = cursor.getInt(2)
            despesa.valor = cursor.getFloat(3)
            return despesa
        } else {
            throw RuntimeException("despesa nao existe!")
        }
    }


}