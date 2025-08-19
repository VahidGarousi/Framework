package ir.vahid.framework.contract.sms.new

internal object OtpExtractor {
    fun from(text: String): String? {
        val fa = Regex("""کد\s*فعالسازی\s*شما\s*\D*?(\d{4,8})""", RegexOption.MULTILINE)
            .find(text)?.groupValues?.getOrNull(1)
        if (!fa.isNullOrBlank()) return fa
        return Regex("""\b(\d{4,8})\b""").find(text)?.groupValues?.getOrNull(1)
    }
}
