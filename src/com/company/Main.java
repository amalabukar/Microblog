package com.company;


import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static User user;



    public static void main(String[] args) {
        ArrayList<Messages> message = new ArrayList<>();

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.name);
                        m.put("message", message);
                        return new ModelAndView(m, "messages.html");
                    }

                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/createUser",
                ((request, response) -> {
                    String name = request.queryParams("newUserName");
                    user = new User(name);
                    response.redirect("/");
                    return " ";

                })

        );
        Spark.post(
                "/createMessage",
                ((request, response) -> {
                    String post = request.queryParams("newMessage");
                    message.add(new Messages(post));
                    response.redirect("/");
                    return " ";

                })

        );


    }
}