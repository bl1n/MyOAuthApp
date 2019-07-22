package com.lft.myoauthapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lft.myoauthapp.api.GithubApi;
import com.lft.myoauthapp.api.model.AccessToken;
import com.lft.myoauthapp.api.model.RepoRequest;
import com.lft.myoauthapp.api.model.RepoResponse;
import com.lft.myoauthapp.api.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoActivity extends AppCompatActivity {

    public static final String TAG = "Debug";

    @BindView(R.id.et_repo_name)
    EditText etRepoName;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.tv_repo_created)
    TextView tvRepoCreated;
    private TextView mTextView;

    @Inject
    GithubApi mApi;

    private String mToken;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);
        AppDelegate.getAppComponent().inject(this);
        mTextView = findViewById(R.id.tv_holder);

        getAccessToken();

    }

    private void getAccessToken() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        GithubApi api = retrofit.create(GithubApi.class);

        mDisposable = api.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> new AccessToken())
                .subscribe((accessToken, throwable) -> {
                    mToken = accessToken.getTokenType() + " " + accessToken.getAccessToken();
                    getUserInfo(mTextView);
                });
    }

    private String getToken() {

        final String s = getSharedPreferences("my_pref", MODE_PRIVATE)
                .getString("accessCode", "null")
                .replace("code=", "");
        Log.d(TAG, "getToken: " + s);
        return s;
    }

    private void getUserInfo(TextView textView) {
        mDisposable = mApi.getUserInfo(mToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .onErrorReturn(throwable -> {
                    Log.d(TAG, "getUserInfo: " + throwable.getMessage());
                    return new User();
                })
                .subscribe(repoResponses -> textView.setText(repoResponses.getLogin()));
    }

    @OnClick({R.id.btn_create, R.id.tv_repo_created})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_create) {
            createRepo();
        }
    }

    private void createRepo() {
        if (!TextUtils.isEmpty(etRepoName.getText())) {
            RepoRequest request = new RepoRequest();
            request.setName(etRepoName.getText().toString());
            request.setDescription("");
            request.setHasIssues(false);
            request.setHasProjects(false);
            request.setHasWiki(false);
            request.setHomepage("");
            request.setPrivateX(false);

            mDisposable = mApi.createRepo(mToken, request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> {
                        Log.d(TAG, "getUserInfo: " + throwable.getMessage());
                        return new RepoResponse();
                    })
                    .subscribe(this::initSuccessUI);


        }
    }

    private void initSuccessUI(RepoResponse repoResponse) {
        tvRepoCreated.setVisibility(View.VISIBLE);
        tvRepoCreated.setText(String.format("Repo was created: name %s\n url: %s", repoResponse.getName(), repoResponse.getHtmlUrl()));
        tvRepoCreated.setOnClickListener(v -> {
            Toast.makeText(this, "yay", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
        super.onDestroy();

    }
}
