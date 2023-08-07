/*
 * Dev:- Emmanuel Israel
 * codename: @logicrey
 */
package com.et.movies.constants;

/**
 *
 * @author sharon
 */
public class Endpoints {

    /**
     * This class includes the name and API end points of this microservices
     * that will be consumed.
     */
    public static final String REGISTER_MOVIE = "/Movie/register";
    public static final String UPDATE_MOVIE = "Movie/update/{movie_id}";
    public static final String DELETE_MOVIE = "Movie/delete/{movie_id}";
    public static final String GET_All_MOVIES = "Movie/get";
    public static final String CREATE_SCREENING = "Screening/create";
    public static final String UPDATE_SCREENING = "Screening/update/{screening_id}";
    public static final String DELETE_SCREENING = "Screening/delete/{screening_id}";
    public static final String GET_All_SCREENING = "Screening/get";
    public static final String BOOK_TICKET = "Book/ticket";
    public static final String GET_All_BOOKING = "Booking/get";
    public static final String REGISTER_USER = "User/register";
    public static final String UPDATE_USER = "User/update/{user_id}";
    public static final String DELETE_USER = "User/delete/{user_id}";
    public static final String GET_All_USERS = "Users/get";
    public static final String SIGNUP = "Users/singup";
    public static final String SIGNIN = "Users/singip";


}
