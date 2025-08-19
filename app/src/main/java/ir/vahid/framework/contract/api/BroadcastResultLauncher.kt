package ir.vahid.framework.contract.api

interface BroadcastResultLauncher<I> {
    fun launch(input: I)
    fun cancel()
}
