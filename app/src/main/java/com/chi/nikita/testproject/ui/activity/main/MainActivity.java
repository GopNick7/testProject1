package com.chi.nikita.testproject.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chi.nikita.testproject.R;
import com.chi.nikita.testproject.data.model.UserModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView, TextWatcher {

    private EditText edtId, edtFirstName, edtLastName, edtPhone;
    private Button btnInsert, btnLoadAll, btnUpdate, btnDelete;
    private TextView tvData;
    private MainPresenter<MainView> presenter;
    private UserModel userModel;
    private StringBuilder showUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl();
        presenter.bindView(this);

        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInsert:
                userModel.setFirstName(getValue(edtFirstName));
                userModel.setLastName(getValue(edtLastName));
                userModel.setPhone(getValue(edtPhone));
                presenter.insertUser(userModel);
                break;
            case R.id.btnUpdate:
                userModel.setFirstName(getValue(edtFirstName));
                userModel.setLastName(getValue(edtLastName));
                userModel.setPhone(getValue(edtPhone));
                presenter.updateUser(Integer.parseInt(getValue(edtId)), userModel);
                break;
            case R.id.btnDelete:
                presenter.deleteUser(Integer.parseInt(getValue(edtId)));
                break;
            case R.id.btnLoadAll:
                presenter.getAllUsers();
                break;
        }
    }

    @Override
    public void onUserCRUD(@NonNull UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onShowUsers(final StringBuilder showUsers) {
        this.showUsers = showUsers;
        tvData.setText(showUsers);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            setButtonEnabled(false);
        } else {
            setButtonEnabled(true);
        }
    }

    private String getValue(EditText edtText) {
        return edtText.getText().toString().trim();
    }

    private void init() {
        edtId = (EditText) findViewById(R.id.edtID);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);

        edtId.addTextChangedListener(this);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnLoadAll = (Button) findViewById(R.id.btnLoadAll);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        setButtonEnabled(false);

        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnLoadAll.setOnClickListener(this);

        tvData = (TextView) findViewById(R.id.tvData);
    }

    private void setButtonEnabled(Boolean bool) {
        btnUpdate.setEnabled(bool);
        btnDelete.setEnabled(bool);
    }
}
