package com.example.ovais.medicure;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllergyActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AllergiesHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    Button butt;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);

        textView = (TextView)findViewById(R.id.textViewplus);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new AllergiesHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButt();
                setTextView();
            }
        });
        updateUI();
    }

    public void onClickButt(){
        final EditText taskEditText = new EditText(this);
        taskEditText.setHint("Type Allergy Here...");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Allergies")

                .setView(taskEditText)
                .setMessage("Add allergies (for e.g. penicillin etc.)")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogue, int which) {

                        if (taskEditText.length()!=0){
                        String task = String.valueOf(taskEditText.getText());
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(AllergiesHelper.TaskEntry.COL_TASK_TITLE, task);
                        db.insertWithOnConflict(AllergiesHelper.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();}
                        else{
                            taskEditText.setText("");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        return;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(AllergiesHelper.TaskEntry.TABLE,
                new String[] {AllergiesHelper.TaskEntry.COL_TASK_TITLE}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(AllergiesHelper.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(index));
            setTextView();
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter);
            setTextView();
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
            setTextView();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(AllergiesHelper.TaskEntry.TABLE, AllergiesHelper.TaskEntry.COL_TASK_TITLE + " = ?", new String[] {task});
        db.close();

        updateUI();

    }

    public void setTextView()
    {
        if (mTaskListView.getCount() == 0)
        {
            textView.setVisibility(View.VISIBLE);

        }
        else
        {
            textView.setVisibility(View.GONE);
        }
    }
}
