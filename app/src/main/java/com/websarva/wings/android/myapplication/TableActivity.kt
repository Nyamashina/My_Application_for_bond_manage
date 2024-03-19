package com.websarva.wings.android.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TableActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // メニューリソースを使用してメニューをインフレートする
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private var selectedBond: Bond? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        //データベースとDAOの取得,
        val db = AppDatabase.getDatabase(applicationContext)
        val bondDao = db.bondDao()
        val factory = BondViewModelFactory(bondDao)
        val bondViewModel = ViewModelProvider(this, factory).get(BondViewModel::class.java)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val adapter = BondAdapter(emptyList()) { bond ->
            selectedBond = bond
            // No adapter attached; skipping layoutを抑制のために空のアダプターを設置
        }
        recyclerView.adapter = adapter

        bondViewModel.bondListLiveData.observe(this) { bonds ->
            val adapter = BondAdapter(bonds){ selectedBond->
                this.selectedBond = selectedBond
                Log.d("TableActivity", "Selected bond: $selectedBond")
                // 選択状態のUI更新など
            }
            recyclerView.adapter = adapter
            Log.d("Tableactivity","adapter,itemcount : ${adapter},${adapter.itemCount}")
            adapter.notifyDataSetChanged()
        }

        //recyclerView.isNestedScrollingEnabled = true



        fun deleteSelectedBond() {
            selectedBond?.let { bondToDelete ->
                lifecycleScope.launch(Dispatchers.IO) {
                    Log.d("Tableactivity","deleteSelectedBond")
                    bondViewModel.delete(bondToDelete)
                    withContext(Dispatchers.Main) {
                        selectedBond = null  // 選択されたBondのクリア
                        Toast.makeText(this@TableActivity, "削除しました: ${bondToDelete.label}", Toast.LENGTH_LONG).show()
                          // データセットを再取得してRecyclerViewを更新
                    }
                }
            } ?: run {
                Toast.makeText(this, "アイテムを選択してください", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.editButton).setOnClickListener {
            selectedBond?.let { bond ->
                val intent = Intent(this, EditBondActivity::class.java).apply {
                    // BondオブジェクトをIntentに添付
                    putExtra("selectedBond", bond)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(this, "編集するアイテムを選択してください", Toast.LENGTH_SHORT).show()
            }
        }



        // 「メニューに戻る」ボタンのクリックイベント
        findViewById<View>(R.id.backToMenuButton).setOnClickListener {
            // MainActivityに遷移
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteSelectedBond()
        }





        // ViewModelの準備（ViewModelクラスが必要です）
        //val bondViewModel = ViewModelProvider(this).get(BondViewModel::class.java)

        /* Debug時にテストデータを生成
        if (BuildConfig.DEBUG) {
            runBlocking {
                BondRepository(bondDao).generateTestSampleData()
            }
        }

         */


        // LiveDataの監視とRecyclerViewの更新


        /*
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val bondToDelete = selectedBond
            if (bondToDelete == null) {
                Toast.makeText(this, "対象を選択して下さい", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    // データベースから削除を実行
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.bondDao().delete(bondToDelete)

                    // UIスレッドに戻る
                    withContext(Dispatchers.Main) {
                        selectedBond = null  // 選択されたBondのクリア
                        Toast.makeText(this@TableActivity, "削除しました: ${bondToDelete.label}", Toast.LENGTH_LONG).show()

                        // LiveDataの最新の状態を再購読することで、UIを更新
                        bondViewModel.bondListLiveData.observe(this@TableActivity) { bonds ->
                            // Adapterに新しいデータセットを設定し、UIを更新
                            recyclerView.adapter = BondAdapter(bonds) { bond ->
                                selectedBond = bond
                                // ここに必要な処理（例：選択状態のUI更新）を記述
                            }
                        }
                    }
                }
            }
        }

        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val bondToDelete = selectedBond
            if (bondToDelete == null) {
                Toast.makeText(this, "長押しで対象を選択して下さい", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.bondDao().delete(bondToDelete)

                    withContext(Dispatchers.Main) {
                        selectedBond = null  // 選択されたBondのクリア
                        Toast.makeText(this@TableActivity, "削除しました: ${bondToDelete.label}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        上述のコードは整理されました。
        refreshData(recyclerView)
        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteSelectedBond()
        }
　　　　　の部分に対応。
         */

    }
}