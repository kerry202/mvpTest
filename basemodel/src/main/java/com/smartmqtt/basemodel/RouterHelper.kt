package com.smartmqtt.basemodel

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author: kerry
 * date: On $ {DATE}
 */
object RouterHelper {

    @JvmStatic
    fun startActivity(path: String, bundle: Bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation()
    }
    @JvmStatic
    fun startActivity(path: String, parameter: String) {
        ARouter.getInstance().build(path).withString("key", parameter).navigation()
    }
    @JvmStatic
    fun startActivity(path: String, parameter: Boolean) {
        ARouter.getInstance().build(path).withBoolean("key", parameter).navigation()
    }
    @JvmStatic
    fun startActivity(path: String, map: Map<String, Any>) {
        val build = ARouter.getInstance().build(path)
        map.forEach {
            build.withObject(it.key, it.value)
        }
        build.navigation()
    }

    @JvmStatic
    fun startActivity(path: String) {
        ARouter.getInstance().build(path).navigation()
    }

}