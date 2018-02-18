package com.example.luchi.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.luchi.nytimessearch.EditNameDialogFragment;
import com.example.luchi.nytimessearch.R;
import com.example.luchi.nytimessearch.model.EndlessScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import com.example.luchi.nytimessearch.adapter.ArticleArrayAdapter;
import cz.msebera.android.httpclient.Header;
import com.example.luchi.nytimessearch.model.Article;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArticleArrayAdapter articleArrayAdapter;
    ArrayList<Article> articles;
    private Intent shareIntent;
    private ShareActionProvider miShareAction;
    private final int REQUEST_CODE = 20;
    String date ;
    String sort ;
    String sport ;
    String fashion;
    String art;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//showEditDialog();
SetupView();
fetchArticles();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
             date = data.getExtras().getString("date");
             sort = data.getExtras().getString("sorted");
             sport = data.getExtras().getString("sport");
            fashion = data.getExtras().getString("fashion");
             art = data.getExtras().getString("art");

            // Toast the name to display temporarily on screen
            Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
            // items.set(code , name);
          //  itemsAdapter.notifyDataSetChanged();
        //    writeItems();

            //items.add(code, name);
        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    public void SetupView(){
     //   etQuery = (EditText) findViewById(R.id.etQuery);
      //  btnSearch = (Button) findViewById(R.id.btnSearch);
        gvResults = (GridView) findViewById(R.id.rgvResults);
        articles = new ArrayList<>();
        articleArrayAdapter = new ArticleArrayAdapter(this ,articles);
    gvResults.setAdapter(articleArrayAdapter);
   gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           Intent i = new Intent(getApplicationContext() , ArticleActivity.class);
           Article article = articles.get(position);

           i.putExtra("article", Parcels.wrap(article));
           startActivity(i);
       }
   });

        gvResults.setOnScrollListener(new EndlessScrollListener() {

            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
       // MenuItem item = menu.findItem(R.id.menu_item_share);
    //    ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
       // Intent shareIntent = new Intent(Intent.ACTION_SEND);
    ///  shareIntent.setType("text/plain");
       //  get reference to WebView
    //    WebView wvArticle = (WebView) findViewById(R.id.wArticle);
      //   pass in the URL currently being used by the WebView
    //   shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());
     //   miShare.setShareIntent(shareIntent);

MenuItem item = menu.findItem(R.id.action_settings);
       // item.setIntent(new Intent(getApplicationContext() , FilterSettingsActivity.class));
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getApplicationContext() , FilterSettingsActivity.class);

                startActivityForResult(i, REQUEST_CODE);
                return true;
            }
        });
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                fetchArticles(query);
                return true;
            }


            public boolean onQueryTextChange(String newText) {
               // fetchArticles(newText);
                return false;
            }
        });






        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void onArticleSearch(View view) {

       String query = etQuery.getText().toString();

       // Toast.makeText(this , "Searching for " + query , Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key" , "c2b1fdf9a00e4c71b862e8b3a2a0d542");
        params.put("page", 0);
        params.put("q", query);
        client.get(url , params , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJson = null;
                try {
                    articleJson = response.getJSONObject("response").getJSONArray("docs");
                    articleArrayAdapter.addAll(Article.fromJSOnArray(articleJson));
               // articleArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
*/
    public void  fetchArticles(String query){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key" , "c2b1fdf9a00e4c71b862e8b3a2a0d542");
        params.put("page", 0);
        params.put("q", query);
        params.put("begin_date",date);
        params.put("sort",sort);
        params.put("news_desk", sport + art + fashion);

        client.get(url , params , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJson = null;
                try {
                    articleJson = response.getJSONObject("response").getJSONArray("docs");
                    articleArrayAdapter.addAll(Article.fromJSOnArray(articleJson));
                    // articleArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void  fetchArticles(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key" , "c2b1fdf9a00e4c71b862e8b3a2a0d542");
        params.put("page", 0);
        params.put("begin_date",date);
        params.put("sort",sort);
        params.put("news_desk",sport);

        client.get(url , params , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJson = null;
                try {
                    articleJson = response.getJSONObject("response").getJSONArray("docs");
                    articleArrayAdapter.addAll(Article.fromJSOnArray(articleJson));
                    // articleArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
