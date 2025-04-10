package org.gaplo917.mcpservercoinprice

import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.*


interface CoinPriceService {
    fun getCryptocurrencyPrice(symbols: List<String>): List<CoinPrice>
}

data class CoinPrice(
    val symbol: String,
    val price: String,
    val time: Long,
    val volume: String
)

data class CryptoTicker(
    val symbol: String,
    val openPrice: String,
    val highPrice: String,
    val lowPrice: String,
    val lastPrice: String,
    val volume: String,
    val quoteVolume: String,
    val openTime: Long,
    val closeTime: Long,
    val firstId: Long,
    val lastId: Long,
    val count: Int
)

@Service
class CoinPriceServiceImpl : CoinPriceService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val client by lazy {
        RestClient.builder()
            .baseUrl("https://api.binance.com")
            .build()
    }

    data class TextInput(val input: String)

    @Tool(description = "convert all characters to uppercase for symbols.")
    fun toUpperCase(textInput: TextInput): String {
        return textInput.input.uppercase(Locale.getDefault())
    }

    // Sample
    // GET https://api.binance.com/api/v3/ticker/24hr?symbols=["BTCUSDT","ETHUSDT"]&type=MINI
    @Tool(description = "Get cryptocurrency price by symbols. Always use a pair with USDT, i.e. BTCUSDT")
    override fun getCryptocurrencyPrice(symbols: List<String>): List<CoinPrice> {
        logger.info("[DEMO_COIN_PRICE] request tickers with symbols: {}", symbols)

        return client.get().uri("/api/v3/ticker/24hr") {
            it.queryParam("symbols", symbols.joinToString(",", prefix = "[", postfix = "]") { "\"$it\"" })
                .queryParam("type", "MINI")
                .build()
        }.retrieve()
            .body(object : ParameterizedTypeReference<List<CryptoTicker>>() {})
            ?.map { it -> CoinPrice(
                symbol = it.symbol,
                price = it.lastPrice,
                time = it.closeTime,
                volume = it.volume
            ) }
            ?.also { logger.info("[DEMO_COIN_PRICE] result: {}", it) }
            ?: listOf()

    }
}
