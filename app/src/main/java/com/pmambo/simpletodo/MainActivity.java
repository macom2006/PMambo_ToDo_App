package com.pmambo.simpletodo;

import android.content.Intent;
import android.os.FileUriExposedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String>itemsAdapter;
    ListView lvItems;
    String messageText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items  = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent  = new Intent();
                intent.setClass(MainActivity.this,EditItemsClass.class);
                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA,items.get(position).toString());
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);
            }



        });
        //items.add("First Item");
       // items.add("Second Items");

        setupListViewListener();
        //onActivityResults(requestCode, results, data);

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void onAddItem(View v) {

        EditText eNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = eNewItem.getText().toString();
        itemsAdapter.add(itemText);
        eNewItem.setText("");
        writeItems();
        if(itemText.isEmpty()){
            return;
        }

    }


    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items  = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }

    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
           FileUtils.writeLines(todoFile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultsCode, Intent data){
        if(requestCode==Intent_Constants.INTENT_REQUEST_CODE_TWO){
            messageText = data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);
            items.remove(position);
            items.add(position,messageText);
            itemsAdapter.notifyDataSetChanged();



        }
    }




}
