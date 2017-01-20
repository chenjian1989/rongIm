
package com.rong.hvming;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String token = "R6Ur/VRJrSkX5VfRkr4qofZPzbyLS2kLpjlNCK7pbaz2vBympLKGPE3Lz2bzy/KlbBVIq" +
            "/Tz800RqEbt9jR5VinMn6iC47zYlpe3SFc3R6MoDNjnTLPOBsQlG5ewPLTaLFlONMv0TN0=";

    private static final String token_huang =
            "0T1tIDL0zzSowtADyCHjVPZPzbyLS2kLpjlNCK7pbaz2vBympLKGPGd77vf2WBL8TeFLBiRFsRo0ouGRu8OJPF3UrHq2N4DmaEgIAsfTcasP8RCd+Mur6zAUFdJNXqufZs9QzOVDrd8=";

    public static final String huangzhouId = "fe33aba7-0491-4569-ad22-bd6b9b240d6e";

    public static final String chenjianId = "491db673-e4c5-4f5e-8704-0033a3408acf";

    private Button mBtn_connect_huang;

    private boolean isChenjian = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button mBtn_connect = (Button) findViewById(R.id.btn_connect);
        mBtn_connect_huang = (Button) findViewById(R.id.btn_connect_huang);
        Button mBtn_list = (Button) findViewById(R.id.btn_list);
        Button mBtn_faqi = (Button) findViewById(R.id.btn_faqi);
        Button mBtn_service = (Button) findViewById(R.id.btn_service);

        mBtn_service.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtn_connect_huang.setVisibility(View.VISIBLE);
            }
        });

        mBtn_connect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isChenjian = true;
                connect(token);
            }
        });

        mBtn_connect_huang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isChenjian = false;
                connect(token_huang);
            }
        });

        mBtn_list.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(MainActivity.this);
            }
        });

        mBtn_faqi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动会话界面
                if (RongIM.getInstance() != null) {
                    if (isChenjian) {
                        RongIM.getInstance().startPrivateChat(MainActivity.this,
                                huangzhouId, "对话");
                    } else {
                        RongIM.getInstance().startPrivateChat(MainActivity.this,
                                chenjianId, "对话");
                    }
                }
            }
        });
    }

    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
                 * Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.e("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.e("LoginActivity", "--onSuccess:   " + userid);
                    if (RongIM.getInstance() != null) {
                        // 设置自己发出的消息监听器。
                        RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
                    }
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (mBtn_connect_huang != null) {
            mBtn_connect_huang.setVisibility(View.GONE);
        }
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}