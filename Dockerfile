# Use a base image with Java 8 and Maven pre-installed
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY *.xml .
COPY src ./src

# Download dependencies and build the project
RUN mvn dependency:go-offline

# Run the tests
CMD ["mvn", "clean", "test"]