# Android AsyncTask 异步任务演示

## 简介

本 Demo 演示 Android 中 AsyncTask 的基本用法，展示了如何在后台执行耗时任务并更新 UI。

## 基本原理

### 什么是 AsyncTask？

AsyncTask 是 Android 提供的一个轻量级异步任务类，专门用于在后台执行耗时操作并在完成后更新 UI。它封装了 Handler 和线程池，简化了异步编程。

### AsyncTask 的泛型参数

```kotlin
AsyncTask<Params, Progress, Result>
```

- **Params**: 执行任务时传入的参数类型
- **Progress**: 后台任务执行过程中的进度类型
- **Result**: 任务执行完成后的返回结果类型

### 生命周期回调

| 方法 | 运行线程 | 用途 |
|------|----------|------|
| onPreExecute() | 主线程 | 任务开始前准备 |
| doInBackground() | 后台线程 | 执行耗时操作 |
| onProgressUpdate() | 主线程 | 更新进度 |
| onPostExecute() | 主线程 | 任务完成后更新 UI |

## 启动和使用

### 环境要求
- Android Studio
- JDK 11+

### 安装和运行
1. 打开项目并运行

## 教程

### 基本使用步骤

1. 继承 AsyncTask 类
2. 重写必要的方法
3. 调用 execute() 方法启动任务

```kotlin
inner class MyAsyncTask : AsyncTask<Int, Int, String>() {

    override fun onPreExecute() {
        // 准备阶段，可以显示加载对话框
    }

    override fun doInBackground(vararg params: Int?): String {
        // 后台执行耗时操作
        // 不能更新 UI
        for (i in 0..100) {
            Thread.sleep(50)
            publishProgress(i)  // 发布进度
        }
        return "完成"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        // 更新进度 UI
    }

    override fun onPostExecute(result: String?) {
        // 更新最终 UI
    }
}

// 启动任务
MyAsyncTask().execute(100)
```

### 注意事项

1. **不要在 doInBackground() 中更新 UI**
2. **必须在主线程创建 AsyncTask**
3. **使用 cancel() 可以取消任务**
4. **注意内存泄漏问题**

### AsyncTask 的限制

- 必须在主线程创建
- 同一个 AsyncTask 只能执行一次
- API 30 已废弃，推荐使用 Kotlin Coroutines
