# Magic-Card
Project developed by implementing a card game according to the established requirements, using Socket web 
communication and http requests, enabling the implementation of asynchronous
responses according to the server's processing time.

The chain of responsability pattern was used as a basis to implement the possible movements, so when new ones 
arise, just include them in the chain and if you need to execute more than one different movement just execute 
the chain passing the types of movement.
A diagram was made to exemplify the model used

# Diagram

![alt text](https://github.com/Natanlsr/Magic-Card/blob/feature/asyncAndWebsockets/images/diagrama.jpeg)

# Execute
To run the application it is necessary to use maven and docker

## Docker
In the docker folder run: $ docker-compose up
