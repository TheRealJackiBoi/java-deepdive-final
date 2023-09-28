# Coding standards

Curly braces on put on same line as the statement.

Database names use underscore "_" instead of spaces.
We use CamelCase in all of our Java classes.
Test all relevant methods.
We use Lombok annotations for our entity classes.

Utilize whitespace to make code more readable.
Write comments for any convoluted code.

### Test-specific
We persist new objects in #BeforeEach. Set up database connection in #BeforeAll.
Truncate table in #AfterEach. Close connection in #AfterAll.