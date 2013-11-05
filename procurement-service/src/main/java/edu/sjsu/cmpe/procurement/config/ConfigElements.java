/**
 * 
 */
package edu.sjsu.cmpe.procurement.config;


/**
 * @author Amit
 *
 */
public class ConfigElements {
	
    private static String stompQueueName;

    private static String stompTopicName;
   
    private static String apolloUser;
    
    private static String apolloPassword;
    
    private static String apolloHost;
    
    private static String apolloPort;
    
    private static String libraryName;
    
    private static String stompTopicComputer;
    
    private static String stompTopicBook;
	/**
	 * @return the stompQueueName
	 */
	public static String getStompQueueName() {
		return stompQueueName;
	}

	/**
	 * @param stompQueueName the stompQueueName to set
	 */
	public static void setStompQueueName(String stompQueueName) {
		ConfigElements.stompQueueName = stompQueueName;
	}

	/**
	 * @return the stompTopicName
	 */
	public static String getStompTopicName() {
		return stompTopicName;
	}

	/**
	 * @param stompTopicName the stompTopicName to set
	 */
	public static void setStompTopicName(String stompTopicName) {
		ConfigElements.stompTopicName = stompTopicName;
	}

	/**
	 * @return the apolloUser
	 */
	public static String getApolloUser() {
		return apolloUser;
	}

	/**
	 * @param apolloUser the apolloUser to set
	 */
	public static void setApolloUser(String apolloUser) {
		ConfigElements.apolloUser = apolloUser;
	}

	/**
	 * @return the apolloPassword
	 */
	public static String getApolloPassword() {
		return apolloPassword;
	}

	/**
	 * @param apolloPassword the apolloPassword to set
	 */
	public static void setApolloPassword(String apolloPassword) {
		ConfigElements.apolloPassword = apolloPassword;
	}

	/**
	 * @return the apolloHost
	 */
	public static String getApolloHost() {
		return apolloHost;
	}

	/**
	 * @param apolloHost the apolloHost to set
	 */
	public static void setApolloHost(String apolloHost) {
		ConfigElements.apolloHost = apolloHost;
	}

	/**
	 * @return the apolloPort
	 */
	public static String getApolloPort() {
		return apolloPort;
	}

	/**
	 * @param apolloPort the apolloPort to set
	 */
	public static void setApolloPort(String apolloPort) {
		ConfigElements.apolloPort = apolloPort;
	}

	/**
	 * @return the libraryName
	 */
	public static String getLibraryName() {
		return libraryName;
	}

	/**
	 * @param libraryName the libraryName to set
	 */
	public static void setLibraryName(String libraryName) {
		ConfigElements.libraryName = libraryName;
	}
	
	public static void setAll(ProcurementServiceConfiguration configuration)
	{
		System.out.println("inside the setAll method");
		ConfigElements.apolloHost = configuration.getApolloHost();
		ConfigElements.apolloPassword = configuration.getApolloPassword();
		ConfigElements.apolloPort = configuration.getApolloPort();
		ConfigElements.apolloUser = configuration.getApolloUser();
		ConfigElements.stompQueueName = configuration.getStompQueueName();
		//ConfigElements.stompTopicName = configuration.getStompTopicName();
		ConfigElements.stompTopicBook = configuration.getStompTopicBook();
		ConfigElements.stompTopicComputer = configuration.getStompTopicComputer();
		System.out.println("All is set now");
	}

	/**
	 * @return the stompTopicComputer
	 */
	public static String getStompTopicComputer() {
		return stompTopicComputer;
	}

	/**
	 * @param stompTopicComputer the stompTopicComputer to set
	 */
	public static void setStompTopicComputer(String stompTopicComputer) {
		ConfigElements.stompTopicComputer = stompTopicComputer;
	}

	/**
	 * @return the stompTopicBook
	 */
	public static String getStompTopicBook() {
		return stompTopicBook;
	}

	/**
	 * @param stompTopicBook the stompTopicBook to set
	 */
	public static void setStompTopicBook(String stompTopicBook) {
		ConfigElements.stompTopicBook = stompTopicBook;
	}
}
