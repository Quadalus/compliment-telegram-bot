package ru.bikkul.compliment.telegram.bot.model;

import jakarta.persistence.*;
import lombok.*;
import ru.bikkul.compliment.telegram.bot.util.enums.SourceType;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "is_scheduled", nullable = false)
    private Boolean isScheduled = true;

    @Column(name = "cron_time", nullable = false)
    private String cronTime = "0 0 8 ? * * *";

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private SourceType sourceType = SourceType.SITE;

    @Column(name = "text_param", nullable = false)
    private String textParam = "default";

    @Column(name = "picture_param", nullable = false)
    private String pictureParam = "default";

    public UserSetting(Long chatId) {
        this.chatId = chatId;
    }
}
