package demos

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * AsyncTask 异步任务演示
 * 展示如何在后台执行任务并更新 UI
 */
class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var startBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultText)
        progressBar = findViewById(R.id.progressBar)
        startBtn = findViewById(R.id.startBtn)

        startBtn.setOnClickListener {
            // 执行异步任务
            val task = MyAsyncTask()
            task.execute(100) // 传入参数
        }
    }

    /**
     * AsyncTask 的泛型参数：
     * Params: 执行任务时传入的参数类型
     * Progress: 后台任务执行过程中的进度类型
     * Result: 任务执行完成后的返回结果类型
     */
    inner class MyAsyncTask : AsyncTask<Int, Int, String>() {

        // 在后台任务执行前调用，运行在主线程
        override fun onPreExecute() {
            super.onPreExecute()
            resultText.text = "开始执行任务..."
            progressBar.progress = 0
            startBtn.isEnabled = false
        }

        // 在后台线程执行耗时操作
        // 这个方法中不能更新 UI
        override fun doInBackground(vararg params: Int?): String {
            val maxProgress = params[0] ?: 100

            for (i in 0..maxProgress) {
                // 模拟耗时操作
                Thread.sleep(50)

                // 发布进度更新
                publishProgress(i)
            }

            return "任务完成！"
        }

        // 在后台任务执行过程中调用，运行在主线程
        // 用于更新进度 UI
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val progress = values[0] ?: 0
            progressBar.progress = progress
            resultText.text = "进度: $progress%"
        }

        // 在后台任务执行完成后调用，运行在主线程
        // 用于更新最终结果 UI
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            resultText.text = result ?: "任务完成"
            startBtn.isEnabled = true
        }

        // 在任务被取消时调用
        override fun onCancelled() {
            super.onCancelled()
            resultText.text = "任务已取消"
            startBtn.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消正在执行的任务，避免内存泄漏
        // 注意：这里的实现需要保存 AsyncTask 实例
    }
}
