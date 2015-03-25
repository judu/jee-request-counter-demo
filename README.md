# JEE Demonstration

This simple JEE project demontsrates a web app using JAX-RS, CDI, running on Glassfish 4, that
stores simple representations of requests in a postgresql database.

## How do I test it?

Just clone the repo, build it and deploy it on a glassfish 4 Full Platform (not Web
Profile).

The default configuration tries to connect to the following postgresql database:
`postgresql://blackpool:blackpool@localhost:5432/blackpool`

```bash
-$ git clone git://github.com/judu/jee-request-counter-demo.git
-$ cd jee-request-counter-demo
-$ mvn package
-$ {glassfish_home}/bin/asadmin start-domain
 Waiting for domain1 to start ....
 Successfully started the domain : domain1
 domain  Location: {glassfish_home}/glassfish/domains/domain1
 Log File: {glassfish_home}/glassfish/domains/domain1/logs/server.log
 Admin Port: 4848
 Command start-domain executed successfully.

-$ {glassfish_home}/bin/asadmin deploy --name "request-counter-demo" --context "/foobar" target/demo-jee.war
 Application deployed with name request-counter-demo.
 Command deploy executed successfully.
```

## How do I change the database config?

Just edit the `src/main/webapp/WEB-INF/glassfish-resources.xml` file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE resources PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Resource Definitions//EN" "http://glassfish.org/dtds/glassfish-resources_1_5.dtd">
<resources>
	<jdbc-connection-pool name="java:app/jdbc/blackpool" res-type="javax.sql.DataSource"
	datasource-classname="org.postgresql.ds.PGPoolingDataSource"
	pool-resize-quantity="1" max-pool-size="5" steady-pool-size="0"
	statement-timeout-in-seconds="60" >
		<property name="serverName" value="<PG_HOST>" />
		<property name="portNumber" value="<PG_PORT>" />
		<property name="dataBaseName" value="<PG_DATABASE_NAME>" />
		<property name="User" value="<PG_USER>" />
		<property name="Password" value="<PG_PASSWORD>" />
		<property name="connectionAttributes" value=";create=true" />
		<property name="driverType" value="4" />
 </jdbc-connection-pool>
 
 <jdbc-resource jndi-name="java:app/jdbc/blackpool-ds" pool-name="java:app/jdbc/blackpool" />
</resources>
```


And change the <PG_*> values with you configuration values. AFAIK, there is no way to put
environment variables names in there :(


## How do I deploy it on Clever Cloud?

Pretty easy too:

  - Provision a free PostgreSQL Add-on
  - Update your database config with values from the add-on configuration tab
  - Create a `clevercloud` folder at the root of your app, and write a `war.json` file in
      it:

```json
{
   "build": {
      "type": "maven",
      "goal": "package"
   },
   "deploy": {
      "container":"GLASSFISH4",
      "war":[{ "file": "target/demo-jee.war", "context": "/"}],
      "pingPaths":["/application.wadl"]
   }
}
```

The `"build"` object configures a maven build of the app.

If you check the pom.xml, you will see the node `<finalName>demo-jee</finalName>`, and the node `<packaging>war</packaging>`.
This means that the app will be built in `target/demo-jee.war`. So, we need to configure
the deployment to use the `target/demo-jee.war` file. In the example above, we deploy it
under the `/` context. That means your application will be accessible
under the "http://yourapp.com/" URI.

  - Create an application on Clever Cloud, choose the WAR/EAR type.
  - Add the given git repository to your remotes
  - Commit your `clevercloud` folder and your `glassfish-resources.xml` file, and push :)
  - Wait about 2min30, and you can test your app!
