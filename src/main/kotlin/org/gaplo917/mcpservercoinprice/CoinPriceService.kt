package org.gaplo917.mcpservercoinprice

import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestClient
import java.net.URI


interface CoinPriceService {
    fun searchCryptocurrency(query: String): CryptoData
    fun getMarketDataByCryptocurrencyId(id: String): CryptoMarketData
}

data class CryptoData(
    val coins: List<Coin>,
    val exchanges: List<Exchange>,
    val icos: List<Any>,
    val categories: List<Category>,
    val nfts: List<Nft>
)

data class Coin(
    val id: String,
    val name: String,
    val api_symbol: String,
    val symbol: String,
    val market_cap_rank: Int,
)

data class Exchange(
    val id: String,
    val name: String,
    val market_type: String,
)

data class Category(
    val id: String,
    val name: String
)

data class Nft(
    val id: String,
    val name: String,
    val symbol: String,
    val thumb: String
)

data class CryptoMarketData(
    val name: String?,
    val tickers: List<Ticker>
)

data class Ticker(
    val base: String?,
    val target: String?,
    val market: Market?,
    val last: Int?,
    val volume: Double?,
    val cost_to_move_up_usd: Double?,
    val cost_to_move_down_usd: Double?,
    val converted_last: ConvertedPrice?,
    val converted_volume: ConvertedPrice?,
    val trust_score: String?,
    val bid_ask_spread_percentage: Double?,
    val timestamp: String?,
    val last_traded_at: String?,
    val last_fetch_at: String?,
    val is_anomaly: Boolean?,
    val is_stale: Boolean?,
    val coin_id: String?,
    val target_coin_id: String?
)

data class Market(
    val name: String,
    val identifier: String,
    val has_trading_incentive: Boolean,
)

data class ConvertedPrice(
    val btc: Double?,
    val eth: Double?,
    val usd: Double?
)

@Service
class CoinPriceServiceImpl() : CoinPriceService, ResponseErrorHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val client by lazy {
        RestClient.builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .build()
    }

    @Tool(description = "search cryptocurrency information by user input query.")
    override fun searchCryptocurrency(query: String): CryptoData {
        logger.info("[DEMO] request search with query: {}", query)

        val resp = client.get().uri("search") {
            it.queryParam("query", query)
                .build()
        }.retrieve()
            .onStatus(this)
            .body(CryptoData::class.java)
            ?: CryptoData(
                coins = listOf(),
                exchanges = listOf(),
                icos = listOf(),
                categories = listOf(),
                nfts = listOf()
            )

        logger.info("[DEMO] search result by query={}: {}", query, resp)
        return resp
    }

    @Tool(description = "get cryptocurrency market data by id. The id must be used by the return of the searchCryptocurrency tools.")
    override fun getMarketDataByCryptocurrencyId(id: String): CryptoMarketData {
        logger.info("[DEMO] request market data with id: {}", id)

        val resp = client.get().uri("coins/${id}/tickers")
            .retrieve()
            .onStatus(this)
            .body(CryptoMarketData::class.java)
            ?: CryptoMarketData(name = "", tickers = listOf())

        logger.info("[DEMO] market data result by id={}: {}", id, resp)

        return resp
    }

    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode != HttpStatus.OK
    }

    override fun handleError(
        url: URI,
        method: HttpMethod,
        response: ClientHttpResponse
    ) {
        logger.error(
            "[DEMO] API returned error code={} url={}, method={}, response={}",
            response.statusCode,
            method,
            url,
            response.body
        )
        super.handleError(url, method, response)
    }
}
