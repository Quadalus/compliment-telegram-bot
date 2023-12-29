package ru.bikkul.compliment.telegram.bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
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

    @Column(name = "username", nullable = false)
    private String userName = "undefined";

    @Column(name = "first_name", nullable = false)
    private String firstName = "undefined";

    @Column(name = "last_name", nullable = false)
    private String lastName = "undefined";

    public User(Long id) {
        this.id = id;
    }
}


