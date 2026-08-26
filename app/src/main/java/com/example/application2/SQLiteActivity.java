package com.example.application2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private SqliteClassDbHelper classDbHelper;
    private TextView tv_data_output;
    private EditText et_student_id, et_student_name, et_student_grade, et_student_new_grade, et_sql_command_string;
    private Button btn_insert, btn_update, btn_delete, btn_show_all_data, btn_execute_sql_command;
    private String strOutputTitle = "結果輸出：";
    private static final String DATATABLE_NAME = "students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tv_data_output = findViewById(R.id.tv_data_output);
        btn_insert = findViewById(R.id.btn_insert);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_show_all_data = findViewById(R.id.btn_show_all);
        btn_execute_sql_command = findViewById(R.id.btn_execute_sql_command);
        et_student_id = findViewById(R.id.et_student_id);
        et_student_name = findViewById(R.id.et_student_name);
        et_student_grade = findViewById(R.id.et_student_grade);
        et_student_new_grade = findViewById(R.id.et_student_new_grade);
        et_sql_command_string = findViewById(R.id.et_sql_command_String);

        classDbHelper = new SqliteClassDbHelper(SQLiteActivity.this);
        db = classDbHelper.getWritableDatabase();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                String name;
                double grade;
                long longInsertResult;
                ContentValues cv;

                id = Integer.parseInt(et_student_id.getText().toString());
                name = et_student_name.getText().toString();
                grade = Double.parseDouble(et_student_grade.getText().toString());
                cv = new ContentValues();

                cv.put("student_id", id);
                cv.put("student_name", name);
                cv.put("student_grade", grade);

                longInsertResult = db.insert(DATATABLE_NAME, null, cv);
                if (longInsertResult != -1) {
                    tv_data_output.setText(strOutputTitle + "新增學生資料成功，學號 id ：" + longInsertResult);
                    resetTextView(2500);
                } else {
                    tv_data_output.setText(strOutputTitle + "新增學生資料失敗！！");
                    resetTextView(3000);
                }
            }
        });

        btn_show_all_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, j;
                String[] columnNames;
                String outputString = "";
                String strSQLCommand = "SELECT * FROM students";

                Cursor c = db.rawQuery(strSQLCommand, null);
                columnNames = c.getColumnNames();
                for (i = 0; i < columnNames.length; i++) {
                    outputString += columnNames[i] + "\t\t";
                }
                outputString += "\n";
                c.moveToFirst();

                for (i = 0; i < c.getCount(); i++) {
                    for (j = 0; j < columnNames.length - 1; j++) {
                        outputString += c.getString(j) + "\t\t\t\t\t\t";
                    }
                    outputString += c.getString(j) + "\n";
                    c.moveToNext();
                }

                tv_data_output.setText(strOutputTitle + "\n" + outputString);
            }
        });

    }

    private void resetTextView(int millisecond) {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_data_output.setText(strOutputTitle);
            }
        }, millisecond);
    }

}