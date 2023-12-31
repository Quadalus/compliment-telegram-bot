package ru.bikkul.compliment.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.compliment.telegram.bot.model.UserSetting;

import java.util.List;

public interface UserSettingsRepository extends JpaRepository<UserSetting, Long> {
    List<UserSetting> searchByIsScheduled(Boolean isScheduled);
}
