package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;

import java.util.List;

public interface GoodMorningRepository extends JpaRepository<GoodMorning, Integer> {
    Boolean existsBySource(String text);

    Boolean existsByText(String text);

    List<GoodMorning> findBySource(String text);
}
