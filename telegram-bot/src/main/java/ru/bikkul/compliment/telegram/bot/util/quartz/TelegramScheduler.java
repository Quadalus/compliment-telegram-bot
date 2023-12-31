package ru.bikkul.compliment.telegram.bot.util.quartz;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

@Slf4j
@Component
public class TelegramScheduler {
    private final TimeZone TIME_ZONE = TimeZone.getTimeZone(ZoneId.of("Europe/Moscow"));
    private final Scheduler scheduler;


    public TelegramScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @SneakyThrows
    public void cronCreateJob(long chatId, String cronExpression) {
        var jobKey = "wishes-job-" + chatId;
        var triggerKey = "wishes-trigger-" + chatId;

        if (!scheduler.checkExists(jobKey(jobKey))) {
            var job = newJob(CronJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("chatId", chatId)
                    .storeDurably()
                    .build();

            var trigger = newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronSchedule(cronExpression).inTimeZone(TIME_ZONE))
                    .build();
            scheduler.scheduleJob(job, trigger);
            log.info("job has been scheduled with, cron:{}", cronExpression);
        }
    }

    @SneakyThrows
    public void cronUpdate(long chatId, String cronExpression) {
        if (checkJobIsNotExists(chatId)) {
            log.info("job by chat id:{}, not exists", chatId);
            return;
        }

        Trigger oldTrigger = scheduler.getTrigger(triggerKey("wishes-trigger-" + chatId));
        Trigger newTrigger = newTrigger()
                .withIdentity("wishes-trigger-" + chatId)
                .withSchedule(cronSchedule(cronExpression).inTimeZone(TIME_ZONE))
                .build();
        log.info("set new cron:{} to trigger", cronExpression);
        scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
    }

    @SneakyThrows
    public void cronDelete(long chatId) {
        if (checkJobIsNotExists(chatId)) {
            log.info("job by chat id:{}, not exists", chatId);
            return;
        }

        var jobKey = jobKey("wishes-job-" + chatId);
        var triggerKey = triggerKey("wishes-trigger-" + chatId);
        scheduler.deleteJob(jobKey);
        log.info("job and trigger has been delete, job id:{}, trigger id:{}", jobKey, triggerKey);
    }

    @SneakyThrows
    public String getCronTime(long chatId) {
        var triggerKey = triggerKey("wishes-trigger-" + chatId);

        if (scheduler.checkExists(triggerKey)) {
            var date = scheduler.getTrigger(triggerKey).getNextFireTime();
            var calendar = Calendar.getInstance();
            calendar.setTime(date);
            var zoneTime = LocalTime.ofInstant(Instant.from(calendar.toInstant()), ZoneId.of("Europe/Moscow"));
            return "%s:%s".formatted(zoneTime.getHour(), zoneTime.getMinute());
        }
        return "время не установлено";
    }

    private boolean checkJobIsNotExists(long chatId) throws SchedulerException {
        var key = "wishes-job-" + chatId;
        var jobKey = jobKey(key);
        var isJobExists = scheduler.checkExists(jobKey);

        return !isJobExists;
    }
}
