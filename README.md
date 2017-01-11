[![Build Status](https://travis-ci.org/R2RML-api/R2RML-api.svg?branch=master)](https://travis-ci.org/R2RML-api)

R2RML-api
=========

## How to use R2RML-api as a Maven dependency

* put the following fragments into your `pom.xml`

```xml        
    <dependencies>
		<!-- Optique R2RML API -->
		<dependency>
			<groupId>eu.optique-project</groupId>
			<artifactId>r2rml-api-core</artifactId>
			<version>0.5.0</version>
		</dependency>

        <!-- Optique R2RML API RDF4J Binding -->
		<dependency>
			<groupId>eu.optique-project</groupId>
			<artifactId>r2rml-api-rdf4j-binding</artifactId>
			<version>0.5.0</version>
		</dependency>

        <!-- Optique R2RML API Jena Binding -->
        <dependency>
			<groupId>eu.optique-project</groupId>
			<artifactId>r2rml-api-jena-binding</artifactId>
			<version>0.5.0</version>
		</dependency>
	</dependencies>
```

## Reference

[1] "An R2RML Mapping Management API in Java", Marius Strandhaug, Master’s Thesis Spring 2014, University of Oslo.

[2] The javadoc documentation of the API v0.1 can be found by following this URL: http://folk.uio.no/marstran/doc/.

