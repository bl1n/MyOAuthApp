package com.lft.myoauthapp.api;




import com.lft.myoauthapp.api.model.RepoRequest;
import com.lft.myoauthapp.api.model.RepoResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GithubApi {

    /*@Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );*/


    @GET("user/repos")
    Single<List<RepoResponse>> getRepos(@Header("Authorization:") String token);


    @POST("user/repos")
    Single<RepoResponse> createRepo(@Header("Authorization:") String token,@Body RepoRequest request);





}
