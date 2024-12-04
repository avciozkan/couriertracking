package com.ozkan.couriertracking.infrastructure.mediator.base;

public interface Mediator {
    <TResponse> TResponse send(Request<TResponse> request);
}