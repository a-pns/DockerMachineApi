package com.allonscotton.docker.visualapi.machines.integrationtests;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.allonscotton.docker.visualapi.machines.DockerVisualApiApplication;
import com.allonscotton.docker.visualapi.machines.StringConstants;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerManagerStatus;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNode;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNodeStatus;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerVersion;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;

@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes = { DockerVisualApiApplication.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class MachineControllerIT {

	@MockBean
	private DockerClient dockerClient;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testThatMachinesEndpointReturns200() throws Exception {
		given(dockerClient.listNodes()).willReturn(new ArrayList<Node>());
		this.mockMvc.perform(get("/machine")).andExpect(status().isOk());
	}

	@Test
	public void testThatMachinesEndpointReturnsCorrectListInfo() throws Exception {
		given(dockerClient.listNodes()).willReturn(
				Arrays.asList(
						new TestDockerNode(
								"123456789", 
								new TestDockerVersion((long) 1), 
								new Date(), 
								new Date(), 
								null,
								null, new TestDockerNodeStatus("active", "192.0.0.1"), 
								new TestDockerManagerStatus(true, "up", "192.0.0.1")), 
						new TestDockerNode(
								"zxcvbnm", 
								new TestDockerVersion((long) 1), 
								new Date(), new Date(), 
								null,
								null, 
								new TestDockerNodeStatus("down", "178.0.0.1"), 
								new TestDockerManagerStatus(false, "up", "178.0.0.1")
								)
						)
				);
		System.out.println(this.mockMvc.perform(get("/machine")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString());
		
		this.mockMvc.perform(get("/machine")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].id").value("123456789"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].id").value("zxcvbnm"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href").value("http://localhost/machine/"));
	}

	@Test
	public void testThatMachinesEndpointReturns500WhenDockerErrorIsReturned() throws Exception {
		given(dockerClient.listNodes()).willThrow(DockerException.class);
		this.mockMvc.perform(get("/machine")).andExpect(status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(StringConstants.UNABLE_TO_LIST_NODES_EXCEPTION))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500));
	}

	@Test
	public void testThatMachinesEndpointReturns500WhenInteruppedExceptionIsReturned() throws Exception {
		given(dockerClient.listNodes()).willThrow(InterruptedException.class);
		this.mockMvc.perform(get("/machine")).andExpect(status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(StringConstants.UNABLE_TO_LIST_NODES_EXCEPTION))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500));
	}
	
	@Test
	public void testThatMachinesEndpointReturns500WhenRuntimeExceptionIsReturned() throws Exception {
		given(dockerClient.listNodes()).willThrow(RuntimeException.class);
		this.mockMvc.perform(get("/machine")).andExpect(status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(StringConstants.GENERIC_INTERNAL_SERVER_ERROR))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500));
	}
};