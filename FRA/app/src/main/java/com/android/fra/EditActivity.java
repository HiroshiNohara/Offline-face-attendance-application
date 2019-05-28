package com.android.fra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fra.db.Face;
import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends BaseActivity {

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private List<String> genderList;
    private String currentGender;
    private Face editFace;
    private ProgressDialog progressDialog;
    private String uid;
    private String name;
    private String gender;
    private String phone;
    private String department;
    private String post;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        SlidrConfig.Builder mBuilder = new SlidrConfig.Builder().edge(true);
        SlidrConfig mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.function_edit));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageView imageView = (ImageView) findViewById(R.id.edit_image_view);
        int resource = R.drawable.edit_image;
        if (imageView != null) {
            Glide.with(this).load(resource).into(imageView);
        }

        final TextView uid_textView = (TextView) findViewById(R.id.uid_textView);
        final EditText name_edit = (EditText) findViewById(R.id.name_edit);
        final EditText phone_edit = (EditText) findViewById(R.id.phone_edit);
        final EditText department_edit = (EditText) findViewById(R.id.department_edit);
        final EditText post_edit = (EditText) findViewById(R.id.post_edit);
        final EditText email_edit = (EditText) findViewById(R.id.email_edit);
        Intent intent = getIntent();

        uid = intent.getStringExtra("uid");
        uid_textView.setText(uid);
        spinner = (Spinner) findViewById(R.id.spinner);
        genderList = new ArrayList<String>();
        genderList.add(this.getString(R.string.select_gender_male));
        genderList.add(this.getString(R.string.select_gender_female));
        adapter = new ArrayAdapter<String>(this, R.layout.gender_spinner, genderList);
        spinner.setAdapter(adapter);
        gender = intent.getStringExtra("gender");
        currentGender = gender;
        if (intent.getStringExtra("gender").equals("male")) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (genderList.get(position).equals(EditActivity.this.getString(R.string.select_gender_male))) {
                    currentGender = "male";
                } else {
                    currentGender = "female";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        department = intent.getStringExtra("department");
        post = intent.getStringExtra("post");
        email = intent.getStringExtra("email");
        name_edit.setText(name);
        phone_edit.setText(phone);
        department_edit.setText(department);
        post_edit.setText(post);
        email_edit.setText(email);
        if (intent.getStringExtra("post") != null && !intent.getStringExtra("post").equals("") && !intent.getStringExtra("post").equals("null")) {
            post_edit.setText(intent.getStringExtra("post"));
        } else {
            post_edit.setHint(this.getString(R.string.edit_post));
        }
        if (intent.getStringExtra("email") != null && !intent.getStringExtra("email").equals("") && !intent.getStringExtra("email").equals("null")) {
            email_edit.setText(intent.getStringExtra("email"));
        } else {
            email_edit.setHint(this.getString(R.string.edit_email));
        }

        ImageButton saveEdit = (ImageButton) findViewById(R.id.save_edit);
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEdit() {

        TextView uid_textView = (TextView) findViewById(R.id.uid_textView);
        EditText name_edit = (EditText) findViewById(R.id.name_edit);
        EditText phone_edit = (EditText) findViewById(R.id.phone_edit);
        EditText department_edit = (EditText) findViewById(R.id.department_edit);
        EditText post_edit = (EditText) findViewById(R.id.post_edit);
        EditText email_edit = (EditText) findViewById(R.id.email_edit);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(department_edit.getText().toString())) {
            department_edit.setError(getString(R.string.error_field_required));
            focusView = department_edit;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone_edit.getText().toString())) {
            phone_edit.setError(getString(R.string.error_field_required));
            focusView = phone_edit;
            cancel = true;
        } else if (!isPhoneValid(phone_edit.getText().toString())) {
            phone_edit.setError(getString(R.string.error_invalid_phone));
            focusView = phone_edit;
            cancel = true;
        }

        if (TextUtils.isEmpty(name_edit.getText().toString())) {
            name_edit.setError(getString(R.string.error_field_required));
            focusView = name_edit;
            cancel = true;
        }

        if (!TextUtils.isEmpty(email_edit.getText().toString()) && !isEmailValid(email_edit.getText().toString())) {
            email_edit.setError(getString(R.string.error_invalid_email));
            focusView = email_edit;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!name.equals(name_edit.getText().toString()) || !gender.equals(currentGender) || !phone.equals(phone_edit.getText().toString())
                    || !department.equals(department_edit.getText().toString()) || !post.equals(post_edit.getText().toString()) || !email.equals(email_edit.getText().toString())) {
                showProgress();
                editFace = new Face();
                editFace.setUid(uid_textView.getText().toString());
                editFace.setName(name_edit.getText().toString());
                editFace.setGender(currentGender);
                editFace.setPhone(phone_edit.getText().toString());
                editFace.setDepartment(department_edit.getText().toString());
                editFace.setPost(post_edit.getText().toString());
                editFace.setEmail(email_edit.getText().toString());
                editFace.updateAll("uid = ?", uid_textView.getText().toString());
                hideProgress();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                Toast.makeText(EditActivity.this, R.string.edit_succeed, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getString(R.string.edit_progressBar_hint));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.hide();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11 && phone.matches("[0-9]+");
    }

}