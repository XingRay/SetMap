package com.xingray.setmap

/**
 * 基于[HashMap]和[Set]实现的`K0->K1->Set<V>`型数据结构
 *
 * @author : leixing
 * @date : 2019/9/14 16:56
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
@Suppress("unused")
class SetMap2<K0, K1, V> : Map<K0, SetMap<K1, V>> {

    private var valueMap: MutableMap<K0, SetMap<K1, V>>? = null
    private var keyMap: MutableMap<V, MutableSet<K0>>? = null

    fun add(k0: K0, k1: K1, v: V) {
        lazyGetSetMap(k0).add(k1, v)
        lazyGetKetSet(v).add(k0)
    }

    fun remove(v: V) {
        val keyMap = keyMap ?: return
        val valueMap = valueMap ?: return
        val keySet = keyMap[v] ?: return

        keySet.forEach { k0 ->
            val setMap = valueMap[k0]
            if (setMap != null) {
                setMap.remove(v)
                if (setMap.isEmpty()) {
                    valueMap.remove(k0)
                }
            }
        }

        keyMap.remove(v)
    }

    fun remove(k0: K0, v: V) {
        val keyMap = keyMap ?: return
        val valueMap = valueMap ?: return

        val setMap = valueMap[k0]
        if (setMap != null) {
            setMap.remove(v)
            if (setMap.isEmpty()) {
                valueMap.remove(k0)
            }
        }

        keyMap.remove(v)
    }

    fun remove(k0: K0, k1: K1, v: V) {
        val keyMap = keyMap ?: return
        val valueMap = valueMap ?: return

        val setMap = valueMap[k0]
        if (setMap != null) {
            setMap.remove(k1, v)
            if (setMap.isEmpty()) {
                valueMap.remove(k0)
            }
        }

        keyMap.remove(v)
    }

    override fun get(key: K0): SetMap<K1, V>? {
        val map = valueMap ?: return null
        return map[key]
    }

    fun get(k0: K0, k1: K1): Set<V>? {
        val map = get(k0) ?: return null
        return map[k1]
    }

    fun clear() {
        valueMap?.clear()
        keyMap?.clear()
    }

    fun contains(v: V): Boolean {
        return keyMap?.containsKey(v) ?: false
    }

    fun sizeOf(k0: K0): Int {
        val map = valueMap ?: return 0
        return map[k0]?.size ?: 0
    }

    fun sizeOf(k0: K0, k1: K1): Int {
        val map = valueMap ?: return 0
        return map[k0]?.sizeOf(k1) ?: 0
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    fun forEach(call: (K0, K1, V) -> Unit) {
        val map = valueMap ?: return
        map.entries.forEach { entry ->
            val k0 = entry.key
            entry.value.forEach { k1, v ->
                call.invoke(k0, k1, v)
            }
        }
    }

    private fun lazyGetValueMap(): MutableMap<K0, SetMap<K1, V>> {
        var map = valueMap
        if (map == null) {
            map = mutableMapOf()
            valueMap = map
        }
        return map
    }

    private fun lazyGetSetMap(k0: K0): SetMap<K1, V> {
        val map = lazyGetValueMap()
        var setMap = map[k0]
        if (setMap == null) {
            setMap = SetMap()
            map[k0] = setMap
        }
        return setMap
    }

    private fun lazyGetKeyMap(): MutableMap<V, MutableSet<K0>> {
        var map = keyMap
        if (map == null) {
            map = mutableMapOf()
            keyMap = map
        }
        return map
    }

    private fun lazyGetKetSet(v: V): MutableSet<K0> {
        val map = lazyGetKeyMap()
        var keySet = map[v]
        if (keySet == null) {
            keySet = mutableSetOf()
            map[v] = keySet
        }
        return keySet
    }

    override val entries: Set<Map.Entry<K0, SetMap<K1, V>>>
        get() = valueMap?.entries ?: setOf()

    override val keys: Set<K0>
        get() = valueMap?.keys ?: setOf()

    override val size: Int
        get() {
            val map = valueMap ?: return 0
            var sum = 0
            map.keys.forEach { k0 ->
                sum += sizeOf(k0)
            }
            return sum
        }

    override val values: Collection<SetMap<K1, V>>
        get() = valueMap?.values ?: setOf()

    override fun containsKey(key: K0): Boolean {
        return valueMap?.containsKey(key) ?: false
    }

    override fun containsValue(value: SetMap<K1, V>): Boolean {
        return valueMap?.containsValue(value) ?: false
    }
}