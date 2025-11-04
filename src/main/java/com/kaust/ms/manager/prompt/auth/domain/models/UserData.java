package com.kaust.ms.manager.prompt.auth.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserData {

    /** id. */
    private String uid;
    /** email. */
    private String email;
    /** name. */
    private String name;
    /** roles. */
    private List<String> roles;

}
