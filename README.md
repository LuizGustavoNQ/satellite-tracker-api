# Satellite Tracker API

A REST API built with **Java and Spring Boot** for tracking satellites using orbital data provided by CelesTrak.

The application retrieves **TLE (Two-Line Element)** data from CelesTrak, parses the orbital information, and uses orbital propagation to calculate the current position of each satellite.

TLE data is cached and refreshed periodically instead of being requested on every position update. Satellite positions are then calculated locally and updated continuously.

The API provides the satellite's:

- Name
- Latitude
- Longitude
- Altitude

## REST API

### Get Current Satellite Positions

`GET /satellites`

Returns the latest calculated positions of all currently tracked satellites.

Example response:

    [
      {
        "name": "ISS (ZARYA)",
        "latitude": 32.909419,
        "longitude": -139.855672,
        "altitude": 421800.78
      },
      {
        "name": "CSS (TIANHE)",
        "latitude": -19.604977,
        "longitude": -124.036544,
        "altitude": 394406.60
      }
    ]

Altitude is returned in meters.

## WebSocket

The API also supports real-time satellite position updates using **WebSocket and STOMP**.

WebSocket endpoint:

`ws://localhost:8080/ws`

Satellite updates are published to:

`/topic/satellites`

A client connects to `/ws` and subscribes to `/topic/satellites`. Whenever the server recalculates satellite positions, the updated list is automatically published to all subscribed clients.

## How It Works

    CelesTrak
        ↓
    TLE Data
        ↓
    TLE Parser
        ↓
    Orbital Propagation
        ↓
    Latitude / Longitude / Altitude
        ↓
    REST API + WebSocket

The application separates **orbital data updates** from **position calculations**. CelesTrak is only accessed periodically to refresh TLE data, while satellite positions are calculated locally at a higher frequency.

This allows the API to provide near real-time tracking without making unnecessary requests to the external data provider.

## Data Source

Orbital data is provided by CelesTrak.
