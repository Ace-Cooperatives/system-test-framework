package house.inksoftware.systemtest.domain.config.infra.sns;

import house.inksoftware.systemtest.domain.config.infra.LocalstackLauncher;
import house.inksoftware.systemtest.domain.config.infra.SystemTestResourceLauncher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Slf4j
@Getter
public class SystemTestSnsLauncher implements SystemTestResourceLauncher {

    private SnsClient snsClient;

    @Override
    public void setup() {
        snsClient = SnsClient.builder()
                .endpointOverride(LocalstackLauncher.launch())
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("accessKey", "secretKey")))
                .build();
    }

    @Override
    public void shutdown() {
        if (snsClient != null) {
            snsClient.close();
        }
        LocalstackLauncher.stop();
    }

    @Override
    public Type type() {
        return Type.SNS;
    }

}
