package com.reqres.pojo.domain;

import com.reqres.pojo.remote.UserRemote;

public class UserMapper {

    public static User map(UserRemote remote) {
        Profile profile = new Profile();
        profile.firstName = remote.firstName;
        profile.lastName = remote.lastName;

        Contact contact = new Contact();
        contact.email = remote.email;

        User user = new User();
        user.profile = profile;
        user.contact = contact;

        return user;
    }
}
