package com.hauxi.project.springredditclone.exception;

public class RedditException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    public RedditException(String exMessage) {
        super(exMessage);
        
    }

    
    public RedditException(String exMessage, Exception e) {
        super(exMessage,e);
        
    }
}
