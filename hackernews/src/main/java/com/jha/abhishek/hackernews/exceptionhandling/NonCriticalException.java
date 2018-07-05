package com.jha.abhishek.hackernews.exceptionhandling;


public class NonCriticalException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -7062585220603630654L;

    private String serviceCode;
    private String errorCode;


    public NonCriticalException( String serviceCode )
    {
        super();
        this.serviceCode = serviceCode;
    }


    public NonCriticalException( String serviceCode, String message )
    {
        super( message );
        this.serviceCode = serviceCode;
    }


    public NonCriticalException( String serviceCode, String errorCode, String message )
    {
        super( message );
        this.serviceCode = serviceCode;
        this.errorCode = errorCode;
    }


    public NonCriticalException( String serviceCode, String message, Throwable thrw )
    {
        super( message, thrw );
        this.serviceCode = serviceCode;
    }


    public String getServiceCode()
    {
        return serviceCode;
    }


    public String getErrorCode()
    {
        return errorCode;
    }

}
