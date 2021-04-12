package com.shability.wingshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    lateinit var name_result : TextView
    lateinit var port_result : TextView
    lateinit var result_msg : TextView
    lateinit var code_result : TextView
    lateinit var code_text : TextView
    lateinit var msg_result : TextView
    lateinit var msg_text : TextView

    var name : String? = null
    var port_numb : String? = null
    var result : String? = null
    var code : String? = null
    var msg : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        name_result = findViewById(R.id.name_result)
        port_result = findViewById(R.id.port_result)
        result_msg = findViewById(R.id.result_msg)
        code_result = findViewById(R.id.code_result)
        msg_result = findViewById(R.id.msg_result)
        code_text = findViewById(R.id.code)
        msg_text = findViewById(R.id.msg)


        name = intent.getStringExtra("name")
        port_numb = intent.getStringExtra("port_numb")
        result = intent.getStringExtra("result")

        name_result.text = name.toString()
        port_result.text = port_numb.toString()
        result_msg.text = result.toString()

        if(result.toString().indexOf("실패") > -1) {
            code = intent.getStringExtra("code")
            msg = intent.getStringExtra("msg")
            code_result.text = code.toString()
            msg_result.text = msg.toString()
            code_result.visibility = View.VISIBLE
            msg_result.visibility = View.VISIBLE
            code_text.visibility = View.VISIBLE
            msg_text.visibility = View.VISIBLE
        }



    }
}