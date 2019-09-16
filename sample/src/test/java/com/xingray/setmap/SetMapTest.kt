package com.xingray.setmap

import org.junit.Test

/**
 * [SetMap]测试用例
 *
 * @author : leixing
 * @date : 2019/9/14 20:09
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class SetMapTest {

    @Test
    fun setMapTest() {
        val setMap = SetMap<String, String>()
        setMap.add("class1", "AAA")
        setMap.add("class1", "BBB")
        setMap.add("class2", "aaa")
        setMap.add("class2", "bbb")

        setMap.get("class1")?.forEach {
            assert(it == "AAA" || it == "BBB")
        }

        setMap.get("class2")?.forEach {
            assert(it == "aaa" || it == "bbb")
        }

        setMap.remove("AAA")
        setMap.remove("aaa")

        setMap.get("class1")?.forEach {
            assert(it == "BBB")
        }

        setMap.get("class2")?.forEach {
            assert(it == "bbb")
        }
    }

    @Test
    fun setMapListenerTest() {
        val setMap = SetMap<String, Callback>()

        val callback1 = object : Callback {
            override fun call() {
                println("callback1")
                setMap.remove(this)
            }
        }

        val callback2 = object : Callback {
            override fun call() {
                println("callback2")
                setMap.remove(this)
            }
        }

        val callback3 = object : Callback {
            override fun call() {
                println("callback3")
                setMap.remove(this)
            }
        }

        val callback4 = object : Callback {
            override fun call() {
                println("callback4")
                setMap.remove(this)
            }
        }

        setMap.add("listeners", callback1)
        setMap.add("listeners", callback2)
        setMap.add("listeners", callback3)
        setMap.add("listeners", callback4)

        setMap.get("listeners")?.forEach {
            it.call()
        }

        assert(setMap.isEmpty())
    }

    interface Callback {
        fun call()
    }
}
