package com.jha.abhishek.hackernews.exceptionhandling;

public class CriticalException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 8987372324835935049L;

    private String serviceCode;


    public CriticalException( String serviceCode )
    {
        super();
        this.serviceCode = serviceCode;
    }


    public CriticalException( String serviceCode, String message )
    {
        super( message );
        this.serviceCode = serviceCode;
    }


    public CriticalException( String serviceCode, String message, Throwable thrw )
    {
        super( message, thrw );
        this.serviceCode = serviceCode;
    }


    public String getServiceCode()
    {
        return serviceCode;
    }

}
