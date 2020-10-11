package com.lucasmontano.shopping.data.domain

data class ChairDomainModel(
    private val productAttr: HasBasicProductAttr,
    private val materialAttr: HasMaterial
) : HasBasicProductAttr by productAttr, HasMaterial by materialAttr {

    override val id = productAttr.id
    override val name = productAttr.name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HasBasicProductAttr

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    // I need to Read Effective Java to understand that better :)
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
