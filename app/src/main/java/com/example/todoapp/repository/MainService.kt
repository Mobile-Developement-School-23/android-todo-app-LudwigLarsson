package com.example.todoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.ItemDao
import com.example.todoapp.retrofit.Element
import com.example.todoapp.retrofit.ItemApiService
import com.example.todoapp.retrofit.ItemsRetrofit
import com.example.todoapp.retrofit.ResponseEl
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class MainService @Inject constructor(
    val application: ToDoApplication,
    val dao: ItemDao,
) {

    private var revision: Int = 0

    private val _load = MutableLiveData<Boolean>()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("Coroutine exception", "${throwable.message}")
        }

    private val job = Job()

    private val scope =
        CoroutineScope(Dispatchers.IO + job + coroutineExceptionHandler + CoroutineName("MainServiceScope"))

    val load: LiveData<Boolean>
        get() = _load

    init {
        _load.value = false
        //updateData()
    }

    /*suspend fun insertToDo(item: TodoItem): Result<ResponseEl> {
        if (revision != getServerRevision()) merge()
        return handleApi { api.addToDo(revision, Element(item)) }.also {
            operationWithServerComplete()
        }
    }

    suspend fun deleteToDo(item: TodoItem): Result<ResponseEl> {
        if (revision != getServerRevision()) merge()
        return handleApi { api.deleteToDo(revision, item.id.toString()) }.also {
            operationWithServerComplete()
        }
    }

    suspend fun updateToDo(item: TodoItem): Result<ResponseEl> {
        if (revision != getServerRevision()) merge()
        return handleApi {
            api.updateToDo(
                getServerRevision(),
                item.id.toString(),
                Element(item)
            )
        }.also {
            operationWithServerComplete()
        }
    }

    private suspend fun getServerRevision(): Int {
        val response = api.getAllToDo()
        return response.body()?.revision ?: 0
    }

    fun updateData() {
        if (application.isInternet()) merge()
    }

    private fun merge() {
        scope.launch {
            withContext(Dispatchers.Main) {
                _load.value = true
            }

            val serverList = api.getAllToDo().body()?.list?.toMutableList() ?: mutableListOf()

            val clientList = dao.joinToDoItems()?.toMutableList() ?: mutableListOf()

            val (biggest, smaller) = if (serverList.size > clientList.size) Pair(
                serverList,
                clientList
            )
            else Pair(clientList, serverList)


            val serverRemainder = mutableListOf<TodoItem>()



            serverRemainder.forEach { dao.insertToDo(it) }

            val (intersection, other) = biggest.partition { todo ->
                todo.id in smaller.flatMap {
                    listOf(
                        it.id
                    )
                }
            }

            revision = getServerRevision()

            withContext(Dispatchers.Main) {
                _load.value = false
            }
        }
    }

    private fun operationWithServerComplete() {
        ++revision
    }*/
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(code = response.code(), message = response.message())
        }
    } catch (e: Throwable) {
        Result.Exception(e)
    } catch (e: HttpException) {
        Result.Error(code = e.code(), message = e.message())
    }
}