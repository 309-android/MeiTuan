package com.androidClass.meituan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidClass.meituan.R;
import com.androidClass.meituan.utils.SPUtils;

public class ExitLoginActivity extends AppCompatActivity {

    // 退出登录按钮
    private Button exitLoginButton;
    // 更改用户名按钮
    private Button updateUserNameButton;
    // 修改密码按钮
    private Button updatePasswordButton;
    // 注销按钮
    private Button removeUserButton;
    // 显示手机号按钮
    private Button checkPhoneNumberButton;
    // 返回我的界面
    private ImageButton backToIndividualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_login);

        // 退出登录按钮
        exitLoginButton = findViewById(R.id.exitLogin_Button);
        // 更改用户名按钮
        updateUserNameButton = findViewById(R.id.updateUserName_Button);
        // 修改密码按钮
        updatePasswordButton = findViewById(R.id.updatePassword_Button);
        // 注销按钮
        removeUserButton = findViewById(R.id.removeUser_Button);
        // 显示手机号按钮
        checkPhoneNumberButton = findViewById(R.id.checkPhoneNumber_Button);
        // 返回我的界面
        backToIndividualButton = findViewById(R.id.backToIndividual_Button);

        // 返回我的页面
        backToIndividual();
        // 动态显示手机号
        checkPhoneNumber();
        // 退出登录
        exitLogin();
        // 加载用户名及修改用户名
        updateUserName();
    }

    /**
     * 返回我的页面
     */
    private void backToIndividual() {
        backToIndividualButton.setOnClickListener(v -> {
            startActivity(new Intent(this, IndividualMsgActivity.class));
        });
    }

    /**
     * 动态显示手机号
     */
    private void checkPhoneNumber() {
        // 进入这个页面一定是已登录 不再判断是否登录
        String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
        // 对手机号进行处理
        String[] split = phoneNumber.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 4; i <= 7; i++) {
            split[i] = "*";
        }
        StringBuilder newPhoneNumber = new StringBuilder();
        for (String s : split) {
            newPhoneNumber.append(s);
        }
        checkPhoneNumberButton.setText(newPhoneNumber.toString() + "   >");
    }

    /**
     * 退出登录
     */
    private void exitLogin(){
        // 退出登录逻辑
        exitLoginButton.setOnClickListener(v -> {
            //创建提醒对话框的构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确定退出？");
            builder.setMessage("退出登录后将无法查看订单，重新登录即可查看");
            //设置肯定按钮
            builder.setPositiveButton("确定", (dialog, which) -> {
                // 移除手机号信息
                SPUtils.remove(getApplicationContext(),"phoneNumber");
                // 返回登录界面
                startActivity(new Intent(this, UseMsgCodeActivity.class));
            });
            //设置否定按钮
            builder.setNegativeButton("取消",(dialog, which)->{

            });

            //根据构建器构建对话框对象
            AlertDialog dialog = builder.create();
            dialog.show();

        });
    }

    /**
     * 加载用户名及修改用户名
     */
    private void updateUserName(){
        // 动态显示用户名 默认手机号码
        String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
        updateUserNameButton.setText(phoneNumber + "   >");

        // 修改用户名逻辑
        updateUserNameButton.setOnClickListener(v -> {

        });
    }
}