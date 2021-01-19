package com.blog.blog_project.exceptions;

public class ZcwBlogException extends Throwable {


    public ZcwBlogException(String exMessage) {
        super(exMessage);
    }

    public ZcwBlogException(String exMessage, Exception exception){
        super(exMessage, exception);
    }
}
