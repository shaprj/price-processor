# Price processor

### Description

We have 10 subscribers for test purposes.

Half of them spend 30 seconds for processing of event. 

The others do it immediately.

ResultsMonitor object counts whether all of subscribers handle each curcur event.

The whole number of events for testing is 21. 

This number consists of both fast and slow events.

Fast events are produced in every 5 seconds. The slow events are produces in every 30 seconds.

This test confirms that all generated events are delivered to all subscribers.
