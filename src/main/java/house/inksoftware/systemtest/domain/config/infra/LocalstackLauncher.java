package house.inksoftware.systemtest.domain.config.infra;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class LocalstackLauncher {

    public static final String LOCALSTACK_URL = "LOCALSTACK_URL";

    private static LocalStackContainer container;

    public static synchronized URI launch() {
        if (container != null) {
            log.info("Localstack has already launched");
            return container.getEndpoint();
        }

        container = new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.14"));
        container.start();
        System.setProperty(LOCALSTACK_URL, container.getEndpoint().toString());
        log.info("Started Localstack at {}", container.getEndpoint());
        return container.getEndpoint();
    }

    public static synchronized void stop() {
        if (container != null) {
            container.stop();
            container = null;
        }
    }
}
