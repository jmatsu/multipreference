package com.github.jmatsu.multipreference.sample

class MethodBasedImpl : MethodBased() {
    override fun firstKey(): String = "this is first"

    override fun getSecondKey(): String = "this is second"
}