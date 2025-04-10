# Kotlin Crypto Price Spring MCP Server Demo

A demonstration project showcasing how to build a Model Context Protocol (MCP) server using Spring AI and Kotlin. This server provides cryptocurrency price information from Binance API through MCP-compatible tools.

## Overview

This project implements an MCP server that allows AI models to retrieve real-time cryptocurrency prices. It uses:

- Spring Boot 3.4.4
- Kotlin 1.9.25
- Spring AI MCP Server
- Model Context Protocol (MCP) SDK 0.8.1

## Features

- **Cryptocurrency Price Tool**: Fetch real-time prices for cryptocurrencies from Binance API
- **Text Transformation Tool**: Simple text transformation utility (uppercase conversion)
- **MCP Server Implementation**: Exposes tools via the Model Context Protocol
- **Logging**: Comprehensive logging of tool requests and responses

## API Endpoints

The server exposes MCP endpoints at:
- `/mcp/messages` - Server-Sent Events (SSE) endpoint for MCP communication

## Tools Available

1. **getCryptocurrencyPrice**
   - Description: Get cryptocurrency price by symbols
   - Parameters: `symbols` (List of strings, e.g., ["BTCUSDT", "ETHUSDT"])
   - Returns: List of CoinPrice objects with symbol, price, time, and volume information

2. **toUpperCase**
   - Description: Convert all characters to uppercase for symbols
   - Parameters: `textInput` (TextInput object with an input string)
   - Returns: Uppercase string

## Configuration

The server runs on port 3001 by default and is configured with the following MCP settings:

- Server name: binance-coinprice-server
- Server version: 1.0.0
- Server type: SYNC
- SSE message endpoint: /mcp/messages

## Getting Started

### Prerequisites

- JDK 21 or higher
- Gradle

### Running the Application

```bash
./gradlew bootRun
```

The server will start on port 3001.

### Building the Application

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## Usage Example

An AI model can connect to this MCP server to retrieve cryptocurrency prices by making tool calls to the `getCryptocurrencyPrice` function.

Example request:
```json
{
  "name": "getCryptocurrencyPrice",
  "parameters": {
    "symbols": ["BTCUSDT", "ETHUSDT"]
  }
}
```

Example response:
```json
[
  {
    "symbol": "BTCUSDT",
    "price": "95494.89000000",
    "time": 1732842910747,
    "volume": "27858.82523000"
  },
  {
    "symbol": "ETHUSDT",
    "price": "3456.78000000",
    "time": 1732842910747,
    "volume": "123456.78900000"
  }
]
```

## License

[Add your license information here]

## Acknowledgments

- Spring AI team for the MCP server implementation
- Model Context Protocol for standardizing AI tool interactions
