@echo off

IF NOT DEFINED GAIA_AGENT_HOME SET GAIA_AGENT_HOME=%CD%

ECHO Using %GAIA_AGENT_HOME% as GAIA_AGENT_HOME

java -jar %GAIA_AGENT_HOME%/lib/gaia-agent.jar
