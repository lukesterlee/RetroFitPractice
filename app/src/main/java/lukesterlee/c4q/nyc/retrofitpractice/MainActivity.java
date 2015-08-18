package lukesterlee.c4q.nyc.retrofitpractice;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    public static final String ENDPOINT = "https://api.github.com";
    private ListView mListView;
    private RepoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mListView = (ListView) findViewById(R.id.listView);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();


        GitHubService service = restAdapter.create(GitHubService.class);

        String username = "lukesterlee";

        service.listRepos(username, new Callback<List<Repository>>() {
            @Override
            public void success(List<Repository> repositories, Response response) {
                mAdapter = new RepoAdapter(getApplicationContext(), repositories);
                mListView.setAdapter(mAdapter);
                Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.i("PRACTICE", error.toString());

            }
        });

    }

    private class RepoAdapter extends BaseAdapter {

        private Context mContext;
        private List<Repository> mList;

        public RepoAdapter(Context context, List<Repository> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Repository getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView  = LayoutInflater.from(mContext).inflate(R.layout.list_item_repo, parent);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
            TextView created = (TextView) convertView.findViewById(R.id.created);
            TextView updated = (TextView) convertView.findViewById(R.id.updated);

            Repository repo = getItem(position);

            name.setText(repo.getName());
            fullname.setText(repo.getFullName());
            created.setText(repo.getCreatedAt());
            updated.setText(repo.getUpdatedAt());

            return convertView;
        }
    }
}
