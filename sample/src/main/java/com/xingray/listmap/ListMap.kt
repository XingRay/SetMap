@file:Suppress("unused")

package com.xingray.listmap

import java.util.concurrent.CopyOnWriteArrayList

/**
 * 基于[HashMap]和[java.util.LinkedList]实现的`K->List<V>`型数据结构
 *
 * @author : leixing
 * @date : 2019/9/14 16:37
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 * todo List<V> => Set<V>
 *
 */
class ListMap<K, V> : Map<K, List<V>> {

    private var valueMap: MutableMap<K, MutableList<V>>? = null
    private var keyMap: MutableMap<V, MutableSet<K>>? = null

    fun add(k: K, v: V) {
        lazyGetValueList(k).add(v)
        lazyGetKeySet(v).add(k)
    }

    fun remove(v: V) {
        val vMap = valueMap ?: return
        val kMap = keyMap ?: return
        val keySet = kMap[v] ?: return

        keySet.forEach { k ->
            val list = vMap[k]
            if (list != null) {
                list.remove(v)
                if (list.isEmpty()) {
                    vMap.remove(k)
                }
            }
        }

        kMap.remove(v)
    }

    fun remove(k: K, v: V) {
        val vMap = valueMap ?: return
        val kMap = keyMap ?: return

        val list = vMap[k]
        if (list != null) {
            list.remove(v)
            if (list.isEmpty()) {
                vMap.remove(k)
            }
        }

        val keepKey = list?.contains(v) ?: false
        if (!keepKey) {
            val keySet = kMap[v]
            if (keySet != null) {
                keySet.remove(k)
                if (keySet.isEmpty()) {
                    kMap.remove(v)
                }
            }
        }
    }

    override fun get(key: K): List<V>? {
        return valueMap?.get(key)
    }

    fun clear() {
        valueMap?.clear()
        keyMap?.clear()
    }

    fun contains(v: V): Boolean {
        return keyMap?.containsKey(v) ?: false
    }

    fun sizeOf(k: K): Int {
        val map = valueMap ?: return 0
        return map[k]?.size ?: 0
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    fun forEach(call: (K, V) -> Unit) {
        val map = valueMap ?: return
        map.entries.forEach { entry ->
            val key = entry.key
            entry.value.forEach { v ->
                call.invoke(key, v)
            }
        }
    }

    private fun lazyGetValueList(k: K): MutableList<V> {
        val map = lazyGetValueMap()
        var list = map[k]
        if (list == null) {
            list = CopyOnWriteArrayList()
            map[k] = list
        }
        return list
    }

    private fun lazyGetValueMap(): MutableMap<K, MutableList<V>> {
        var map = valueMap
        if (map == null) {
            map = HashMap()
            valueMap = map
        }
        return map
    }

    private fun lazyGetKeyMap(): MutableMap<V, MutableSet<K>> {
        var map = keyMap
        if (map == null) {
            map = mutableMapOf()
            keyMap = map
        }
        return map
    }

    private fun lazyGetKeySet(v: V): MutableSet<K> {
        val map = lazyGetKeyMap()
        var keySet = map[v]
        if (keySet == null) {
            keySet = mutableSetOf()
            map[v] = keySet
        }
        return keySet
    }

    override val entries: Set<Map.Entry<K, List<V>>>
        get() = valueMap?.entries ?: setOf()

    override val keys: Set<K>
        get() = valueMap?.keys ?: setOf()

    override val size: Int
        get() {
            val map = valueMap ?: return 0
            var sum = 0

            map.keys.forEach {
                sum += sizeOf(it)
            }
            return sum
        }

    override val values: Collection<List<V>>
        get() = valueMap?.values ?: listOf()

    override fun containsKey(key: K): Boolean {
        return valueMap?.containsKey(key) ?: false
    }

    override fun containsValue(value: List<V>): Boolean {
        return valueMap?.containsValue(value) ?: false
    }
}