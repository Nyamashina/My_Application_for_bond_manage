package com.websarva.wings.android.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.websarva.wings.android.myapplication.databinding.ActivityAddBondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddBondActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // メニューリソースを使用してメニューをインフレートする
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    // View Bindingのインスタンスを初期化します。
    private lateinit var binding: ActivityAddBondBinding

    /*
    private fun setupDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel(editText, calendar)
            }
        editText.setOnClickListener {
            DatePickerDialog(
                this@AddBondActivity, date, calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }



    private fun updateLabel(editText: EditText, calendar: Calendar) {
        val myFormat = "yyyy-MM-dd"// In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(calendar.time))
    }

     */


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("AddBondActivity","Oncreate")
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        binding = ActivityAddBondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val label = binding.editTextLabel.text.toString()
        val usdJpyRate = binding.editTextUSDJPY.text.toString().toDoubleOrNull()
        val purchasePrice = binding.editTextTannka.text.toString().toDoubleOrNull()
        val valuation = binding.editTextHyoka.text.toString().toDoubleOrNull()
        val interestRate = binding.editTextRiritu.text.toString().toDoubleOrNull()
        val yield = binding.editTextRimawari.text.toString().toDoubleOrNull()
        val issuerRating = binding.editTextKakuduke.text.toString()
        val remainingYears = binding.editTextNennsuu.text.toString().toDoubleOrNull()
        val quantity = binding.editTextSuuryou.text.toString().toDoubleOrNull()

         */

        var interestPaymentDate: Date? = null
        var redemptionDate: Date? = null

        binding.editTextRibaraibi.setOnClickListener {
            Log.d("AddBondActivity","EDITTEXTRibaraibi")
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@AddBondActivity, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.editTextRibaraibi.setText(dateFormat.format(calendar.time))
                interestPaymentDate = calendar.time // interestPaymentDateを更新
                Log.d("AddBondActivity","DataPickerRibaraibi")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.editTextSyoukann.setOnClickListener {
            Log.d("AddBondActivity","EDITTEXTSyoukann")
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@AddBondActivity, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.editTextSyoukann.setText(dateFormat.format(calendar.time))
                redemptionDate = calendar.time // redemptionDateを更新
                Log.d("AddBondActivity","DataPickerSyoukann")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }




        binding.buttonSaveBond.setOnClickListener {
            Log.d("AddBondActivity","SAVEBond")
            // 各フィールドからの入力を取得
            val label = binding.editTextLabel.text.toString().trim()
            val usdJpyRateStr = binding.editTextUSDJPY.text.toString()
            val purchasePriceStr = binding.editTextTannka.text.toString()
            val valuationStr = binding.editTextHyoka.text.toString()
            val interestRateStr = binding.editTextRiritu.text.toString()
            val yieldStr = binding.editTextRimawari.text.toString()
            val issuerRating = binding.editTextKakuduke.text.toString().trim()
            val remainingYearsStr = binding.editTextNennsuu.text.toString()
            val quantityStr = binding.editTextSuuryou.text.toString()

            // 数値フィールドのパースを試みる
            val usdJpyRate = usdJpyRateStr.toDoubleOrNull()
            val purchasePrice = purchasePriceStr.toDoubleOrNull()
            val valuation = valuationStr.toDoubleOrNull()
            val interestRate = interestRateStr.toDoubleOrNull()
            val yield = yieldStr.toDoubleOrNull()
            val remainingYears = remainingYearsStr.toDoubleOrNull()
            val quantity = quantityStr.toDoubleOrNull()

            // すべてのフィールドが記入されているか確認
            if (label.isBlank() || usdJpyRate == null || purchasePrice == null || valuation == null ||
                interestRate == null || yield == null || interestPaymentDate == null ||
                redemptionDate == null || issuerRating.isBlank() || remainingYears == null ||
                quantity == null) {
                // 何かが欠けている場合はエラーメッセージを表示
                Toast.makeText(this, "すべてのフィールドを正しく入力してください", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            // 非同期でデータベースに保存
            // 実際のデータベースや他の保存方法へのBondオブジェクトの保存処理を実装する
            // Roomデータベースを使用。ここでDAOのsaveメソッドを呼び出す
            lifecycleScope.launch(Dispatchers.IO) {
                //val database = DatabaseBuilder.getInstance(applicationContext)
                val database = AppDatabase.getDatabase(applicationContext)

                // Bondインスタンス
                val bond = Bond(
                    label = label,
                    usdJpyRate = usdJpyRate,
                    purchasePrice = purchasePrice,
                    valuation = valuation,
                    interestRate = interestRate,
                    yield = yield,
                    interestPaymentDate = interestPaymentDate,
                    redemptionDate = redemptionDate,
                    issuerRating = issuerRating,
                    remainingYears = remainingYears,
                    quantity = quantity
                )

                database.bondDao().insert(bond)


                // 保存成功のメッセージと失敗時
                withContext(Dispatchers.Main) {
                    // UIスレッド上に戻す
                    Toast.makeText(this@AddBondActivity, "債券を保存しました: $label", Toast.LENGTH_LONG).show()
                }
                startActivity(intent)

            }


        }

        binding.backToMenuButton.setOnClickListener {
            // MainActivityに遷移
            startActivity(intent)
        }
    }
}