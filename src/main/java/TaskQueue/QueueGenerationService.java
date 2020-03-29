package TaskQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: 覃义雄
 * @dateTime: 2020/3/23 11:12 上午
 * @project_Name: PersonalPractice
 * @Name: QueueGenerationService
 */
public class QueueGenerationService {
    // 日志监控
    private static final Logger log = LoggerFactory.getLogger(QueueGenerationService.class);
    // 根据业务与服务器性能自行配置 这里我配置的是最多50000个任务
    // LinkedBlockingQueue构造的时候若没有指定大小，则默认大小为Integer.MAX_VALUE
    private final LinkedBlockingQueue<QueueTaskHandler> tasks = new LinkedBlockingQueue<QueueTaskHandler>(50000);
    // 类似于一个线程总管 保证所有的任务都在队列之中
    private ExecutorService service = Executors.newSingleThreadExecutor();
    // 检查服务是否运行
    private volatile boolean running = true;
    //线程状态
    private Future<?> serviceThreadStatus = null;

    public void init() {
        serviceThreadStatus = service.submit(new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        //开始一个任务
                        QueueTaskHandler task = tasks.take();
                        try {
                            task.processData();
                        } catch (Exception e) {
                            log.error("任务处理发生错误", e);
                        }
                    } catch (InterruptedException e) {
                        log.error("服务停止，退出", e);
                        running = false;
                    }
                }
            }
        }, "save data thread"));
    }

    public boolean addData(QueueTaskHandler dataHandler) {
        if (!running) {
            log.warn("service is stop");
            return false;
        }
        //offer 队列已经满了，无法再加入的情况下
        boolean success = tasks.offer(dataHandler);
        if (!success) {
            log.warn("添加任务到队列失败");
        }
        return success;
    }

    //判断队列是否有任务
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public boolean checkServiceRun() {
        return running && !service.isShutdown() && !serviceThreadStatus.isDone();
    }

    public void activeService() {
        running = true;
        if (service.isShutdown()) {
            service = Executors.newSingleThreadExecutor();
            init();
            log.info("线程池关闭，重新初始化线程池及任务");
        }
        if (serviceThreadStatus.isDone()) {
            init();
            log.info("线程池任务结束，重新初始化任务");
        }
    }

    public void destory() {
        running = false;
        service.shutdownNow();
    }
}
