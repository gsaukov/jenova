This is simple demonstration of the concept (having security implemented in a single layer API gateway so that other modules do not have to handle, but this is automatically provided as a service to them by default).

As said, this is a demo, so you will find a lot of missing things (e.g. no javadoc, no tests, etc.). I simply wanted to learn the internals of sprint security 5.0 and I used this as means to play around. Feel free to ping me if you want more info on this.

The whole ecosystem consists of 6 modules:
1. hyperion: spring boot admin (this is similar to Nagios we have)
2. earthrise: spring cloud discovery server
3. justitia: spring cloud auth. server (oauth2 server)
4. gatekeeper: spring cloud gateway (layer where common concerns like security, auditing, etc. are being handled)
5. omnidrive: resource server (any of our modules with business functions)
5. exodus: resource server (any of our modules with business functions)