This is a simple Spring Boot application that interacts with the GitHub API 
to list all repositories of a given user that are not forks. For each repository, 
it also lists all branches and the last commit SHA of each branch.

You can do this in two possible ways: by using a web interface by visiting localhost:8998
or by using GET requests to localhost:8998/{userName}/repos