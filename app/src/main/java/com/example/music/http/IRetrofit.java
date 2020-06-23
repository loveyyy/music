
package com.example.music.http;
/**
 * Author:MorningSun
 * Time:2018/8/9
 * Description: Retrofit 接口
 */


import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.LrcBeen;
import com.example.music.model.Activity_music;
import com.example.music.model.Arisit_Info;
import com.example.music.model.Artist_Album;
import com.example.music.model.Artist_Music;
import com.example.music.model.Artist_Mv;
import com.example.music.model.Artist_list;
import com.example.music.model.Artist_list_index;
import com.example.music.model.Bananer;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.Bang_list;
import com.example.music.model.Bang_meau;
import com.example.music.model.BaseRespon;
import com.example.music.model.Music_list;
import com.example.music.model.Rec_List;
import com.example.music.model.Rec_List_Info;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

public interface IRetrofit {
    @GET("/")
    Observable<ResponseBody>all();

    //banaber
    @GET("/api/www/banner/index/bannerList")
    Observable<BaseRespon<List<Bananer>>> Bananer_list(@Query("reqId")String reqId);


    //推荐歌单
    @GET("/api/www/rcm/index/playlist")
    Observable<BaseRespon<Music_list>> Music_list(@Query("loginUid")String mid, @Query("reqId")String reqId);



    //推荐歌单 更多 //order ==new  hot
    @GET("/api/pc/classify/playlist/getRcmPlayList")
    Observable<BaseRespon<Rec_List>> Music_list_more(@Query("loginUid") String loginUid,@Query("loginSid") String loginSid,
                                                     @Query("pn")String pn,
                                                     @Query("rn")String rn,
                                                     @Query("order")String order,
                                                     @Query("reqId")String reqId);


    //推荐歌单 进入 title
    @GET("/api/www/playlist/playListInfo")
    Observable<BaseRespon<Rec_List_Info>> Music_list_info(@Query("pid") String pid, @Query("pn")String pn,
                                                          @Query("rn")String rn,
                                                          @Query("reqId")String reqId);



    //推荐歌手 title
    @GET("/api/www/artist/index/tags")
    Observable<BaseRespon<Artist_list_index>> Artist_list_index(@Query("reqId")String reqId);

    //推荐歌手 more rn =100
    @GET("/api/www/artist/artistInfo")
    Observable<BaseRespon<Artist_list>> Artist_list(@Query("category")String category,
                                                          @Query("pn")String pn,
                                                          @Query("rn")String rn,
                                                          @Query("reqId")String reqId);

    //歌手简介
    @GET("/api/www/artist/artist")
    Observable<BaseRespon<Arisit_Info>> Artist_info(@Query("artistid")String artistid,
                                                           @Query("reqId")String reqId);

    //歌手音乐列表
    @GET("/api/www/artist/artistMusic")
    Observable<BaseRespon<Artist_Music>> Artist_Music(@Query("artistid")String artistid,
                                                      @Query("pn")String pn,
                                                      @Query("rn")String rn,
                                                      @Query("reqId")String reqId);

    //歌手专辑
    @GET("/api/www/artist/artistAlbum")
    Observable<BaseRespon<Artist_Album>> Artist_Album(@Query("artistid")String artistid,
                                                     @Query("pn")String pn,
                                                     @Query("rn")String rn,
                                                     @Query("reqId")String reqId);

    //歌手专辑
    @GET("/api/www/artist/artistMv")
    Observable<BaseRespon<Artist_Mv>> Artist_Mv(@Query("artistid")String artistid,
                                                @Query("pn")String pn,
                                                @Query("rn")String rn,
                                                @Query("reqId")String reqId);



    //排行
    @GET("/api/www/bang/index/bangList")
    Observable<BaseRespon<List<Bang_list>>> Bang_list(@Query("reqId")String reqId);

    //总排行榜 排行名称
    @GET("/api/www/bang/bang/bangMenu")
    Observable<BaseRespon<List<Bang_meau>>> Bang_Menu(@Query("reqId")String reqId);

    //排行榜音乐列表
    @GET("/api/www/bang/bang/musicList")
    Observable<BaseRespon<Bang_Music_list>> Bang_Music_list(@Query("bangId")String bangId,
                                                                  @Query("pn")String pn,
                                                                  @Query("rn")String rn,
                                                                  @Query("reqId")String reqId);

    //mv
    @GET("/api/www/music/mvList")
    Observable<BaseRespon<Artist_Mv>> MvList(@Query("pid")String pid,
                                           @Query("pn")String pn,
                                           @Query("rn")String rn,
                                           @Query("httpsStatus")String httpsStatus,
                                           @Query("reqId")String reqId);


    //播放音乐
    @GET("/url")
    Observable<BaseRespon> music_info(@Query("format")String format,@Query("rid")String rid,
                                      @Query("response")String response, @Query("type")String type,
                                      @Query("br")String br, @Query("from")String from,
                                      @Query("t")String t,@Query("reqId")String reqId);



    //获取歌词
    @GET("http://m.kuwo.cn/newh5/singles/songinfoandlrc")
    Observable<BaseRespon<LrcBeen>> music_lrc(@Query("musicId")String musicId, @Query("reqId")String reqId);


    //获取歌曲下载地址
    @FormUrlEncoded
    @POST("http://www.333ttt.com/up/tool/")
    Observable<BaseRespon<List<DownlodMusciInfo>>> downloadMuisc(@Field("input") String musicId, @Field("type")String type,
                                                           @Field("filter") String filter,@Field("page") int page,
                                                           @Header("X-Requested-With") String header);

    @Streaming
    @GET()
    Observable<ResponseBody> download(@Url() String url, @Header("RANGE")String header);


}
