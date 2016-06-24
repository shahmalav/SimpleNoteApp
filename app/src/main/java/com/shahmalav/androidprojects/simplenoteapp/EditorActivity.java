package com.shahmalav.androidprojects.simplenoteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import data.Note;

/**
 * Created by shahm on 6/23/2016.
 */
public class EditorActivity extends Activity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Intent intent = this.getIntent();
        note = new Note();
        note.setKey(intent.getStringExtra("key"));
        note.setText(intent.getStringExtra("text"));

        EditText editText = (EditText) findViewById(R.id.noteText);
        editText.setText(note.getText());
    }

    private void save(){
        EditText editText = (EditText) findViewById(R.id.noteText);
        String noteText = editText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("key", note.getKey());
        intent.putExtra("text",noteText);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        save();
    }
}
