package ir.vahid.core.platform.broadcast.model

sealed interface ScreenState {
    data object On : ScreenState

    data object Off : ScreenState
}
