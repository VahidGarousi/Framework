package ir.vahid.core.platform.broadcast.contract

interface BroadcastResultLauncher<I> {
    fun launch(input: I)

    fun cancel()
}
