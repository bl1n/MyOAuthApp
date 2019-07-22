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
import com.lft.myoauthapp.api.model.RepoRequest;
import com.lft.myoauthapp.api.model.RepoResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RepoActivity extends AppCompatActivity {

    @BindView(R.id.et_repo_name)
    EditText etRepoName;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.tv_repo_created)
    TextView tvRepoCreated;
    private TextView mTextView;
    private String result;

    @Inject
    GithubApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);
        AppDelegate.getAppComponent().inject(this);

        mTextView = findViewById(R.id.tv_holder);

        mTextView.setText(getSharedPreferences("my_pref", MODE_PRIVATE).getString("accessCode", "null"));

        addRepos(mTextView);

    }
    private  String getToken(){

        final String s = getSharedPreferences("my_pref", MODE_PRIVATE).getString("accessCode", "null").replace("code=", "");
        Log.d("Debug", "getToken: " + s);
        return s;
    }

    private void addRepos(TextView textView) {
        final Disposable subscribe = mApi.getRepos(getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .onErrorReturn(throwable -> {
                    Log.d("Debug", "addRepos: " + throwable.getMessage());
                    return new ArrayList<>();
                })
                .subscribe(repoResponses -> {
                    StringBuilder builder = new StringBuilder();
                    for (RepoResponse response : repoResponses) {
                        builder.append(response.getName() + "\n");
                    }
                    textView.setText(builder);
                });
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

            final Disposable subscribe = mApi.createRepo(getToken(),request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> {
                        Log.d("Debug", "addRepos: " + throwable.getMessage());
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
}
