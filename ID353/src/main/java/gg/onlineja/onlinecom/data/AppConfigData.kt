import java.io.Serializable

data class AppConfigData(
    val privacy_policy_html: String?,
    val user_term_html: String?,
    val show_news_item: String?,
    val show_calculator_item: String?,
    val show_chat: String?,
    val show_phone_authentication: String?
): Serializable
