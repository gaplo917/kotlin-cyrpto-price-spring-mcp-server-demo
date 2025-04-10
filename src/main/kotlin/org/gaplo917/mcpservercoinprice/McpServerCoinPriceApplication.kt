package org.gaplo917.mcpservercoinprice

import io.modelcontextprotocol.spec.McpSchema
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Consumer


@SpringBootApplication
class McpServerCoinPriceApplication {
    private val logger = org.slf4j.LoggerFactory.getLogger(McpServerCoinPriceApplication::class.java)
    @Bean
    fun coinPriceTools(coinPriceService: CoinPriceService): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder().toolObjects(coinPriceService).build()
    }

    @Bean
    fun callToolRequestConsumer(): Consumer<MutableList<McpSchema.CallToolRequest>> {
        return Consumer { requests ->
            logger.info("call tools request: {}", requests)
        }
    }

    @Bean
    fun callToolResultConsumer(): Consumer<MutableList<McpSchema.CallToolResult>> {
        return Consumer { requests ->
            logger.info("call tools result: {}", requests)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<McpServerCoinPriceApplication>(*args)
}
