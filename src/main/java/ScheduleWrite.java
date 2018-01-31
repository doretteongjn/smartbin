import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class ScheduleWrite {

    public void schedulerwrite() throws SchedulerException {

        JobDetail job = JobBuilder.newJob(WritingCSV.class)//mention the Job Class Name here
                .build();

        //create schedule builder
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 20 * * ?");

        //create trigger which the schedule Builder
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(scheduleBuilder)
                .build();

        //create scheduler 
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // start your scheduler
        scheduler.start();

        // let the scheduler call the Job using trigger
        scheduler.scheduleJob(job, trigger);
    }

}

//https://mighty-inlet-63036.herokuapp.com/ | https://git.heroku.com/mighty-inlet-63036.git