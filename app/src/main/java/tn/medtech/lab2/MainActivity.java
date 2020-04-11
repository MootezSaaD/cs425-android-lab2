package tn.medtech.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView gradesList;
    AutoCompleteTextView studentsList;
    String[][] grades = {
            {"17", "18", "14.5", "19", "20", "17.5"},
            {"12", "14", "16.5", "8", "14", "14"},
            {"19", "20", "17.5", "11", "10", "7.5"},
            {"11", "12.5", "9.5", "20", "6.25", "16.25"},
            {"20", "6.25", "16.25", "17", "18", "14.5"}
    };
    String[] students = {"Alice", "Bob", "Carol", "Chuck", "Craig"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lab2-MootezSaad");
        gradesList = (ListView) findViewById(R.id.gradesList);
        gradesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "My Grade is" + grades[position], Toast.LENGTH_SHORT).show();
            }
        });
        studentsList = (AutoCompleteTextView) findViewById(R.id.studentName);
        studentsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, students));
        studentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idx = Arrays.asList(students).lastIndexOf(((TextView) view).getText());
                MyLineAdapter adapter = new MyLineAdapter(MainActivity.this, grades[idx]);
                gradesList.setAdapter(adapter);
            }
        });
    }
}

class MyLineAdapter extends ArrayAdapter<String> {
    Activity context;
    String[] items;

    public MyLineAdapter(@NonNull Activity context, String[] items) {
        super(context, R.layout.element, items);
        this.context = context;
        this.items = items;
    }
// Method 1
//    @NonNull
//    @Override
//    public View getView(int position, View convertViewn, ViewGroup parent) {
//        LayoutInflater layoutInflater = context.getLayoutInflater();
//        View line = layoutInflater.inflate(R.layout.element, null);
//        TextView label = (TextView) line.findViewById(R.id.grade);
//        ImageView image = (ImageView) line.findViewById(R.id.image);
//        label.setText(items[position]);
//        float grade = Float.valueOf(items[position]);
//        image.setImageResource((grade >= 10)?R.drawable.ic_mood:R.drawable.ic_mood_bad);
//
//        return line;
//    }

// Method 2
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater layoutInflater = context.getLayoutInflater();
//        if (convertView==null) {
//            convertView = layoutInflater.inflate(R.layout.element, null);
//        }
//        TextView label = (TextView) convertView.findViewById(R.id.grade);
//        ImageView image = (ImageView) convertView.findViewById(R.id.image);
//        label.setText(items[position]);
//        float grade = Float.valueOf(items[position]);
//        image.setImageResource((grade >= 10)?R.drawable.ic_mood:R.drawable.ic_mood_bad);
//        return convertView;
//    }

    // Method 3
    static class ViewHolder {
        TextView label;
        ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.element, null);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.grade);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.label.setText(items[position]);
        float grade = Float.valueOf(items[position]);
        viewHolder.image.setImageResource((grade >= 10) ? R.drawable.ic_mood : R.drawable.ic_mood_bad);

        return convertView;
    }

}
