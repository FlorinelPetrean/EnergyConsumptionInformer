package com.ds.EnergyUtilityPlatform.config;


import com.ds.EnergyUtilityPlatform.rmi.RmiService;
import com.ds.EnergyUtilityPlatform.rmi.RmiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@RequiredArgsConstructor
public class RmiHessianConfig {
    private final RmiServiceImpl rmiServiceImpl;

    @Bean(name = "/rmi")
    RemoteExporter sayHelloServiceHessian() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(rmiServiceImpl);
        exporter.setServiceInterface(RmiService.class);
        return exporter;
    }
}
