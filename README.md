# JBomberman [![Build Status](https://travis-ci.org/HSR-SE2Proj/JBomberman.svg?branch=master)](https://travis-ci.org/HSR-SE2Proj/JBomberman) [![Coverage Status](https://coveralls.io/repos/github/HSR-SE2Proj/JBomberman/badge.svg?branch=master)](https://coveralls.io/github/HSR-SE2Proj/JBomberman?branch=master)

It's a Bomberman Clone, which can be played by up to 4 Players.

![Playground](https://cloud.githubusercontent.com/assets/1950155/9037647/ae9253c6-39ed-11e5-85fe-2f7eba8ec1d1.png)

##Rules

A Game goes over 3 Rounds and the Bomberman, who got the highest score wins.

##Latest Release

<a href="https://github.com/silvanadrian/jbomberman/releases/latest">JBomberman Client and Server</a>

##Server as Docker Container
You will find the Dockerfile here:
<a href="https://github.com/silvanadrian/jbomb-docker">jbomb-docker</a>

or from Docker Hub (or directly run it):

    docker pull silvanadrian/jbomb-docker

And start the container:

    docker run -d -p 5672:5672 -p 15672:15672 silvanadrian/jbomb-docker
