package com.ozkan.couriertracking.infrastructure.mediator.impl;

import com.ozkan.couriertracking.infrastructure.mediator.base.Mediator;
import com.ozkan.couriertracking.infrastructure.mediator.base.Request;
import com.ozkan.couriertracking.infrastructure.mediator.base.RequestHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MediatorImpl implements Mediator {

    private final ApplicationContext applicationContext;

    public MediatorImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <TResponse> TResponse send(Request<TResponse> request) {
        String handlerBeanName = request.getClass().getSimpleName() + "Handler";
        RequestHandler<Request<TResponse>, TResponse> handler =
                (RequestHandler<Request<TResponse>, TResponse>) applicationContext.getBean(handlerBeanName);
        return handler.handle(request);
    }
}
