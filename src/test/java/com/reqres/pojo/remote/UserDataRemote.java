package com.reqres.pojo.remote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataRemote {

    public UserRemote data;
}
