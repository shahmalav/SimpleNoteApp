package com.shahmalav.androidprojects.simplenoteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import data.Note;
import data.NotesDataSource;

public class MainActivity extends AppCompatActivity {

    private static final int EDITOR_ACTIVITY_REQUEST_CODE = 999;
    private static final int MENU_DELETE = 1000;

    private int selectedNoteID;
    private NotesDataSource notesDataSource;
    private List<Note> notes;
    private ListView notesView;
    private Button btnCreate;
    private ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesView = (ListView) findViewById(R.id.list);
        btnCreate = (Button)findViewById(R.id.btnCreate);
        registerForContextMenu(getListView());

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editNote(notes.get(position));
            }
        });

        notesDataSource = new NotesDataSource(this);
        refreshDisplay();
    }


    private void editNote(Note note){

        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text",note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST_CODE);
    }

    private void createNote() {
        Note note = Note.getNewNote();
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text",note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST_CODE);
    }

    private void refreshDisplay() {
        notes = notesDataSource.findAll();
        adapter = new ArrayAdapter<Note>(this, R.layout.note,notes);
        notesView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDITOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Note note = new Note();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            notesDataSource.update(note);
            refreshDisplay();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public View getListView() {
        return notesView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedNoteID = (int) info.id;
        menu.add(0,MENU_DELETE,0, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == MENU_DELETE){
            Note note = notes.get(selectedNoteID);
            notesDataSource.remove(note);
            Toast.makeText(this,"Note Deleted Successfully!", Toast.LENGTH_LONG ).show();
            refreshDisplay();
        }

        return super.onContextItemSelected(item);
    }
}
