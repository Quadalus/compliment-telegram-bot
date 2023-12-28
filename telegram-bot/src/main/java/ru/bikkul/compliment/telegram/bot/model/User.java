package ru.bikkul.compliment.telegram.bot.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telegram_user")
public class User {
    @Id
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}


