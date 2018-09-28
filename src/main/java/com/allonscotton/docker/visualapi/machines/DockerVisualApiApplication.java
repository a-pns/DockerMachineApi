package com.allonscotton.docker.visualapi.machines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.allonscotton.docker.visualapi.machines.services.MachineService;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

@SpringBootApplication
@Configuration
@PropertySource("classpath:application.properties")
public class DockerVisualApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerVisualApiApplication.class, args);
	}
	
	@Bean
	public DockerClient dockerClient() throws DockerCertificateException
	{
		return DefaultDockerClient.fromEnv().build();
	}
	
	@Bean
	public MachineService machineService() throws DockerCertificateException
	{
		return new MachineService(dockerClient());
	}
}
