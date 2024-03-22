package com.websarva.wings.android.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
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
    private var selectedBond: Bond? = null
    /*
    private inner class ItemClickListener : View.OnClickListener{
        override fun onClick(view: View?) {
            selectedBond?.let{val position = recyclerView.getChildAdapterPosition(view)
                selectedBond =

            }?: run {
                selectedBond = null
            }
        }
    }

     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // メニューリソースを使用してメニューをインフレートする
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        //データベースとDAOの取得,
        val factory = BondViewModelFactory(AppDatabase.getDatabase(applicationContext).bondDao())
        val bondViewModel = ViewModelProvider(this, factory).get(BondViewModel::class.java)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val adapter = BondAdapter(emptyList()) { bond ->
            selectedBond = bond
            // No adapter attached; skipping layoutを抑制のために空のアダプターを設置
        }
        recyclerView.adapter = adapter

        bondViewModel.bondListLiveData.observe(this) { bonds ->
            // アダプターのデータセットを更新
            adapter.updateDataSet(bonds)
            Log.d("TableActivity", "Adapter item count: ${adapter.itemCount}")
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



    //item_bond.xml用
    class BondAdapter(private var bondList: List<Bond>, private val onItemClicked: (Bond) -> Unit) : RecyclerView.Adapter<BondViewHolder>() {
        var selectedPosition = RecyclerView.NO_POSITION

        override fun getItemCount(): Int {
            return bondList.size
        }
        fun updateDataSet(newBondList: List<Bond>) {
            this.bondList = newBondList
            notifyDataSetChanged()
        }

        fun clearSelection() {
            Log.d("Adapter","clearSelection")
            if (selectedPosition != RecyclerView.NO_POSITION) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = RecyclerView.NO_POSITION
                notifyItemChanged(previousSelectedPosition)
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BondViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bond, parent, false)
            Log.d("Adapter","onCreateViewHolder")

            val viewHolder = BondViewHolder(view)
            view.setOnClickListener {
                // ViewHolderの現在の位置を取得
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedBond = bondList[position]
                    onItemClicked(selectedBond)
                    // 以前の選択をクリア
                    clearSelection()
                    // 新しい選択を設定
                    selectedPosition = position
                    // アイテムの背景色を更新
                    notifyItemChanged(position)
                    // クリックされたアイテムの詳細をLogやToastで表示
                    Log.d("Adapter", "Clicked on position: $position")
                    Toast.makeText(view.context, "Selected: ${selectedBond.label}", Toast.LENGTH_SHORT).show()
                }
            }

            return viewHolder
        }




        override fun onBindViewHolder(holder: BondViewHolder, position: Int) {
            Log.d("Adapter","onBindViewHolder")
            val bond = bondList[position]
            holder.bind(bond)
            Log.d("Adapter","onBindViewHolder label:${holder.label.text},${holder.itemView}")


            // アイテムの選択状態を設定
            holder.itemView.setBackgroundColor(if (selectedPosition == position) Color.BLUE else Color.TRANSPARENT)

            // アイテムのクリックイベント
            holder.itemView.setOnClickListener {
                Log.d("Adapter","onBindViewHolder on click,position:${position}")
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)
                }

                val currentPosition = holder.adapterPosition// 現在のアイテムビューの位置を取得
                val selectedBond = bondList[currentPosition]

                onItemClicked(selectedBond)
                selectedPosition = currentPosition
                notifyItemChanged(selectedPosition)
            }
        }





        /*
        inner class LongClickViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            init {
                view.setOnLongClickListener {
                    onItemLongClick(bondList[adapterPosition])
                    true
                }
            }
        }

         */

    }
}