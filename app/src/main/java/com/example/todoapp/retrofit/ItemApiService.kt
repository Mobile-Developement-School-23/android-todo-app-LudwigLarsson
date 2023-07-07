package com.example.todoapp.retrofit

import retrofit2.Response
import retrofit2.http.*

interface ItemApiService {
    @GET("list")
    suspend fun getAllToDo(): Response<ResponseList>

    @GET("list/{id}")
    suspend fun getToDoByID(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<Element>

    @POST("list")
    suspend fun addToDo(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: Element
    ): Response<ResponseEl>

    @PUT("list/{id}")
    suspend fun updateToDo(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body element: Element
    ): Response<ResponseEl>

    @DELETE("list/{id}")
    suspend fun deleteToDo(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<ResponseEl>

    @PATCH("list")
    suspend fun updateAllToDo(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: ListElement
    ): Response<ResponseList>
}