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

        setMap2.forEach { s, s2, s3 ->
            println("s=$s, s2=$s2, s3=$s3")
        }

        assert(setMap2.size == 8)
    }
}