package dev.kazusato.tools.appdep

data class DependencyInfo(
    val frontend: MutableList<String> = mutableListOf(),
    val backend: MutableList<String> = mutableListOf(),
    val library: MutableList<String> = mutableListOf(),
    val database: MutableList<String> = mutableListOf(),
    val queue: MutableList<String> = mutableListOf(),
    val extsys: MutableList<String> = mutableListOf()
)