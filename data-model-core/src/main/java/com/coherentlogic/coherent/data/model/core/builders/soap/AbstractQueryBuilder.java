package com.coherentlogic.coherent.data.model.core.builders.soap;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.coherentlogic.coherent.data.model.core.builders.CacheableQueryBuilder;
import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;

public abstract class AbstractQueryBuilder<T>
    extends CacheableQueryBuilder<String, Object>
    implements RequestMethodSpecification<T> {

    private final WebServiceTemplate webServiceTemplate;

    public AbstractQueryBuilder(WebServiceTemplate webServiceTemplate) {
        this (webServiceTemplate, NULL_CACHE);
    }

    public AbstractQueryBuilder(
        WebServiceTemplate webServiceTemplate,
        CacheServiceProviderSpecification<String, Object> cache
    ) {
        super(cache);
        this.webServiceTemplate = webServiceTemplate;
    }

    protected WebServiceTemplate getWebServiceTemplate () {
        return webServiceTemplate;
    }

//    protected P getRequestPayload () {
//        return null;
//    }

//    public S doGet(R request) {
//
//        throw new RuntimeException ("Method not yet implemented!");
//
////        T t = getRequestPayload ();
//
////        return webServiceTemplate.marshalSendAndReceive(requestPayload);
//    }
}
