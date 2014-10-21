class GroovyResourceHelper {

    private static String baseRestResPath = "/rest/jbehave-for-jira/1.0/groovy-client/";

    public static <T> T loadGroovyResource(String jiraUrl, String resourcePath,
                                           String username, String password, String className, Object[] constructorArgs) {
        return loadGroovyResource(jiraUrl, baseRestResPath, resourcePath, username, password, className, constructorArgs);
    }

    private static <T> T loadGroovyResource(String jiraUrl, String restResBasePath, String resourcePath,
                                           String username, String password, String className, Object[] constructorArgs) {

        URI jiraSearchUrl = null;
        try {
            String fullPath = jiraUrl + restResBasePath + resourcePath;
            fullPath += "?os_username=" + username + "&os_password=" + password;
            jiraSearchUrl = new URI(fullPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Client client = Client.create();
        ClientResponse response = client
                .resource(jiraSearchUrl)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        if (response.getStatus() != 200) {

            int status = response.getStatus();
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Error occurred while trying to load groovy resource from Jira. " +
                    "Response status was - " + status + ", status info - " + statusInfo);

        } else {
            String resString = response.getEntity(String.class);

            GroovyClassLoader gcl = new GroovyClassLoader();
            Class clazz = gcl.parseClass(resString, className);
            Object instance = null;
            try {

                Class<?>[] argTypes = new Class<?>[constructorArgs.length];
                for (int i = 0; i < constructorArgs.length; i++) {
                    Object constructorArg = constructorArgs[i];
                    Class<?> argType = constructorArg.getClass();
                    argTypes[i] = argType;
                }
                Constructor constructor = clazz.getConstructor(argTypes);
                instance = constructor.newInstance(constructorArgs);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            T typedInstance = (T) instance;
            return typedInstance;
        }
    }

}

        <!--<dependency>-->
            <!--<groupId>org.codehaus.groovy</groupId>-->
            <!--<artifactId>groovy-all</artifactId>-->
            <!--<version>2.3.6</version>-->
        <!--</dependency>-->
