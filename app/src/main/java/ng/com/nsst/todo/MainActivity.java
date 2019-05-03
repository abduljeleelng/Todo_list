package ng.com.nsst.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    Button saveButton;
    EditText edItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveButton = findViewById(R.id.btnSave);
        edItems = findViewById(R.id.etItems);
        lvItems = findViewById(R.id.lvItem);
        readItems();
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("Ola");
        items.add("Abduljeleel");
        saveButton.setOnClickListener(saveButtonListener);

        /*
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Items Successfully deleted ",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        */
        setUpListView();
    }
    public void AddItems(View v){
        EditText newItems = findViewById(R.id.etItems);
        String itemsTest  = newItems.getText().toString();
        itemsAdapter.add(itemsTest);
        newItems.setText("");
    }
    private void setUpListView(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }
    public View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edItems.getText().length()>0){
                String items = edItems.getText().toString();
                itemsAdapter.add(items);
                writeItems();
                edItems.setText("");
            }
            else
              Toast.makeText(getApplicationContext(), "Enter Items ",Toast.LENGTH_LONG).show();
        }
    };
    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.text");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.text");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
