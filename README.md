# Kotlin Crypto Price Spring MCP Server Demo

A demonstration project showcasing the integration of Spring AI's Model Context Protocol (MCP) server with a cryptocurrency price API service. This project allows AI models to retrieve real-time cryptocurrency information through a standardized interface.

## Overview

This project implements a Spring Boot application that serves as an MCP server, providing tools for AI models to:

1. Search for cryptocurrencies by name or symbol
2. Retrieve market data for specific cryptocurrencies

The application uses the CoinGecko API to fetch real-time cryptocurrency data.

## Features

- **Cryptocurrency Search**: Search for cryptocurrencies, exchanges, categories, and NFTs
- **Market Data Retrieval**: Get detailed market data for specific cryptocurrencies
- **MCP Integration**: Exposes functionality as tools for AI models via Spring AI's MCP server
- **Logging**: Comprehensive logging of tool requests and responses

## Technologies

- Kotlin 1.9.25
- Spring Boot 3.4.4
- Spring AI MCP Server
- Model Context Protocol SDK 0.8.1
- Java 17

## Project Structure

```
src/main/kotlin/org/gaplo917/mcpservercoinprice/
├── CoinPriceService.kt         # Service for cryptocurrency data retrieval
├── McpServerCoinPriceApplication.kt  # Main application class
```

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle

### Running the Application

```bash
./gradlew bootRun
```

The MCP server will start on the default port (typically 8080).

## API Tools

The application exposes the following tools for AI models:

### 1. Search Cryptocurrency

```kotlin
@Tool(description = "search cryptocurrency information by user input query.")
fun searchCryptocurrency(query: String): CryptoData
```

### 2. Get Market Data

```kotlin
@Tool(description = "get cryptocurrency market data by id. The id must be used by the return of the searchCryptocurrency tools.")
fun getMarketDataByCryptocurrencyId(id: String): CryptoMarketData
```

## Data Models

- `CryptoData`: Contains lists of coins, exchanges, categories, and NFTs
- `CryptoMarketData`: Contains market data including tickers for a specific cryptocurrency
- `Coin`: Represents a cryptocurrency with its ID, name, symbol, and market cap rank
- `Ticker`: Contains trading information for a cryptocurrency on a specific market

## Integration with AI Models

This server can be used with AI models that support the Model Context Protocol (MCP), allowing them to retrieve cryptocurrency data in a standardized way. The Spring AI MCP Server handles the communication between the AI model and the cryptocurrency service.

## License

This project is licensed under the terms included in the LICENSE file.

## References

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [CoinGecko API](https://www.coingecko.com/api/documentation)
