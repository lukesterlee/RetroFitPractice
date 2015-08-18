package lukesterlee.c4q.nyc.retrofitpractice;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {

    @GET("/users/{user}/repos")
    List<Repository> getRepos(@Path("user") String user);

    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repository>> cb);
}
