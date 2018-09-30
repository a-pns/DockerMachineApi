package com.allonscotton.docker.visualapi.machines.integrationtests;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDataHelper;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.exceptions.NodeNotFoundException;
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
		

		Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 29, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date testDate1 = cal.getTime();
		cal.set(2018, 10, 1, 23, 59, 0);
		Date testDate2 = cal.getTime();
		
		given(dockerClient.listNodes()).willReturn(
				Arrays.asList(
						TestDataHelper.getTestDockerNode("123456789", (long) 1, "active", "active", "192.0.0.1", "server1", true, testDate1, testDate2),
						TestDataHelper.getTestDockerNode("zxcvbnm", (long) 1, "inactive", "inactive", "178.1.1.1", "server2", false, testDate2, testDate1)
						)
				);
		this.mockMvc.perform(get("/machine")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		
		this.mockMvc.perform(get("/machine")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].id").value("123456789"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].status").value("active"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].version").value("1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].ip").value("192.0.0.1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].hostname").value("server1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].created").value("29-10-2018 00:00:00.000"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[0].updated").value("01-11-2018 23:59:00.000"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].id").value("zxcvbnm"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].status").value("inactive"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].version").value("1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].ip").value("178.1.1.1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].hostname").value("server2"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].updated").value("29-10-2018 00:00:00.000"))
		.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.machines[1].created").value("01-11-2018 23:59:00.000"))
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
	
	@Test
	public void testThat404IsReturnedWhenGettingAMachineThatDoesNotExist() throws Exception
	{
		given(dockerClient.inspectNode(anyString())).willThrow(NodeNotFoundException.class);
		this.mockMvc.perform(get("/machine/123abc")).andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(StringConstants.MACHINE_NOT_FOUND))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404));
	}
	
	@Test
	public void testThat500IsReturnedWhenGettingAMachineThatDoesExistButErrorWasThrown() throws Exception
	{
		given(dockerClient.inspectNode(anyString())).willThrow(RuntimeException.class);
		this.mockMvc.perform(get("/machine/123abc")).andExpect(status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(StringConstants.UNABLE_TO_RETRIEVE_NODE_INFO))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500));
	}
	
	@Test
	public void testThat200IsReturnedWhenGettingAMachineThatDoesExist() throws Exception
	{
		given(dockerClient.inspectNode(anyString())).willReturn(
				TestDataHelper.getTestDockerNodeInfo("123456789", (long) 1, "active", "active", "192.0.0.1", "server1", true, new Date(), new Date())
				);
		this.mockMvc.perform(get("/machine/123abc")).andExpect(status().isOk());
	}
};