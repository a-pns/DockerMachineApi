package com.allonscotton.docker.visualapi.machines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

@SpringBootApplication
public class DockerVisualApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerVisualApiApplication.class, args);
	}
	
	@Bean
	public DockerClient dockerClient() throws DockerCertificateException
	{
		return DefaultDockerClient.fromEnv().build();
	}
}
