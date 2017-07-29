package com.vitek.jcoa.net;

import android.content.Context;
import android.text.TextUtils;

import com.misdk.net.ResponseListener;
import com.misdk.util.NetUtil;
import com.misdk.util.SharePreUtil;
import com.vitek.jcoa.Constant;

import java.util.Map;

/**
 * Created by javakam on 2017/5/16.
 */
public class LoginUtil {
    private Context mContext;
    private  static SharePreUtil sharePreUtil;
    /*
   内存泄露
    */
//    private static SharePreUtil sharePreUtil;
    private ResponseListener mResponseListener;
    /**
     * #link CookiePostRequest.class  in  misdk
     */
    public static final String COOKIE = "Cookie";

    public LoginUtil(Context context) {
        this.mContext = context;
        if (sharePreUtil==null) {
            sharePreUtil = new SharePreUtil(context, Constant.SPF_LOGIN);
        }
    }

    /**
     * login methond
     *
     * @param params
     */
    public void login(Map<String, String> params, ResponseListener mResponseListener) {
        this.mResponseListener = mResponseListener;
        if (NetUtil.isAvailable(mContext)) {
            CloudPlatformUtil.getInstance().loginMethod(params, mResponseListener, getLocalCookie());
        }
    }

    // ==============================Cookie=============================//

    public  void setLocalCookie(String value) {
        sharePreUtil.setValue(Constant.SP_LOGIN_COOKIE, value);
        sharePreUtil.commit();
    }

    public static  String getLocalCookie() {
        return sharePreUtil.getValue(Constant.SP_LOGIN_COOKIE);
    }
    /*
    内存泄露
     */
//    public static String getLocalCookie() {
//        return sharePreUtil.getValue(Constant.SP_LOGIN_COOKIE);
//    }
// ==============================Cookie End=============================//

    /**
     * Login by token value<p>
     * get login information from SP --- in onResponse()  check right !
     *
     * @return token value null or not (if you never login cck count ,token is null)
     */
    public void loginByToken(String token, ResponseListener mResponseListener) {
        this.mResponseListener = mResponseListener;
        if (token != "") {
//            L.e("token-welAct---" + token);
//            login(QUIZApi.getLoginMap("", "", token, ""), mResponseListener);
        }
    }

    /**
     * 判断用户是否登录
     *
     * @return true 已登录 false 未登录
     */
    public static boolean isLogin(Context context) {
        boolean flag = false;
        if (!TextUtils.isEmpty(getUserId(context))) {
            flag = true;
        }
        return flag;
    }

    /**
     * Get UserID(uid)
     *
     * @param context
     * @return uid
     */
    public static String getUserId(Context context) {
        return sharePreUtil.getValue(Constant.SP_USER_ID);
    }

    /**
     * Get UserName
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        return sharePreUtil.getValue(Constant.SP_USER_NAME);
    }

    /**
     * Save user data
     */
    public void saveLoginData(String user, String passwd) {
        sharePreUtil.setValue(Constant.SP_USER_NAME, user);//account
        sharePreUtil.setValue(Constant.SP_USER_PWD, passwd);//password
        sharePreUtil.commit();
//        VLogUtil.e(user + "---" + passwd);
    }


    /**
     * (注：token  如果存在此值其他不传)
     *
     * @return token值    为""则表示第一次登陆或是token已过期
     */
    public String[] getLoginData() {
        String[] args = new String[2];
        String name = sharePreUtil.getValue(Constant.SP_USER_NAME);
        String pwd = sharePreUtil.getValue(Constant.SP_USER_PWD);
//        String token = sharePreUtil.getValue(Constant.SP_USER_TOKEN);
//        if (!StringUtils.isBlank(token)) {
//            return token;
//        }
        args[0] = name;
        args[1] = pwd;
        return args;
//        return StringUtils.isBlank(name) || StringUtils.isBlank(pwd) ? null : null;
    }

    /**
     * 退出  清空TaskStack
     */
//    public static void logOut(final Activity activity) {
//        synchronized (LoginUtil.class.getSimpleName()) {
//            SharePreUtil sp = new SharePreUtil(activity, Constant.SPF_LOGIN);
//            sp.clear();
//            sp.commit();
//            activity.finish();
//            System.gc();
//            Intent logoutIntent = new Intent(activity, LoginActivity.class);
//            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            activity.startActivity(logoutIntent);
//            //sharePreUtil.remove(Constant.SP_USER_NAME);//账号 //密码//token值  //uid
//        }
//    }
}