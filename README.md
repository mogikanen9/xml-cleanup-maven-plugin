# xml-cleanup-maven-plugin
Maven plugin which can remove specified tags from xml file(s)

[![Build Status](https://travis-ci.org/mogikanen9/xml-cleanup-maven-plugin.svg?branch=master)](https://travis-ci.org/mogikanen9/xml-cleanup-maven-plugin)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.mogikanen9.maven.plugins/xml-cleanup-maven-plugin.svg?label=Maven%20Central)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.github.mogikanen9.maven.plugins%22%20AND%20a%3A%22xml-cleanup-maven-plugin%22)

[![GitHub release](https://img.shields.io/github/release/mogikanen9/xml-cleanup-maven-plugin.svg)](https://github.com/mogikanen9/xml-cleanup-maven-plugin/releases/tag/1.0)

![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.mogikanen9.maven.plugins%3Axml-cleanup-maven-plugin-all&metric=alert_status)

## Project description
 * plugin - core source code of the plugin
 * test - test/demo projects which shows how to use the plugin

## Module "plugin"

### logging
 * folder /scr/plugin-logging contains a sample configuration of [SimpleLogging](https://www.slf4j.org/apidocs/org/slf4j/impl/SimpleLogger.html) for the plugin
 * file "simplelogger.properties" needs to be placed to "${maven.home}/conf/logging/" folder to enable custom logging for the plugin
 * general details regarding logging available at [maven-logging](https://maven.apache.org/maven-logging.html)
