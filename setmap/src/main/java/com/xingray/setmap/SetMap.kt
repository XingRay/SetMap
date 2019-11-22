@file:Suppress("unused")

package com.xingray.setmap

/**
 * 基于[HashMap]和[Set]实现的`K->Set<V>`型数据结构
 *
 * @author : leixing
 * @date : 2019/9/14 16:37
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 *
 */
class SetMap<K, V> : Map<K, Set<V>?> {

    private var valueMap: MutableMap<K, MutableSet<V>?>? = null
    private var keyMap: MutableMap<V, MutableSet<K>?>? = null

    fun add(k: K, v: V) {
        lazyGetValueSet(k).add(v)
        lazyGetKeySet(v).add(k)
    }

    fun remove(v: V) {
        val vMap = valueMap ?: return
        val kMap = keyMap ?: return
        val keySet = kMap[v] ?: return

        keySet.forEach { k ->
            val set = vMap[k] ?: return@forEach
            set.remove(v)
            if (set.isEmpty()) {
                vMap.remove(k)
            }
        }

        kMap.remove(v)
    }

    fun remove(k: K, v: V) {
        val vMap = valueMap ?: return
        val kMap = keyMap ?: return

        val valueSet = vMap[k]
        if (valueSet != null) {
            valueSet.remove(v)
            if (valueSet.isEmpty()) {
                vMap.remove(k)
            }
        }

        val keySet = kMap[v]
        if (keySet != null) {
            keySet.remove(k)
            if (keySet.isEmpty()) {
                kMap.remove(v)
            }
        }
    }

    override fun get(key: K): Set<V>? {
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

    fun traverse(call: (K, V) -> Unit) {
        val map = valueMap ?: return
        map.entries.forEach { entry ->
            val key = entry.key
            entry.value?.forEach { v ->
                call.invoke(key, v)
            }
        }
    }

    private fun lazyGetValueSet(k: K): MutableSet<V> {
        val map = lazyGetValueMap()
        var set = map[k]
        if (set == null) {
            set = mutableSetOf()
            map[k] = set
        }
        return set
    }

    private fun lazyGetValueMap(): MutableMap<K, MutableSet<V>?> {
        var map = valueMap
        if (map == null) {
            map = HashMap()
            valueMap = map
        }
        return map
    }

    private fun lazyGetKeyMap(): MutableMap<V, MutableSet<K>?> {
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

    override val entries: Set<Map.Entry<K, Set<V>?>>
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

    override val values: Collection<Set<V>?>
        get() = valueMap?.values ?: setOf()

    override fun containsKey(key: K): Boolean {
        return valueMap?.containsKey(key) ?: false
    }

    override fun containsValue(value: Set<V>?): Boolean {
        return valueMap?.containsValue(value) ?: false
    }
}