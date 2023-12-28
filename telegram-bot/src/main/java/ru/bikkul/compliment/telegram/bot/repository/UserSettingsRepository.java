package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.compliment.telegram.bot.model.UserSettings;

import java.util.List;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    List<UserSettings> searchByIsScheduled(Boolean isScheduled);
}
