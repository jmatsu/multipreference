package com.github.jmatsu.multipreference.processor.exception

class OutOfNameStrategyException(strategy: Int) : RuntimeException("Out of name strategy = $strategy")