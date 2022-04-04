package com.example.demoapp.core

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class ErrorBodyResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

fun parseError(errorBody: ResponseBody?): String {
    return try {
        errorBody?.string().toString().let {
            val data =
                GsonBuilder().serializeNulls().create().fromJson(it, ErrorBodyResponse::class.java)
            "${data.code} - ${data.message}"
        }
    } catch (e: JsonParseException) {
        "Unable to parse the error."
    } catch (e: Throwable) {
        "Something went wrong with that request."
    }
}