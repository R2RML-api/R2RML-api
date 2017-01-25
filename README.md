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


## Release history

<a name="v0.5.0"></a>
### 13 January, 2017 ::  Version 0.5.0 
* A major rewriting using commons-rdf 
* retired owlapi-binding 

<a name="v0.4.0"></a>
### 3 January, 2017 ::  Version 0.4.0 
* Upgrade Sesame to RDF4J 2.1.4  

<a name="v0.3.0"></a>
### 26 Feb, 2016 ::  Version 0.3.0 
* Upgrade Jena to v3

<a name="v0.2.1"></a>
### 23 Feb, 2016 ::  Version 0.2.1 
* Fix an issue of deploying `r2rml-api-jena-bridge` to central repository

<a name="v0.2.0"></a>
### 23 Feb, 2016 ::  Version 0.2.0
* Upgrade OWL-API to v4
* Deployed to central maven repository

<a name="v0.1.0"></a>
### 2014 ::  Version 0.1.0
* First release

## Reference

[1] "An R2RML Mapping Management API in Java", Marius Strandhaug, Master’s Thesis Spring 2014, University of Oslo.

[2] The javadoc documentation of the API v0.1 can be found by following this URL: http://folk.uio.no/marstran/doc/.

