package ruhulbdapp.com.blogspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ruhulbdapp.com.blogspot.adapter.Continents_Adapter;
import ruhulbdapp.com.blogspot.model.Continents;
import ruhulbdapp.com.blogspot.utilities.Constant;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Continents_Adapter.In_itemsellect {
   private RetrofitClient retrofitClient;
   private List<Continents> allcontinentslist;
   private RecyclerView recyclerView;
   public Continents_Adapter continents_adapter;
    public SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rec_list_id);

        Retrofit retrofit = new Retrofit
                            .Builder()
                            .baseUrl(Constant.base_url)
                            .addConverterFactory(GsonConverterFactory.create())
                              .build();


        //getallcontinents(retrofit);
        allcontinentslist=new ArrayList<>();

        retrofitClient = retrofit.create(RetrofitClient.class);

        Call<OurMainDataClass> call = retrofitClient.getData(Constant.api_token);

        call.enqueue(new Callback<OurMainDataClass>() {
            @Override
            public void onResponse(Call<OurMainDataClass> call, Response<OurMainDataClass> response) {

                if (response.isSuccessful())
                {
                    allcontinentslist=response.body().getData();

                    continents_adapter=new Continents_Adapter(MainActivity.this,MainActivity.this,allcontinentslist);
                    // recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(continents_adapter);
                    continents_adapter.notifyDataSetChanged();

                    for (Continents continents:allcontinentslist)
                    {
                        Log.d("resource",continents.getResource());
                        Log.d("id",String.valueOf(continents.getId()));
                        Log.d("name",continents.getName());
                        Log.d("updated_at",continents.getUpdated_at());

                    }


                }

            }

            @Override
            public void onFailure(Call<OurMainDataClass> call, Throwable t) {

                Log.d("failed","not data fetch");
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getallcontinents(Retrofit retrofit)
    {
        retrofitClient = retrofit.create(RetrofitClient.class);

        Call<OurMainDataClass> call = retrofitClient.getData(Constant.api_token);

        call.enqueue(new Callback<OurMainDataClass>() {
            @Override
            public void onResponse(Call<OurMainDataClass> call, Response<OurMainDataClass> response) {

                if (response.isSuccessful())
                {
                    allcontinentslist=response.body().getData();

                    for (Continents continents:allcontinentslist)
                    {
                        Log.d("resource",continents.getResource());
                        Log.d("id",String.valueOf(continents.getId()));
                        Log.d("name",continents.getName());
                        Log.d("updated_at",continents.getUpdated_at());
                    }

                }

            }

            @Override
            public void onFailure(Call<OurMainDataClass> call, Throwable t) {

                Log.d("failed","not data fetch");
            }
        });
    }

    @Override
    public void sellect(Continents continents) {

        Intent intent=new Intent(MainActivity.this,Show_item_Activity.class);
        intent.putExtra("resource", continents.getResource());
        intent.putExtra("id", String.valueOf(continents.getId()));
        intent.putExtra("Continent_name", continents.getName());
        intent.putExtra("updated_at", continents.getUpdated_at());
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serch_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_settings).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter serch ..");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(MainActivity.this, "serch...", Toast.LENGTH_SHORT).show();
                continents_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "wait...", Toast.LENGTH_SHORT).show();
                continents_adapter.getFilter().filter(newText);

                return false;
            }
        });






        return true;
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
