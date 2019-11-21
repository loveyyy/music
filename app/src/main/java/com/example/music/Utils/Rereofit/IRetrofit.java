
package com.example.music.Utils.Rereofit;
/**
 * Author:MorningSun
 * Time:2018/8/9
 * Description: Retrofit 接口
 */


import com.example.music.entry.HotMusicBeens;
import com.example.music.entry.LrcBeen;
import com.example.music.entry.RankBeens;
import com.example.music.entry.RankMusicBeens;
import com.example.music.entry.SearchBeens;
import com.example.music.entry.SingerBeens;
import com.example.music.entry.Singer_MusicBeens;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

public interface IRetrofit {
    //获得排行榜
    @GET()
    Observable<RankBeens>getmusic(@Url String url, @Query("reqId") String reqId);

    //获得歌曲信息
    @GET()
    Observable<ResponseBody>getmusicinfo(@Url String url, @Query("format") String format, @Query("rid") int rid, @Query("response") String response,
                                       @Query("type") String type,@Query("br")String br);


    //获得歌曲排行榜
    @GET()
    Observable<RankMusicBeens>getmusicrank(@Url String url, @Query("bangId") int bangId, @Query("pn") int pn, @Query("rn") int rn,@Query("reqId") String reqid);


    //获得歌手推荐
    @GET()
    Observable<SingerBeens>getsinger(@Url String url, @Query("category") int category, @Query("pn") int pn,
                                     @Query("rn") int rn,@Query("reqId")String reqId);

    //获得歌手歌曲信息
    @GET()
    Observable<Singer_MusicBeens>getsinger_music(@Url String url, @Query("artistid") int artistid, @Query("pn") int pn, @Query("rn") int rn
                                                 );

    //获得歌词信息
    @GET()
    Observable<LrcBeen>getmusic_lrc(@Url String url, @Query("musicId") int musicId);


    //搜索
    @GET()
    Observable<SearchBeens>search(@Url String url, @Query("key") String key, @Query("pn") int pn, @Query("rn") int rn);

}
