package ru.bikkul.compliment.telegram.bot.util.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.bikkul.compliment.telegram.bot.service.impl.BotServiceImpl;

@RequiredArgsConstructor
public class CronJob implements Job {
    private final BotServiceImpl botService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long chatId = jobExecutionContext.getJobDetail().getJobDataMap().getLongValue("chatId");
        botService.sendRandomWish(chatId);
    }
}
