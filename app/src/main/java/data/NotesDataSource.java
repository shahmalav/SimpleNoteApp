package data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by shahm on 6/23/2016.
 */
public class NotesDataSource {

    private static final String PREFKEY = "notes";
    private SharedPreferences sharedPreferences;

    public NotesDataSource(Context context){
        sharedPreferences = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public List<Note> findAll(){
        Map<String, ?> notesMap = sharedPreferences.getAll();

        SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());

        List<Note> noteList = new ArrayList<Note>();

        for (String key: keys) {
            Note note = new Note();
            note.setKey(key);
            note.setText((String) notesMap.get(key));
            noteList.add(note);
        }

        return noteList;
    }

    public boolean update(Note note){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(note.getKey(),note.getText());
        editor.commit();
        return true;
    }

    public boolean remove(Note note){

        if (sharedPreferences.contains(note.getKey())) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(note.getKey());
            editor.commit();
        }
        return true;
    }

}
