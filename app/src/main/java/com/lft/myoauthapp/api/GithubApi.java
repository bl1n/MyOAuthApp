package com.lft.myoauthapp.api;




import com.lft.myoauthapp.api.model.AccessToken;
import com.lft.myoauthapp.api.model.RepoRequest;
import com.lft.myoauthapp.api.model.RepoResponse;
import com.lft.myoauthapp.api.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GithubApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Single<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );


    @GET("user/repos")
    Single<List<RepoResponse>> getRepos(@Header("Authorization") String token);

    @GET("user")
    Single<User> getUserInfo(@Header("Authorization") String token);


    @POST("user/repos")
    Single<RepoResponse> createRepo(@Header("Authorization") String token,@Body RepoRequest request);





}
