package com.shability.wingshare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.util.HashMap

class ChargeStartActivity : AppCompatActivity() {

    val key = "test"
    val url = "http://192.168.219.134:3001/station/request"
    var Code_result : String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_start)


        Code_result = getIntent().getStringExtra("result")
        var str : String = "key=" + URLEncoder.encode("test", "UTF-8") + "&code=" + URLEncoder.encode(Code_result.toString(), "UTF-8")


        val webView: WebView = findViewById(R.id.charge_Webview)

        Log.d("POST", "POST : $str")

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(WebBrideg(this),"Android")
        webView.postUrl(url, str.toByteArray())
        webView.settings.apply {
            this.javaScriptEnabled = true // 자바스크립트 허용
            this.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기 허용
            this.useWideViewPort = true // html의 viewport 메타 태그 지
            this.domStorageEnabled = true
            this.allowFileAccess = true
        }
      WebView.setWebContentsDebuggingEnabled(true);

    //        AuthService(this, url, key, Code_result.toString())
    }


    fun AuthService(context: Context, url: String, key: String, code: String) {

        val requestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response ->
                },
                com.android.volley.Response.ErrorListener { error ->
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()

                }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["key"] = key
                params["code"] = code
                return params
            }
        }
        requestQueue.add(request)

    }

    class WebBrideg(private val context: Context){
        @JavascriptInterface
        fun charge_start(name : String, port_numb : String) {
            Log.d("charge_start", "name : $name")
            Log.d("charge_start", "port_numb : $port_numb")
            Toast.makeText(context, "$name : $port_numb", Toast.LENGTH_SHORT).show()
            var intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("port_numb", port_numb)
            intent.putExtra("result", "성공")
            context.startActivity(intent)
        }

        @JavascriptInterface
        fun charge_fail(name : String, port_numb: String, code: String, msg: String) {
            Log.d("charge_fail", "name : $name")
            Log.d("charge_fail", "port_numb : $port_numb")
            Log.d("charge_fail", "code : $code")
            Log.d("charge_fail", "msg : $msg")
            Toast.makeText(context, "$name $port_numb $code $msg", Toast.LENGTH_SHORT).show()
            var intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("port_numb", port_numb)
            intent.putExtra("result", "실패")
            intent.putExtra("code", code)
            intent.putExtra("msg", msg)
            context.startActivity(intent)
        }

    }



//    fun AuthJson(response: String) {
//        try {
//            Log.d("TAG", "Authresponse : " + response)
//            val j_object = response.replace("[", "")
//            val result = JSONObject(j_object.toString()).getString("result")
//            val numb = JSONObject(j_object.toString()).getString("numb")
//            Log.d("TAG", "result : " + result)
//            Log.d("TAG", "numb : " + numb)
//
//
//
//
//        }catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }
}