/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.converter.gson

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.io.StringReader
import java.lang.reflect.Type

internal class MyResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>,
    private val type: Type
) :
    Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        //println("GOING TO CUSTOM")

        val body = value.string()
        val jsonReader = JsonReader(StringReader(body))

        //val jsonReader = gson.newJsonReader(value.charStream())
        return try {
                val result = adapter.read(jsonReader)
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw JsonIOException("JSON document was not fully consumed.")
                }
                result
            } catch (e: Exception) {
                //println("type : $type")
                //val type = object : TypeToken<T>() {}.type

                //println("MYBODY: $body, type: $type")

                val data = Gson().fromJson(body, String::class.java)
                val s: T = Gson().fromJson(data, type)
                //println("T: $s")
                s
            } finally {
                value.close()
            }
        }
    }
