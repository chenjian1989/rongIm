
package com.rong.hvming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends Activity {

    public static String chenjianId = "491db673-e4c5-4f5e-8704-0033a3408acf";
    private static String token = "R6Ur/VRJrSkX5VfRkr4qofZPzbyLS2kLpjlNCK7pbaz2vBympLKGPE3Lz2bzy/KlbBVIq" +
            "/Tz800RqEbt9jR5VinMn6iC47zYlpe3SFc3R6MoDNjnTLPOBsQlG5ewPLTaLFlONMv0TN0=";
    public static String huangzhouId = "fe33aba7-0491-4569-ad22-bd6b9b240d6e";
    private static String token_huang =
            "0T1tIDL0zzSowtADyCHjVPZPzbyLS2kLpjlNCK7pbaz2vBympLKGPGd77vf2WBL8TeFLBiRFs" +
                    "Ro0ouGRu8OJPF3UrHq2N4DmaEgIAsfTcasP8RCd+Mur6zAUFdJNXqufZs9QzOVDrd8=";

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
        final Button mBtn_moshi1 = (Button) findViewById(R.id.btn_moshi1);
        final Button mBtn_moshi2 = (Button) findViewById(R.id.btn_moshi2);
        final Button mBtn_moshi3 = (Button) findViewById(R.id.btn_moshi3);

        mBtn_moshi1.setTextColor(getResources().getColor(R.color.colorAccent));

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
                                huangzhouId, "小号");
                    } else {
                        RongIM.getInstance().startPrivateChat(MainActivity.this,
                                chenjianId, "邦德");
                    }
                }
            }
        });

        mBtn_moshi1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chenjianId = "491db673-e4c5-4f5e-8704-0033a3408acf";
                huangzhouId = "fe33aba7-0491-4569-ad22-bd6b9b240d6e";
                token = "R6Ur/VRJrSkX5VfRkr4qofZPzbyLS2kLpjlNCK7pbaz2vBympLKGPE3Lz2bzy/KlbBVIq" +
                        "/Tz800RqEbt9jR5VinMn6iC47zYlpe3SFc3R6MoDNjnTLPOBsQlG5ewPLTaLFlONMv0TN0=";
                token_huang = "0T1tIDL0zzSowtADyCHjVPZPzbyLS2kLpjlNCK7pbaz2vBympLKGPGd77vf2WBL8Te" +
                        "FLBiRFsRo0ouGRu8OJPF3UrHq2N4DmaEgIAsfTcasP8RCd+Mur6zAUFdJNXqufZs9QzOVDrd8=";

                mBtn_moshi1.setTextColor(getResources().getColor(R.color.colorAccent));
                mBtn_moshi2.setTextColor(getResources().getColor(R.color.white));
                mBtn_moshi3.setTextColor(getResources().getColor(R.color.white));
            }
        });

        mBtn_moshi2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chenjianId = "88666027-19aa-437f-8993-4d3c3af08e27";
                huangzhouId = "4b9c0005-4928-4d89-b65b-02a797adba8c";
                token = "9qiM1ymrK5XHYaHpQDeutj5vF9sObSgBakUFjnrhNWexNXc3kdq2Csy1UNRZ/teegp03" +
                        "/HmEiarSdu8QlaYPTyT/3Q8pwORB377jbX3rc0oI2UBD8BAXW/RjCblwRWxmLUx+K/FOJEY=";
                token_huang = "DbUc7wlW7HcXCPRcCg3WegmLx95uJB0/sNLM+Tk9D/wh4YuJ4ayx11zpv8OwSgawueiLxVdkGX" +
                        "aEiKlGk3ADpq/HFsO+SNiQgr72+pcD/0bDdZs81hI2F91L01W+vnMrcU9yMvwocNU=";

                mBtn_moshi2.setTextColor(getResources().getColor(R.color.colorAccent));
                mBtn_moshi1.setTextColor(getResources().getColor(R.color.white));
                mBtn_moshi3.setTextColor(getResources().getColor(R.color.white));
            }
        });

        mBtn_moshi3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chenjianId = "e6020046-033d-4da5-8511-4c81cd8fc172";
                huangzhouId = "760d4f89-302f-4749-8e22-eecbb00491ba";
                token = "FBvu9JMUS9OJslDR26Ln6H+/KT2lZJqIMMrAxmbYewbmX0RDzs7p1TU3URg1zxj0D0zrqj1lwwoVwPrIr" +
                        "6nU6ZUhDAq+6lgvRkNrsCJlCQHtGOntU1gFuZIusLnQqumQvEL/HAINT3E=";
                token_huang =
                        "lqKLAOf1e+30hpCOxXqB5gmLx95uJB0/sNLM+Tk9D/wh4YuJ4ayx14TzOyiGRDJE2CbMz6e8JTxL1Bwe" +
                                "0jSmKMW9dEkZJg06Qm1ACt79Enr1t7jM1TZtfUh2UEr0QtWBzvNXeuMLWeo=";

                mBtn_moshi3.setTextColor(getResources().getColor(R.color.colorAccent));
                mBtn_moshi2.setTextColor(getResources().getColor(R.color.white));
                mBtn_moshi1.setTextColor(getResources().getColor(R.color.white));
            }
        });

        App.getInstance().CheckVersion(MainActivity.this);
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

                    LogUtil.e("RongIM.connect--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    LogUtil.e("RongIM.connect--onSuccess:   " + userid);
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
                    LogUtil.e("RongIM.connect--onError" + errorCode);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("MainActivity--onDestroy()");
    }
}
