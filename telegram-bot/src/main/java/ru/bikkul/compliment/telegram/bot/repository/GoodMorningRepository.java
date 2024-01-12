package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;

import java.util.List;

public interface GoodMorningRepository extends JpaRepository<GoodMorning, Integer> {
    @Query("""
            SELECT id
            FROM GoodMorning
            WHERE source LIKE 'site'
            """)
    List<Integer> findAllIdBySource(String text);

    @Query("""
            SELECT text
            FROM GoodMorning
            WHERE id = :id
            """)
    String getTextById(@Param("id") long id);
}
