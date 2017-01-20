package com.rong.hvming;

import android.net.Uri;
import android.text.TextUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MyUserInfoProvider implements RongIM.UserInfoProvider {

    /**
     * 用户信息的提供者：GetUserInfoProvider 的回调方法，获取用户信息。
     *
     * @param userId 用户 Id。
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfo(String userId) {

        if (TextUtils.isEmpty(userId))
            return null;
        Uri uri = Uri.parse("https://dn-i8public.qbox" +
                ".me/cd2977ad-7aee-4db1-9d32-1516a9addf89/491db673-e4c5-4f5e-8704-0033a3408acf" +
                ".jpg?imageView2/1/w/140/h/140");
        Uri uri1 = Uri.parse("https://dn-i8public.qbox" +
                ".me/85e6a2e2-faee-4ea4-9c08-61f903b9ab9e/6f597c6a-acc1-47f4-995a-138d3d048d57" +
                ".jpg?imageView2/1/w/140/h/140");
        if (MainActivity.chenjianId.equals(userId)) {
            return new UserInfo(userId, "邦德", uri);
        } else if (MainActivity.huangzhouId.equals(userId)) {
            return new UserInfo(userId, "小号", uri1);
        }

        return null;
    }

}
