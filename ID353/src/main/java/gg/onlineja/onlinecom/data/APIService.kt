package gg.onlineja.onlinecom.data

import gg.onlineja.onlinecom.data.subs.bodies.*
import gg.onlineja.onlinecom.data.subs.responses.*
import gg.onlineja.onlinecom.utils.Constants
import gg.onlineja.onlinecom.data.ResponseFolder
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET(Constants.BASE_URL)
    suspend fun uploadData(
        @Query("p1") p1: String?,
        @Query("p2") p2: String,
        @Query("p3") p3: String?,
        @Query("p4") p4: String,
        @Query("p5") p5: String,
        @Query("p6") p6: String?,
        @Query("p7") p7: String?,
        @Query("p8") p8: String?,
        @Query("p9") p9: String?,
        @Query("p10") p10: String,
        @Query("p11") p11: String? = null,
        @Query("p12") p12: String? = null,
        @Query("p13") p13: String? = null,
        @Query("p14") p14: String? = null,
    ): Response<ResponseFolder>

    @GET("${Constants.BASE_URL}/{folder}/db.json")
    suspend fun getDbJson(
        @Path("folder") folder: String
    ): Response<ResponseMainData>

    @POST("http://139.162.136.152:8002/subs/aff_sub1")
    suspend fun getAffSub1(
        @Body body: Sub1Body
    ): Response<Sub1Response>

    @POST("http://139.162.136.152:8002/subs/aff_sub2")
    suspend fun getAffSub2(
        @Body body: Sub2Body
    ): Response<Sub2Response>

    @POST("http://139.162.136.152:8002/subs/aff_sub3")
    suspend fun getAffSub3(
        @Body body: Sub3Body
    ): Response<Sub3Response>

    @POST("http://139.162.136.152:8002/subs/aff_sub4")
    suspend fun getAffSub4(
        @Body body: Sub4Body
    ): Response<Sub4Response>

    @POST("http://139.162.136.152:8002/subs/aff_sub5")
    suspend fun getAffSub5(
        @Body body: Sub5Body
    ): Response<Sub5Response>


    @GET("https://google.com")
    suspend fun testConnection(): Response<Unit>

}