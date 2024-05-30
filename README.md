# AGiXT Java SDK

The AGiXT Java SDK is a lightweight library that provides a simple interface for interacting with the AGiXT platform from your Java applications.

## Usage

Here's an example of how to use the AGiXT Java SDK:

```java
import com.agixtsdk.AGiXTSDK;

public class Example {
    public static void main(String[] args) {
        // Create an instance of the AGiXTSDK with your API key
        AGiXTSDK sdk = new AGiXTSDK("http://localhost:7437", "your_api_key");

        // Get a list of available providers
        List<String> providers = sdk.getProviders();
        System.out.println("Providers: " + providers);

        // Get a list of providers for a specific service
        List<String> providersByService = sdk.getProvidersByService("myservice");
        System.out.println("Providers by service: " + providersByService);

        // Get the settings for a specific provider
        Map<String, Object> providerSettings = sdk.getProviderSettings("myprovider");
        System.out.println("Provider settings: " + providerSettings);

        // Get a list of agents
        List<Map<String, Object>> agents = sdk.getAgents();
        for (Map<String, Object> agent : agents) {
            System.out.println("Agent: " + agent);
        }
    }
}
```

## API Reference

The AGiXT Java SDK provides the following methods:

- `getProviders()`: Retrieves a list of available providers.
- `getProvidersByService(String service)`: Retrieves a list of providers for a specific service.
- `getProviderSettings(String providerName)`: Retrieves the settings for a specific provider.
- `getAgents()`: Retrieves a list of agents.

## Error Handling

The AGiXT Java SDK uses a simple error handling mechanism. If an exception occurs during the execution of any of the SDK methods, the SDK will print the error message to the console and throw a `RuntimeException` with the error message.

## Getting Started

To use the AGiXT Java SDK, you'll need to have the following dependencies in your project:

- `org.apache.httpcomponents:httpclient`
- `com.fasterxml.jackson.core:jackson-databind`
- `com.fasterxml.jackson.datatype:jackson-datatype-jsr310`

You can add these dependencies to your project's `pom.xml` file or your build tool of choice.

Once you have the dependencies set up, you can create an instance of the `AGiXTSDK` class and start using the provided methods.