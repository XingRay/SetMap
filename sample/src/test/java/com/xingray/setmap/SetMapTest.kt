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
        setMap.add("class1", "aaa")
        setMap.add("class1", "bbb")
        setMap.add("class2", "aaa")
        setMap.add("class2", "bbb")

        assert(setMap.size == 4)

        setMap["class1"]?.forEach {
            assert(it == "aaa" || it == "bbb")
        }

        setMap["class2"]?.forEach {
            assert(it == "aaa" || it == "bbb")
        }

        setMap.remove("aaa")
        assert(setMap.size == 2)

        setMap["class1"]?.forEach {
            assert(it == "bbb")
        }

        setMap["class2"]?.forEach {
            assert(it == "bbb")
        }

        setMap.remove("bbb")
        assert(setMap.isEmpty())
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

        setMap["listeners"]?.forEach {
            it.call()
        }

        assert(setMap.isEmpty())
    }

    interface Callback {
        fun call()
    }
}
