package com.blog.blog_project.exceptions;

public class ZcwBlogException extends Throwable {

    //when exceptions occur, we do not want to expose the nature of them to the user
    //create custom exception to pass in our own exception messages for a better user experience
    public ZcwBlogException(String exMessage) {
        super(exMessage);
    }

    public ZcwBlogException(String exMessage, Exception exception){
        super(exMessage, exception);
    }
}
