package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.nms.common.NmsSound
import me.adrigamer2950.adriapi.api.nms.common.NmsVersions
import org.jetbrains.annotations.ApiStatus.Experimental

@Experimental
@ExperimentalAPI
object Nms {

    var sound: NmsSound

    init {
        when (NmsVersions.getCurrent()) {
            NmsVersions.V1_17_R1 -> sound = me.adrigamer2950.adriapi.api.nms.v1_17_R1.NmsSoundImpl()
            NmsVersions.V1_18_R1 -> sound = me.adrigamer2950.adriapi.api.nms.v1_18_R1.NmsSoundImpl()
            NmsVersions.V1_18_R2 -> sound = me.adrigamer2950.adriapi.api.nms.v1_18_R2.NmsSoundImpl()
            NmsVersions.V1_19_R1 -> TODO()
            NmsVersions.V1_19_R2 -> TODO()
            NmsVersions.V1_19_R3 -> TODO()
            NmsVersions.V1_20_R1 -> TODO()
            NmsVersions.V1_20_R2 -> TODO()
            NmsVersions.V1_20_R3 -> TODO()
            NmsVersions.V1_20_R4 -> TODO()
            NmsVersions.V1_21_R1 -> TODO()
            NmsVersions.V1_21_R2 -> TODO()
            NmsVersions.V1_21_R3 -> TODO()
            NmsVersions.V1_21_R4 -> TODO()
        }
    }
}