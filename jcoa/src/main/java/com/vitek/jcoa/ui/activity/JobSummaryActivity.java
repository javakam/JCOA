package com.vitek.jcoa.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.PostObjectRequest;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.LoginUtil;
import com.vitek.jcoa.util.VLogUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责人工作动态汇总 /jc_oa/job_summary.action (POST))    -    只有大队长才可以操作
 * <p>
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0208/2444.html
 */
public class JobSummaryActivity extends GBaseAppCompatActivity<JSONObject> {
    private FrameLayout titleBack;
    private TextView tvTitle;
    private ImageView imgPrint;
    private WebView webView;
    private List<PrintJob> mPrintJobs = new ArrayList<>();
    private PostObjectRequest request;

    @Override
    protected void initVariables() {
        setTranslucentStatus(R.color.titleColor);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_jobsummary;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("负责人工作动态汇总");
        imgPrint = (ImageView) findViewById(R.id.imgPrint);
        FrameLayout framePrint = (FrameLayout) imgPrint.getParent();
        framePrint.setVisibility(View.VISIBLE);
        framePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPrintJob(webView);

            }
        });
        // WebView加载显示本地html文件
        webView = (WebView) this.findViewById(R.id.webview_document);
        initWebView();
//        net();
    }

    private void initWebView() {
        /**
         * 这里需要根据不同的分辨率设置不同的比例,比如 5寸手机设置190 屏幕宽度 > 650 180 4.5寸手机设置170 屏幕宽度> 500
         * 小于 650 160 4寸手机设置150 屏幕宽度> 450 小于 550 150 3 屏幕宽度> 300 小于 450 120 小于
         * 300 100 320×480 480×800 540×960 720×1280
         */
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if (width > 650) {
            this.webView.setInitialScale(190);
        } else if (width > 520) {
            this.webView.setInitialScale(160);
        } else if (width > 450) {
            this.webView.setInitialScale(140);
        } else if (width > 300) {
            this.webView.setInitialScale(120);
        } else {
            this.webView.setInitialScale(100);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                VLogUtil.e(" WebView  Data   " + view.getHitTestResult());
            }
        });
        //OK 2017-05-26 15:12:49    采用下面的方法
        //需要    compile 'org.eclipse.ecf:org.apache.httpcomponents.httpcore:4.4.6.v20170210-0925'
//        byte[] postBys = EncodingUtils.getBytes("day=2017-05-25", "base64");
//        webView.postUrl(JCOAApi.JOB_SUMMARY+ ";jsessionid=" + LoginUtil.getLocalCookie(), postBys);

        //参数     day	yyyy-mm-dd格式	汇总的日期    eg: "day=2017-05-25".getBytes()
        //返回值   data	字符串	要打印的html
        String day = "day=" + TimeUtil.getNowDateyyyyMMdd();
        webView.postUrl(JCOAApi.JOB_SUMMARY
                + ";jsessionid=" + LoginUtil.getLocalCookie(), day.getBytes());
//        webView.loadData(" ","text/html","utf-8");
//        webView.loadUrl("file:/" + savePath + name + ".html");
    }

    /**
     * 在创建了WebView并加载了我们的HTML内容之后，应用就已经几乎完成了属于它的任务。
     * 接下来，我们需要访问PrintManager，创建一个打印适配器，并在最后创建一个打印任务。
     */
    private void createWebPrintJob(WebView webView) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            showShortToast("暂不支持Android 4.4以下版本系统");
            return;
        }

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter("Summary " + TimeUtil.getNowDateMinute());
            // Create a print job with name and adapter instance
            String jobName = getString(R.string.app_name) + "Document";
            PrintJob printJob = printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
            // Save the job object for later status checking
            mPrintJobs.add(printJob);
        }
    }

    @Override
    protected void getNetData() {
        //15.负责人工作动态汇总/jc_oa/job_summary.action (POST))  JobSummaryEntity
        //admin weixiao提示没有操作权限

        //参数     day	yyyy-mm-dd格式	汇总的日期
        //返回值   data	字符串	要打印的html
        String day = "day=" + TimeUtil.getNowDateyyyyMMdd();
        request = CloudPlatformUtil.getInstance().getJobSummary(
                JCOAApi.getJobSummaryMap("2017-05-25"), this);//"2017-05-11"
        request.getJsonString().equals("没有权限进行此操作");
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e("网页获取失败 -- " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(JSONObject response) {
        if (response != null) {
            VLogUtil.w("html content --- " + response.toString());
        }
    }

    /*
     ====POST-NetworkResponse===<!DOCTYPE html><html><head>
     <meta http-equiv='Content-Type'content='text/html; charset=UTF-8'>
     <style><style> div,body,h1,h2{margin:0;padding:0;}</style>
     <title>汇总页</title>
     </head>
     <body><div style='width:756px;margin:0 auto;padding:10px;'>
     <h1 style='text-align: center;font-size:3em;font-weight:900'>工作动态</h1>
     <p style='text-align: center'>【领导及各科室负责人每日工作情况】</p><br/><br/><br/>
     <p style='text-align: center;'> 保定市建设市场稽查大队&nbsp;&nbsp;&nbsp;&nbsp;2017年05月25日星期四</p>
     <hr/><br/><br/><h3>副大队长魏洪利-单位办公<br/>在大队办公，指导建筑市场稽查科工作。</h3>
     <br/><h3>大队长张春晖-单位办公<br/>参加全省城市执法体制改革电视电话会，协调鑫丰近水庭院信访事宜。组织6部门召开房地产中介整治协调会。</h3><br/><h3>科长卢卫东-单位办公<br/>1、组织并召开中介整治领导小组办公室第一次联席会；处理推进“三项制度”有关工作；3、准备回复局创城办关于创建省级文明城市大队工作开展情况；4、着手准备全市中介整治动员大会有关工作。
     </h3><br/><h3>科长刘明-单位办公<br/>1、整理办证难项目。2、指导莲池区住建局对违法施工案件移交。</h3><br/>
     <p style='text-align: left'> 印发：队领导、各科室</p><hr/><br/>
     <p style='text-align: left'> 承编：办公室&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;联系电话：5020500</p>
     <hr/></div></body></html>
     */
}
