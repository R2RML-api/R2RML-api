[![Build Status](https://travis-ci.org/R2RML-api/R2RML-api.svg?branch=master)](https://travis-ci.org/R2RML-api)



R2RML-api
=========

## How to use R2RML-api as a Maven dependency

* put the following fragments into your `pom.xml`

```xml        
    </repositories>
        <repository>
			<!-- for R2RML api -->
			<id>bolzano-nexus-public</id>
			<url>http://obdavm.inf.unibz.it:8080/nexus/content/groups/public/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
	
	<dependencies>
		<!-- Optique R2RML API -->
		<dependency>
			<groupId>eu.optique-project</groupId>
			<artifactId>r2rml-api</artifactId>
			<version>0.2</version>
		</dependency>

		<dependency>
			<groupId>eu.optique-project</groupId>
			<artifactId>r2rml-api-sesame-bridge</artifactId>
			<version>0.2</version>
		</dependency>

	</dependencies>
```

## Reference

[1] "An R2RML Mapping Management API in Java", Marius Strandhaug, Master’s Thesis Spring 2014, University of Oslo.

[2] The javadoc documentation of the API v0.1 can be found by following this URL: http://folk.uio.no/marstran/doc/.

