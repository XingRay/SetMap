package com.xingray.setmap

import org.junit.Test

/**
 * [SetMap2]测试用例
 *
 * @author : leixing
 * @date : 2019/9/14 20:09
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class SetMap2Test {
    @Test
    fun test() {
        val setMap2 = SetMap2<String, String, String>()

        setMap2.add("grade1", "class1", "aaa11")
        setMap2.add("grade1", "class1", "bbb11")

        setMap2.add("grade1", "class2", "aaa12")
        setMap2.add("grade1", "class2", "bbb12")

        setMap2.add("grade2", "class1", "aaa21")
        setMap2.add("grade2", "class1", "bbb21")

        setMap2.add("grade2", "class2", "aaa22")
        setMap2.add("grade2", "class2", "bbb22")

        setMap2.traverse { s, s2, s3 ->
            println("s=$s, s2=$s2, s3=$s3")
        }

        assert(setMap2.size == 8)
    }

    @Test
    fun simulateTest() {
        val setMap2 = SetMap2<String, String, String>()

        setMap2.add("grade1", "class1", "aaa")

        setMap2.add("grade2", "class1", "aaa")
        setMap2.add("grade2", "class2", "aaa")

        setMap2.add("grade3", "class1", "aaa")
        setMap2.add("grade3", "class1", "bbb")

        setMap2.add("grade4", "class1", "aaa")
        setMap2.add("grade4", "class1", "bbb")
        setMap2.add("grade4", "class2", "aaa")
        setMap2.add("grade4", "class2", "bbb")

        setMap2.add("grade5", "class1", "aaa")

        var size = setMap2.size
        assert(size == 10)

        setMap2.remove("grade1", "aaa")
        size--
        assert(setMap2.size == size)

        setMap2.remove("grade2", "aaa")
        size -= 2
        assert(setMap2.size == size)

        setMap2.remove("grade3", "aaa")
        size--
        assert(setMap2.size == size)

        setMap2.remove("grade4", "class1", "aaa")
        size--
        assert(setMap2.size == size)

        setMap2.remove("aaa")
        size = 3
        println(setMap2.size)
        assert(setMap2.size == size)

        setMap2.remove("bbb")
        assert(setMap2.isEmpty())
    }
}