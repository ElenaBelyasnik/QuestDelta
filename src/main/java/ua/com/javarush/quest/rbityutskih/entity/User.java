package ua.com.javarush.quest.rbityutskih.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(builderMethodName = "with")

public final class User extends Entity{

    Long id;
    String login;
    String password;
    //String image;


    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId() {

    }
}
