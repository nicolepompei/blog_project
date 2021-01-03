package com.blog.blog_project.exceptions;

public class TagNotFoundException extends Throwable{
    public TagNotFoundException(String exMessage) {
        super(exMessage);
    }


    public TagNotFoundException(String exMessage, Exception exception){
        super(exMessage, exception);


    }
}
