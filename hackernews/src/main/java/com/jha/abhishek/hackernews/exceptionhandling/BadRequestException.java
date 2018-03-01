package com.jha.abhishek.hackernews.exceptionhandling;

import org.springframework.validation.Errors;

public class BadRequestException extends CriticalException
{

    /**
     * 
     */
    private static final long serialVersionUID = 872945513413626038L;
    private Errors errors;


    public BadRequestException( String serviceCode, Errors errors )
    {
        super( serviceCode );
        this.errors = errors;
    }


    public Errors getErrors()
    {
        return errors;
    }

}
