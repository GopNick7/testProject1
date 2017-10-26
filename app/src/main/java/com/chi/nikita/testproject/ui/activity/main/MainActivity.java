package com.chi.nikita.testproject.ui.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chi.nikita.testproject.R;
import com.chi.nikita.testproject.data.model.UserModel;
import com.chi.nikita.testproject.ui.activity.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView {

    private EditText edtId, edtFirstName, edtLastName, edtPhone;
    private Button btnInsert, btnLoadAll, btnUpdate, btnDelete;
    private TextView tvData;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl();
//        presenter.bindView(this);

        init();
    }

    @Override
    public void onClick(View view) {
        final UserModel userModel = new UserModel();
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
                final List<UserModel> userModelList = presenter.getAllUsers();
                final StringBuilder sb = new StringBuilder();
                for(UserModel userModel1 : userModelList){
                    sb.append(userModel1.getId());
                    sb.append(" - ");
                    sb.append(userModel1.getFirstName());
                    sb.append(" - ");
                    sb.append(userModel1.getLastName());
                    sb.append(" - ");
                    sb.append(userModel1.getPhone());
                    sb.append("\n");
                    tvData.setText(sb);
                }
                break;
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

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnLoadAll = (Button) findViewById(R.id.btnLoadAll);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnLoadAll.setOnClickListener(this);

        tvData = (TextView) findViewById(R.id.tvData);
    }
}
