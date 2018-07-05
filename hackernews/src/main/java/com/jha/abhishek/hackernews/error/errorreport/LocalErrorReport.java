package com.jha.abhishek.hackernews.error.errorreport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LocalErrorReport implements ErrorReport
{

    private static final Logger LOG = LoggerFactory.getLogger( LocalErrorReport.class );


    @Override
    public void reportError( Exception e )
    {
        LOG.info( "Printing error {}", e.getMessage() );
    }

}
