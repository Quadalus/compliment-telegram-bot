package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.exception.UserNotExistsException;
import ru.bikkul.compliment.telegram.bot.model.User;
import ru.bikkul.compliment.telegram.bot.repository.UserRepository;
import ru.bikkul.compliment.telegram.bot.service.UserService;
import ru.bikkul.compliment.telegram.bot.util.mapper.UserMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User saveUser(Message message) {
        User savedUser = new User();
        var chatId = message.getChatId();
        if (!userRepository.existsById(chatId)) {
            var user = UserMapper.userFromMessage(message, chatId);
            savedUser = userRepository.save(user);
        }
        log.info("пользователь c id:%s, сохранен.");
        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(User user, long chatId) {
        var updatedUser = userRepository.save(user);
        log.info("пользователь c id:%s, обновлён.");
        return updatedUser;
    }

    @Override
    @Transactional
    public void deleteUser(long chatId) {
        userRepository.deleteById(chatId);
        log.info("Пользователь с id:%s, был удален.".formatted(chatId));
    }

    @Override
    public User getUser(long chatId) {
        return userRepository.findById(chatId)
                .orElseThrow(() -> new UserNotExistsException("Пользователь с id:%s, на найден".formatted(chatId)));
    }

}
