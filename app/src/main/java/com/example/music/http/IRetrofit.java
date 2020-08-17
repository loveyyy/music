
package com.example.music.http;
/**
 * Author:MorningSun
 * Time:2018/8/9
 * Description: Retrofit 接口
 */


import com.example.music.model.ArtistMv;
import com.example.music.model.BangList;
import com.example.music.model.BangMenu;
import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.LrcBeen;
import com.example.music.model.ArtistInfo;
import com.example.music.model.ArtistAlbum;
import com.example.music.model.ArtistMusic;
import com.example.music.model.ArtistList;
import com.example.music.model.ArtistListIndex;
import com.example.music.model.Banner;
import com.example.music.model.BangMusicList;
import com.example.music.model.BaseRespon;
import com.example.music.model.MusicList;
import com.example.music.model.RecList;
import com.example.music.model.RecListInfo;
import com.example.music.model.Search;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

public interface IRetrofit {
    @GET("/")
    Observable<ResponseBody>all();

    //banaber
    @GET("/api/www/banner/index/bannerList")
    Observable<BaseRespon<List<Banner>>> bannerList(@Query("reqId")String reqId);


    //推荐歌单
    @GET("/api/www/rcm/index/playlist")
    Observable<BaseRespon<MusicList>> musicList(@Query("loginUid")String mid, @Query("reqId")String reqId);



    //推荐歌单 更多 //order ==new  hot
    @GET("/api/pc/classify/playlist/getRcmPlayList")
    Observable<BaseRespon<RecList>> musicListMore(@Query("loginUid") String loginUid, @Query("loginSid") String loginSid,
                                                  @Query("pn")String pn,
                                                  @Query("rn")String rn,
                                                  @Query("order")String order,
                                                  @Query("reqId")String reqId);


    //推荐歌单 进入 title
    @GET("/api/www/playlist/playListInfo")
    Observable<BaseRespon<RecListInfo>> musicListInfo(@Query("pid") String pid, @Query("pn")String pn,
                                                      @Query("rn")String rn,
                                                      @Query("reqId")String reqId);



    //推荐歌手 title
    @GET("/api/www/artist/index/tags")
    Observable<BaseRespon<ArtistListIndex>> Artist_list_index(@Query("reqId")String reqId);

    //推荐歌手 more rn =100
    @GET("/api/www/artist/artistInfo")
    Observable<BaseRespon<ArtistList>> artistList(@Query("category")String category,
                                                   @Query("pn")String pn,
                                                   @Query("rn")String rn,
                                                   @Query("reqId")String reqId);

    //歌手简介
    @GET("/api/www/artist/artist")
    Observable<BaseRespon<ArtistInfo>> artistInfo(@Query("artistid")String artistid,
                                                  @Query("reqId")String reqId);

    //歌手音乐列表
    @GET("/api/www/artist/artistMusic")
    Observable<BaseRespon<ArtistMusic>> artistMusic(@Query("artistid")String artistid,
                                                    @Query("pn")String pn,
                                                    @Query("rn")String rn,
                                                    @Query("reqId")String reqId);

    //歌手专辑
    @GET("/api/www/artist/artistAlbum")
    Observable<BaseRespon<ArtistAlbum>> artistAlbum(@Query("artistid")String artistid,
                                                    @Query("pn")String pn,
                                                    @Query("rn")String rn,
                                                    @Query("reqId")String reqId);

    //歌手专辑
    @GET("/api/www/artist/artistMv")
    Observable<BaseRespon<ArtistMv>> artistMv(@Query("artistid")String artistid,
                                              @Query("pn")String pn,
                                              @Query("rn")String rn,
                                              @Query("reqId")String reqId);



    //排行
    @GET("/api/www/bang/index/bangList")
    Observable<BaseRespon<List<BangList>>> bangList(@Query("reqId")String reqId);

    //总排行榜 排行名称
    @GET("/api/www/bang/bang/bangMenu")
    Observable<BaseRespon<List<BangMenu>>> bangMenu(@Query("reqId")String reqId);

    //排行榜音乐列表
    @GET("/api/www/bang/bang/musicList")
    Observable<BaseRespon<BangMusicList>> bangMusicList(@Query("bangId")String bangId,
                                                        @Query("pn")String pn,
                                                        @Query("rn")String rn,
                                                        @Query("reqId")String reqId);

    //mv
    @GET("/api/www/music/mvList")
    Observable<BaseRespon<ArtistMv>> mvList(@Query("pid")String pid,
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

    //播放mv
    @GET("/url")
    Observable<ResponseBody> mv_info(@Query("format")String format, @Query("rid")String rid,
                                    @Query("response")String response, @Query("type")String type,
                                    @Query("t")String t, @Query("reqId")String reqId);



    //获取歌词
    @GET("http://m.kuwo.cn/newh5/singles/songinfoandlrc")
    Observable<BaseRespon<LrcBeen>> music_lrc(@Query("musicId")String musicId, @Query("reqId")String reqId);

    //搜索音乐
    @GET("/api/www/search/searchMusicBykeyWord")
    Observable<BaseRespon<Search>> searchMusic(@Query("key")String key, @Query("pn")String pn,
                                          @Query("rn")String rn, @Query("reqId")String reqId,
                                          @Header("Referer")String referer);

    //搜索关键字
    @GET("/api/www/search/searchKey")
    Observable<BaseRespon<List<String>>> searchKey(@Query("key")String key,@Query("reqId")String reqId,
                                               @Header("Referer")String referer);



    //获取歌曲下载地址
    @FormUrlEncoded
    @POST("http://www.333ttt.com/up/tool/")
    Observable<BaseRespon<List<DownlodMusciInfo>>> downloadMusic(@Field("input") String musicId, @Field("type")String type,
                                                           @Field("filter") String filter,@Field("page") int page,
                                                           @Header("X-Requested-With") String header);

    @Streaming
    @GET()
    Observable<ResponseBody> download(@Url() String url, @Header("RANGE")String header);


}
