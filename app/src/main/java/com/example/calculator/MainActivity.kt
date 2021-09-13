package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private val currentInputNumSB = StringBuilder()
    //存贮数字和运算符的数组
    private val numsList = mutableListOf<Int>()
    private val operatorList = mutableListOf<String>()
    private var isNumStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //清空按钮
        textView3.setOnClickListener{
            clearButtonClicked(it)
        }
        textView5.setOnClickListener {
            backButtonClicked(it)
        }
        textView7.setOnClickListener{
            operatorButtonClicked(it)
        }
        textView16.setOnClickListener{
            operatorButtonClicked(it)
        }
        textView19.setOnClickListener{
            operatorButtonClicked(it)
        }
        textView23.setOnClickListener{
            operatorButtonClicked(it)
        }

        textView8.setOnClickListener(this)
        textView20.setOnClickListener(this)
        textView15.setOnClickListener(this)
        textView9.setOnClickListener(this)
        textView17.setOnClickListener(this)
        textView18.setOnClickListener(this)
        textView10.setOnClickListener(this)
        textView21.setOnClickListener(this)
        textView22.setOnClickListener(this)
        textView24.setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changdu -> {
                val intent = Intent(this, changdu::class.java)
                startActivityForResult(intent, 1)
            }
            R.id.tiji -> {
                val intent = Intent(this, tiji::class.java)
                startActivityForResult(intent, 1)
            }
            R.id.jinzhi -> {
                val intent = Intent(this, jinzhi::class.java)
                startActivityForResult(intent, 1)
            }

        }
        return true
    }

    override fun onClick(v: View?) {
        numberButtonClicked(v!!)
    }

    //数字键
    fun numberButtonClicked(view: View) {
        //Log.v("myTag", "number")
        //将view强制转换为textview
        val tv = view as TextView
        currentInputNumSB.append(tv.text)
//        //将选中的标题拼接到sb中
//        if (currentInputNumSB.toString() == "0") {
//            currentInputNumSB.clear()
//        }
//        currentInputNumSB.append(tv.text)
//        process_textView.text = currentInputNumSB.toString()

        if (isNumStart == true) {
            //当前输入的是一个新的数字，添加到数组中
            numsList.add(tv.text.toString().toInt())
            //更改状态 不是一个新数字的开始
            isNumStart = false
        }else {
            //用当前的数字去替换数组中最后一个元素
            numsList[numsList.size-1] = currentInputNumSB.toString().toInt()
        }
        //Log.v("myTag", "$numsList")
        //显示内容
        showUI()
        //计算结果
        calculate()
    }

    //运算符键
    fun operatorButtonClicked(view: View) {
        //Log.v("myTag", "operator")
        val tv = view as TextView
        //保存当前运算符
        operatorList.add(tv.text.toString())
        //改变状态
        isNumStart = true
        currentInputNumSB.clear()
        //Log.v("myTag", "$operatorList")
        showUI()
    }

    //清空键
    fun clearButtonClicked(view: View) {
        //Log.v("myTag", "clear")
        process_textView.text = ""
        result_textView.text = "0"
        currentInputNumSB.clear()
        numsList.clear()
        operatorList.clear()
        isNumStart = true
    }

    //撤销键
    fun backButtonClicked(view: View) {
        //Log.v("myTag", "back")
        //判断应该撤销运算符还是数字
        if (numsList.size > operatorList.size) {
            //撤销数字
            if (numsList.size > 0) {
                numsList.removeLast()
                isNumStart = true
                currentInputNumSB.clear()
            }
        }else {
            //撤销运算符
            if (operatorList.size > 0) {
                operatorList.removeLast()
                isNumStart = false
                if (numsList.size > 0) {
                    currentInputNumSB.append(numsList.last())
                }
            }
        }
        showUI()
        calculate()
    }

    //等于键
    fun equalButtonClicked(view: View) {
        Log.v("myTag", "equal")

    }

    //拼接当前运算的表达式 显示到界面上
    private fun showUI() {
        val str = StringBuilder()
        for ((i, num) in numsList.withIndex()) {
            //将当前的数字拼接上去
            str.append(num)
            //判断运算符数组中对应位置是否有内容
            if (operatorList.size > i) {
                //将i对应的运算符拼接到字符串中
                str.append(" ${operatorList[i]}")
            }
        }
        process_textView.text = str.toString()
    }

    //实现逻辑运算功能
    private fun calculate() {
        if (numsList.size > 0) {
            //记录运算符数组遍历时的下标
            var i = 0
            //记录第一个运算数 == 数字数组的第一个数
            var param1 = numsList[0].toFloat()
            var param2 = 0.0f
            if (operatorList.size > 0) {
                while (true) {
                    //获取i对应的运算符
                    val operator = operatorList[i]
                    //判断是不是乘除
                    if (operator == "×" || operator == "÷") {
                        //乘除直接运算
                        //找到第二个运算数
                            if (i+1 < numsList.size) {
                                param2 = numsList[i + 1].toFloat()
                                //运算
                                param1 = realCalculate(param1, operator, param2)
                            }
                    }else {
                        //判断是不是最后一个或者后面不是乘除
                        if (i == operatorList.size-1 || (operatorList[i+1] != "×" &&
                                    operatorList[i+1] != "÷")) {
                            if (i < numsList.size-1) {
                                param2 = numsList[i+1].toFloat()
                                param1 = realCalculate(param1, operator, param2)
                            }
                        }else {
                            //后面有而且是乘 或者 是除
                            var mparam1 = numsList[i+1].toFloat()
                            var mparam2 = 0.0f
                            var j = i + 1
                            while (true) {
                                //获取j对应的运算符
                                if (operatorList[j] == "×" || operatorList[j] == "÷") {
                                    if (j < operatorList.size-1) {
                                        mparam2 = numsList[j+1].toFloat()
                                        mparam1 = realCalculate(mparam1,operatorList[j],mparam2)
                                    }
                                }else {
                                    //之前那个运算符后面所有连续的乘除都运算结束了
                                    break
                                }
                                j++
                                if (j == operatorList.size) {
                                    break
                                }
                            }
                            param2 = mparam1
                            param1 = realCalculate(mparam1,operator,mparam2)
                            i = j - 1
                        }
                    }

                    i++
                    if (i == operatorList.size) {
                        //遍历结束
                        break
                    }
                }
            }
            //显示对应的结果
            result_textView.text = String.format("%.1f", param1)
        }else {
            result_textView.text = "0"
        }
    }

    private fun realCalculate(param1: Float, operator: String,param2: Float) : Float {
        var result = 0.0f
        when (operator) {
            "+" -> {
                result = param1 + param2
            }
            "-" -> {
                result = param1 - param2
            }
            "×" -> {
                result = param1 * param2
            }
            "÷" -> {
                result = param1 / param2
            }
        }
        return result
    }

}