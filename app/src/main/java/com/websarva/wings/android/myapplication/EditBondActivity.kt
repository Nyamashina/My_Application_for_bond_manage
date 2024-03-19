package com.websarva.wings.android.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import com.websarva.wings.android.myapplication.databinding.ActivityEditBondBinding

class EditBondActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IntentからBondオブジェクトを取得
        val bond: Bond? = BundleCompat.getParcelable(intent.extras!!, "bondKey", Bond::class.java)

        // Bondオブジェクトがnullでなければ、各フィールドに値をセット
        bond?.let {
            // ここでbindingを使って、画面の各フィールドにBondの情報を表示する
            // 例:
            binding.editTextLabel.setText(it.label)
            //binding.editTextUSDJPY.setText(it.usdJpyRate.toString())
            // 他のフィールドも同様にセット...
        } ?: run {
            // Bondオブジェクトがnullの場合は、エラーメッセージを表示
            Toast.makeText(this, "エラー: Bondデータをロードできませんでした。", Toast.LENGTH_LONG).show()
            finish() // Activityを終了
        }

        // 保存ボタン等のリスナーをセット
        setupListeners()
    }

    private fun setupListeners() {

        binding.btnSave.setOnClickListener {
            // ここで入力されたデータを取得し、Bondオブジェクトを更新する処理を実装
            // 必要に応じて、結果を呼び出し元に返す
        }

    }
}