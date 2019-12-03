package eventbus.co.rxapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eventbus.co.rxapplication.adapter.ResAdapter;
import eventbus.co.rxapplication.apiservice.BaseApiService;
import eventbus.co.rxapplication.apiservice.UtilsApi;
import eventbus.co.rxapplication.model.Repo;
import eventbus.co.rxapplication.model.ResponseRepos;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.rvRepos)
    RecyclerView rvRepos;

    private BaseApiService baseApiService;
    private List<Repo> repoList;
    private ResAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        baseApiService = UtilsApi.getAPIService();
        repoList = new ArrayList<>();

        adapter = new ResAdapter(repoList);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setItemAnimator(new DefaultItemAnimator());
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(adapter);

        etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*
                EditorInfo.IME_ACTION_SEARCH ini berfungsi untuk men-set keyboard kamu
                agar enter di keyboard menjadi search.
                 */
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = etUsername.getText().toString();
                    requestRepos(username);
                    return true;
                }
                return false;
            }
        });

    }

    public void requestRepos(String username) {
        pbLoading.setVisibility(View.VISIBLE);
        baseApiService.requestRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ResponseRepos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ResponseRepos> responseRepos) {
                        for (int i = 0; i < responseRepos.size(); i++) {
                            String name = responseRepos.get(i).getName();
                            String description = responseRepos.get(i).getDescription();
                            repoList.add(new Repo(name, description));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Berhasil mengambil data", Toast.LENGTH_SHORT).show();

                        adapter = new ResAdapter(repoList);
                        rvRepos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
