package com.allonscotton.docker.visualapi.machines;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.allonscotton.docker.visualapi.machines.services.MachineService;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerCertificatesStore;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
@Configuration
@PropertySource("classpath:application.properties")
public class DockerVisualApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerVisualApiApplication.class, args);
	}
	
	@Value("${docker.certificatePath}")
	private String dockerCertificatesPath;
	
	@Value("${docker.hostUri}")
	private String dockerHostUri;
	
	@Bean
	public DockerClient dockerClient() throws DockerCertificateException
	{
		DockerCertificates store = new DockerCertificates(Paths.get(dockerCertificatesPath));
		return DefaultDockerClient.builder().dockerCertificates(store).uri(dockerHostUri).build();
		//return DefaultDockerClient.fromEnv().build();
	}
	
	@Bean
	public MachineService machineService() throws DockerCertificateException
	{
		return new MachineService(dockerClient());
	}
}