package ru.bikkul.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.parser.model.GoodMorning;

public interface GoodMorningRepository extends JpaRepository<GoodMorning, Integer> {
    Boolean existsBySource(String text);
}
