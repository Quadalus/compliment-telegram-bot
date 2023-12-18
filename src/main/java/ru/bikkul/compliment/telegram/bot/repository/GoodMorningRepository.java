package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;

public interface GoodMorningRepository extends JpaRepository<GoodMorning, Integer> {
}
