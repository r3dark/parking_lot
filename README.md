# Parking Lot Application
This is a parking lot application which will 
setup a parking lot based on the capacity
and performs set of instructions entered by the
user.

## Getting started
These instructions will get you a copy of 
the project up and running on your local 
machine for development and testing purposes.

### Prerequisites
This project was designed and developed 
using following libraries and tools.
To run this application successfully and
avoid incompatibility issues, please 
install / update them to at least the 
versions mentioned.
- Java 1.8
- Gradle 5.5.1
- Junit 4.12
- Git 2.21.0

### Setup
To setup the project, open terminal at root 
of project and execute : 

```
./bin/setup.sh
```

This will build the application with dependencies, 
run the test suite and get it ready for execution.

### Execute / Run
This project can be run in one of the 
following two ways :

- To run the application with _**commands 
specified in a file**_, open terminal at 
root of project and execute : 

```
./bin/parking_lot.sh {input_file_path}
```

- There is a sample test input file already present 
in bin directory. To run application with that, 
open terminal at root of project and execute : 

```
./bin/parking_lot.sh file_inputs.txt
```

- To run the application in _**interactive mode**_, 
open terminal at root of project and execute : 

```
./bin/parking_lot.sh
```

