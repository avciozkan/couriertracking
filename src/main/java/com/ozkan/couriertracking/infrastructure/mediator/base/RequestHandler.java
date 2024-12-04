package com.ozkan.couriertracking.infrastructure.mediator.base;

public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {
    TResponse handle(TRequest request);
}