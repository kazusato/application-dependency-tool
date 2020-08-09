package dev.kazusato.tools.appdep.diagram.renderer

import dev.kazusato.tools.appdep.diagram.DiagramConfiguration

class SystemwideRenderer(val config: DiagramConfiguration) {

    fun generateBeginStatement(): String {
        return "@startuml"
    }

    fun generateEndStatement(): String {
        return "@enduml"
    }

    fun generateSystemwideConfiguration(): String {
        return "skinparam defaultFontSize ${config.defaultConfiguration.fontSize}"
    }

}