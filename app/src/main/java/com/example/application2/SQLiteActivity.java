package com.example.application2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    }
}