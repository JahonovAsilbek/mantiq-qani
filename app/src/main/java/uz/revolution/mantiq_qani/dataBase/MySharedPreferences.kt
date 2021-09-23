package uz.revolution.mantiq_qani.dataBase

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.revolution.mantiq_qani.model.MyData

object MySharedPreferences {
    private const val NAME = "USER"
    private const val MODE = Context.MODE_PRIVATE
    private val TYPE = object : TypeToken<List<MyData>>() {}.type
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var gson: Gson

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE)
        gson = Gson()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    private var usersData: String?
        get() = sharedPreferences.getString("key", "")
        set(value) = sharedPreferences.edit {
            if (value != null) {
                it.putString("key", value)
            }
        }

    fun insertUser(myData: MyData) {
        var data = getUsersDataList()
        data.add(myData)
        usersData = gson.toJson(data, TYPE)
    }

    fun getUsersDataList(): ArrayList<MyData> {
        return if (usersData!!.isNotEmpty()) {
            gson.fromJson(usersData, TYPE)
        } else ArrayList()
    }

    fun getUserData(name: String): MyData {
        var index = -1
        for (i in 0 until getUsersDataList().size) {
            if (getUsersDataList()[i].name == name) {
                index = i
            }
        }
        return getUsersDataList()[index]
    }

    fun editUserData(myData: MyData) {
        val data = getUsersDataList()
        for (i in 0 until data.size) {
            if (data[i].name == myData.name) {
                data[i] = myData
            }
        }
        usersData = gson.toJson(data, TYPE)
    }

    fun deleteAllData() {
        sharedPreferences.edit().clear()
    }
}