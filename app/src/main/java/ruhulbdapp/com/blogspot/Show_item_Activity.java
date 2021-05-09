package ruhulbdapp.com.blogspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Show_item_Activity extends AppCompatActivity {

    public TextView resource,name,id,updated_at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_);

        resource=findViewById(R.id.resource_id);
        name=findViewById(R.id.name_id);
        id=findViewById(R.id.continent_id_id);
        updated_at=findViewById(R.id.updated_at_id);


        try {
            Intent intent=getIntent();
            resource.setText("resource: "+intent.getStringExtra("resource").toString());
            name.setText("id: "+intent.getStringExtra("id").toString());
            id.setText("Continent_name: "+intent.getStringExtra("Continent_name").toString());
            updated_at.setText("updated_at: "+intent.getStringExtra("updated_at").toString());

        }catch (Exception e)
        {

        }



    }
}