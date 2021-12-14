package com.ds.EnergyUtilityPlatform.config;

import com.ds.EnergyUtilityPlatform.rmi.RmiService;
import com.ds.EnergyUtilityPlatform.rmi.RmiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

//@Configuration
@RequiredArgsConstructor
public class RmiConfig {
    private final RmiServiceImpl rmiServiceImpl;

    //@Bean
    RemoteExporter registerRMIExporter() {
        //default port 1099
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("rmiservice");
        exporter.setServiceInterface(RmiService.class);
        exporter.setService(rmiServiceImpl);


        return exporter;
    }
}
