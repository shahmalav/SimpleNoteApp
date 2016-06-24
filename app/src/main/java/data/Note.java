package data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shahm on 6/23/2016.
 */
public class Note {

    private String key;
    private String text;
    private static Locale locale = new Locale("en_US");

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Note getNewNote(){

        locale.setDefault(locale);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        String key = simpleDateFormat.format(new Date());

        Note note = new Note();
        note.setKey(key);
        note.setText("");
        return note;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
