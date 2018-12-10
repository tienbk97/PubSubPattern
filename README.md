## Overview
 
Using Publish Subcribe pattern to implement a message passing application where
1. Publisher can send messages to multiple topics.
2. Subscriber can subscribe and receive messages from multiple topics.
3. Broker is the one handle receiving from Publishers & broadcasting messages to the correct Subscribers


## How it works
After cloning the repo, run simultaneously Receiver.java and Sender.java (save port 6789) 

## Receiver

1. Subscribe 
2. Unsubscribe 

Pick one of the options above: 1 \
Subscribe to topic: vCS 
 
==>> Received message from topic [vCS] Heyyyyy <<==

## Sender

Input message: Heyyyyy \
Input topic: vCS \
Send to topic [vCS] "Heyyyyy"? (Y/n): y \
Sent to topic [vCS] "Heyyyyy" \
Do you want to continue? (Y/n): n 
