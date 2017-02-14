package com.pmambo.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.transition.Fade.IN;

public class EditItemsClass extends AppCompatActivity {
    String messageText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        messageText = intent.getStringExtra((Intent_Constants.INTENT_MESSAGE_DATA));
        position = intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);
        EditText messageData = (EditText) findViewById(R.id.txtEditMessage);
        messageData.setText(messageText);


    }

    public void saveButtonClicked(View v){
        String changedMessageText = ((EditText) findViewById(R.id.txtEditMessage)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_CHANGED_MESSAGE,changedMessageText);
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
        setResult(Intent_Constants.INTENT_RESULTS_CODE_TWO,intent);
        finish();
    }
}
