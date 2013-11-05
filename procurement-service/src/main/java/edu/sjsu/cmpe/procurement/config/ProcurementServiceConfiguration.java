package edu.sjsu.cmpe.procurement.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class ProcurementServiceConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String stompQueueName;

    @NotEmpty
    @JsonProperty
    private String apolloUser;
    
    @NotEmpty
    @JsonProperty
    private String apolloPassword;
    
    @NotEmpty
    @JsonProperty
    private String apolloHost;
    
    @NotEmpty
    @JsonProperty
    private String apolloPort;
    
    @NotEmpty
    @JsonProperty
    private String stompTopicBook;
    
    @NotEmpty
    @JsonProperty
    private String stompTopicComputer;
    
    /**
     * @return the stompQueueName
     */
    public String getStompQueueName() {
	return stompQueueName;
    }

    /**
     * @param stompQueueName
     *            the stompQueueName to set
     */
    public void setStompQueueName(String stompQueueName) {
	this.stompQueueName = stompQueueName;
    }


	/**
	 * @return the apolloUser
	 */
	public String getApolloUser() {
		return apolloUser;
	}

	/**
	 * @param apolloUser the apolloUser to set
	 */
	public void setApolloUser(String apolloUser) {
		this.apolloUser = apolloUser;
	}

	/**
	 * @return the apolloPassword
	 */
	public String getApolloPassword() {
		return apolloPassword;
	}

	/**
	 * @param apolloPassword the apolloPassword to set
	 */
	public void setApolloPassword(String apolloPassword) {
		this.apolloPassword = apolloPassword;
	}

	/**
	 * @return the apolloHost
	 */
	public String getApolloHost() {
		return apolloHost;
	}

	/**
	 * @param apolloHost the apolloHost to set
	 */
	public void setApolloHost(String apolloHost) {
		this.apolloHost = apolloHost;
	}

	/**
	 * @return the apolloPort
	 */
	public String getApolloPort() {
		return apolloPort;
	}

	/**
	 * @param apolloPort the apolloPort to set
	 */
	public void setApolloPort(String apolloPort) {
		this.apolloPort = apolloPort;
	}

	/**
	 * @return the stompTopicBook
	 */
	public String getStompTopicBook() {
		return stompTopicBook;
	}

	/**
	 * @param stompTopicBook the stompTopicBook to set
	 */
	public void setStompTopicBook(String stompTopicBook) {
		this.stompTopicBook = stompTopicBook;
	}

	/**
	 * @return the stompTopicComputer
	 */
	public String getStompTopicComputer() {
		return stompTopicComputer;
	}

	/**
	 * @param stompTopicComputer the stompTopicComputer to set
	 */
	public void setStompTopicComputer(String stompTopicComputer) {
		this.stompTopicComputer = stompTopicComputer;
	}
}
