package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.compliment.telegram.bot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
