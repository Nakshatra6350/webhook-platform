# Webhook Platform

A webhook delivery platform built in Java: reliable delivery of events to
customer endpoints, with retries, backoff and failure handling.

## Build

    mvn clean package

Requires JDK 17 and Maven.

## Status

- [x] Retry policies (exponential backoff, fixed delay, capped)
- [x] Domain model (endpoints, events, delivery outcomes)
- [x] Error handling
- [ ] Persistence (MySQL)
- [ ] REST API (Spring Boot)
- [ ] Distributed delivery (Kafka)
