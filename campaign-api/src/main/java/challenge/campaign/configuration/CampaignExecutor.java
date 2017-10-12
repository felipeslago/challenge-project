package challenge.campaign.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * A scheduler for threading requests.
 */
@Configuration
public class CampaignExecutor {

    @Value("${campaign.thread.core-pool}")
    private int corePoolSize;

    @Value("${campaign.thread.max-pool}")
    private int maxPoolSize;

    @Value("${campaign.queue.capacity}")
    private int queueCapacity;

    @Value("${campaign.thread.timeout}")
    private int threadTimeout;

    @Bean
    @Qualifier("campaignExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(threadTimeout);

        return threadPoolTaskExecutor;
    }

}
