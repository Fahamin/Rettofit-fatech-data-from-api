package app.seeker.rettofitjsondatafatech.jsonplaoption;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.seeker.rettofitjsondatafatech.R;
import app.seeker.rettofitjsondatafatech.marbel.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonPlacehActivity extends AppCompatActivity {

    TextView textView;
    JsonApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_placeh);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.texId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JsonPlacehActivity.this, MainActivity.class));

            }
        });

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://jsonplaceholder.typicode.com/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(JsonApi.class);


       //   getPOSt();
        getCommet();

    }

    private void getCommet() {

        Call<List<JsonModelComment>> call =api.getAllComment("posts/3/comments");

        call.enqueue(new Callback<List<JsonModelComment>>() {
            @Override
            public void onResponse(Call<List<JsonModelComment>> call, Response<List<JsonModelComment>> response) {

                if(response.isSuccessful())
                {
                    List<JsonModelComment> list = response.body();

                    for(JsonModelComment comment : list)
                    {
                        String addString = "";
                        addString += comment.getId()+"\n\n";
                        addString += comment.getPostId() + "\n\n";
                        addString += comment.getName()+ "\n\n";
                        addString += comment.getEmail()+ "\n\n";

                        textView.append(addString);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonModelComment>> call, Throwable t) {

            }
        });
    }

    private void getPOSt() {

        Map<String ,String> param = new HashMap<>();
        param.put("id","3");
        param.put("_sort","desc");


        Call<List<JsonModelPost>> call = api.getAllDATA(param);

        call.enqueue(new Callback<List<JsonModelPost>>() {
            @Override
            public void onResponse(Call<List<JsonModelPost>> call, Response<List<JsonModelPost>> response) {
                if (response.isSuccessful()) {
                    List<JsonModelPost> list = response.body();

                    for (JsonModelPost jsonModel : list) {
                        String result = "";

                        result += "Id" + jsonModel.getId() + "\n";
                        result += "userId" + jsonModel.getUserId() + "\n";
                        result += "title" + jsonModel.getTitle() + "\n\n";
                        result += "body" + jsonModel.getBody() + "\n\n";

                        textView.append(result);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonModelPost>> call, Throwable t) {

            }
        });
    }

}