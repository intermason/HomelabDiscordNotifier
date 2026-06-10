# HomelabDiscordNotifier

A Java program for my homelab, where once it starts, will send a Discord message showing all statuses for my services in a list.

This project supports automatic updates via `update.sh`.

## Overview
This notifier will send a Discord message displaying the status for every service in a list, at runtime.

The program takes in a JSON array of all services.
It will then check the status of each service and send a message to a Discord webhook.

## How it works

- **`HTTPService`** — A service that checks the status via a HTTP or HTTPS request. Use this if you want to check the status of a service web UI or similar.
- **`PortService`** — A service that checks the status via an IP address and port. Use this for local containers, like Docker.
- **`ICMPService`** — A service that checks the status via an ICMP ping with an IP address. Use this for physical devices and servers.
> [!WARNING]
> ICMP Services do not work with containers. Use PortService instead.

## Prerequisites

- JDK 25 or above

## Setup

### 1. Discord Webhook

1. Go to your Discord server settings.
2. Click on Integrations.
3. Click on Webhooks.
4. Click on Create Webhook.
5. Give the webhook a name.
6. Copy the webhook URL.


### 2. Environment Variables

Create a `.env` file in the project root:

```
DISCORD_WEBHOOK=your_webhook_url_here
```

### 3. Configure Services

Edit `services.json` in the project root to add your services:

```json
[
  {
    "name": "HTTP Service",
    "type": "http",
    "url": "https://example.com",
    "quiet": true 
  },
  {
    "name": "Port Service",
    "type": "port",
    "ip": "192.168.1.100",
    "port": 8080
  },
  {
    "name": "ICMP Service",
    "type": "icmp",
    "ip": "192.168.1.101"
  }
]
```
> [!NOTE]
> The `quiet` field is only used for HTTP services. If quiet is true, the program will not send status code messages.

#### Service fields

| Field | Type | Required          | Description                       |
|-------|------|-------------------|-----------------------------------|
| `name` | string | yes               | Name of service.                  |
| `type` | string | yes               | Type of service.                  |
| `url` | string | Only HTTP         | HTTP(s) URL.                      |
| `ip` | string | Only ICMP or Port | IP address.                       |
| `port` | int | Only Port         | Port number.                      |
| `quiet` | boolean | Only HTTP         | Suppress HTTP status code output. |

## Running

```bash
java -jar HomelabDiscordNotifier.jar
```

## Authors

- Mason Doti